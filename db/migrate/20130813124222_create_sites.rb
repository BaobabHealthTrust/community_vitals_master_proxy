class CreateSites < ActiveRecord::Migration
  def self.up
    create_table :sites do |t|
      t.string :name
      t.integer :voided
      t.string :void_reason
      t.date :date_voided

      t.timestamps
    end
  end

  def self.down
    drop_table :sites
  end
end
