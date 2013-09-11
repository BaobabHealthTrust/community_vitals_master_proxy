class CoreLocation < ActiveRecord::Base
  set_table_name "location"
  set_primary_key "location_id"
  include CoreOpenmrs

  cattr_accessor :current_location

  def site_id
    CoreLocation.current_health_center.location_id.to_s
  rescue 
    raise "The id for this location has not been set (#{CoreLocation.current_location.name}, #{CoreLocation.current_location.id})"
  end

  def children
    return [] if self.name.match(/ - /)
    CoreLocation.find(:all, :conditions => ["name LIKE ?","%" + self.name + " - %"])
  end

  def parent
    return nil unless self.name.match(/(.*) - /)
    CoreLocation.find_by_name($1)
  end

  def site_name
    self.name.gsub(/ -.*/,"")
  end

  def related_locations_including_self
    if self.parent
      return self.parent.children + [self]
    else
      return self.children + [self]
    end
  end

  def related_to_location?(location)
    self.site_name == location.site_name
  end

  def self.current_health_center
    @@current_health_center ||= CoreLocation.find(CoreGlobalProperty.find_by_property("current_health_center_id").property_value) rescue self.current_location
  end

  def self.current_arv_code
    current_health_center.neighborhood_cell rescue nil
  end
end
