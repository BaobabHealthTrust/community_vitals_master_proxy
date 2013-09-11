class CreateCoreUserProperty < ActiveRecord::Migration
	def self.up		
		
    create_table "user_property", :id => false, :force => true do |t|
      t.integer "user_id",                       :default => 0,  :null => false
      t.string  "property",       :limit => 100, :default => "", :null => false
      t.string  "property_value",                :default => "", :null => false
    end

	end

	def self.down
			drop_table "user_property"
	end
end
