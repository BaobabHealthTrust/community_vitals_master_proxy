class CreateCorePersonName < ActiveRecord::Migration
	def self.up		
		
      create_table "person_name", :primary_key => "person_name_id", :force => true do |t|
        t.integer  "preferred",          :limit => 2,  :default => 0, :null => false
        t.integer  "person_id"
        t.string   "prefix",             :limit => 50
        t.string   "given_name",         :limit => 50
        t.string   "middle_name",        :limit => 50
        t.string   "family_name_prefix", :limit => 50
        t.string   "family_name",        :limit => 50
        t.string   "family_name2",       :limit => 50
        t.string   "family_name_suffix", :limit => 50
        t.string   "degree",             :limit => 50
        t.integer  "creator",                          :default => 0, :null => false
        t.datetime "date_created",                                    :null => false
        t.integer  "voided",             :limit => 2,  :default => 0, :null => false
        t.integer  "voided_by"
        t.datetime "date_voided"
        t.string   "void_reason"
        t.integer  "changed_by"
        t.datetime "date_changed"
        t.string   "uuid",               :limit => 38,                :null => false
      end

      add_index "person_name", ["creator"], :name => "user_who_made_name"
      add_index "person_name", ["family_name"], :name => "last_name"
      add_index "person_name", ["family_name2"], :name => "family_name2"
      add_index "person_name", ["given_name"], :name => "first_name"
      add_index "person_name", ["middle_name"], :name => "middle_name"
      add_index "person_name", ["person_id"], :name => "name_for_patient"
      add_index "person_name", ["uuid"], :name => "person_name_uuid_index", :unique => true
      add_index "person_name", ["voided_by"], :name => "user_who_voided_name"

	end

	def self.down
			drop_table "person_name"
	end
end
