#!/usr/bin/env ruby

require "rubygems"
require "json"
require "mysql"
require 'bantu_soundex'

def colorize(text, color_code)
  "\e[#{color_code}m#{text}\e[0m"
end

def red(text); colorize(text, 31); end
def green(text); colorize(text, 32); end
def yellow(text); colorize(text, 33); end
def blue(text); colorize(text, 34); end
def magenta(text); colorize(text, 35); end
def cyan(text); colorize(text, 36); end

system("clear")

puts ""
print green("\tEnter MySQL username: ")

$user = gets.chomp

system("clear")

`stty -echo`
puts ""
print green("\tEnter MySQL password: ")

$pass = gets.chomp
`stty echo`

system("clear")

puts ""
print green("\tEnter Source Database Name: ")

$db = gets.chomp

system("clear")

puts ""
print green("\tEnter Target Destination Name: ")

$dst = gets.chomp

system("clear")

$host = "localhost"

#query = `mysql -u #{$user} -p#{$pass} #{$dst} < initial.sql`

$con = Mysql.connect($host, $user, $pass, $db)

$dstcon = Mysql.connect($host, $user, $pass, $dst)

rs = $con.query("SELECT #{$db}.person_name.person_id as id, identifier as national_id, city_village as creator_site_id, given_name, middle_name, family_name, gender, birthdate, birthdate_estimated, city_village as village, #{$db}.person_name.date_created as created_at FROM #{$db}.person_name LEFT OUTER JOIN #{$db}.person ON #{$db}.person.person_id = #{$db}.person_name.person_id LEFT OUTER JOIN #{$db}.person_address ON #{$db}.person_address.person_id = #{$db}.person.person_id LEFT OUTER JOIN #{$db}.patient ON #{$db}.patient.patient_id = #{$db}.person.person_id LEFT OUTER JOIN #{$db}.patient_identifier ON #{$db}.patient_identifier.patient_id = #{$db}.patient.patient_id WHERE #{$db}.patient_identifier.identifier_type = 3 AND #{$db}.patient_identifier.voided = 0;")

rs.each_hash do |person| 

  puts "Importing person #{person["id"]}..."
  
  irs = $dstcon.query("INSERT INTO national_identifiers (id, identifier, person_id, site_id, assigned_gvh, assigned_vh, requested_by_vh,assigned_at, created_at, updated_at) VALUES (#{person["id"]}, '#{person["national_id"]}', #{person["id"]}, 'MTA', 'Mtema1', '#{person["village"]}', 1,'#{person["created_at"]}' ,'#{person["created_at"]}', '#{person["created_at"]}')")
  
  irs = $dstcon.query("INSERT INTO people (id, national_id, given_name, middle_name, family_name,given_name_code, family_name_code, gender, birthdate, birthdate_estimated, village, gvh, ta, created_at, updated_at) VALUES (#{person["id"]}, #{person["id"]}, \"#{person["given_name"]}\", \"#{person["middle_name"]}\", \"#{person["family_name"]}\", \"#{person["given_name"].soundex}\", \"#{person["family_name"].soundex}\", \"#{(person["gender"].match(/F/) ? "Female" : "Male")}\", \"#{person["birthdate"]}\", \"#{person["birthdate_estimated"]}\", \"#{person["village"]}\", \"Mtema1\", \"Mtema\", \"#{person["created_at"]}\", \"#{person["created_at"]}\")")
  
  puts ".. Done."
  
end


