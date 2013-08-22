# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  helper :all # include all helpers, all the time
  # protect_from_forgery # See ActionController::RequestForgeryProtection for details
  
  unloadable 
  
  before_filter :start_session


  # Scrub sensitive parameters from your log
  # filter_parameter_logging :password
  
protected

  def start_session
    session[:started] = true
    
    file = "#{File.expand_path("#{Rails.root}/config", __FILE__)}/application.yml"
    
    locale = YAML.load_file(file)["#{Rails.env}"]["locale"].strip
    
    Vocabulary.current_locale = locale
  end

end
