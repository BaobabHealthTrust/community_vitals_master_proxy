# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 201307181255011) do

  create_table "attribute_type", :primary_key => "attribute_type_id", :force => true do |t|
    t.string   "attribute"
    t.string   "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean  "voided",      :default => false
    t.string   "void_reason"
  end

  create_table "district", :primary_key => "district_id", :force => true do |t|
    t.string   "name",          :default => "",    :null => false
    t.integer  "region_id",     :default => 0,     :null => false
    t.integer  "creator",       :default => 0,     :null => false
    t.datetime "date_created",                     :null => false
    t.boolean  "retired",       :default => false, :null => false
    t.integer  "retired_by"
    t.datetime "date_retired"
    t.string   "retire_reason"
  end

  add_index "district", ["creator"], :name => "user_who_created_district"
  add_index "district", ["region_id"], :name => "region_for_district"
  add_index "district", ["retired"], :name => "retired_status"
  add_index "district", ["retired_by"], :name => "user_who_retired_district"

  create_table "location", :primary_key => "location_id", :force => true do |t|
    t.string   "name",                            :default => "",    :null => false
    t.string   "description"
    t.string   "address1",          :limit => 50
    t.string   "address2",          :limit => 50
    t.string   "city_village",      :limit => 50
    t.string   "state_province",    :limit => 50
    t.string   "postal_code",       :limit => 50
    t.string   "country",           :limit => 50
    t.string   "latitude",          :limit => 50
    t.string   "longitude",         :limit => 50
    t.integer  "creator",                         :default => 0,     :null => false
    t.datetime "date_created",                                       :null => false
    t.string   "county_district",   :limit => 50
    t.string   "neighborhood_cell", :limit => 50
    t.string   "region",            :limit => 50
    t.string   "subregion",         :limit => 50
    t.string   "township_division", :limit => 50
    t.boolean  "retired",                         :default => false, :null => false
    t.integer  "retired_by"
    t.datetime "date_retired"
    t.string   "retire_reason"
    t.integer  "location_type_id"
    t.integer  "parent_location"
    t.string   "uuid",              :limit => 38,                    :null => false
  end

  add_index "location", ["creator"], :name => "user_who_created_location"
  add_index "location", ["location_type_id"], :name => "type_of_location"
  add_index "location", ["name"], :name => "name_of_location"
  add_index "location", ["parent_location"], :name => "parent_location"
  add_index "location", ["retired"], :name => "retired_status"
  add_index "location", ["retired_by"], :name => "user_who_retired_location"
  add_index "location", ["uuid"], :name => "location_uuid_index", :unique => true

  create_table "locations", :force => true do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "national_identifiers", :force => true do |t|
    t.string   "identifier"
    t.integer  "person_id"
    t.string   "site_id"
    t.string   "assigned_gvh"
    t.string   "assigned_vh"
    t.integer  "requested_by_gvh",     :default => 0
    t.integer  "requested_by_vh",      :default => 0
    t.integer  "request_gvh_notified", :default => 0
    t.integer  "request_vh_notified",  :default => 0
    t.integer  "posted_by_gvh",        :default => 0
    t.integer  "posted_by_vh",         :default => 0
    t.integer  "posted_by_ta",         :default => 0
    t.integer  "post_gvh_notified",    :default => 0
    t.integer  "post_vh_notified",     :default => 0
    t.integer  "voided",               :default => 0
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "assigned_at"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "decimal_num"
    t.string   "allocated_gvh"
    t.string   "allocated_vh"
    t.boolean  "allocated_to_vh",      :default => false
  end

  create_table "outcome_types", :force => true do |t|
    t.string   "name"
    t.integer  "vocabulary_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "outcomes", :force => true do |t|
    t.integer  "person_id"
    t.integer  "outcome_type"
    t.datetime "outcome_date"
    t.integer  "posted_by_vh",  :default => 0
    t.integer  "posted_by_gvh", :default => 0
    t.integer  "posted_by_ta",  :default => 0
    t.integer  "voided",        :default => 0
    t.string   "void_reason"
    t.date     "date_voided"
    t.string   "uuid"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "people", :force => true do |t|
    t.string   "national_id"
    t.integer  "creator_site_id"
    t.integer  "creator_id"
    t.string   "given_name"
    t.string   "middle_name"
    t.string   "family_name"
    t.string   "maiden_name"
    t.string   "given_name_code"
    t.string   "family_name_code"
    t.string   "gender"
    t.date     "birthdate"
    t.integer  "birthdate_estimated"
    t.string   "county_district"
    t.string   "state_province"
    t.string   "adrress1"
    t.string   "address2"
    t.string   "city_village"
    t.string   "neighbourhood_cell"
    t.string   "cell_phone_number"
    t.string   "occupation"
    t.string   "outcome"
    t.date     "outcome_date"
    t.string   "village"
    t.string   "gvh"
    t.string   "ta"
    t.integer  "voided",              :default => 0
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

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

  create_table "person_attribute", :primary_key => "person_attribute_id", :force => true do |t|
    t.integer  "person_id"
    t.string   "value"
    t.integer  "attribute_type_id"
    t.integer  "creator"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "updated_by"
    t.boolean  "voided",            :default => false
    t.integer  "voided_by"
    t.datetime "date_voided"
  end

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

  create_table "privilege", :primary_key => "uuid", :force => true do |t|
    t.string "privilege",   :limit => 250
    t.string "description", :limit => 250, :default => "", :null => false
  end

  add_index "privilege", ["uuid"], :name => "privilege_uuid_index", :unique => true

  create_table "region", :primary_key => "region_id", :force => true do |t|
    t.string   "name",          :default => "",    :null => false
    t.integer  "creator",       :default => 0,     :null => false
    t.datetime "date_created",                     :null => false
    t.boolean  "retired",       :default => false, :null => false
    t.integer  "retired_by"
    t.datetime "date_retired"
    t.string   "retire_reason"
  end

  add_index "region", ["creator"], :name => "user_who_created_region"
  add_index "region", ["retired"], :name => "retired_status"
  add_index "region", ["retired_by"], :name => "user_who_retired_region"

  create_table "relationship_types", :force => true do |t|
    t.string   "relation"
    t.integer  "vocabulary_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "relationships", :force => true do |t|
    t.string   "person_national_id"
    t.string   "relation_national_id"
    t.integer  "person_is_to_relation"
    t.integer  "posted_by_vh",          :default => 0
    t.integer  "posted_by_gvh",         :default => 0
    t.integer  "posted_by_ta",          :default => 0
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "role", :primary_key => "uuid", :force => true do |t|
    t.string  "role",          :limit => 250
    t.string  "description",                  :default => "", :null => false
    t.integer "vocabulary_id"
  end

  add_index "role", ["uuid"], :name => "role_uuid_index", :unique => true

  create_table "role_privilege", :id => false, :force => true do |t|
    t.string "role",      :limit => 50, :default => "", :null => false
    t.string "privilege", :limit => 50, :default => "", :null => false
  end

  add_index "role_privilege", ["role"], :name => "role_privilege"

  create_table "sessions", :force => true do |t|
    t.string   "session_id"
    t.text     "data"
    t.datetime "updated_at"
  end

  add_index "sessions", ["session_id"], :name => "sessions_session_id_index"

  create_table "sites", :force => true do |t|
    t.string   "name"
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "traditional_authority", :primary_key => "traditional_authority_id", :force => true do |t|
    t.string   "name",          :default => "",    :null => false
    t.integer  "district_id",   :default => 0,     :null => false
    t.integer  "creator",       :default => 0,     :null => false
    t.datetime "date_created",                     :null => false
    t.boolean  "retired",       :default => false, :null => false
    t.integer  "retired_by"
    t.datetime "date_retired"
    t.string   "retire_reason"
  end

  add_index "traditional_authority", ["creator"], :name => "user_who_created_traditional_authority"
  add_index "traditional_authority", ["district_id"], :name => "district_for_ta"
  add_index "traditional_authority", ["retired"], :name => "retired_status"
  add_index "traditional_authority", ["retired_by"], :name => "user_who_retired_traditional_authority"

  create_table "user_property", :id => false, :force => true do |t|
    t.integer "user_id",                       :default => 0,  :null => false
    t.string  "property",       :limit => 100, :default => "", :null => false
    t.string  "property_value",                :default => "", :null => false
  end

  create_table "user_role", :id => false, :force => true do |t|
    t.integer "user_id",               :default => 0,  :null => false
    t.string  "role",    :limit => 50, :default => "", :null => false
  end

  add_index "user_role", ["user_id"], :name => "user_role"

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

  create_table "village", :primary_key => "village_id", :force => true do |t|
    t.string   "name",                     :default => "",    :null => false
    t.integer  "traditional_authority_id", :default => 0,     :null => false
    t.integer  "creator",                  :default => 0,     :null => false
    t.datetime "date_created",                                :null => false
    t.boolean  "retired",                  :default => false, :null => false
    t.integer  "retired_by"
    t.datetime "date_retired"
    t.string   "retire_reason"
  end

  add_index "village", ["creator"], :name => "user_who_created_village"
  add_index "village", ["retired"], :name => "retired_status"
  add_index "village", ["retired_by"], :name => "user_who_retired_village"
  add_index "village", ["traditional_authority_id"], :name => "ta_for_village"

  create_table "vocabularies", :force => true do |t|
    t.string   "value"
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "words", :force => true do |t|
    t.integer  "vocabulary_id"
    t.string   "locale"
    t.string   "value"
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
