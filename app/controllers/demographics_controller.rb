class DemographicsController < ApplicationController
  def daily_summary
    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode'] rescue 'vh'
     day = params[:start_date].to_date
    new_nat_ids = NationalIdentifier.find(:all, :conditions => ["DATE(assigned_at) = ?", day])
    people = new_nat_ids.map{|x| x.person}
    @today_count = get_eligible(people,day)
    @today_gender_count,@today_gender_count_id = gender_counter(people, day)
    @today_ages,@today_ages_ids = age_categorizer(people, day)
    @today_outcomes_id,@today_outcomes = outcome_sorter(Outcome.find(:all, :conditions => ["DATE(outcome_date) = ?", day]))
    @cumulative = cumulative_summarizer(day)
    @village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    @duration = day.day.to_s + ", " + Vocabulary.search(day.strftime('%B').to_s) + " " + day.year.to_s
    render :layout => 'report'
  end

  def gvh_reports
  end

  def gvh_statistics
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @cumulative = cumulative_summarizer(Date.today.to_date)
    render :layout => 'report'
  end

  def gvh_outcomes_by_village
    date = params[:start_date].to_date
    @duration = Vocabulary.search(date.strftime('%B').to_s) +" "+date.strftime('%Y').to_s
    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode'] rescue 'vh'
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    @outcomes, @ids = specific_outcome_sorter(NationalIdentifier.find(:all,:conditions => ['person_id IS NOT NULL']),date.end_of_month,'assigned_vh')
    render :layout => 'report'
  end

  def gvh_births_by_village
    render :layout => 'report'
  end

  def gvh_transfers_by_village
  end

  def village_reports
  end

  def village_statistics

    @village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    @cumulative = cumulative_summarizer(Date.today.to_date)
    render :layout => 'report'
  end
  def report_index

    if params[:type] == "daily_summary"
      @text = "Select Date"
    else
      @text = "Select Month and Year"
    end

    render :layout => 'report'
  end

  def cohort

    @start_date,@end_date = cohort_date_range(params[:quarter])
    nat_ids = NationalIdentifier.find(:all, :conditions => ["DATE(assigned_at) >= ? AND DATE(assigned_at) <= ?", @start_date,@end_date])
    cumulative = cumulative_summarizer(@end_date)
    cohort_gender_count,cohort_gender_count_ids = gender_counter(nat_ids.map{|x| x.person}, @end_date)
    cohort_ages,cohort_ages_ids = age_categorizer(nat_ids.map{|x| x.person},@end_date)
    cohort_outcomes_id, cohort_outcomes = outcome_sorter(nat_ids.map{|x| x.person.outcome_by_date(@end_date)})
    @keys = ['Registration Details','Registered People By Age','Registered People By Outcome']
    @report = {}
    @report['Registration Details'] = [['Registered People', nat_ids.length, cumulative['count']],
                                       ['Males', cohort_gender_count['males'], cumulative['gender_count']['males']],
                                       ['Females', cohort_gender_count['females'], cumulative['gender_count']['females']]
                                      ]

    @report['Registered People By Age'] = [[ '0-12', cohort_ages['children'], cumulative['ages']['children']],
                                            ['12-21', cohort_ages['youth'], cumulative['ages']['youth']],
                                            ['22-59', cohort_ages['adults'], cumulative['ages']['adults']],
                                            ['60 and above', cohort_ages['grannies'], cumulative['ages']['grannies']]]

    @report['Registered People By Outcome'] = [ ['Alive', cohort_outcomes['Alive'], cumulative['outcomes']['Alive'],cohort_outcomes_id['Alive'],cumulative['outcomes_id']['Alive']],
                                                ['Died', cohort_outcomes['Dead'], cumulative['outcomes']['Dead'],cohort_outcomes_id['Dead'],cumulative['outcomes_id']['Dead']],
                                                ['Transferred', cohort_outcomes['Transferred'], cumulative['outcomes']['Transferred'],cohort_outcomes_id['Transferred'],cumulative['outcomes_id']['Transferred']]
                                              ]
    render :layout => 'report'
  end

  def quarterly_report

    if params[:report_type] == "Quarterly"
      @start_date,@end_date = cohort_date_range(params[:quarter])
      s_date = @start_date.strftime('%d').to_s + " " + Vocabulary.search(@start_date.strftime('%B').to_s) + " "+ @start_date.strftime('%Y').to_s
      e_date = @end_date.strftime('%d').to_s + " " + Vocabulary.search(@end_date.strftime('%B').to_s) + " "+ @end_date.strftime('%Y').to_s
      @title = "#{Vocabulary.search('Cohort Report for')} #{params[:quarter]}  (#{s_date} #{Vocabulary.search('To')} #{e_date})"
    elsif params[:report_type] == "Annual"
      @start_date,@end_date = ["01/01/#{params[:year]}".to_date,"12/31/#{params[:year]}".to_date ]
      @title = "#{Vocabulary.search('Cohort Report for')} #{params[:year]}"
    end


    @keys = ['Registered People Alive','Age Distribution', 'New Births', 'Deaths', 'Transfer Out']
    @age_key = ['0 to < 1' , '1-4','5-14', '15-24', '25-34','35-44', '45-54', '55-64', '65-74','75+']
    @report = Hash.new([])
    people = people = Person.find(:all,
                                  :conditions => ["COALESCE(voided,0) = ? AND birthdate <= ? AND (outcome IS NULL OR outcome_date > ? ) ",0,@end_date,@end_date])
    @report['Age Distribution'] = age_sorter(people, @end_date)

    @report['Registered People Alive'] = getPeopleAlive(@start_date,@end_date, people)
    @report['New Births'] = getBirths(@start_date,@end_date)
    @report['Deaths'] = getByOutcome(@start_date,@end_date,"Dead")
    @report['Transfer Out'] = getByOutcome(@start_date,@end_date,"Transfer Out")

    render :layout => 'report'
  end

  def village_outcomes_by_month
    date = params[:start_date].to_date
    @duration = date.strftime('%B, %Y')
    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode'] rescue 'vh'
    @village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    nat_ids = NationalIdentifier.find(:all,
                                      :joins => "INNER JOIN people on national_identifiers.person_id = people.id",
                                      :conditions => ["birthdate <= ?", date.end_of_month])

    month_nat_ids = Person.find_by_sql("SELECT * FROM people WHERE DATE(birthdate) <= DATE('#{date.end_of_month}')")

    @month_summary ={}
    @cumulative_summary = {}
    @month_summary['count'] = get_eligible(month_nat_ids, date.end_of_month)
    @month_summary['outcomes_id'],@month_summary['outcomes'] = outcome_sorter(Outcome.find(:all,
                                                             :conditions => ["MONTH(outcome_date) = ? AND YEAR(outcome_date) = ?",
                                                                             date.month, date.year]))
    (month_nat_ids || []).each do |r|
      if r.outcome_date.blank?
        @month_summary['outcomes_id']['Alive'] << r.id
        @month_summary['outcomes']['Alive'] += 1
      else
        if r.outcome_date.to_date > date.end_of_month
          @month_summary['outcomes_id']['Alive'] << r.id
          @month_summary['outcomes']['Alive'] += 1
        end
      end
    end
    @cumulative_summary['count'] = get_eligible(nat_ids.map{|x| x.person},date.end_of_month)
    @cumulative_summary['outcomes_id'],@cumulative_summary['outcomes'] = outcome_sorter(nat_ids.map{|x| x.person.outcome_by_date(date.end_of_month)})
    render :layout => 'report'
  end

  def village_births_by_month
    date = params[:start_date].to_date
    @duration = Vocabulary.search(date.strftime('%B').to_s) +" "+ date.strftime('%Y')
    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode'] rescue 'vh'
    @village = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["village"] rescue nil
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    people = Person.find(:all, :conditions => ["DATE(birthdate) <= ? AND COALESCE(voided,0) = 0", date.end_of_month])
    new_borns = Person.find(:all, :conditions => ["MONTH(birthdate) = ? AND YEAR(birthdate) = ? AND COALESCE(voided,0) = 0", date.month, date.year])
    @month_summary ={}
    @cumulative_summary = {}
    @cumulative_summary['count'] = get_eligible(people, date.end_of_month)
    @month_summary['count'] = new_borns.length
    @month_summary['gender_count'],@month_summary['gender_count_id']  = gender_sorter(new_borns.map{|x| x}, date )
    @month_summary['outcomes_id'],@month_summary['outcomes'] = outcome_sorter(new_borns.map{|x| x.outcome_by_date(date.end_of_month)})
    @month_summary['outcomes']['Alive'] = @month_summary['count'] - (@month_summary['outcomes']['Transferred'] + @month_summary['outcomes']['Dead'])
    @cumulative_summary['outcomes_id'],@cumulative_summary['outcomes'] = outcome_sorter(people.map{|x| x.outcome_by_date(date.end_of_month)})
    @cumulative_summary['gender_count'],@cumulative_summary['gender_count_id']  = gender_counter(people,date.end_of_month)
    render :layout => 'report'
  end

  def village_transfers_by_month
  end

  def ta_reports
  end

  def ta_statistics
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    @cumulative = cumulative_summarizer(Date.today.to_date)
    render :layout => 'report'
  end

  def ta_outcomes_by_gvh
    date = params[:start_date].to_date
    @duration = date.strftime('%B, %Y')
    @mode = YAML.load_file("#{Rails.root}/config/application.yml")['dde_mode'] rescue 'vh'
    @gvh = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["gvh"] rescue nil
    @ta = YAML.load_file("#{Rails.root}/config/application.yml")[Rails.env]["ta"] rescue nil
    people = NationalIdentifier.find(:all,
                                     :joins => "INNER JOIN people on national_identifiers.person_id = people.id",
                                     :conditions => [' DATE(people.birthdate) <= ?',date.end_of_month])

    @outcomes,@ids = specific_outcome_sorter(people,date.end_of_month,'assigned_gvh')
    render :layout => 'report'
  end

  def ta_births_by_gvh
    render :layout => 'report'
  end

  def ta_transfers_by_gvh
    render :layout => 'report'
  end

  def view_village_demographics
  end

  def view_unposted_village_demographics
  end

  def village_post_demographics
  end

  def gvh_view_demographics
  end

  def gvh_view_unposted_demographics
  end

  def gvh_post_demographics
  end

  def ta_view_demographics
  end

  def ta_view_unposted_demographics
  end

  def ta_post_demographics
  end

  def people_drill_down

    @title = params[:title]
    @data = params[:data]
    @keys = params[:keys]

    render :layout => 'report'
  end

  def outcomes_drill_down

    ids = NationalIdentifier.find(:all ,:conditions =>["id in (?)", params[:data].split(',')])  rescue []
    data = []
    (ids || []).each do |id|
       data << {
           'National ID' => id.identifier,
           'First Name' => id.person.given_name,
           'Last Name' => id.person.family_name,
           'Date of Birth' => id.person.birthdate,
           'Outcome Date' => id.person.outcome_date
       }
    end
    keys = ['National ID', 'First Name', 'Last Name','Date of Birth','Outcome Date']

    redirect_to :action => "people_drill_down", :title => params[:title], :keys => keys, :data => data
  end

  def outcome_drill_down

    outcomes = Outcome.find(:all ,:conditions =>["id in (?)", params[:data].split(',')])  rescue []
    data = []
    (outcomes || []).each do |outcome|
      data << {
          'National ID' => outcome.person.identifier.identifier,
          'First Name' => outcome.person.given_name,
          'Last Name' => outcome.person.family_name,
          'Date of Birth' => outcome.person.birthdate
      }
    end
    keys = ['National ID', 'First Name', 'Last Name','Date of Birth']

    redirect_to :action => "people_drill_down", :title => params[:title], :keys => keys, :data => data
  end
  def age_drill_down
    person = Person.find(:all ,:conditions =>["id in (?)", params[:data].split(',')])
    data = []
    (person || []).each do |person|
      data << {
          'National ID' => person.identifier.identifier,
          'First Name' =>person.given_name,
          'Last Name' => person.family_name,
          'Date of Birth' => person.birthdate
      }
    end
    keys = ['National ID', 'First Name', 'Last Name','Date of Birth']

    redirect_to :action => "people_drill_down", :title => params[:title], :keys => keys, :data => data

  end

  def gender_drill_down

    person = Person.find(:all ,:conditions =>["id in (?)", params[:data].split(',')])
    data = []
    (person || []).each do |person|
      data << {
          'National ID' => person.identifier.identifier,
          'First Name' =>person.given_name,
          'Last Name' => person.family_name,
          'Date of Birth' => person.birthdate
      }
    end
    keys = ['National ID', 'First Name', 'Last Name','Date of Birth']

    redirect_to :action => "people_drill_down", :title => params[:title], :keys => keys, :data => data

  end
  def cumulative_summarizer(date)
    nat_ids = NationalIdentifier.find(:all, :conditions => ["DATE(assigned_at) <= ?", date])
    people = nat_ids.map{|x| x.person}
    cumulative_summary = {}
    cumulative_summary['count'] = get_eligible(people, date)
    cumulative_summary['gender_count'],cumulative_summary['gender_count_id'] = gender_counter(people, date)
    cumulative_summary['ages'],cumulative_summary['ages_id'] = age_categorizer(people, date)
    cumulative_summary['outcomes_id'],cumulative_summary['outcomes'] = outcome_sorter(nat_ids.map{|x| x.person.outcome_by_date(date)})
    cumulative_summary
  end

  def gender_counter(people, date)
    genders = {'males' => 0, 'females'=> 0 }
    genders_id = {'males' => [], 'females'=> [] }
    (people || []).each do |person|
      next if person.outcome_date.to_date <= date rescue false
       if person.gender == "Male"
          genders['males'] +=1
          genders_id['males'] << person.id
       else
         genders['females'] +=1
         genders_id['females'] << person.id
       end
    end

    return [genders,genders_id]
  end

  def gender_sorter(people, date)
    genders = {'males' => 0, 'females'=> 0 }
    genders_id = {'males' => [], 'females'=> [] }
    (people || []).each do |person|
      if person.gender == "Male"
        genders['males'] +=1
        genders_id['males'] << person.id
      else
        genders['females'] +=1
        genders_id['females'] << person.id
      end
    end

    return [genders,genders_id]
  end

  def age_categorizer(people, date)
    age_groups = {'0 to < 1' => 0, '1-4' => 0,'5-14' => 0, '15-24' => 0, '25-34'=> 0,
                  '35-44'=> 0, '45-54' => 0, '55-64' => 0, '65-74'=> 0,'75+'=> 0 }
    age_groups_ids = {'0 to < 1' => [], '1-4' => [],'5-14' => [], '15-24' => [], '25-34'=> [],
                      '35-44'=> [], '45-54' => [], '55-64' => [], '65-74'=> [],'75+'=> []}
    (people || []).each do |person|
      next if person.outcome_date.to_date <= date rescue false
      age = Date.today.year.to_i - person.birthdate.year.to_i rescue 0
      if (age >= 0  && age < 1)
        age_groups['0 to < 1'] +=1
        age_groups_ids['0 to < 1'] << person.id
      elsif (age >= 1 && age <= 4 )
        age_groups['1-4'] +=1
        age_groups_ids['1-4'] << person.id
      elsif (age >= 5 && age <= 14 )
        age_groups['5-14'] +=1
        age_groups_ids['5-14'] << person.id
      elsif (age >= 15 && age <= 24 )
        age_groups['15-24'] +=1
        age_groups_ids['15-24'] << person.id
      elsif (age >= 25 && age <= 34 )
        age_groups['25-34'] +=1
        age_groups_ids['25-34'] << person.id
      elsif (age >= 35 && age <= 44 )
        age_groups['35-44'] +=1
        age_groups_ids['35-44'] << person.id
      elsif (age >= 45 && age <= 54 )
        age_groups['45-54'] +=1
        age_groups['45-54'] << person.id
      elsif (age >= 55 && age <= 64 )
        age_groups['55-64'] +=1
        age_groups['55-64'] << person.id
      elsif (age >= 65 && age <= 74 )
        age_groups['65-74'] +=1
        age_groups['65-74'] << person.id
      elsif (age >= 75)
        age_groups['75+'] +=1
        age_groups_ids['75+'] << person.id
      end

    end
    [age_groups, age_groups_ids]
  end

  def age_sorter(people, date)

    age_groups = {'0 to < 1' => [0,0], '1-4' => [0,0],'5-14' => [0,0], '15-24' => [0,0], '25-34'=> [0,0],
                  '35-44'=> [0,0], '45-54' => [0,0], '55-64' => [0,0], '65-74'=> [0,0],'75+'=> [0,0] }
    gender = {'Male' => 0, 'Female' => 1}
    age_groups_ids = []

    (people || []).each do |person|
      age = date.year.to_i - person.birthdate.year.to_i
      if (age >= 0  && age < 1)
        age_groups['0 to < 1'][gender[person.gender].to_i] +=1
      elsif (age >= 1 && age <= 4 )
        age_groups['1-4'][gender[person.gender].to_i] +=1
      elsif (age >= 5 && age <= 14 )
        age_groups['5-14'][gender[person.gender].to_i] +=1
      elsif (age >= 15 && age <= 24 )
        age_groups['15-24'][gender[person.gender].to_i] +=1
      elsif (age >= 25 && age <= 34 )
        age_groups['25-34'][gender[person.gender].to_i] +=1
      elsif (age >= 35 && age <= 44 )
        age_groups['35-44'][gender[person.gender].to_i] +=1
      elsif (age >= 45 && age <= 54 )
        age_groups['45-54'][gender[person.gender].to_i] +=1
      elsif (age >= 55 && age <= 64 )
        age_groups['55-64'][gender[person.gender].to_i] +=1
      elsif (age >= 65 && age <= 74 )
        age_groups['65-74'][gender[person.gender].to_i] +=1
      elsif (age >= 75)
        age_groups['75+'][gender[person.gender].to_i] +=1
      end
    end
    age_groups
  end

  def outcome_sorter(outcomes)

    outcome = {"Alive" => 0, "Transferred" => 0, "Dead" => 0}
    outcome_id = {"Alive" => [], "Transferred" => [], "Dead" => []}
    (outcomes || []).each do |outcome_type|

      if outcome_type.blank?
        outcome['Alive'] += 1
      else
        if outcome_type.name == 'Dead'
          outcome['Dead'] += 1
          outcome_id['Dead'] << outcome_type.id
        elsif outcome_type.name == 'Transfer Out'
          outcome['Transferred'] += 1
          outcome_id['Transferred'] << outcome_type.id
        else
          outcome['Alive'] += 1
          outcome_id['Alive'] << outcome_type.id
        end
      end
    end
    [outcome_id,outcome]
  end

  def specific_outcome_sorter(ids, end_date, mode)

    collection = {}
    collection_ids = {}

    (ids || []).each do |x|
      outcome = x.person.outcome_by_date(end_date).name rescue 'Alive'
      if collection[x[mode]].blank?
        collection[x[mode]] =  {"Alive" => 0, "Transferred" => 0, "Dead" => 0, "Total" => 0}
        collection_ids[x[mode]] =  {"Alive" => [], "Transferred" => [], "Dead" => [], "Total" => []}
      end
      if outcome == 'Dead'
        collection[x[mode]]['Dead'] += 1
        collection_ids[x[mode]]['Dead'] << x.id
      elsif outcome == 'Transfer Out'
        collection[x[mode]]['Transferred'] += 1
        collection_ids[x[mode]]['Transferred'] << x.id
      else
        collection[x[mode]]['Alive'] += 1
        collection_ids[x[mode]]['Alive'] << x.id
      end
      collection[x[mode]]["Total"] += 1
      collection_ids[x[mode]]["Total"] << x.id
    end
    [collection, collection_ids]
  end

  def cohort_date_range(quarter)


    quarter, quarter_year = quarter.humanize.split(" ")

    quarter_start_dates = ["#{quarter_year}-01-01".to_date, "#{quarter_year}-04-01".to_date, "#{quarter_year}-07-01".to_date, "#{quarter_year}-10-01".to_date]
    quarter_end_dates   = ["#{quarter_year}-03-31".to_date, "#{quarter_year}-06-30".to_date, "#{quarter_year}-09-30".to_date, "#{quarter_year}-12-31".to_date]

    current_quarter   = (quarter.match(/\d+/).to_s.to_i - 1)
    quarter_beginning = quarter_start_dates[current_quarter]
    quarter_ending    = quarter_end_dates[current_quarter]

    date_range = [quarter_beginning, quarter_ending]
    return date_range
  end

  def village_register
    
    count = Person.all(:conditions => ["COALESCE(voided,0) = 0"]).length

    page_size = 10

    current_page = (params[:page].nil? ? 1 : params[:page].to_i)
    
    @next_page = ((current_page * page_size) < count ? current_page + 1 : current_page)

    @previous_page = (current_page > 1 ? current_page - 1 : current_page)

    @last_page = (count / page_size) + 1

    @people = []
    @details = {}

    Person.paginate(:page => current_page, :per_page => page_size, :conditions => "COALESCE(voided,0) = 0").each do |person|

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
          Vocabulary.search("Birthdate") => "#{year}- #{"%02d" % month}-#{"%02d" % day}",
          Vocabulary.search("Gender") => Vocabulary.search(person.gender),
          Vocabulary.search("National ID") => person.identifier.identifier,
          Vocabulary.search("Relations") => "#{}",
          Vocabulary.search("Outcome") => (!person.outcome.blank? ? Vocabulary.search(person.outcome).titlecase : Vocabulary.search("Alive")),
          "synced" => (person.identifier.posted_by_vh rescue 0).to_i
      }

    end
    
  end

  def gvh_register
    
    count = Person.all(:conditions => ["COALESCE(voided,0) = 0"]).length

    page_size = 10

    current_page = (params[:page].nil? ? 1 : params[:page].to_i)
    
    @next_page = ((current_page * page_size) < count ? current_page + 1 : current_page)

    @previous_page = (current_page > 1 ? current_page - 1 : current_page)

    @last_page = (count / page_size) + 1

    @people = []
    @details = {}

    Person.paginate(:page => current_page, :per_page => page_size, :joins => [:identifier], :order => "posted_by_gvh",
        :conditions => "COALESCE(people.voided,0) = 0").each do |person|

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
          Vocabulary.search("Birthdate") => "#{year}- #{"%02d" % month}-#{"%02d" % day}",
          Vocabulary.search("Gender") => person.gender,
          Vocabulary.search("National ID") => person.identifier.identifier,
          Vocabulary.search("Relations") => "#{}",
          Vocabulary.search("Outcome") => (!person.outcome.blank? ? person.outcome : Vocabulary.search("Alive")),
          "synced" => (person.identifier.posted_by_vh rescue 0).to_i,
          "Village" => person.village,
          "Notified" => person.identifier.post_gvh_notified,
          "Posted" => person.identifier.posted_by_gvh
      }

    end
    
  end

  def gvh_flag
    unless params[:p].nil? or params[:s].nil?
      NationalIdentifier.find_by_person_id(params[:p]).update_attributes({
        :post_gvh_notified => params[:s]
      }) rescue nil
    end
    
    render :text => ""
  end

  def ta_register

    count = Person.all(:conditions => ["COALESCE(voided,0) = 0"]).length

    page_size = 10

    current_page = (params[:page].nil? ? 1 : params[:page].to_i)

    @next_page = ((current_page * page_size) < count ? current_page + 1 : current_page)

    @previous_page = (current_page > 1 ? current_page - 1 : current_page)

    @last_page = (count / page_size) + 1

    @people = []
    @details = {}

    Person.paginate(:page => current_page, :per_page => page_size, :joins => [:identifier], :order => "posted_by_ta",
                    :conditions => "COALESCE(people.voided,0) = 0").each do |person|

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
          Vocabulary.search("Birthdate") => "#{year}- #{"%02d" % month}-#{"%02d" % day}",
          Vocabulary.search("Gender") => person.gender,
          Vocabulary.search("National ID") => person.identifier.identifier,
          Vocabulary.search("Relations") => "#{}",
          Vocabulary.search("Outcome") => (!person.outcome.blank? ? person.outcome : Vocabulary.search("Alive")),
          "synced" => (person.identifier.posted_by_ta rescue 0).to_i,
          "Village" => person.village,
          "Notified" => person.identifier.post_gvh_notified,
          "Posted" => person.identifier.posted_by_ta
      }

    end

  end

  def getBirths(start_date,end_date)

    female = 0
    male = 0

    people = Person.find(:all,
                         :conditions => ["COALESCE(voided,0) = ? AND birthdate BETWEEN ? AND ? ",0, start_date, end_date])

    people.each do |person|
       if person.gender == "Male"
         male += 1
       else
         female += 1
       end
    end
    return [[male],[female]]
  end

  def getPeopleAlive(start_date,end_date,people)

    female = 0
    male = 0

    people.each do |person|
      if person.gender == "Male"
        male += 1
      else
        female += 1
      end
    end

    return [[male],[female]]
  end

  def getByOutcome(start_date,end_date, outcome)

    female = 0
    male = 0

    people = Person.find(:all,
                         :conditions => [" COALESCE(voided,0) = ? AND Outcome = ? AND outcome_date BETWEEN ? AND ? ",0,outcome,start_date, end_date])

    people.each do |person|
      if person.gender == "Male"
        male += 1
      else
        female += 1
      end
    end
    return [[male],[female]]
  end

  def get_eligible(people, date)
    count = 0

    (people || []).each do |person|
      if person.outcome_date.blank?
        count += 1
      else
        if person.outcome_date.to_date > date
          count +=1
        end
      end
    end

    count

  end
end

