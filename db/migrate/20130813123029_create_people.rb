class CreatePeople < ActiveRecord::Migration
  def self.up
    create_table :people do |t|
      t.string :national_id
      t.integer :creator_site_id
      t.integer :creator_id
      t.string :given_name
      t.string :middle_name
      t.string :family_name
      t.string :maiden_name
      t.string :gender
      t.date :birthdate
      t.integer :birthdate_estimated
      t.string :county_district
      t.string :state_province
      t.string :adrress1
      t.string :address2
      t.string :city_village
      t.string :neighbourhood_cell
      t.string :cell_phone_number
      t.string :occupation
      t.string :outcome
      t.date :outcome_date
      t.string :village
      t.string :gvh
      t.string :ta
      t.integer :voided
      t.string :void_reason
      t.date :date_voided

      t.timestamps
    end
  end

  def self.down
    drop_table :people
  end
end
