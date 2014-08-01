class CreateAttributeTypes < ActiveRecord::Migration
  def self.up
    create_table :attribute_type, :primary_key => :attribute_type_id do |t|
      t.string :attribute
      t.string :description
      t.timestamps
      t.boolean :voided, :default => false
      t.string :void_reason
    end
  end

  def self.down
    drop_table :attribute_types
  end
end
