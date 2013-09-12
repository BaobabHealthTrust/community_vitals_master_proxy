# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  helper :all # include all helpers, all the time
  # protect_from_forgery # See ActionController::RequestForgeryProtection for details
  
  unloadable 
  
  before_filter :start_session

  before_filter :check_user, :except => [:user_login, :user_logout, :missing_program,
    :missing_concept, :no_user, :no_patient, :project_users_list, :check_role_activities,
    :login, :logout, :authenticate, :verify]
  
  def print_and_redirect(print_url, redirect_url, 
      message = Vocabulary.search("Printing, please wait..."), show_next_button = false, patient_id = nil)
      
    @print_url = print_url
    @redirect_url = redirect_url
    @message = message
    @show_next_button = show_next_button
    @patient_id = patient_id
    render :template => 'print/print', :layout => nil
  end
  
  def get_global_property_value(global_property)
		property_value = Settings[global_property]
		return property_value
	end

  # Scrub sensitive parameters from your log
  # filter_parameter_logging :password
  
protected

  def start_session
    session[:started] = true
    
    file = "#{File.expand_path("#{Rails.root}/config", __FILE__)}/application.yml"
    
    locale = YAML.load_file(file)["#{Rails.env}"]["locale"].strip
    
    Vocabulary.current_locale = locale
  end

  def check_user

    # raise request.headers['Authorization'].to_yaml

    if !request.headers['Authorization'].blank?
    
      result = Rails.decode64(request.headers['Authorization'])
      
      return if !result.nil?
        
    end

    if !params[:token].nil?
      session[:token] = params[:token]
    end

    if !params[:user_id].nil?
      session[:user_id] = params[:user_id]
    end

    if !params[:location_id].nil?
      session[:location_id] = params[:location_id]
    end

    link = get_global_property_value("user.management.url").to_s rescue nil

    if link.nil?
      flash[:error] = "Missing configuration for <br/>user management connection!"

      redirect_to "/no_user" and return
    end    

    # Track final destination
    file = "#{File.expand_path("#{Rails.root}/tmp", __FILE__)}/current.path.yml"

    f = File.open(file, "w")

    f.write("#{Rails.env}:\n    current.path: #{request.referrer}")

    f.close

    if session[:token].nil?
      redirect_to "/login?internal=true" and return
    end

  end

end
