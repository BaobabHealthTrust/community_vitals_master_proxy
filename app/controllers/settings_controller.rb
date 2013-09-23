class SettingsController < ApplicationController
  
  def site_setup
  
  end
  
  def save_setup
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    if !settings.empty?
      mode = settings["dde_mode"] rescue nil
      
      if !mode.nil?
        
        settings["dde_#{mode}"]["batch_count"] = params[:batch].to_i if !params[:batch].nil?
        
        settings["dde_#{mode}"]["site_code"] = params[:code].to_s if !params[:code].nil?
        
        settings["dde_#{mode}"]["threshold"] = params[:threshold].to_i if !params[:threshold].nil?
        
        settings["dde_#{mode}"]["password"] = params[:password].to_s if !params[:password].nil?
        
        settings["dde_#{mode}"]["username"] = params[:username].to_s if !params[:username].nil?
        
        settings["dde_#{mode}"]["target_server"] = "#{params[:ip].to_s}:#{params[:port].to_s}" if !params[:ip].nil? && !params[:port].nil?
        
        file = File.open("#{Rails.root}/config/application.yml", "w")
        
        file.write(settings.to_yaml)
        
        file.close
    
      end
    end
    
    redirect_to "/"
  end

end
