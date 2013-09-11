require "composite_primary_keys"
class CoreRolePrivilege < ActiveRecord::Base
  set_table_name "role_privilege"
  set_primary_keys :privilege, :role

  include CoreOpenmrs
  belongs_to :role, :class_name => "CoreRole", :foreign_key => :role
  belongs_to :privilege, :class_name => "CorePrivilege", :foreign_key => :privilege
end


### Original SQL Definition for role_privilege #### 
#   `role_id` int(11) NOT NULL ,
#   `privilege_id` int(11) NOT NULL ,
#   PRIMARY KEY  (`privilege_id`,`role_id`),
#   KEY `role_privilege` (`role_id`),
#   CONSTRAINT `privilege_definitons` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`privilege_id`),
#   CONSTRAINT `role_privilege` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
