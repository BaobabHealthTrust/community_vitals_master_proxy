class Person < ActiveRecord::Base
  has_one :identifier, :class_name => "NationalIdentifier", :foreign_key => :person_id
  
  def age
    (Date.today - self.birthdate).to_i / 365 rescue "?"
  end
  
end
