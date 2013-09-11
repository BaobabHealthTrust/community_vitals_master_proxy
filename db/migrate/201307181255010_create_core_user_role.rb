class CreateCoreUserRole < ActiveRecord::Migration
	def self.up		
		
      create_table "user_role", :id => false, :force => true do |t|
        t.integer "user_id",               :default => 0,  :null => false
        t.string  "role",    :limit => 50, :default => "", :null => false
      end

      add_index "user_role", ["user_id"], :name => "user_role"

	end

	def self.down
			drop_table "user_role"
	end
end
