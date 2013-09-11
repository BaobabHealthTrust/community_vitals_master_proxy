class CoreRole < ActiveRecord::Base
  set_table_name "role"
  set_primary_key "role"
  
  include CoreOpenmrs

  has_many :role_roles, :class_name => "CoreRoleRole", :foreign_key => :parent_role
  has_many :role_privileges, :class_name => "CoreRolePrivilege", :foreign_key => :role, :dependent => :delete_all
  has_many :privileges, :class_name => "CorePrivilege", :through => :role_privileges, :foreign_key => :role
  has_many :user_roles, :class_name => "CoreUserRole", :foreign_key => :role
#role_id

  def self.setup_privileges_for_roles
#    roles = ["provider", "superuser", "Clinician", "Nurse", "Pharmacist", "Registration Clerk", "Vitals Clerk", "Therapeutic Feeding Clerk"]
#    privileges = ["HIV First visit", "ART Visit", "Give drugs", "ART Transfer in", "HIV Staging", "HIV Reception", "Height/Weight", "Barcode scan", "Update outcome"]

    Role.find(:all).each{|r|
      Privilege.find(:all).each{|p|
        self.add_privilege(p)
      }
    }
    
  end

  def add_privilege(privilege)
    rp = RolePrivilege.new
    rp.role = self.role
    rp.privilege = privilege.privilege
    rp.save
  end


end


### Original SQL Definition for role #### 
#   `role_id` int(11) NOT NULL auto_increment,
#   `role` varchar(50) NOT NULL default '',
#   `description` varchar(255) NOT NULL default '',
#   PRIMARY KEY  (`role_id`)
