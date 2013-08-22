class CreateRelationshipTypes < ActiveRecord::Migration
  def self.up
    create_table :relationship_types do |t|
      t.string :relation
      t.integer :vocabulary_id

      t.timestamps
    end
  end

  def self.down
    drop_table :relationship_types
  end
end
