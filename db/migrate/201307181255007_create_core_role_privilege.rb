class CreateCoreRolePrivilege < ActiveRecord::Migration
	def self.up		
		
      create_table "role_privilege", :id => false, :force => true do |t|
        t.string "role",      :limit => 50, :default => "", :null => false
        t.string "privilege", :limit => 50, :default => "", :null => false
      end

      add_index "role_privilege", ["role"], :name => "role_privilege"

	end

	def self.down
			drop_table "role_privilege"
	end
end
