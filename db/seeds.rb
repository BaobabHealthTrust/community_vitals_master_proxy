# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)

Dir.foreach("db/data").sort.each do |file|
  next if file == "." or file == ".." 
  
  if file.match(/.json$/)
  
      json = ActiveSupport::JSON.decode(File.read("db/data/#{file}"))

      root = file.gsub(/.json$/, "")

      puts "== #{root.gsub(/\_/, " ").titleize.gsub(/\s/, "")}: seeding started ============================"
          
      json.each do |a|
      
        root.gsub(/\_/, " ").titleize.gsub(/\s/, "").constantize.create!(a[root])
        
      end
       
      puts "== #{root.gsub(/\_/, " ").titleize.gsub(/\s/, "")}: seeded ============================"
      
  elsif file.match(/.sql$/)
  
      config = YAML.load_file("#{Rails.root}/config/database.yml")[Rails.env]
  
      puts "== Loading #{file} ==============================================="
          
      system("mysql -u #{config["username"]} -p#{config["password"]} #{config["database"]} < db/data/#{file}")
      
      puts "== #{file} loaded ==============================================="    
  
  end
  
end

