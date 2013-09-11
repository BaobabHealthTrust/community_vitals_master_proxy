class CorePerson < ActiveRecord::Base
  set_table_name "person"
  set_primary_key "person_id"
  include CoreOpenmrs

  cattr_accessor :session_datetime
  cattr_accessor :migrated_datetime
  cattr_accessor :migrated_creator
  cattr_accessor :migrated_location

  # has_one :patient, :class_name => "CorePatient", :foreign_key => :patient_id, :dependent => :destroy, :conditions => {:voided => 0}
  has_many :names, :class_name => 'CorePersonName', :foreign_key => :person_id, :dependent => :destroy, :order => 'person_name.preferred DESC', :conditions => {:voided => 0}
  # has_many :addresses, :class_name => 'CorePersonAddress', :foreign_key => :person_id, :dependent => :destroy, :order => 'person_address.preferred DESC', :conditions => {:voided => 0}
  # has_many :person_attributes, :class_name => 'CorePersonAttribute', :foreign_key => :person_id, :conditions => {:voided => 0}

  def after_void(reason = nil)
    self.patient.void(reason) rescue nil
    self.names.each{|row| row.void(reason) }
    self.addresses.each{|row| row.void(reason) }
    # self.relationships.each{|row| row.void(reason) }
    self.person_attributes.each{|row| row.void(reason) }
    # We are going to rely on patient => encounter => obs to void those
  end

  def self.create_patient_from_dde(params, dont_recreate_local=false)
    
	  address_params = params["person"]["addresses"]
		names_params = params["person"]["names"]
		patient_params = params["person"]["patient"]
    birthday_params = params["person"]
		params_to_process = params.reject{|key,value|
      key.match(/identifiers|addresses|patient|names|relation|cell_phone_number|home_phone_number|office_phone_number|agrees_to_be_visited_for_TB_therapy|agrees_phone_text_for_TB_therapy/)
    }
		birthday_params = params_to_process["person"].reject{|key,value| key.match(/gender/) }
		person_params = params_to_process["person"].reject{|key,value| key.match(/birth_|age_estimate|occupation/) }


		if person_params["gender"].to_s == "Female"
      person_params["gender"] = 'F'
		elsif person_params["gender"].to_s == "Male"
      person_params["gender"] = 'M'
		end

		unless birthday_params.empty?
		  if birthday_params["birth_year"] == "Unknown"
			  birthdate = Date.new(Date.today.year - birthday_params["age_estimate"].to_i, 7, 1)
        birthdate_estimated = 1
		  else
			  year = birthday_params["birth_year"]
        month = birthday_params["birth_month"]
        day = birthday_params["birth_day"]

        month_i = (month || 0).to_i
        month_i = Date::MONTHNAMES.index(month) if month_i == 0 || month_i.blank?
        month_i = Date::ABBR_MONTHNAMES.index(month) if month_i == 0 || month_i.blank?

        if month_i == 0 || month == "Unknown"
          birthdate = Date.new(year.to_i,7,1)
          birthdate_estimated = 1
        elsif day.blank? || day == "Unknown" || day == 0
          birthdate = Date.new(year.to_i,month_i,15)
          birthdate_estimated = 1
        else
          birthdate = Date.new(year.to_i,month_i,day.to_i)
          birthdate_estimated = 0
        end
		  end
    else
      birthdate_estimated = 0
		end

    passed_params = {"person"=>
        {"data" =>
          {"addresses"=>
            {"address1"=> (address_params["address1"] rescue ""),
            "address2"=> (address_params["address2"] rescue ""),
            "subregion"=> (address_params["subregion"] rescue ""),
            "county_district"=> (address_params["county_district"] rescue ""),
            "neighborhood_cell"=> (address_params["neighborhood_cell"] rescue ""),
            "city_village"=> (address_params["city_village"] rescue "")
          },
          "attributes"=>
            {"occupation"=> (params["person"]["occupation"] rescue ""),
            "cell_phone_number" => (params["person"]["cell_phone_number"] rescue ""),
            "home_phone_number" => (params["person"]["home_phone_number"] rescue ""),
            "office_phone_number" => (params["person"]["office_phone_number"] rescue ""),
            "citizenship" => (params["person"]["citizenship"] rescue ""),
            "race" => (params["person"]["race"] rescue "")
          },
          "patient"=>
            {"identifiers"=>
              {
              "diabetes_number"=>""
            }
          },
          "gender"=> person_params["gender"],
          "birthdate"=> birthdate,
          "birthdate_estimated"=> birthdate_estimated ,
          "names"=>{
            "family_name"=> names_params["family_name"],
            "given_name"=> names_params["given_name"],
            "family_name2"=> names_params["family_name2"],
            "middle_name"=> names_params["middle_name"]
          }
        }
      }
    }

    if !params["remote"]

      @dde_server = self.get_global_property_value("dde_server_ip") rescue "" # GlobalProperty.find_by_property("dde_server_ip").property_value rescue ""

      @dde_server_username = self.get_global_property_value("dde_server_username") rescue "" # GlobalProperty.find_by_property("dde_server_username").property_value rescue ""

      @dde_server_password = self.get_global_property_value("dde_server_password") rescue "" # GlobalProperty.find_by_property("dde_server_password").property_value rescue ""

      uri = "http://#{@dde_server_username}:#{@dde_server_password}@#{@dde_server}/people.json/"

      recieved_params = RestClient.post(uri, passed_params)

      national_id = JSON.parse(recieved_params)["npid"]["value"]
    else
      national_id = params["person"]["patient"]["identifiers"]["National_id"]
    end


    if (dont_recreate_local == false)
      person = self.create_from_form(params["person"])

      identifier_type = CorePatientIdentifierType.find_by_name("National id") || CorePatientIdentifierType.find_by_name("Unknown id")

      person.patient.patient_identifiers.create("identifier" => national_id,
        "identifier_type" => identifier_type.patient_identifier_type_id) unless national_id.blank?
      return person
    else

      return national_id
    end
    
  end

  def self.get_global_property_value(global_property)
		property_value = Settings[global_property]
		if property_value.nil?
			property_value = CoreGlobalProperty.find(:first, :conditions => {:property => "#{global_property}"}
      ).property_value rescue nil
		end
		return property_value
	end

	def self.create_from_form(params)

		address_params = params["addresses"]
		names_params = params["names"]
		patient_params = params["patient"]
		params_to_process = params.reject{|key,value| key.match(/addresses|patient|names|relation|cell_phone_number|home_phone_number|office_phone_number|agrees_to_be_visited_for_TB_therapy|agrees_phone_text_for_TB_therapy/) }
		birthday_params = params_to_process.reject{|key,value| key.match(/gender|attributes/) }
		person_params = params_to_process.reject{|key,value| key.match(/birth_|age_estimate|occupation|identifiers|citizenship|race|attributes/) }

		if person_params["gender"].to_s == "Female"
      person_params["gender"] = 'F'
		elsif person_params["gender"].to_s == "Male"
      person_params["gender"] = 'M'
		end

		person = CorePerson.create(person_params)

		if !birthday_params.empty? && birthday_params["birthdate"].blank?
		  if birthday_params["birth_year"] == "Unknown"
        self.set_birthdate_by_age(person, birthday_params["age_estimate"], person.session_datetime || Date.today)
		  else
        self.set_birthdate(person, birthday_params["birth_year"], birthday_params["birth_month"], birthday_params["birth_day"])
		  end
		end
		person.save

		person.names.create(names_params)
		person.addresses.create(address_params) unless address_params.empty? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Occupation").person_attribute_type_id,
		  :value => params["occupation"]) unless params["occupation"].blank? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Cell Phone Number").person_attribute_type_id,
		  :value => params["cell_phone_number"]) unless params["cell_phone_number"].blank? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Office Phone Number").person_attribute_type_id,
		  :value => params["office_phone_number"]) unless params["office_phone_number"].blank? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Home Phone Number").person_attribute_type_id,
		  :value => params["home_phone_number"]) unless params["home_phone_number"].blank? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Citizenship").person_attribute_type_id,
		  :value => params["citizenship"]) unless params["citizenship"].blank? rescue nil

		person.person_attributes.create(
		  :person_attribute_type_id => CorePersonAttributeType.find_by_name("Race").person_attribute_type_id,
		  :value => params["race"]) unless params["race"].blank? rescue nil

    # TODO handle the birthplace attribute

		if (!patient_params.nil?)
		  patient = person.create_patient

		  patient_params["identifiers"].each{|identifier_type_name, identifier|
        next if identifier.blank?
        identifier_type = CorePatientIdentifierType.find_by_name(identifier_type_name) || CorePatientIdentifierType.find_by_name("Unknown id")
        patient.patient_identifiers.create("identifier" => identifier, "identifier_type" => identifier_type.patient_identifier_type_id)
		  } if patient_params["identifiers"]

		  # This might actually be a national id, but currently we wouldn't know
		  #patient.patient_identifiers.create("identifier" => patient_params["identifier"], "identifier_type" => PatientIdentifierType.find_by_name("Unknown id")) unless params["identifier"].blank?
		end

		return person
	end

  def self.set_birthdate_by_age(person, age, today = Date.today)
    person.birthdate = Date.new(today.year - age.to_i, 7, 1)
    person.birthdate_estimated = 1
  end

  def self.set_birthdate(person, year = nil, month = nil, day = nil)
    raise "No year passed for estimated birthdate" if year.nil?

    # Handle months by name or number (split this out to a date method)
    month_i = (month || 0).to_i
    month_i = Date::MONTHNAMES.index(month) if month_i == 0 || month_i.blank?
    month_i = Date::ABBR_MONTHNAMES.index(month) if month_i == 0 || month_i.blank?

    if month_i == 0 || month == "Unknown"
      person.birthdate = Date.new(year.to_i,7,1)
      person.birthdate_estimated = 1
    elsif day.blank? || day == "Unknown" || day == 0
      person.birthdate = Date.new(year.to_i,month_i,15)
      person.birthdate_estimated = 1
    else
      person.birthdate = Date.new(year.to_i,month_i,day.to_i)
      person.birthdate_estimated = 0
    end
  end

  def national_id_label
    return unless self.national_id
    sex =  self.patient.person.gender.match(/F/i) ? "(F)" : "(M)"
    address = self.address.strip[0..24].humanize.delete("'") rescue ""
    label = ZebraPrinter::StandardLabel.new
    label.font_size = 2
    label.font_horizontal_multiplier = 2
    label.font_vertical_multiplier = 2
    label.left_margin = 50
    label.draw_barcode(50,180,0,1,5,15,120,false,"#{self.national_id}")
    label.draw_multi_text("#{self.name.titleize.delete("'")}") #'
    label.draw_multi_text("#{self.national_id_with_dashes} #{self.birthdate_formatted}#{sex}")
    label.draw_multi_text("#{address}")
    label.print(1)
  end

  def national_id(force = true)
    id = self.patient.patient_identifiers.find_by_identifier_type(CorePatientIdentifierType.find_by_name("National id").id).identifier rescue nil
    return id unless force
    id ||= CorePatientIdentifierType.find_by_name("National id").next_identifier(:patient => self.patient).identifier
    id
  end

  def national_id_with_dashes(force = true)
    id = self.national_id(force)
    id[0..4] + "-" + id[5..8] + "-" + id[9..-1] rescue id
  end

  def name
    "#{self.names.first.given_name} #{self.names.first.family_name}".titleize rescue nil
  end

  def address
    "#{self.addresses.first.city_village}" rescue nil
  end

  def birthdate_formatted
    if self.birthdate_estimated==1
      if self.birthdate.day == 1 and self.birthdate.month == 7
        self.birthdate.strftime("??/???/%Y")
      elsif self.birthdate.day == 15
        self.birthdate.strftime("??/%b/%Y")
      end
    else
      self.birthdate.strftime("%d/%b/%Y")
    end
  end

  def self.search_by_identifier(identifier)
    people = CorePatientIdentifier.find_all_by_identifier(identifier, :conditions => ["voided = 0"]).map{|id|
      id.patient.person
    }.uniq unless identifier.blank? rescue nil

    return people unless people.blank?

    create_from_dde_server = get_global_property_value('create.from.dde.server').to_s == "true" rescue false
    if create_from_dde_server
      dde_server = get_global_property_value("dde_server_ip").to_s rescue ""
      dde_server_username = get_global_property_value("dde_server_username").to_s rescue ""
      dde_server_password = get_global_property_value("dde_server_password").to_s rescue ""
      uri = "http://#{dde_server_username}:#{dde_server_password}@#{dde_server}/people/find.json"
      uri += "?value=#{identifier}"
      p = JSON.parse(RestClient.get(uri)).first rescue nil

      return [] if p.blank?

      birthdate_year = p["person"]["birthdate"].to_date.year rescue "Unknown"
      birthdate_month = p["person"]["birthdate"].to_date.month rescue nil
      birthdate_day = p["person"]["birthdate"].to_date.day rescue nil
      birthdate_estimated = p["person"]["birthdate_estimated"] rescue nil
      gender = p["person"]["gender"] == "F" ? "Female" : "Male"

      passed = {
        "person" => {
          "home_phone_number" => p["person"]["data"]["attributes"]["home_phone_number"],
          "age_estimate" => birthdate_estimated,
          "citizenship" => p["person"]["data"]["attributes"]["citizenship"],
          "birth_day" => birthdate_day,
          "birth_month" => birthdate_month ,
          "race" => p["person"]["data"]["attributes"]["race"],
          "office_phone_number" => p["person"]["data"]["attributes"]["office_phone_number"],
          "occupation" => p["person"]["data"]["attributes"]["occupation"],
          "birth_year"=>birthdate_year,
          "cell_phone_number" => p["person"]["data"]["attributes"]["cell_phone_number"],
          "addresses" => {
            "address1"=>p["person"]["data"]["addresses"]["address1"],
            "address2" => p["person"]["data"]["addresses"]["address2"],
            "subregion" => p["person"]["data"]["addresses"]["subregion"],
            "county_district" => p["person"]["data"]["addresses"]["county_district"],
            "neighborhood_cell" => p["person"]["data"]["addresses"]["neighborhood_cell"],
            "city_village" => p["person"]["data"]["addresses"]["city_village"]
          },
          "gender" => gender ,
          "patient" => {
            "identifiers" => {
              "National id" => p["person"]["value"]
            }
          },
          "names"=>{
            "family_name"=>p["person"]["family_name"],
            "family_name2"=>p["person"]["family_name2"],
            "given_name"=>p["person"]["given_name"],
            "middle_name"=>p["person"]["middle_name"]
          }
        },
        "relation"=>""
      }

      results = CorePatientIdentifier.find_all_by_identifier(passed["person"]["patient"]["identifiers"]["National id"],
        :conditions => ["voided = 0"])

      if results.length == 0

        return [self.create_from_form(passed["person"])]

      else

        patients = []

        results.each{|patient|
          
          patients << CorePerson.find(patient.patient_id)

        }

        return patients
        
      end
    end
    return people
  end

  def age(today = Date.today)
    return nil if self.birthdate.nil?

    # This code which better accounts for leap years
    patient_age = (today.year - self.birthdate.year) + ((today.month -
          self.birthdate.month) + ((today.day - self.birthdate.day) < 0 ? -1 : 0) < 0 ? -1 : 0)

    # If the birthdate was estimated this year, we round up the age, that way if
    # it is March and the patient says they are 25, they stay 25 (not become 24)
    birth_date=self.birthdate
    estimate=self.birthdate_estimated==1
    patient_age += (estimate && birth_date.month == 7 && birth_date.day == 1  &&
        today.month < birth_date.month && self.date_created.year == today.year) ? 1 : 0
  end

  def age_in_months(today = Date.today)
    years = (today.year - self.birthdate.year)
    months = (today.month - self.birthdate.month)
    (years * 12) + months
  end
    
  def maiden_name
    self.names.last.family_name2 rescue ""
  end

  def first_name
    self.names.last.given_name rescue ""
  end

  def last_name
    self.names.last.family_name rescue ""
  end

  def middle_name
    self.names.last.middle_name rescue ""
  end

  def home_phone_number
    self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Home Phone Number").id).value rescue ""
  end

  def nationality
    self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Citizenship").id).value rescue ""
  end

  def office_phone_number
    self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Office Phone Number").id).value rescue ""
  end

  def cell_phone_number
    self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Cell Phone Number").id).value rescue ""
  end

  def occupation
    self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Occupation").id).value rescue ""
  end

  def nationality
    citizenship = self.person_attributes.find_by_person_attribute_type_id(
      CorePersonAttributeType.find_by_name("Citizenship").id).value rescue ""

    if citizenship.downcase == "other"
      citizenship = self.person_attributes.find_by_person_attribute_type_id(
        CorePersonAttributeType.find_by_name("Race").id).value rescue ""
    end

    citizenship
  end

  def home_village
    self.addresses.last.neighborhood_cell rescue ""
  end

  def district_of_origin
    self.addresses.last.subregion rescue ""
  end

  def current_residence_location
    self.addresses.last.city_village rescue ""
  end

  def ancestral_t_a
    self.addresses.last.county_district rescue ""
  end

  def landmark_or_plot_number
    self.addresses.last.address1 rescue ""
  end

  def current_district
    self.addresses.last.address2 rescue ""
  end

  def demographics
    {
      "birth date" => self.birthdate_formatted,
      "gender" => self.gender,
      "attributes"=>{
        "home phone number" => home_phone_number,
        "nationality" => nationality,
        "office phone number" => office_phone_number,
        "occupation" => occupation,
        "cell phone number" => cell_phone_number
      },
      "addresses"=>{
        "home village" => home_village,
        "district of origin" => district_of_origin,
        "current residence" => current_residence_location,
        "ancestral traditional authority" => ancestral_t_a,
        "landmark or plot number" => landmark_or_plot_number,
        "current district" => current_district
      },
      "names"=>{
        "first name" => first_name,
        "middle name" => middle_name,
        "last name" => last_name,
        "maiden name" => maiden_name
      },
      "patient"=>{
        "identifiers"=>{
          "national id" => national_id
        }
      },
      "birthdate estimated" => "1",
      "patient_id" => self.id
    }
  end

  def check_old_national_id(identifier, user_id)
    create_from_dde_server = get_global_property_value('create.from.dde.server').to_s == "true" rescue false
    
    if create_from_dde_server

      if identifier.to_s.strip.length != 6 and identifier == self.national_id

        dde_server = get_global_property_value("dde_server_ip").to_s rescue ""
        dde_server_username = get_global_property_value("dde_server_username").to_s rescue ""
        dde_server_password = get_global_property_value("dde_server_password").to_s rescue ""
        uri = "http://#{dde_server_username}:#{dde_server_password}@#{dde_server}/people/find.json"
        uri += "?value=#{identifier}"
        p = JSON.parse(RestClient.get(uri)).first rescue nil

        if !p.blank?

          current_national_id = get_full_identifier("National id")

          if current_national_id.identifier == identifier

            set_identifier("Old Identification Number", current_national_id.identifier)

            current_national_id.void("National ID version change", user_id)

            set_identifier("National id", p["person"]["value"])

          end

          return true

        end
        
        person = {"person" => {
            "birthdate_estimated" => (self.person.birthdate_estimated rescue nil),
            "gender" => (self.gender rescue nil),
            "birthdate" => (self.birthdate rescue nil),
            "birth_year" => (self.birthdate.to_date.year rescue nil),
            "birth_month" => (self.birthdate.to_date.month rescue nil),
            "birth_day" => (self.birthdate.to_date.date rescue nil),
            "names" => {
              "given_name" => self.first_name,
              "family_name" => self.last_name,
              "family_name2" => self.maiden_name,
              "middle_name" => self.middle_name
            },
            "patient" => {
              "identifiers" => {
                "old_identification_number" => self.national_id
              }
            },
            "attributes" => {
              "occupation" => (self.get_full_attribute("Occupation").value rescue nil),
              "cell_phone_number" => (self.get_full_attribute("Cell Phone Number").value rescue nil),
              "office_phone_number" => (self.get_full_attribute("Office Phone Number").value rescue nil),
              "home_phone_number" => (self.get_full_attribute("Home Phone Number").value rescue nil),
              "citizenship" => (self.get_full_attribute("Citizenship").value rescue nil),
              "race" => (self.get_full_attribute("Race").value rescue nil)
            },
            "addresses" => {
              "address1" => (self.landmark_or_plot_number rescue nil),
              "city_village" => (self.current_residence_location rescue nil),
              "address2" => (self.current_district rescue nil),
              "county_district" => (self.ancestral_t_a rescue nil),
              "neighborhood_cell" => (self.home_village rescue nil),
              "subregion" => (self.district_of_origin rescue nil)
            }
          }
        }

        current_national_id = get_full_identifier("National id")

        set_identifier("Old Identification Number", current_national_id.identifier)

        current_national_id.void("National ID version change", user_id)

        national_id = CorePerson.create_patient_from_dde(person, true)

        set_identifier("National id", national_id)

      end
    end
    
  end

  def get_full_attribute(attribute)
    CorePersonAttribute.find(:first,:conditions =>["voided = 0 AND person_attribute_type_id = ? AND person_id = ?",
        CorePersonAttributeType.find_by_name(attribute).id,self.person.id]) rescue nil
  end

  def set_attribute(attribute, value)
    CorePersonAttribute.create(:person_id => self.person.person_id, :value => value,
      :person_attribute_type_id => (CorePersonAttributeType.find_by_name(attribute).id))
  end

  def get_full_identifier(identifier)
    CorePatientIdentifier.find(:first,:conditions =>["voided = 0 AND identifier_type = ? AND patient_id = ?",
        CorePatientIdentifierType.find_by_name(identifier).id, self.patient.id]) rescue nil
  end

  def set_identifier(identifier, value)
    CorePatientIdentifier.create(:patient_id => self.patient.patient_id, :identifier => value,
      :identifier_type => (CorePatientIdentifierType.find_by_name(identifier).id))
  end

  def change_national_id(user_id)
    create_from_dde_server = get_global_property_value('create.from.dde.server').to_s == "true" rescue false

    current_national_id = get_full_identifier("National id")

    current_national_id.void("National ID conflict resolution", user_id)

    local_national_id = national_id(true)

    if create_from_dde_server

      passed_params = {
        "person" => {
          "data" => {
            "birthdate_estimated" => (self.birthdate_estimated rescue nil),
            "gender" => (self.gender rescue nil),
            "birthdate" => (self.birthdate.to_s rescue nil),
            "birth_year" => (self.birthdate.to_date.year rescue nil),
            "birth_month" => (self.birthdate.to_date.month rescue nil),
            "birth_day" => (self.birthdate.to_date.date rescue nil),
            "names" => {
              "given_name" => self.first_name,
              "family_name" => self.last_name,
              "family_name2" => self.maiden_name,
              "middle_name" => self.middle_name
            },
            "patient" => {
              "identifiers" => {
                "old_identification_number" => self.national_id
              }
            },
            "attributes" => {
              "occupation" => (self.get_full_attribute("Occupation").value rescue nil),
              "cell_phone_number" => (self.get_full_attribute("Cell Phone Number").value rescue nil),
              "office_phone_number" => (self.get_full_attribute("Office Phone Number").value rescue nil),
              "home_phone_number" => (self.get_full_attribute("Home Phone Number").value rescue nil),
              "citizenship" => (self.get_full_attribute("Citizenship").value rescue nil),
              "race" => (self.get_full_attribute("Race").value rescue nil)
            },
            "addresses" => {
              "address1" => (self.landmark_or_plot_number rescue nil),
              "city_village" => (self.current_residence_location rescue nil),
              "address2" => (self.current_district rescue nil),
              "county_district" => (self.ancestral_t_a rescue nil),
              "neighborhood_cell" => (self.home_village rescue nil),
              "subregion" => (self.district_of_origin rescue nil)
            }
          }
        }
      }

      @dde_server = self.get_global_property_value("dde_server_ip") rescue ""

      @dde_server_username = self.get_global_property_value("dde_server_username") rescue ""

      @dde_server_password = self.get_global_property_value("dde_server_password") rescue ""

      uri = "http://#{@dde_server_username}:#{@dde_server_password}@#{@dde_server}/people.json/"

      recieved_params = RestClient.post(uri, passed_params)

      local_national_id = JSON.parse(recieved_params)["npid"]["value"] rescue nil
      
      if local_national_id.nil?

        local_national_id = national_id(true)
        
      else

        current_national_id = get_full_identifier("National id")

        current_national_id.void("National ID conflict resolution", user_id)

        set_identifier("National id", local_national_id)

      end


      return local_national_id
 
    end

    return ""
    
  end

  def self.update_demographics(params, user_id)
    person = CorePerson.find(params['person_id'])

    if params.has_key?('person')
      params = params['person']
    end

    address_params = params["addresses"]
    names_params = params["names"]
    patient_params = params["patient"]
    person_attribute_params = params["attributes"]

    params_to_process = params.reject{|key,value| key.match(/addresses|patient|names|attributes/) }
    birthday_params = params_to_process.reject{|key,value| key.match(/gender|occupation/) }

    person_params = params_to_process.reject{|key,value| key.match(/birth_|age_estimate|occupation/) }

    if !birthday_params.empty?
=begin


		if !birthday_params.empty? && birthday_params["birthdate"].blank?
		  if birthday_params["birth_year"] == "Unknown"
        self.set_birthdate_by_age(person, birthday_params["age_estimate"], person.session_datetime || Date.today)
		  else
        self.set_birthdate(person, birthday_params["birth_year"], birthday_params["birth_month"], birthday_params["birth_day"])
		  end
		end

=end
      if birthday_params["birth_year"] == "Unknown"
        self.set_birthdate_by_age(person, birthday_params["age_estimate"])
      else
        self.set_birthdate(person, birthday_params["birth_year"], birthday_params["birth_month"], birthday_params["birth_day"])
      end

      person.birthdate_estimated = 1 if params["birthdate_estimated"] == 'true'
      person.save
    end

    person.update_attributes(person_params) if !person_params.empty?
    person.names.first.update_attributes(names_params) if names_params
    person.addresses.first.update_attributes(address_params) if address_params && !person.addresses.empty?

    person.addresses.create(address_params) if person.addresses.empty? && address_params

    #update or add new person attribute
    person_attribute_params.each{|attribute_type_name, attribute|
      attribute_type = CorePersonAttributeType.find_by_name(attribute_type_name.humanize.titleize) ||
        CorePersonAttributeType.find_by_name("Unknown id")
      #find if attribute already exists
      exists_person_attribute = CorePersonAttribute.find(:first, :conditions =>
          ["person_id = ? AND person_attribute_type_id = ?", person.id, attribute_type.person_attribute_type_id]) rescue nil
      if exists_person_attribute
        exists_person_attribute.update_attributes({'value' => attribute})
      else
        person.person_attributes.create("value" => attribute, "person_attribute_type_id" =>
            attribute_type.person_attribute_type_id)
      end
    } if person_attribute_params

  end

end
