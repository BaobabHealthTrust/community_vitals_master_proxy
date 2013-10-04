require 'json'
#!/usr/bin/env ruby
def start

  settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_ta"] rescue {}

  unless settings.empty?

     username = settings["username"]
     password = settings["password"]
     target = settings["target_server"]
     ta = settings["ta"]
     district= YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["district"] rescue nil

     url = "http://#{username}:#{password}@#{target}/people/push_demographics_to_traditional_authority"

     ack_url = "http://#{username}:#{password}@#{target}/people/acknowledge_traditional_authority_push"

     params = {
        "district"=> district,"traditional_authority" => ta
     }

     demographics = RestClient.post(url,params)

     (JSON.parse(demographics) || {}).each do |key, data|

     end

  end

end

start