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

ActiveRecord::Schema.define(:version => 20130820141851) do

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
    t.integer  "post_gvh_notified",    :default => 0
    t.integer  "post_vh_notified",     :default => 0
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
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
    t.integer  "voided"
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
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

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
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "sites", :force => true do |t|
    t.string   "name"
    t.integer  "voided"
    t.string   "void_reason"
    t.date     "date_voided"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

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
