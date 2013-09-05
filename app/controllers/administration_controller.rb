class AdministrationController < ApplicationController
  def site_management
  end

  def national_id_management
    nat_ids = NationalIdentifier.all
    available = NationalIdentifier.find(:all, :conditions =>["person_id is null"])
    assigned = NationalIdentifier.find(:all, :conditions =>["person_id is not null"])
    @counts = {'all' => nat_ids.length, 'used' => assigned.length, 'available' => available.length}
    render :layout => 'report'
  end

  def user_management
  end

  def address_book_management
  end

end
