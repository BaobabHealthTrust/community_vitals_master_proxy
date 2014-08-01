class CreateRegions < ActiveRecord::Migration
  def self.up
    create_table :region do |t|

      t.timestamps
    end
  end

  def self.down
    drop_table :region
  end
end
