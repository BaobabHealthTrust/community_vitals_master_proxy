class PeopleController < ApplicationController

  def add_person
    # raise session.to_yaml
  end

  def search  
    if params[:next_url].nil?
      
      flash[:error] = "No destination provided!"
      
      redirect_to "/" and return
      
    end
  end

  def select     
    
    if !params[:filter_by].nil?
        
        if params[:filter_by].to_s.downcase == "birthdate"
        
            if !params[:year_of_birth].nil?
            
                age = 0
                
                if params[:year_of_birth].to_s.downcase == "unknown"
                  
                  age = params[:age].to_i
                  
                elsif params[:month_of_birth].to_s.downcase == "unknown"
                
                  age = ((Date.today - "#{params[:year_of_birth].to_i}-07-15".to_date).to_i / 365).floor
                
                elsif params[:day_of_birth].to_s.downcase == Vocabulary.search("Unknown").downcase
                
                  age = ((Date.today - "#{params[:year_of_birth].to_i}-#{"%02d" % params[:month_of_birth].to_i}-15".to_date).to_i / 365).floor
                
                else
                
                  age = ((Date.today - "#{params[:year_of_birth].to_i}-#{"%02d" % params[:month_of_birth].to_i}-#{"%02d" % params[:day_of_birth].to_i}".to_date).to_i / 365).floor
                
                end
            
                # raise age.to_yaml
            
                @count = Person.all(:conditions => ["given_name = ? AND family_name = ? " + 
                    "AND gender = ? AND FLOOR(DATEDIFF(NOW(),birthdate)/365) = ?",                    
                  params[:first_name], params[:last_name], params[:gender], age]).length
                  
                @people = []
                @details = {}
                
                Person.paginate(:page => params[:page], :per_page => 20,
                  :conditions => ["given_name = ? AND family_name = ? " + 
                    "AND gender = ? AND FLOOR(DATEDIFF(NOW(),birthdate)/365) = ?",                    
                  params[:first_name], params[:last_name], params[:gender], age]).each do |person|
                  
                  @people << ["#{person.given_name} #{person.family_name} (#{person.identifier.identifier} - " + 
                      "#{Vocabulary.search(person.gender)} - " + 
                      "#{Vocabulary.search("Age")}: #{person.age})".strip, "#{person.id}"]      
                      
                  year = person.birthdate.to_date.year
                  month = person.birthdate.to_date.month
                  day = person.birthdate.to_date.day
                      
                  @details[person.id] = {      
                    Vocabulary.search("Name") => "#{person.given_name} #{person.family_name}",
                    Vocabulary.search("First name") => "#{person.given_name}",
                    Vocabulary.search("Middle name") => "#{person.middle_name}",
                    Vocabulary.search("Last name") => "#{person.family_name}",
                    Vocabulary.search("Birthdate") => "#{year}-#{(month == 7 && person.birthdate_estimated == 1 ? "?-?" :
                        (day == 15 && person.birthdate_estimated == 1 ? "#{"%02d" % month}-?" : "#{"%02d" % month}-#{"%02d" % day}"))}",
                    Vocabulary.search("Gender") => person.gender,
                    Vocabulary.search("National ID") => person.identifier.identifier,
                    Vocabulary.search("Relations") => "#{}"
                  }
                  
                end
            
            end
            
        elsif !params[:middle_name].nil?
        
            @count = Person.all(:conditions => ["given_name = ? AND middle_name = ? AND family_name = ? AND gender = ?",                    
              params[:first_name], params[:middle_name], params[:last_name], params[:gender]]).length
              
            @people = []
            @details = {}
            
            Person.paginate(:page => params[:page], :per_page => 20,
              :conditions => ["given_name = ? AND middle_name = ? AND family_name = ? AND gender = ?",                    
              params[:first_name], params[:middle_name], params[:last_name], params[:gender]]).each do |person|
              
              @people << ["#{person.given_name} #{person.family_name} (#{person.identifier.identifier} - " + 
                  "#{Vocabulary.search(person.gender)} - " + 
                  "#{Vocabulary.search("Age")}: #{person.age})".strip, "#{person.id}"]      
                  
              year = person.birthdate.to_date.year
              month = person.birthdate.to_date.month
              day = person.birthdate.to_date.day
                  
              @details[person.id] = {      
                Vocabulary.search("Name") => "#{person.given_name} #{person.family_name}",
                Vocabulary.search("First name") => "#{person.given_name}",
                Vocabulary.search("Middle name") => "#{person.middle_name}",
                Vocabulary.search("Last name") => "#{person.family_name}",
                Vocabulary.search("Birthdate") => "#{year}-#{(month == 7 && person.birthdate_estimated == 1 ? "?-?" :
                    (day == 15 && person.birthdate_estimated == 1 ? "#{"%02d" % month}-?" : "#{"%02d" % month}-#{"%02d" % day}"))}",
                Vocabulary.search("Gender") => person.gender,
                Vocabulary.search("National ID") => person.identifier.identifier,
                Vocabulary.search("Relations") => "#{}"
              }
              
            end
                            
        end
      
    else
    
        @count = Person.all(:conditions => ["given_name = ? AND family_name = ? AND gender = ?",                    
          params[:first_name], params[:last_name], params[:gender]]).length
          
        @people = []
        @details = {}
        
        Person.paginate(:page => params[:page], :per_page => 20,
          :conditions => ["given_name = ? AND family_name = ? AND gender = ?",                                                            
          params[:first_name], params[:last_name], params[:gender]]).each do |person|
          
          @people << ["#{person.given_name} #{person.family_name} (#{person.identifier.identifier} - " + 
              "#{Vocabulary.search(person.gender)} - " + 
              "#{Vocabulary.search("Age")}: #{person.age})".strip, "#{person.id}"]      
              
          year = person.birthdate.to_date.year
          month = person.birthdate.to_date.month
          day = person.birthdate.to_date.day
              
          @details[person.id] = {      
            Vocabulary.search("Name") => "#{person.given_name} #{person.family_name}",
            Vocabulary.search("First name") => "#{person.given_name}",
            Vocabulary.search("Middle name") => "#{person.middle_name}",
            Vocabulary.search("Last name") => "#{person.family_name}",
            Vocabulary.search("Birthdate") => "#{year}-#{(month == 7 && person.birthdate_estimated == 1 ? "?-?" :
                (day == 15 && person.birthdate_estimated == 1 ? "#{"%02d" % month}-?" : "#{"%02d" % month}-#{"%02d" % day}"))}",
            Vocabulary.search("Gender") => person.gender,
            Vocabulary.search("National ID") => person.identifier.identifier,
            Vocabulary.search("Relations") => "#{}"
          }
          
        end
            
    end
    
  end
                                                                                                                                                                          
  def find_first_name
    result = []
    
    Person.all(:limit => 20, :conditions => ["given_name LIKE ?", "#{params[:search]}%"]).each do |p|
      result << p.given_name
    end if !params[:search].nil?
    
    render :text => "<li>" + result.uniq.join("</li><li>") + "</li>"
  end

  def find_last_name
    result = []
    
    Person.all(:limit => 20, :conditions => ["family_name LIKE ?", "#{params[:search]}%"]).each do |p|
      result << p.family_name
    end if !params[:search].nil?
    
    render :text => "<li>" + result.uniq.join("</li><li>") + "</li>"
  end

  def save_person
    
    identifier = NationalIdentifier.first(:conditions => ["COALESCE(person_id, '') = ''"]) rescue nil
    
    if identifier.nil?
      redirect_to "/people/no_ids" and return
    end
    
    village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil
    
    gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    
    ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    
    if !params[:age].blank? && params[:year_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
      dob = "#{Date.today.year - params[:age].to_i}-07-15"
      estimated = 1
    else
      month = 7
      day = 15
      estimated = 1
      
      if params[:month_of_birth].to_s.strip.downcase != "unknown"
        month = params[:month_of_birth].to_i
        
        if params[:month_of_birth].to_s.strip.downcase != "unknown"
          day = params[:day_of_birth].to_i
                    
          estimated = 0        
        end
        
      end
      
      dob = "#{params[:year_of_birth]}-#{"%02d" % month}-#{"%02d" % day}"
      
    end
      
    if estimated == 0
    
      person = Person.find_or_create_by_given_name_and_middle_name_and_family_name_and_gender_and_birthdate_and_village_and_gvh_and_ta({
        :given_name => params[:first_name],
        :middle_name => params[:middle_name],
        :family_name => params[:last_name],
        :gender => params[:gender],
        :birthdate => dob,
        :birthdate_estimated => estimated,
        :village => village,
        :gvh => gvh,
        :ta => ta
      })
      
    else
    
      person = Person.create({
        :given_name => params[:first_name],
        :middle_name => params[:middle_name],
        :family_name => params[:last_name],
        :gender => params[:gender],
        :birthdate => dob,
        :birthdate_estimated => estimated,
        :village => village,
        :gvh => gvh,
        :ta => ta
      })
      
    end
    
    identifier.update_attributes({:person_id => person.id})        
    
    person.update_attributes({:national_id => identifier.id})        
    
    redirect_to "/"
    
  end

  def update_outcome
  end

  def update_person_relationships
    if params[:next_url].nil?
      redirect_to "/people/search?next_url=/people/update_person_relationships" and return
    end
  end

end
