class NationalIdentifiersController < ApplicationController

  def request_gvh_ids
    result = {}
    
    if !params[:site_code].nil? and !params[:gvh].nil? and !params[:count].nil?
    
      NationalIdentifier.all(:limit => params[:count], 
          :conditions => ["requested_by_gvh = 0 AND requested_by_vh = 1"]).each do |id|
        
        result[id[:identifier]] = {
          :site_id => id[:site_id],
          :assigned_gvh => id[:assigned_gvh],
          :assigned_vh => id[:assigned_vh],
          :requested_by_vh => id[:requested_by_vh]
        }
            
      end
    
    end
  
    render :text => result.to_json
  end

  def request_gvh_ids_ack
    result = "ERROR: Parameters"
    if !params[:ids].nil? && !params[:gvh].nil?
      ids = JSON.parse(params[:ids])
      
      ids.each do |id|
        NationalIdentifier.find_by_identifier(id).update_attributes({
          :requested_by_gvh => 1
        })
      end
      
      result = "OK"
    end
    render :text => result
  end

  def request_village_ids
    result = []
    
    if !params[:site_code].nil? and !params[:gvh].nil? and !params[:vh].nil? and !params[:count].nil?
    
      NationalIdentifier.all(:limit => params[:count],
        :conditions => ["site_id = ? AND requested_by_gvh = 0 AND requested_by_vh = 0", 
        params[:site_code]]).each{|id|

        result << id.identifier
        
      }
    
    end
  
    render :text => result.to_json
  end

  def request_village_ids_ack
    result = "ERROR: Parameters"
    if !params[:ids].nil? && !params[:gvh].nil? && !params[:vh].nil?
      ids = JSON.parse(params[:ids])
      
      ids.each do |id|
        NationalIdentifier.find_by_identifier(id).update_attributes({
          :assigned_gvh => params[:gvh],
          :assigned_vh => params[:vh],
          :requested_by_vh => 1
        })
      end
      
      result = "OK"
    end
    render :text => result
  end

  def manage_gvh_ids
  end

  def manage_village_ids
  end

  def manage_ta_ids
  end

  def receive_village_demographics

    ids = []
    records = JSON.parse(params[:details])
    (records || {}).each do |pnid, details|

      ActiveRecord::Base.transaction do

          person = Person.create(
              :national_id => NationalIdentifier.find_by_identifier(pnid).id,
              :given_name => records[pnid]["person_details"]['fname'],
              :middle_name => records[pnid]["person_details"]['middle_name'],
              :family_name => records[pnid]["person_details"]['lname'],
              :gender => records[pnid]["person_details"]['gender'],
              :birthdate => records[pnid]["person_details"]['dob'],
              :outcome => records[pnid]["person_details"]['outcome'],
              :outcome_date =>  records[pnid]["person_details"]['outcome_date'],
              :village => records[pnid]["person_details"]['village'],
              :gvh => records[pnid]["person_details"]['gvh'],
              :ta => records[pnid]["person_details"]['ta']
            )
          (records[pnid]["relationships"] || []).each do |relationship|
            Relationship.create(
                :person_national_id => relationship['person'],
                :relation_national_id => relationship['relative'],
                :person_is_to_relation => relationship['relationship']
            )
          end

          (records[pnid]["outcomes"] || []).each do |outcome|
            Outcome.create(
              :person_id => person.id,
              :outcome_type => outcome["outcome"],
              :outcome_date => outcome["outcome_date"]
            )
          end
        NationalIdentifier.find_by_identifier(pnid).update_attributes(:person_id => person.id)
        ids << pnid
      end
    end
    render :text => ids.to_json
  end

  def gvh_request_demographics

    unsent_demographics = NationalIdentifier.find(:all, :conditions => ['person_id IS NOT NULL AND site_id = ?
                                                      AND posted_by_gvh = 0 AND voided = 0 AND assigned_gvh = ?',
                                                      params[:site_code], params[:gvh]])

    to_post = {}
    (unsent_demographics || []).each do |identifier|
      person = identifier.person
      person_details = {
          'fname' => person.given_name,
          'middle_name' => person.middle_name,
          'lname' => person.family_name,
          'gender' => person.gender,
          'dob' => person.birthdate,
          'outcome' => person.outcome,
          'outcome_date' => person.outcome_date,
          'village' => person.village,
          'gvh' => person.gvh,
          'ta' => person.ta
      }

      outcomes = []
      (person.outcomes || []).each do |outcome|

        person_outcome = {
            'outcome'=> outcome.outcome_type,
            'outcome_date' => outcome.outcome_date
        }

        outcomes << person_outcome
      end

      relationships = []
      (person.relationships || []).each do |relation|

        relative = {
            'person' => relation.person_national_id,
            'relative' => relation.relation_national_id,
            'relationship' => relation.person_is_to_relation
        }
        relationships << relative
      end

      to_post[identifier.identifier] = { 'person_details' => person_details, 'relationships' => relationships, 'outcomes' => outcomes }

    end

    render :text => to_post.to_json

  end

end
