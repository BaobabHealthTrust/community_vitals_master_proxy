class CreateCoreLocation < ActiveRecord::Migration
	def self.up				
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

	end

	def self.down
			drop_table "location"
	end
end
