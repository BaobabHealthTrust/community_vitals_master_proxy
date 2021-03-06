class LocationController < ApplicationController
  def region
    region_conditions = ["name LIKE (?)", "#{params[:value]}%"]

    regions = Region.find(:all,:conditions => region_conditions, :order => 'region_id')
    regions = regions.map do |r|
      if r.name != "Foreign"
        "<li value='#{r.name}'>#{Vocabulary.search(r.name)}</li>"
      end
    end
    render :text => regions.join('')  and return
  end

  def district
    region_id = Region.find_by_name("#{params[:filter_value]}").id
    region_conditions = ["name LIKE (?) AND region_id = ? ", "#{params[:search_string]}%", region_id]

    districts = District.find(:all,:conditions => region_conditions, :order => 'name')
    districts = districts.map do |d|
      "<li value='#{d.name}'>#{d.name}</li>"
    end
    render :text => districts.join('') and return

  end

  def traditional_authority
    district_id = District.find_by_name("#{params[:filter_value]}").id
    traditional_authority_conditions = ["name LIKE (?) AND district_id = ?", "%#{params[:search_string]}%", district_id]

    traditional_authorities = TraditionalAuthority.find(:all,:conditions => traditional_authority_conditions, :order => 'name')
    traditional_authorities = traditional_authorities.map do |t_a|
      "<li value='#{t_a.name}'>#{t_a.name}</li>"
    end
    render :text => traditional_authorities.join('') and return
  end

  def village
    traditional_authority_id = TraditionalAuthority.find_by_name("#{params[:filter_value]}").id
    village_conditions = ["name LIKE (?) AND traditional_authority_id = ?", "%#{params[:search_string]}%", traditional_authority_id]

    villages = Village.find(:all,:conditions => village_conditions, :order => 'name')
    villages = villages.map do |v|
      "<li value='" + v.name + "'>" + v.name + "</li>"
    end
    render :text => villages.join('') + "<li value='Other'>Other</li>" and return
  end

  def facilities
    facilities = Location.find(:all, :conditions => ["name LIKE ? AND retired = ?", "%#{params[:search_string]}%", false],
                               :order => 'name').map do |f|
      "<li value='" + f.name + "'>" + f.name + "</li>"
    end

    render :text => facilities.join('') and return
  end

  def birth_village

    village_conditions = ["name LIKE (?) AND retired = ?", "%#{params[:search_string]}%", false]

    villages = Village.find(:all,:conditions => village_conditions, :order => 'name', :limit => 50)
    villages = villages.map do |v|
      "<li value='" + v.name + "'>" + v.name + "</li>"
    end
    render :text => villages.join('') + "<li value='Other'>Other</li>" and return
  end
end
