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

  def acknowledge_received_ids

    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode']
    if @mode == "vh"
      @options = NationalIdentifier.find(:all,:conditions => ["request_vh_notified = 0"]).map{|x| [x.identifier,x.identifier]}
    elsif @mode == "gvh"
      @options = NationalIdentifier.find(:all,:conditions => ["request_gvh_notified = 0"]).map{|x| [x.identifier,x.identifier]}
    end
  end

  def acknowledge_received_demographics


    @options = NationalIdentifier.find(:all, :conditions => ["post_gvh_notified = 0 AND person_id IS NOT NULL"]).map{|x| ["#{x.person.name}(#{x.identifier})", x.identifier]}

  end
  def post_demographics_acknowledgement

    unless params[:demographics].blank?
      settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_gvh"] rescue {}

      unless settings.empty?
        username = settings["username"]
        password = settings["password"]
        target = settings["target_server"]
        url = "http://#{username}:#{password}@#{target}/administration/demographics_ack"
        values = params[:demographics]
        params = {:demographics => values}
        post = RestClient.post(url,params )

        NationalIdentifier.update_all({:post_gvh_notified => 1}, ["identifier IN (?)", values])
      end
    end
    redirect_to "/"
  end

  def post_ids_acknowledgement

    unless params[:ids].blank?
      mode = params[:mode]
      if mode == "vh"
        settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_vh"] rescue {}
      else
        settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_gvh"] rescue {}
      end

      unless settings.empty?
        username = settings["username"]
        password = settings["password"]
        target = settings["target_server"]

        url = "http://#{username}:#{password}@#{target}/administration/ids_ack"
        values = params[:ids]
        params = {:ids => values, :mode => mode}
        post = RestClient.post(url,params )

        if mode == 'vh'
          NationalIdentifier.update_all({:request_vh_notified => 1}, ["identifier IN (?)", values])
        elsif mode == 'gvh'
          NationalIdentifier.update_all({:request_gvh_notified => 1}, ["identifier IN (?)", values])
        end
      end
    end
    redirect_to "/"

  end

  def ids_ack

    if params[:mode] == 'vh'
      NationalIdentifier.update_all({:request_vh_notified => 1}, ["identifier IN (?)", params[:ids]])
    elsif params[:mode] == 'gvh'
      NationalIdentifier.update_all({:request_gvh_notified => 1}, ["identifier IN (?)", params[:ids]])
    end
    render :text => "OK"
  end
  def demographics_ack
    NationalIdentifier.update_all({:post_gvh_notified => 1}, ["identifier IN (?)", params[:demographics]])
    render :text => "OK"
  end
end
