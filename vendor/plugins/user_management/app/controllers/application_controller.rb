
class ApplicationController < ActionController::Base
  helper :all
  
  before_filter :start_session

  def get_global_property_value(global_property)
		property_value = Settings[global_property]
		if property_value.nil?
			property_value = GlobalProperty.find(:first, :conditions => {:property => "#{global_property}"}
													).property_value rescue nil
		end
		return property_value
	end

  def create_from_dde_server
    get_global_property_value('create.from.dde.server').to_s == "true" rescue false
  end

  def print_and_redirect(print_url, redirect_url, message = "Printing, please wait...", show_next_button = false, patient_id = nil)
    @print_url = print_url
    @redirect_url = redirect_url
    @message = message
    @show_next_button = show_next_button
    @patient_id = patient_id
    render :template => 'print/print', :layout => nil
  end

protected

  def start_session
    session[:started] = true
    
    file = "#{File.expand_path("#{Rails.root}/config", __FILE__)}/application.yml"
    
    locale = YAML.load_file(file)["#{Rails.env}"]["locale"].strip
    
    Vocabulary.current_locale = locale
  end

end
