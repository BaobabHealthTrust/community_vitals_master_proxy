class SettingsController < ApplicationController
  
  def site_setup  
  end
  
  def save_setup
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    if !settings.empty?
      mode = settings["dde_mode"] rescue nil
      
      if !mode.blank?
        
        settings["dde_#{mode}"]["batch_count"] = params[:batch].to_i if !params[:batch].blank?
        
        settings["dde_#{mode}"]["site_code"] = params[:code].to_s if !params[:code].blank?
        
        settings["dde_#{mode}"]["threshold"] = params[:threshold].to_i if !params[:threshold].blank?
        
        settings["dde_#{mode}"]["password"] = params[:password].to_s if !params[:password].blank?
        
        settings["dde_#{mode}"]["username"] = params[:username].to_s if !params[:username].blank?
        
        settings["dde_#{mode}"]["target_server"] = "#{params[:ip]}:#{params[:port]}" if !params[:ip].blank? && !params[:port].blank?
        
        file = File.open("#{Rails.root}/config/application.yml", "w")
        
        file.write(settings.to_yaml)
        
        file.close
    
      end
    end
    
    redirect_to "/"
  end

  def setup_summary
    @settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    @dde_mode = @settings["dde_mode"] rescue nil
    
    @site_code = @settings["dde_#{@dde_mode}"]["site_code"] rescue nil
    
    @batch_count = @settings["dde_#{@dde_mode}"]["batch_count"] # rescue nil
    @target_server = @settings["dde_#{@dde_mode}"]["target_server"] # rescue nil
    @threshold = @settings["dde_#{@dde_mode}"]["threshold"] # rescue nil
    @username = @settings["dde_#{@dde_mode}"]["username"] # rescue nil
    @vh = @settings["dde_#{@dde_mode}"]["vh"] rescue nil
    @password = @settings["dde_#{@dde_mode}"]["password"] # rescue nil
    @gvh = @settings["dde_#{@dde_mode}"]["gvh"] # rescue nil
    @ta = @settings["#{Rails.env}"]["ta"] # rescue nil
    
    @available = NationalIdentifier.available_ids # rescue 0
    
    @total = 0
    
  end

  def done
    redirect_to "/"
  end

  def query
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    news_app_url = settings["#{Rails.env}"]["news.app.url"]
    ip_address = request.remote_ip
    query_ip_address = news_app_url
    query_news_path = "/api/news_feed?ip_address=" + ip_address
    path = query_ip_address + query_news_path
    result = RestClient.get(path)
    json = JSON.parse(result)
    json["ip_address"] = ip_address
    render :text => json.to_json
  end

  def log
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    news_app_url = settings["#{Rails.env}"]["news.app.url"]
    ip_address = request.remote_ip
    query_ip_address = news_app_url    
    query_news_path = "/api/log?news_id=" + params["news_id"] + "&category=" + params["category"] + "&ip_address=" + ip_address
    path = query_ip_address + query_news_path
    result = RestClient.get(path)
    render :text => result.to_json
  end
  
end
