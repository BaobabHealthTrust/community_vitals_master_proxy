class CreateRelationships < ActiveRecord::Migration
  def self.up
    create_table :relationships do |t|
      t.string :person_national_id
      t.string :relation_national_id
      t.integer :person_is_to_relation
      t.integer :posted_by_vh, :default => 0
      t.integer :posted_by_gvh, :default => 0
      t.integer :posted_by_ta, :default => 0
      t.integer :voided 
      t.string :void_reason
      t.date :date_voided

      t.timestamps
    end
  end

  def self.down
    drop_table :relationships
  end
end
