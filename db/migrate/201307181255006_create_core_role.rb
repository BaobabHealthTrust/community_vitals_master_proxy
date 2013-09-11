class CreateCoreRole < ActiveRecord::Migration
	def self.up		
		
      create_table "role", :id => false, :force => true do |t|
        t.string "role", :limit => 250
        t.string "description",               :default => "", :null => false
        t.integer "vocabulary_id"
        t.string "uuid",        :limit => 38,                 :null => false
      end

      add_index "role", ["uuid"], :name => "role_uuid_index", :unique => true

	end

	def self.down
			drop_table "role"
	end
end
