class PeopleController < ApplicationController

  def index

    @person = Person.find(params[:person])
    render :layout => "report"
  end
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
                Vocabulary.search("Gender") => Vocabulary.search(person.gender),
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
              Vocabulary.search("Gender") => Vocabulary.search(person.gender),
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

        year = person.birthdate.to_date.year rescue nil
        month = person.birthdate.to_date.month rescue nil
        day = person.birthdate.to_date.day rescue nil

        @details[person.id] = {
            Vocabulary.search("Name") => "#{person.given_name} #{person.family_name}",
            Vocabulary.search("First name") => "#{person.given_name}",
            Vocabulary.search("Middle name") => "#{person.middle_name}",
            Vocabulary.search("Last name") => "#{person.family_name}",
            Vocabulary.search("Birthdate") => "#{year}-#{(month == 7 && person.birthdate_estimated == 1 ? "?-?" :
                (day == 15 && person.birthdate_estimated == 1 ? "#{"%02d" % month}-?" : "#{"%02d" % month}-#{"%02d" % day}"))}",
            Vocabulary.search("Gender") => Vocabulary.search(person.gender),
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

    estimated = 0
    month = 7
    day = 15
    year = 1900

    if params[:year_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
      year = params[:estimated_year_of_birth].to_i
      estimated = 1
    else
      year = params[:year_of_birth].to_i
    end

    if params[:month_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
      month = params[:estimated_month_of_birth].to_i
      estimated = 1
    else
      month = params[:month_of_birth].to_i
    end

    if params[:day_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
      day = params[:estimated_day_of_birth].to_i
      estimated = 1
    else
      day = params[:day_of_birth].to_i
    end

    dob = "#{year}-#{"%02d" % month}-#{"%02d" % day}"

    person = Person.find(:first, :conditions => [" given_name = ? AND middle_name = ? AND family_name =? AND gender = ?
                                                AND birthdate = ? AND village =? AND gvh = ? AND ta = ? AND voided = 0",
                                                 params[:first_name],params[:middle_name],params[:last_name],params[:gender],
                                                 dob,village,gvh,ta])


    if person.blank?

      person = Person.create({
                                 :given_name => params[:first_name],
                                 :middle_name => params[:middle_name],
                                 :family_name => params[:last_name],
                                 :given_name_code => params[:first_name].soundex,
                                 :family_name_code => params[:last_name].soundex,
                                 :gender => params[:gender],
                                 :birthdate => dob,
                                 :birthdate_estimated => estimated,
                                 :state_province => params[:district],
                                 :neighbourhood_cell => params[:village],
                                 :address2 => params[:ta],
                                 :village => village,
                                 :gvh => gvh,
                                 :ta => ta
                             })

    else

      person.update_attributes({
                                   :given_name => params[:first_name],
                                   :middle_name => params[:middle_name],
                                   :family_name => params[:last_name],
                                   :gender => params[:gender],
                                   :birthdate => dob,
                                   :birthdate_estimated => estimated,
                                   :state_province => params[:district],
                                   :neighbourhood_cell => params[:village],
                                   :address2 => params[:ta],
                                   :village => village,
                                   :gvh => gvh,
                                   :ta => ta
                               })

    end

    (params["person_attributes"] || []).each do |attribute_type, value|
      attribute_type_id = AttributeType.find_by_attribute(attribute_type)
      PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                              :value => value, :creator => params[:creator]})

    end

    attribute_type_id = AttributeType.find_by_attribute("place of birth")
    PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                            :value => (params["facility of birth"].blank? ? params["village of birth"] : params["facility of birth"]),
                            :creator => params[:creator]})

    unless params["remarks"].blank?
      attribute_type_id = AttributeType.find_by_attribute("other")
      PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                              :value => params["remarks"], :creator => params[:creator]})
    end

    identifier.update_attributes({:person_id => person.id, :assigned_at => Time.now})

    person.update_attributes({:national_id => identifier.id})

    print_and_redirect("/people/national_id_label?person_id=#{person.id}", "/people/index?person=#{person.id}")

    # redirect_to "/"

  end

  def save_transfer_in

    village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil

    gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil

    ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil

    site_code = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["site_code"] rescue nil

    national_identifier = NationalIdentifier.find_by_identifier(params[:nat_id]) rescue nil


    if national_identifier.blank?

      national_identifier = NationalIdentifier.create({
                                    :identifier => params[:nat_id].upcase,
                                    :site_id => site_code,
                                    :assigned_gvh => gvh,
                                    :assigned_vh => village,
                                    :requested_by_gvh => 1,
                                    :requested_by_vh => 1
                                })

    else
        redirect_to "failed_transfer" and return
    end

    estimated = 0
    month = 7
    day = 15
    year = 1900

    if params[:year_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
      year = params[:estimated_year_of_birth].to_i
      estimated = 1
    else
      year = params[:year_of_birth].to_i
    end

    if params[:month_of_birth].to_s.strip.downcase == "unknown"
      month = params[:estimated_month_of_birth].to_i
      estimated = 1
    else
      month = params[:month_of_birth].to_i
    end

    if params[:day_of_birth] == Vocabulary.search("unknown").downcase
      day = params[:estimated_day_of_birth].to_i
      estimated = 1
    else
      day = params[:day_of_birth].to_i
    end

    dob = "#{year}-#{"%02d" % month}-#{"%02d" % day}"

    person = Person.find(:first, :conditions => [" given_name = ? AND middle_name = ? AND family_name =? AND gender = ?
                                                AND birthdate = ? AND village =? AND gvh = ? AND ta = ? AND voided = 0",
                                                 params[:first_name],params[:middle_name],params[:last_name],params[:gender],
                                                 dob,village,gvh,ta])

    if person.blank?
      person = Person.create({
                                 :given_name => params[:first_name],
                                 :middle_name => params[:middle_name],
                                 :family_name => params[:last_name],
                                 :gender => params[:gender],
                                 :birthdate => dob,
                                 :birthdate_estimated => estimated,
                                 :state_province => params[:district],
                                 :neighbourhood_cell => params[:village],
                                 :address2 => params[:ta],
                                 :national_id => national_identifier.id,
                                 :village => village,
                                 :gvh => gvh,
                                 :ta => ta
                             })

      (params["person_attributes"] || []).each do |attribute_type, value|
        attribute_type_id = AttributeType.find_by_attribute(attribute_type)
        PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                                :value => value, :creator => params[:creator]})

      end

      attribute_type_id = AttributeType.find_by_attribute("place of birth")
      PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                              :value => (params["facility of birth"].blank? ? params["village of birth"] : params["facility of birth"]),
                              :creator => params[:creator]})

      unless params["remarks"].blank?
        attribute_type_id = AttributeType.find_by_attribute("other")
        PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                                :value => params["remarks"], :creator => params[:creator]})
      end

      national_identifier.update_attributes({:person_id => person.id, :assigned_at => Time.now})
      print_and_redirect("/people/national_id_label?person_id=#{person.id}", "/people/index?person=#{person.id}") and return
    end

  end

  def update_outcome
    @outcomes = OutcomeType.all.collect{|r|
      ["#{Vocabulary.search(r.name).titleize}", r.id]
    }
  end

  def update_person_relationships
    @relations = RelationshipType.all.collect{|r|
      ["#{Vocabulary.search(r.relation).titleize}", r.id]
    }
  end

  def national_id_label
    @person = Person.find(params[:person_id])
    print_string = @person.national_id_label # rescue (raise "Unable to find patient (#{params[:person_id]}) or generate a national id label for that patient")
    send_data(print_string,
              :type=>"application/label; charset=utf-8",
              :stream=> false,
              :filename=>"#{params[:person_id]}#{rand(10000)}.lbl",
              :disposition => "inline")
  end

  def re_print_barcode
    person = Person.find(params[:person_id])
    print_and_redirect("/people/national_id_label?person_id=#{person.id}", "/people/index?person=#{person.id}")
  end

  def save_relationship
    # raise params.to_yaml

    person = Person.find(params[:person]) rescue nil
    relative = Person.find(params[:relation_person]) rescue nil
    relation = RelationshipType.find(params[:relation]) rescue nil

    unless person.blank? || relative.blank? || relation.blank?
      Relationship.find_or_create_by_person_national_id_and_relation_national_id_and_person_is_to_relation({
                                                                                                               :person_national_id => person.identifier.identifier,
                                                                                                               :relation_national_id => relative.identifier.identifier,
                                                                                                               :person_is_to_relation => relation.id
                                                                                                           })

      redirect_to "/people/index?person=#{person.id}"
    else
      flash[:error] = "A required field is empty"

      redirect_to "/people/update_person_relationships" and return
    end
  end

  def load_people
    @count = Person.all(:conditions => ["given_name_code = ? AND family_name_code = ? AND gender = ?",
                                        params[:first_name].soundex, params[:last_name].soundex, params[:gender]]).length

    @people = []
    @details = {}

    Person.paginate(:page => params[:page], :per_page => 20,
                    :conditions => ["given_name_code = ? AND family_name_code = ? AND gender = ?",
                                    params[:first_name].soundex, params[:last_name].soundex, params[:gender]]).each do |person|

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
          Vocabulary.search("Gender") => Vocabulary.search(person.gender),
          Vocabulary.search("National ID") => person.identifier.identifier,
          Vocabulary.search("Relations") => person.family.collect{|r|
            "#{r}"
          }.compact.join(",<br />")
      }

    end

    render :text => { :people => @people, :details => @details}.to_json
  end

  def save_outcome
    person = Person.find(params[:person]) rescue nil
    outcome = OutcomeType.find(params[:outcome]) rescue nil

    unless person.blank? || outcome.blank? || params[:outcome_date].blank?
      Outcome.create({
                         :person_id => person.id,
                         :outcome_type => outcome.id,
                         :outcome_date => params[:outcome_date] + " " + Time.now.strftime("%H:%M:%S")
                     })

      person.update_attributes({:outcome => outcome.name,
                                :outcome_date => params[:outcome_date] + " " + Time.now.strftime("%H:%M:%S")})

      redirect_to "/people/index?person=#{person.id}"
    else
      flash[:error] = "A required field is empty"

      redirect_to "/people/update_outcome" and return
    end
  end

  def below_threshold
    threshold = NationalIdentifier.threshold
    ids = NationalIdentifier.available_ids
    
    result = false
    
    result = true if ids < threshold 
    
    render :text => result
  end

  def available_ids
    result = 0
    
    result = NationalIdentifier.available_ids rescue 0
    
    render :text => result
  end

  def connected  
    settings = YAML.load_file("#{Rails.root}/config/application.yml") rescue {}
    
    result = false

    if !settings.empty?
      mode = settings["dde_mode"] rescue nil
      
      server = settings["dde_#{mode}"]["target_server"] rescue nil

      server = server.split(":")[0] if !server.nil?

      result = Net::HTTP.new("#{server}").head("/").kind_of? Net::HTTPOK rescue false
    end

    render :text => result
  end

  def confirm_selection

    @people = []
    @details = {}
    results = Person.find(:all,:conditions => ["given_name_code = ? AND family_name_code = ? AND gender = ?",
                                         params[:first_name].soundex, params[:last_name].soundex, params[:gender]]).each do |x|

      @people <<  ["#{x.given_name} #{x.family_name} (#{x.identifier.identifier} - " +
                                           "#{Vocabulary.search(x.gender)} - " +
                                           "#{Vocabulary.search("Age")}: #{x.age})".strip, "#{x.id}"]


      year = x.birthdate.to_date.year
      month = x.birthdate.to_date.month
      day = x.birthdate.to_date.day

      @details[x.id] = {
          Vocabulary.search("Name") => "#{x.given_name} #{x.family_name}",
          Vocabulary.search("First name") => "#{x.given_name}",
          Vocabulary.search("Middle name") => "#{x.middle_name}",
          Vocabulary.search("Last name") => "#{x.family_name}",
          Vocabulary.search("Birthdate") => "#{year}-#{(month == 7 && x.birthdate_estimated == 1 ? "?-?" :
              (day == 15 && x.birthdate_estimated == 1 ? "#{"%02d" % month}-?" : "#{"%02d" % month}-#{"%02d" % day}"))}",
          Vocabulary.search("Gender") => Vocabulary.search(x.gender),
          Vocabulary.search("National ID") => x.identifier.identifier,
          Vocabulary.search("Relations") => x.family.collect{|r|
            "#{r}"
          }.compact.join(",<br />")
      }

    end
  end

  def edit_person
    @person = Person.find(params[:person])
    render :layout => "report"
  end

  def edit_demographics

  end
  def update_person

    person = Person.find(params[:person])

    case params[:field]
      when 'given_name'
        person.given_name = params[:first_name]
        person.given_name_code = params[:first_name].soundex
      when 'middle_name'
        person.middle_name = params[:middle_name]
      when 'last_name'
        person.family_name = params[:last_name]
        person.family_name_code = params[:last_name].soundex
      when 'gender'
        person.gender = params[:gender]
      when 'dob'
        estimated = 0
        month = 7
        day = 15
        year = 1900

        if params[:year_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
          year = params[:estimated_year_of_birth].to_i
          estimated = 1
        else
          year = params[:year_of_birth].to_i
        end

        if params[:month_of_birth].to_s.strip.downcase == "unknown"
          month = params[:estimated_month_of_birth].to_i
          estimated = 1
        else
          month = params[:month_of_birth].to_i
        end

        if params[:day_of_birth].to_s.strip.downcase == Vocabulary.search("unknown").downcase
          day = params[:estimated_day_of_birth].to_i
          estimated = 1
        else
          day = params[:day_of_birth].to_i
        end

        dob = "#{year}-#{"%02d" % month}-#{"%02d" % day}"

        person.birthdate = dob
        person.birthdate_estimated = estimated
      when 'place_of_birth'
        attribute_type_id = AttributeType.find_by_attribute('place of birth')
        attribute_type_id2 = AttributeType.find_by_attribute('place of birth type')
        attribute = PersonAttribute.find(:last, :conditions => ["person_id = ? and attribute_type_id = ?", person.id, attribute_type_id.id ])

        if attribute.blank?
          PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id.id,
                                  :value => (params["facility of birth"].blank? ? params["village of birth"] : params["facility of birth"]),
                                  :creator => params[:creator]})
        else
          attribute.value = (params["facility of birth"].blank? ? params["village of birth"] : params["facility of birth"])
          attribute.save
        end

        attribute2 = PersonAttribute.find(:last, :conditions => ["person_id = ? and attribute_type_id = ?", person.id, attribute_type_id2.id ])

        if attribute2.blank?
          PersonAttribute.create({:person_id => person.id,:attribute_type_id => attribute_type_id2.id,
                                  :value => params["place of birth type"], :creator => params[:creator]})
        else
          attribute2.value = params["place of birth type"]
          attribute2.save
        end
      when 'current_district'
        person.state_province = params[:district]
        person.neighbourhood_cell = params[:village]
        person.address2 = params[:ta]
    end

    person.save
    print_and_redirect("/people/national_id_label?person_id=#{person.id}", "/people/index?person=#{person.id}") and return
  end
end
