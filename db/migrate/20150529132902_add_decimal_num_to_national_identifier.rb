class AddDecimalNumToNationalIdentifier < ActiveRecord::Migration
  def self.up 
		add_column :national_identifiers, :decimal_num, :integer, :default => nil, :after => :identifier
  end

  def self.down
		remove_column :national_identifiers, :decimal_num
  end
end
