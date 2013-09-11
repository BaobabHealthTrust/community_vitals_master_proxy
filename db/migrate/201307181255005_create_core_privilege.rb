class CreateCorePrivilege < ActiveRecord::Migration
	def self.up		
		
      create_table "privilege", :id => false, :force => true do |t|
        t.string "privilege", :limit => 250
        t.string "description", :limit => 250, :default => "", :null => false
        t.string "uuid",        :limit => 38,                  :null => false
      end

      add_index "privilege", ["uuid"], :name => "privilege_uuid_index", :unique => true

	end

	def self.down
			drop_table "privilege"
	end
end
