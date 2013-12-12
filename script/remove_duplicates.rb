
def start

  people = Person.find_by_sql("SELECT given_name, middle_name, family_name, gender, birthdate, count(id) as dup_count
FROM openmrs_chalasa_new.people group by given_name, middle_name,
family_name, gender, birthdate having dup_count > 1")

  (people || {}).each do |person|

    duplicates = Person.find(:all,
                             :conditions => ["given_name = ? and middle_name = ? and family_name = ? and gender = ? and birthdate = ?",person.given_name, person.middle_name, person.family_name, person.gender, person.birthdate])
    count = 0
    (duplicates || []).each do |duplicate|
       unless count == 0
         void(duplicate)
       end
      count += 1
    end

  end

end

def void (person)


  nat_id = NationalIdentifier.find_by_person_id(person.id)
  nat_id.update_attributes({:voided => 1, :void_reason => "duplicate entry", :date_voided => Date.today})
  person.update_attributes({:voided => 1, :void_reason => "duplicate entry", :date_voided => Date.today})
end

start