require "composite_primary_keys"
class CoreUserProperty < ActiveRecord::Base
  set_table_name "user_property"
  set_primary_keys :user_id, :property

  include CoreOpenmrs
  belongs_to :user, :class_name => "CoreUser", :foreign_key => :user_id
end


### Original SQL Definition for user_property #### 
#   `user_id` int(11) NOT NULL default '0',
#   `property` varchar(100) NOT NULL default '',
#   `property_value` varchar(255) NOT NULL default '',
#   PRIMARY KEY  (`user_id`,`property`),
#   CONSTRAINT `user_property` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
