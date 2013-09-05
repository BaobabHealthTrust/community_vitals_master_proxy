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
  def change_language
    render :layout => 'report'
  end

  def save_language
    settings = YAML.load_file("#{Rails.root}/config/application.yml")
    settings[Rails.env]["locale"] = params[:language]
    File.open("#{Rails.root}/config/application.yml",'w'){|f| YAML.dump(settings, f)}
    redirect_to "/"
  end

  def id_drill_down
    data = []
    if params[:type] == 'Assigned'
      nat_ids = NationalIdentifier.all

      (nat_ids || []).each do |id|
         data << {
             'National ID' => id.identifier,
             'Assigned GVH' => id.assigned_gvh,
             'Assigned VH' => id.assigned_vh,
             'Assigned To' => id.person.blank? ? " ": (id.person.given_name + '' + id.person.family_name),
             'Assigned On' => id.assigned_at
         }
      end
      keys = ['National ID', 'Assigned GVH','Assigned VH','Assigned To','Assigned On']
    elsif params[:type] == 'Available'
      nat_ids = NationalIdentifier.find(:all, :conditions =>["person_id is null"])
      (nat_ids || []).each do |id|
        data << {
            'National ID' => id.identifier,
            'Assigned GVH' => id.assigned_gvh,
            'Assigned VH' => id.assigned_vh
        }
      end
        keys = ['National ID', 'Assigned GVH','Assigned VH']
    elsif params[:type] == 'Used'
          nat_ids = NationalIdentifier.find(:all, :conditions =>["person_id is not null"])
          (nat_ids || []).each do |id|
            data << {
                'National ID' => id.identifier,
                'Assigned GVH' => id.assigned_gvh,
                'Assigned VH' => id.assigned_vh,
                'Assigned To' => id.person.blank? ? " ": (id.person.given_name + '' + id.person.family_name),
                'Assigned On' => id.assigned_at
            }
          end
          keys = ['National ID', 'Assigned GVH','Assigned VH','Assigned To','Assigned On']
    end
    redirect_to :controller => 'demographics', :action => "people_drill_down", :title => params[:title], :keys => keys, :data => data
  end

end
