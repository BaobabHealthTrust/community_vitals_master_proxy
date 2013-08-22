class AssetsController < ApplicationController
  unloadable 
  
  def loadfields
    result = []
    selected = []
    
    if !params[:t].nil? && !params[:k].nil? && !params[:d].nil?
      
      if !params[:s].nil? 
        selected = params[:s].strip.split(",")
      end
      
      result = MysqlConnection.connection.select_all(
        "SELECT #{params[:k]} pkey, #{params[:d]} dname FROM #{params[:t]} WHERE voided = 0"
      )
    
    end
    
    render :text => result.collect{|e| 
        "<option #{(selected.include?(e["pkey"]) ? "selected" : "")} value='#{e["pkey"]}'>#{e["dname"]}"
    }.join("")
  end
  
  def display
    unless params[:f].nil?
      file = File.open("#{File.dirname(File.dirname(__FILE__))}/assets/#{params[:f]}", "r")
          
      result = file.read()    
          
      file.close
          
      render :text => result
    else 
      render :text => ""
    end
    
  end

end 
