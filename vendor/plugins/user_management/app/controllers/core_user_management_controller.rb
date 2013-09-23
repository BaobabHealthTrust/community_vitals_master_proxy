
class CoreUserManagementController < ApplicationController
  unloadable

  before_filter :__check_user, :except => [:login, :logout, :authenticate, :verify]

  before_filter :__check_location, :except => [:login, :authenticate, :logout, :verify, :location, :location_update]

  def login

    # Track final destination
    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.login.yml"

    if !params[:ext].nil?

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}#{
        (!request.referrer.match(/user\_id|location\_id/) ? request.referrer : "") }")

      f.close

    end

    @destination = nil

    if File.exists?(file)

      @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

      # File.delete(file)

    end

		redirect_to "clinic/index/#{params[:user_id]}" if !params[:user_id].blank?

  end

  def authenticate

    user = CoreUser.authenticate(params[:login], params[:password]) # rescue nil

    if user.nil?
      flash[:error] = "Wrong username or password!"
      redirect_to request.referrer and return
    end

    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.login.yml"

    CoreUserProperty.find_by_user_id_and_property(user.id, "Status").delete rescue nil

    u = CoreUserProperty.create(
      :user_id => user.id,
      :property => "Status",
      :property_value => "ACTIVE"
    )

    if (user.status_value.nil? rescue false) and File.exists?(file)
      flash[:error] = "Unauthorised user!"
      redirect_to request.referrer and return
    elsif (user.status_value.downcase != "active" rescue false) and File.exists?(file)
      flash[:error] = "Unauthorised user!"
      redirect_to request.referrer and return
    end

    CoreUserProperty.find_by_user_id_and_property(user.id, "Token").delete rescue nil

    u = CoreUserProperty.create(
      :user_id => user.id,
      :property => "Token",
      :property_value => CoreUser.random_string(16)
    )

    session[:token] = u.property_value
    session[:user_id] = u.user_id

    # redirect_to "/location?user_id=#{user.id}&src=#{params[:src]}&token=#{session[:token]}" and return
    
    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.login.yml"

    @destination = nil

    if File.exists?(file)

      @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

      File.delete(file)

    end

    # @destination = params[:src] if @destination.blank? && !params[:src].blank?
    
    if !@destination.nil?
      q = (@destination.match(/\?/))
      u = (@destination.match(/user_id=(\d+)/))

      if u

        @destination = @destination.gsub(/user_id=(\d+)/, "user_id=#{session[:user_id]}&token=#{session[:token]}")

        redirect_to "http://#{@destination}" and return

      else

        # raise "http://#{@destination}#{(!q ? "?" : "")}user_id=#{user.id}".to_yaml

        redirect_to "http://#{@destination}#{(!q ? "?" : "")}user_id=#{session[:user_id]}&token=#{session[:token]}" and return

      end

    else

      redirect_to "http://#{request.raw_host_with_port}?user_id=#{session[:user_id]}" and return

    end
    

  end

  def new_user
    @roles = CoreRole.find(:all).collect{|r| Vocabulary.search(r.role)}
  end

  def create_user

    existing = CoreUser.find_by_username(params[:login]) rescue nil

    if !existing.nil?
      flash[:error] = "Username already taken!"
      redirect_to "/new_user?user_id=#{session[:user_id]}&first_name=#{params[:first_name]
          }&last_name=#{params[:last_name]}&gender=#{params[:gender]}#{
      (!params[:src].nil? ? "&src=#{params[:src]}" : "")}" and return
    end

    user = CoreUser.create(
      :username => params[:login],
      :password => params[:password],
      :creator => params[:user_id],
      :date_created => Date.today,
      :uuid => ActiveRecord::Base.connection.select_one("SELECT UUID() as uuid")['uuid']
    )

    CoreUserProperty.create(
      :user_id => user.id,
      :property => "First Name",
      :property_value => (params[:first_name] rescue nil)
    )

    CoreUserProperty.create(
      :user_id => user.id,
      :property => "Last Name",
      :property_value => (params[:last_name] rescue nil)
    )

    CoreUserProperty.create(
      :user_id => user.id,
      :property => "Gender",
      :property_value => (params[:gender] rescue nil)
    )

    CoreUserProperty.create(
      :user_id => user.id,
      :property => "Status",
      :property_value => "PENDING"
    )

    params[:roles].each do |role|
        
      CoreUserRole.create(
        :user_id => user.id,
        :role => role
      )
        
    end

    redirect_to "/user_list?user_id=#{(params[:id] || params[:user_id])}&location_id=#{
    params[:location_id]}#{(!params[:src].nil? ? "&src=#{params[:src]}" : "")}" and return
  end

  def select_user_task

    # Track final destination
    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.login.yml"

    if !params[:ext].nil?

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}#{request.referrer}")

      f.close

    end

    @destination = "/logout/#{session[:user_id]}"

    if File.exists?(file)

      @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

      File.delete(file)

    end

  end

  def user_list

    @destination = "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}"

    if !params[:src].nil?
      # Track final destination
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}?user_id=#{session[:user_id]}")

      f.close

      if File.exists?(file)

        @destination = "http://" + YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

      end
    end

    @users = CoreUser.find(:all).collect { |user|
      [
        user.name,
        user.username,
        user.gender,
        user.user_roles.collect{|r|
          r.role
        },
        (user.status.property_value rescue ""),
        user.id
      ]
    }

    if @user.status_value.to_s.downcase != "pending" and @user.status_value.to_s.downcase != "blocked"
      
      @can_edit = true

    else

      @can_edit = false

    end

    redirect_to "/login" and return if @user.nil?

  end

  def edit_user_status

    if params[:target_id].nil?
      flash[:error] = "Missing User ID!"
      redirect_to request.referrer and return
    end

    @target = CoreUser.find(params[:target_id]) rescue nil

  end

  def update_user_status

    property = CoreUserProperty.find_by_property_and_user_id("Status", params[:target_id]) rescue nil

    if property.nil?
      CoreUserProperty.create(
        :user_id => params[:target_id],
        :property => "Status",
        :property_value => (params[:status] rescue nil)
      )
    else
      property.update_attributes(:property_value => params[:status])
    end

    flash[:notice] = "Status changed to #{params[:status].upcase}"
    redirect_to "/user_list?user_id=#{session[:user_id]}&location_id=#{
    params[:location_id]}#{(!params[:src].nil? ? "&src=#{params[:src]}" : "")}" and return
  end

  def edit_roles

    @target = CoreUser.find(params[:target_id]) rescue nil
    
    current_roles = @target.user_roles.collect{|r| Vocabulary.search(r.role)}

    @roles = CoreRole.find(:all).collect{|r| Vocabulary.search(r.role)} - current_roles
    
  end

  def add_user_roles

    @target = CoreUser.find(params[:target_id]) rescue nil

    params[:roles].each do |role|

      CoreUserRole.create(
        :user_id => @target.id,
        :role => role
      )
    end

    redirect_to "/user_list?user_id=#{session[:user_id]}&location_id=#{
    params[:location_id]}#{(!params[:src].nil? ? "&src=#{params[:src]}" : "")}" and return
  end

  def void_role

    @target = CoreUser.find(params[:target_id]) rescue nil

    CoreUserRole.find_by_user_id_and_role(@target.id, params[:role]).delete rescue nil

    redirect_to "/user_list?user_id=#{session[:user_id]}&location_id=#{params[:location_id]
}#{(!params[:src].nil? ? "&src=#{params[:src]}" : "")}" and return
  end

  def edit_user

    if !params[:src].blank?
      # Track final destination
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}?user_id=#{session[:user_id]}")

      f.close

      @destination = nil

      if File.exists?(file)

        @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

        File.delete(file)

      end
      
    end

    @first_name = CoreUserProperty.find_by_property_and_user_id("First Name", params[:user_id]).property_value rescue nil

    @last_name = CoreUserProperty.find_by_property_and_user_id("Last Name", params[:user_id]).property_value rescue nil

    @gender = CoreUserProperty.find_by_property_and_user_id("Gender", params[:user_id]).property_value rescue nil

  end

  def update_user

    fn_property = CoreUserProperty.find_by_property_and_user_id("First Name", params[:user_id]) rescue nil

    if fn_property.nil?
      CoreUserProperty.create(
        :user_id => params[:user_id],
        :property => "First Name",
        :property_value => (params[:first_name] rescue nil)
      )
    else
      fn_property.update_attributes(:property_value => params[:first_name])
    end

    ln_property = CoreUserProperty.find_by_property_and_user_id("Last Name", params[:user_id]) rescue nil

    if ln_property.nil?
      CoreUserProperty.create(
        :user_id => params[:user_id],
        :property => "Last Name",
        :property_value => (params[:last_name] rescue nil)
      )
    else
      ln_property.update_attributes(:property_value => params[:last_name])
    end

    gn_property = CoreUserProperty.find_by_property_and_user_id("Gender", params[:user_id]) rescue nil

    if gn_property.nil?
      CoreUserProperty.create(
        :user_id => params[:user_id],
        :property => "Gender",
        :property_value => (params[:gender] rescue nil)
      )
    else
      gn_property.update_attributes(:property_value => params[:gender])
    end

    if !params[:src].blank?
      
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      @destination = nil

      if File.exists?(file)

        @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

        File.delete(file)

      else

        flash[:notice] = "Demographics updated!"

        redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return unless !params[:src].blank?

      end

      @destination = params[:src] if @destination.blank? && !params[:src].blank?
     
      if !@destination.blank?
        q = (@destination.match(/\?/))
        u = (@destination.match(/user_id=(\d+)/))

        if u

          @destination = @destination.gsub(/user_id=(\d+)/, "user_id=#{session[:user_id]}&location_id=#{params[:location_id]}")

          redirect_to "http://#{@destination}" and return

        else

          redirect_to "http://#{@destination}#{(!q ? "?" : "")}user_id=#{
          session[:user_id]}&location_id=#{params[:location_id]}" and return

        end

      else

        flash[:notice] = "Demographics updated!"

        redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return
        
      end

    else

      flash[:notice] = "Demographics updated!"

      redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return 

    end

  end

  def edit_password

    if !params[:src].nil?
      # Track final destination
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}?user_id=#{session[:user_id]}")

      f.close

      @destination = nil

      if File.exists?(file)

        @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

        File.delete(file)

      end
    end

  end

  def update_password
    old = CoreUser.authenticate(@user.username, params[:old_password]) # rescue nil

    if old.blank?
      flash[:error] = "Invalid current password!"

      redirect_to request.referrer and return
    end

    user = CoreUser.find(params[:user_id]) #rescue nil

    if !user.nil?
      
      user.update_attributes(:password => params[:password])
      
      flash[:notice] = "Password updated!"
    end

    # redirect_to "/select_user_task?user_id=#{params[:user_id]}" and return

    if !params[:src].blank?

      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      @destination = nil

      if File.exists?(file)

        @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

        File.delete(file)

      else

        flash[:notice] = "Password updated!"
        
        redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return unless !params[:src].blank?

      end
      
      @destination = params[:src] if @destination.blank? && !params[:src].blank?

      if !@destination.nil?
        q = (@destination.match(/\?/))
        u = (@destination.match(/user_id=(\d+)/))

        if u

          @destination = @destination.gsub(/user_id=(\d+)/, "user_id=#{session[:user_id]}&location_id=#{params[:location_id]}")

          redirect_to "http://#{@destination}" and return

        else

          redirect_to "http://#{@destination}#{(!q ? "?" : "")}user_id=#{
          session[:user_id]}&location_id=#{params[:location_id]}" and return

        end

      else

        flash[:notice] = "Password updated!"

        redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return

      end

    else

      flash[:notice] = "Password updated!"

      redirect_to "/select_user_task?user_id=#{params[:user_id]}&location_id=#{params[:location_id]}" and return

    end
  end

  def logout
  
    request_link = params[:ext]? request.referrer.split("?").first  : ""
 
    user = CoreUserProperty.find_by_user_id_and_property(params[:id], "Token") rescue nil

    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml" rescue ""

    reset_session
 
    if File.exists?(file)

      @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip
      File.delete(file)
    end

    if user
      user.delete      

      flash[:notice] = "You've been logged out"
    end
    
    redirect_to "#{request_link}" and return if params[:ext]
    
    redirect_to "/login" and return    
    
  end

  def verify

    demo = CoreUser.find(params[:user_id] || params[:id]).demographics rescue {}

    render :text => demo.to_json
  end

  def location
   
    if !params[:src].blank?
      
      # Track final destination
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"

      f = File.open(file, "w")

      f.write("#{Rails.env}:\n    host.path.login: #{params[:src] rescue ""}?user_id=#{session[:user_id]}")

      f.close

      @destination = nil

      if File.exists?(file)

        @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

        File.delete(file)

      end
  
    end

  end

  def location_update

    if params[:location].strip.match(/^\d+$/)

      @location = CoreLocation.find(params[:location]) rescue nil

    else
      
      @location = CoreLocation.find_by_name(params[:location]) rescue nil

    end

    if @location.nil?

      flash[:error] = "Invalid location"
      
      redirect_to "/location?user_id=#{session[:user_id]}&src=#{params[:src]}&token=#{session[:token]}" and return

    end

    session[:location_id] = @location.id

    if !params[:src].nil?
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.#{session[:user_id]}.yml"
    else
      file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/user.login.yml"
    end

    @destination = nil

    if File.exists?(file)

      @destination = YAML.load_file(file)["#{Rails.env
        }"]["host.path.login"].strip

      File.delete(file)

    end

    @destination = params[:src] if @destination.blank? && !params[:src].blank?
    
    if !@destination.nil?
      q = (@destination.match(/\?/))
      u = (@destination.match(/user_id=(\d+)/))

      if u

        @destination = @destination.gsub(/user_id=(\d+)/, "user_id=#{session[:user_id]}&location_id=#{@location.id}&token=#{session[:token]}")

        redirect_to "http://#{@destination}" and return

      else

        # raise "http://#{@destination}#{(!q ? "?" : "")}user_id=#{user.id}".to_yaml

        redirect_to "http://#{@destination}#{(!q ? "?" : "")}user_id=#{session[:user_id]}&location_id=#{@location.id}&token=#{session[:token]}" and return

      end

    else

      redirect_to "http://#{request.raw_host_with_port}?user_id=#{session[:user_id]}&location_id=#{@location.id}" and return

    end
    
  end

  def user_demographics
    render :layout => false
  end

  protected
  
  def __check_user
    
    token = session[:token] rescue nil
    
    if token.nil?
      redirect_to "/login" and return
    else
      @user = CoreUser.find(session[:user_id]) rescue nil
      
      if @user.nil?
        redirect_to "/login" and return
      end
    end
    
  end

  def __check_location
    
    location = session[:location_id] rescue nil
    
    if location.nil?
      redirect_to "/location?user_id=#{session[:user_id]}" and return if !session[:user_id].nil?
    end
    
  end

end
