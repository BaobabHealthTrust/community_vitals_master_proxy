class AttributeType < ActiveRecord::Base
  set_primary_key :attribute_type_id
  set_table_name :attribute_type
  validates_presence_of :attribute
  default_scope :voided => false

end
