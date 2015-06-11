require "composite_primary_keys"
class CoreUserRole < ActiveRecord::Base
  set_table_name :user_role
  set_primary_keys :role, :user_id
  
  include CoreOpenmrs
  belongs_to :user, :class_name => 'CoreUser', :foreign_key => :user_id

  def self.distinct_roles
    CoreUserRole.find(:all, :select => "DISTINCT role")
  end
end
