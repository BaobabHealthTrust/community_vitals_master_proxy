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

end
