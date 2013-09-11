class CreateCoreUser < ActiveRecord::Migration
	def self.up		
		
      create_table "users", :primary_key => "user_id", :force => true do |t|
        t.string   "system_id",       :limit => 50,  :default => "", :null => false
        t.string   "username",        :limit => 50
        t.string   "password",        :limit => 128
        t.string   "salt",            :limit => 128
        t.string   "secret_question"
        t.string   "secret_answer"
        t.integer  "creator",                        :default => 0,  :null => false
        t.datetime "date_created",                                   :null => false
        t.integer  "changed_by"
        t.datetime "date_changed"
        t.integer  "person_id"
        t.integer  "retired",         :limit => 1,   :default => 0,  :null => false
        t.integer  "retired_by"
        t.datetime "date_retired"
        t.string   "retire_reason"
        t.string   "uuid",            :limit => 38,                  :null => false
      end

      add_index "users", ["changed_by"], :name => "user_who_changed_user"
      add_index "users", ["creator"], :name => "user_creator"
      add_index "users", ["person_id"], :name => "person_id_for_user"
      add_index "users", ["retired_by"], :name => "user_who_retired_this_user"
      add_index "users", ["username"], :name => "username_UNIQUE", :unique => true

	end

	def self.down
			drop_table "users"
	end
end
