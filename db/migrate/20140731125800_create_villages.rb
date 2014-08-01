class CreateVillages < ActiveRecord::Migration
  def self.up
    create_table :village do |t|

      t.timestamps
    end
  end

  def self.down
    drop_table :village
  end
end
