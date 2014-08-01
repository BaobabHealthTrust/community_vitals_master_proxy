class CreateDistricts < ActiveRecord::Migration
  def self.up
    create_table :district do |t|

      t.timestamps
    end
  end

  def self.down
    drop_table :district
  end
end
