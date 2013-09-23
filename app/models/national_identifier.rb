class NationalIdentifier < ActiveRecord::Base
  has_one :person, :class_name => "Person", :foreign_key => "national_id"
  
  def self.threshold
    
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    mode = settings["dde_mode"] rescue nil
    
    threshold = settings["dde_#{mode}"]["threshold"] rescue 0
    
  end
  
  def self.available_ids
    
    result = 0
    
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    mode = settings["dde_mode"] rescue nil
    
    result = NationalIdentifier.all(:conditions => ["COALESCE(person_id, 0) = 0"]).length if mode.to_s.downcase == "vh"
    
    result
    
  end
  
end
