require 'json'
#!/usr/bin/env ruby
def start

  settings = YAML.load_file("#{Rails.root}/config/application.yml")["dde_ta"] rescue {}

  unless settings.empty?

     username = settings["username"]
     password = settings["password"]
     target = settings["target_server"]
     site_code = settings["site_code"]

     ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
     district= YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["district"] rescue nil

     url = "http://#{username}:#{password}@#{target}/people/push_demographics_to_traditional_authority"

     ack_url = "http://#{username}:#{password}@#{target}/people/acknowledge_traditional_authority_push"

     params = {
        :district => district, :traditional_authority => ta, :site_code => site_code
     }

     demographics = RestClient.post(url,params)

     puts "Received #{JSON.parse(demographics).count} New ID's'"
     (JSON.parse(demographics) || {}).each do |key, data|

       ActiveRecord::Base.transaction do
         new_id = NationalIdentifier.new()
         new_id.identifier = key
         new_id.site_id = site_code
         new_id.assigned_vh = data["data"]["attributes"]["neighbourhood_cell"]
         new_id.requested_by_gvh = 1,
         new_id.requested_by_vh = 1

         if new_id.save
          new_person = Person.new()
          new_person.given_name = data["data"]["names"]["given_name"]
          new_person.family_name = data["data"]["names"]["family_name"]
          new_person.gender = data["gender"]
          new_person.birthdate = data["birthdate"]
          new_person.birthdate_estimated = data["birthdate_estimated"]
          new_person.occupation = data["data"]["attributes"]["occupation"]
          new_person.address2 = data["data"]["addresses"]["address2"]
          new_person.city_village = data["data"]["addresses"]["city_village"]
          new_person.state_province = data["data"]["addresses"]["state_province"]
          new_person.county_district = data["data"]["addresses"]["county_district"]
          new_person.cell_phone_number = data["data"]["attributes"]["cell_phone_number"]
          new_person.neighbourhood_cell = data["data"]["attributes"]["neighbourhood_cell"]
          new_person.village = data["data"]["attributes"]["neighbourhood_cell"]
          new_person.ta =  data["data"]["addresses"]["county_district"]
          new_person.national_id = new_id.id
          new_person.save
          new_id.update_attributes(:person_id => new_person.id)
          puts "#{key} ........OK"
         else
           puts "#{key} ........Failed"
         end

       end

     end
     puts "Done"
     demographics = RestClient.post(ack_url,params)

  end

end

start
