class CreateOutcomeTypes < ActiveRecord::Migration
  def self.up
    create_table :outcome_types do |t|
      t.string :name
      t.integer :vocabulary_id

      t.timestamps
    end
  end

  def self.down
    drop_table :outcome_types
  end
end
