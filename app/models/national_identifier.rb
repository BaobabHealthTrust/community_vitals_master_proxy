class NationalIdentifier < ActiveRecord::Base
  has_one :person, :class_name => "Person", :foreign_key => "national_id"
end
