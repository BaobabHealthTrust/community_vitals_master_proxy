class CreatePersonAttributes < ActiveRecord::Migration
  def self.up
    create_table :person_attribute, :primary_key => :person_attribute_id do |t|

      t.integer :person_id
      t.string :value
      t.integer :attribute_type_id
      t.integer :creator
      t.timestamps
      t.integer :updated_by
      t.boolean :voided, :default => false
      t.integer :voided_by
      t.datetime :date_voided
    end
  end

  def self.down
    drop_table :person_attributes
  end
end
