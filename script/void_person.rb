def colorize(text, color_code)
  "\e[#{color_code}m#{text}\e[0m"
end

def red(text); colorize(text, 31); end
def green(text); colorize(text, 32); end
def yellow(text); colorize(text, 33); end
def blue(text); colorize(text, 34); end
def magenta(text); colorize(text, 35); end
def cyan(text); colorize(text, 36); end

def start


  system("clear")

  puts ""
  print green("\tfirst name of person to void: ")

  fname = gets.chomp

  puts ""
  print green("\tlast name of person to void: ")

  lname = gets.chomp

  begin
    puts ""
    print green("\tEnter person's gender (Male/Female): ")
    gender = gets.chomp.to_s

  end  while (gender.upcase != "MALE" && gender.upcase != "FEMALE" )

  people = Person.find(:all, :conditions => ["given_name = ? AND family_name = ? AND gender = ?", fname, lname, gender.capitalize])

  system("clear")

  puts ""
  puts "Found #{people.length}"

  if people.length > 1
    i = 1
    puts "Parameters given belong to more than one person. Select specific person to void"
    (people || []).each do |person|
      puts "#{i}. #{person.given_name} #{person.family_name}; #{person.gender}; #{person.birthdate}; #{person.created_at.to_date}"
      i +=1
    end
    puts ""
    print green("Enter the number of the person to void (1 - #{people.length}):")
    id = gets.chomp
    void(people[(id.to_i - 1)]) unless (id.to_i < 1 || id.to_i > people.length)

  elsif (people.length == 1)
    void(people.first)
  end

end

def void (person)

  puts "Voiding person with ID: #{person.id}. Name: #{person.given_name} #{person.family_name}; #{person.gender}; #{person.birthdate};Created At #{person.created_at.to_date}"
  nat_id = NationalIdentifier.find_by_person_id(person.id)
  nat_id.update_attributes({:voided => 1, :void_reason => "duplicate entry", :date_voided => Date.today})
  person.update_attributes({:voided => 1, :void_reason => "duplicate entry", :date_voided => Date.today})
end

start