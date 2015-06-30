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
        :conditions => ["site_id = ? AND COALESCE(allocated_gvh,0) = 0 AND COALESCE(allocated_vh,0) = 0 AND allocated_to_vh = 0",
        params[:site_code]]).each{|id|

        result << id.identifier
        id.update_attributes({:allocated_gvh => params[:gvh],
                              :allocated_vh => params[:vh],
                              :allocated_to_vh => 1
                             })

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
    settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_ta"]
    site_code = settings["site_code"]
    (records || {}).each do |pnid, details|

      ActiveRecord::Base.transaction do

        nat_id = NationalIdentifier.find_by_identifier(pnid) rescue nil

        if nat_id.blank?

            nat_id = NationalIdentifier.create({:identifier => pnid.upcase,
                                                :site_id => site_code,
                                                :assigned_gvh => records[pnid]["person_details"]['gvh'],
                                                :assigned_vh => records[pnid]["person_details"]['village'],
                                                :requested_by_gvh => 1,
                                                :requested_by_vh => 1
                                              })
        end
        outcome = (records[pnid]["person_details"]['outcome'].upcase == "NULL" ? nil : records[pnid]["person_details"]['outcome']) rescue nil
        if nat_id.person_id.blank?
          person = Person.create(
              :national_id => nat_id.id,
              :given_name => records[pnid]["person_details"]['fname'],
              :middle_name => records[pnid]["person_details"]['middle_name'],
              :family_name => records[pnid]["person_details"]['lname'],
              :gender => records[pnid]["person_details"]['gender'],
              :birthdate => records[pnid]["person_details"]['dob'],
              :birthdate_estimated => records[pnid]["person_details"]['dob_estimated'],
              :outcome => outcome,
              :outcome_date =>  records[pnid]["person_details"]['outcome_date'],
              :village => records[pnid]["person_details"]['village'],
              :gvh => records[pnid]["person_details"]['gvh'],
              :ta => records[pnid]["person_details"]['ta']
          )
          nat_id.update_attributes(:person_id => person.id, :assigned_at => records[pnid]["person_details"]['assigned_at'])
        else
          nat_id.person.update_attributes(
              :given_name => records[pnid]["person_details"]['fname'],
              :middle_name => records[pnid]["person_details"]['middle_name'],
              :family_name => records[pnid]["person_details"]['lname'],
              :gender => records[pnid]["person_details"]['gender'],
              :birthdate => records[pnid]["person_details"]['dob'],
              :birthdate_estimated => records[pnid]["person_details"]['dob_estimated'],
              :outcome => outcome,
              :outcome_date =>  records[pnid]["person_details"]['outcome_date'],
              :village => records[pnid]["person_details"]['village'],
              :gvh => records[pnid]["person_details"]['gvh'],
              :ta => records[pnid]["person_details"]['ta']
          )
          person = nat_id.person
        end

          (records[pnid]["relationships"] || []).each do |relationship|
            Relationship.create(
                :person_national_id => relationship['person'],
                :relation_national_id => relationship['relative'],
                :person_is_to_relation => relationship['relationship'].to_i
            ) rescue nil
          end

          (records[pnid]["outcomes"] || []).each do |outcome|
            Outcome.create(
              :person_id => person.id,
              :outcome_type => outcome["outcome"],
              :outcome_date => outcome["outcome_date"]
            )
          end

        ids << pnid
      end
    end
    render :text => ids.to_json
  end

  def gvh_request_demographics

    unsent_demographics = NationalIdentifier.find(:all, :conditions => ['person_id IS NOT NULL AND site_id = ? AND
                                                      requested_by_gvh = 1 AND posted_by_gvh = 0 AND voided = 0
                                                      AND assigned_gvh = ?', params[:site_code], params[:gvh]])

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
          'ta' => person.ta,
          'assigned_at' => identifier.assigned_at
      }

      outcomes = []
      (person.new_outcomes("GVH") || []).each do |outcome|

        person_outcome = {
            'outcome'=> outcome.outcome_type,
            'outcome_date' => outcome.outcome_date
        }

        outcomes << person_outcome
      end

      relationships = []
      (person.new_relationships("GVH") || []).each do |relation|

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

  def gvh_acknowledge_demographics

    ids = JSON.parse(params[:ids])

    (ids["acknowledged"] || []).each do |id|
      nat_id = NationalIdentifier.find_by_identifier(id)
      nat_id.update_attributes({:posted_by_gvh => 1 })
      (nat_id.person.new_outcomes("GVH") || []).each do |x|
        x.update_attributes(:posted_by_gvh => 1)
      end
      (nat_id.person.new_relationships("GVH") || []).each do |x|
        x.update_attributes(:posted_by_gvh => 1)
      end
    end

    render :text => "Successfully done"
  end

  def sync_village_demographics
    
    to_post = {}

    unless params[:ids].nil? or params[:site].nil? or params[:vh].nil? or params[:gvh].nil?
    
        existing_people = NationalIdentifier.find(:all, :limit => 10, :conditions => 
            ['person_id IS NOT NULL AND voided = 0 AND NOT identifier IN (?) AND ' + 
            'assigned_gvh = ? AND assigned_vh = ? AND site_id = ?', 
            (JSON.parse(params[:ids]) rescue ""), params[:gvh], params[:vh], params[:site]])

        (existing_people || []).each do |identifier|

          person = identifier.person
          person_details = {
            'fname' => person.given_name,
            'middle_name' => person.middle_name,
            'lname' => person.family_name,
            'gender' => person.gender,
            'dob' => person.birthdate,
            'dob_estimated' => person.birthdate_estimated,
            'outcome' => person.outcome,
            'outcome_date' => person.outcome_date,
            'village' => person.village,
            'gvh' => person.gvh,
            'ta' => person.ta,
            'assigned_at' => identifier.assigned_at
          }

          identifier_details = {"created_at"=> identifier.created_at,
                                 "voided"=> identifier.voided,
                                 "post_vh_notified"=> identifier.post_vh_notified,
                                 "posted_by_gvh"=> identifier.posted_by_gvh,
                                 "request_gvh_notified"=> identifier.request_gvh_notified,
                                 "assigned_at"=> identifier.assigned_at,
                                 "posted_by_vh"=> 1,
                                 "request_vh_notified"=> identifier.request_vh_notified,
                                 "post_gvh_notified"=> identifier.post_gvh_notified,
                                 "void_reason"=> identifier.void_reason,
                                 "requested_by_vh"=> identifier.requested_by_vh,
                                 "assigned_gvh"=> identifier.assigned_gvh,
                                 "site_id"=> identifier.site_id,
                                 "updated_at"=> identifier.updated_at,
                                 "requested_by_gvh"=> identifier.requested_by_gvh,
                                 "assigned_vh"=> identifier.assigned_vh,
                                 "identifier"=> identifier.identifier,
                                 "date_voided"=> identifier.date_voided,
                                 "posted_by_ta"=> identifier.posted_by_ta,
                                 "person_id"=> identifier.person_id
                               }

          outcomes = []
          (person.new_outcomes("VH") || []).each do |outcome|

             person_outcome = {
                'outcome'=> outcome.outcome_type,
                'outcome_date' => outcome.outcome_date
             }

            outcomes << person_outcome
          end

          relationships = []
          (person.new_relationships("VH") || []).each do |relation|

            relative = {
                'person' => relation.person_national_id,
                'relative' => relation.relation_national_id,
                'relationship' => relation.person_is_to_relation
            }
            relationships << relative
          end

          to_post[identifier.identifier] = { 'person_details' => person_details, 
              'relationships' => relationships, 'outcomes' => outcomes, 
              'identifier_details' => identifier_details 
          }

        end
        
    end

    render :text => to_post.to_json
        
  end

  def sync_gvh_demographics
    
    to_post = {}

    unless params[:ids].nil? or params[:site].nil? or params[:gvh].nil?
    
        existing_people = NationalIdentifier.find(:all, :limit => 10, :conditions => 
            ['person_id IS NOT NULL AND COALESCE(voided,0) = 0 AND identifier NOT IN (?) AND ' +
            'assigned_gvh = ? AND site_id = ?', 
            (params[:ids]), params[:gvh], params[:site]])

        (existing_people || []).each do |identifier|

          person = identifier.person
          person_details = {
            'fname' => person.given_name,
            'middle_name' => person.middle_name,
            'lname' => person.family_name,
            'gender' => person.gender,
            'dob' => person.birthdate,
            'dob_estimated' => person.birthdate_estimated,
            'outcome' => person.outcome,
            'outcome_date' => person.outcome_date,
            'village' => person.village,
            'gvh' => person.gvh,
            'ta' => person.ta,
            'assigned_at' => identifier.assigned_at
          }

          identifier_details = {"created_at"=> identifier.created_at,
                                 "voided"=> identifier.voided,
                                 "post_vh_notified"=> identifier.post_vh_notified,
                                 "posted_by_gvh"=> identifier.posted_by_gvh,
                                 "request_gvh_notified"=> identifier.request_gvh_notified,
                                 "assigned_at"=> identifier.assigned_at,
                                 "posted_by_vh"=> identifier.posted_by_vh,
                                 "request_vh_notified"=> identifier.request_vh_notified,
                                 "post_gvh_notified"=> identifier.post_gvh_notified,
                                 "void_reason"=> identifier.void_reason,
                                 "requested_by_vh"=> identifier.requested_by_vh,
                                 "assigned_gvh"=> identifier.assigned_gvh,
                                 "site_id"=> identifier.site_id,
                                 "updated_at"=> identifier.updated_at,
                                 "requested_by_gvh"=> identifier.requested_by_gvh,
                                 "assigned_vh"=> identifier.assigned_vh,
                                 "identifier"=> identifier.identifier,
                                 "date_voided"=> identifier.date_voided,
                                 "posted_by_ta"=> identifier.posted_by_ta,
                                 "person_id"=> identifier.person_id
                               }

          outcomes = []
          (person.new_outcomes("VH") || []).each do |outcome|

             person_outcome = {
                'outcome'=> outcome.outcome_type,
                'outcome_date' => outcome.outcome_date
             }

            outcomes << person_outcome
          end

          relationships = []
          (person.new_relationships("VH") || []).each do |relation|

            relative = {
                'person' => relation.person_national_id,
                'relative' => relation.relation_national_id,
                'relationship' => relation.person_is_to_relation
            }
            relationships << relative
          end

          to_post[identifier.identifier] = { 'person_details' => person_details, 
              'relationships' => relationships, 'outcomes' => outcomes, 
              'identifier_details' => identifier_details 
          }

        end
        
    end

    render :text => to_post.to_json
        
  end

  def update_gvh_demographics
  
      result = []

      post = params[:details]
  
      (JSON.parse(post) || []).each do |id|
        
        nat_id = NationalIdentifier.find_by_identifier(id)
        
        nat_id.update_attributes({:posted_by_gvh => 1 })
        
        result << id
        
      end
  
      render :text => result.to_json
  
  end

end


