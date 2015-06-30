class AddFirstLevelAcknowledgementToNpids < ActiveRecord::Migration
  def self.up
    add_column :national_identifiers, :allocated_gvh, :string, :default => nil
    add_column :national_identifiers, :allocated_vh, :string, :default => nil
    add_column :national_identifiers, :allocated_to_vh, :boolean, :default => false
  end

  def self.down
    remove_column :national_identifiers, :allocated_gvh, :string
    remove_column :national_identifiers, :allocated_vh, :string
    remove_column :national_identifiers, :allocated_to_vh, :boolean
  end
end
