class CreateNationalIdentifiers < ActiveRecord::Migration
  def self.up
    create_table :national_identifiers do |t|
      t.string :identifier
      t.integer :person_id
      t.string :site_id
      t.string :assigned_gvh
      t.string :assigned_vh
      t.integer :requested_by_gvh, :default => 0
      t.integer :requested_by_vh, :default => 0
      t.integer :request_gvh_notified, :default => 0
      t.integer :request_vh_notified, :default => 0
      t.integer :posted_by_gvh, :default => 0
      t.integer :posted_by_vh, :default => 0
      t.integer :post_gvh_notified, :default => 0
      t.integer :post_vh_notified, :default => 0
      t.integer :voided, :default => 0
      t.string :void_reason
      t.date :date_voided

      t.timestamps
    end
  end

  def self.down
    drop_table :national_identifiers
  end
end
