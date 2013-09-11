class CreateCorePerson < ActiveRecord::Migration
	def self.up		
		
    create_table "person", :primary_key => "person_id", :force => true do |t|
      t.string   "gender",              :limit => 50, :default => ""
      t.date     "birthdate"
      t.integer  "birthdate_estimated", :limit => 2,  :default => 0,  :null => false
      t.integer  "dead",                :limit => 2,  :default => 0,  :null => false
      t.datetime "death_date"
      t.integer  "cause_of_death"
      t.integer  "creator",                           :default => 0,  :null => false
      t.datetime "date_created",                                      :null => false
      t.integer  "changed_by"
      t.datetime "date_changed"
      t.integer  "voided",              :limit => 2,  :default => 0,  :null => false
      t.integer  "voided_by"
      t.datetime "date_voided"
      t.string   "void_reason"
      t.string   "uuid",                :limit => 38,                 :null => false
    end

    add_index "person", ["birthdate"], :name => "person_birthdate"
    add_index "person", ["cause_of_death"], :name => "person_died_because"
    add_index "person", ["changed_by"], :name => "user_who_changed_pat"
    add_index "person", ["creator"], :name => "user_who_created_patient"
    add_index "person", ["death_date"], :name => "person_death_date"
    add_index "person", ["uuid"], :name => "person_uuid_index", :unique => true
    add_index "person", ["voided_by"], :name => "user_who_voided_patient"

	end

	def self.down
			drop_table "person"
	end
end
