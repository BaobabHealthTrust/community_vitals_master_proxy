class PersonAttribute < ActiveRecord::Base
  set_primary_key :person_attribute_id
  set_table_name :person_attribute

  validates_presence_of :person_id
  validates_presence_of :creator
  validates_presence_of :attribute_type

  belongs_to :person, :conditions => {:voided => 0}
  belongs_to :attribute_type,:foreign_key => :attribute_type_id ,:conditions => {:voided => 0}
  default_scope :voided => false

  #before_save :add_creator


  def add_creator
      if self.creator.blank?
        self.creator = session[:user_id]
      end
  end
end
