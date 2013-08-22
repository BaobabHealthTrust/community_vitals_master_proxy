class CreateOutcomes < ActiveRecord::Migration
  def self.up
    create_table :outcomes do |t|
      t.integer :person_id
      t.integer :outcome_type
      t.datetime :outcome_date
      t.integer :voided
      t.string :void_reason
      t.date :date_voided
      t.string :uuid

      t.timestamps
    end
  end

  def self.down
    drop_table :outcomes
  end
end
