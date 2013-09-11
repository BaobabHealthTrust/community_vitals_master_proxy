require 'digest/sha1'

class CoreUser < ActiveRecord::Base
  set_table_name :users
  set_primary_key :user_id
  
  include CoreOpenmrs

  cattr_accessor :current_user
  attr_accessor :plain_password

  has_many :user_properties, :class_name => "CoreUserProperty", :foreign_key => :user_id
  has_many :user_roles, :class_name => "CoreUserRole", :foreign_key => :user_id, :dependent => :delete_all

  def first_name
    CoreUserProperty.find_by_user_id_and_property(self.user_id, "First Name").property_value rescue ""
  end

  def last_name
    CoreUserProperty.find_by_user_id_and_property(self.user_id, "Last Name").property_value rescue ""
  end

  def gender
    CoreUserProperty.find_by_user_id_and_property(self.user_id, "Gender").property_value rescue ""
  end

  def name
    self.first_name + " " + self.last_name
    # CorePerson.find(self.user_id).name
  end
    
  def before_create    
    self.salt = CoreUser.random_string(10) if !self.salt?
    self.password = encrypt(self.password, self.salt) #if self.plain_password
  end
   
  def self.authenticate(login, password) 
     
    u = find :first, :conditions => {:username => login} 
    
    u && u.authenticated?(password) ? u : nil
    #raise password
  end
      
  def authenticated?(plain)
    #  raise "#{plain} #{password} #{encrypt(plain, salt)} #{salt} :  #{Digest::SHA1.hexdigest(plain+salt)} : #{self.salt}"
    #raise "#{self.salt}"
    encrypt(plain, salt) == password || Digest::SHA1.hexdigest("#{plain}#{salt}") == password
  end

  def admin?
    admin = user_roles.map{|user_role| user_role.role }.include? 'Informatics Manager'
    admin = user_roles.map{|user_role| user_role.role }.include? 'System Developer' unless admin
    admin = user_roles.map{|user_role| user_role.role }.include? 'Superuser' unless admin
    admin
  end
      
  # Encrypts plain data with the salt.
  # Digest::SHA1.hexdigest("#{plain}#{salt}") would be equivalent to
  # MySQL SHA1 method, however OpenMRS uses a custom hex encoding which drops
  # Leading zeroes
  def encrypt(plain, salt)
    encoding = ""
    digest = Digest::SHA1.digest("#{plain}#{salt}") 
    (0..digest.size-1).each{|i| encoding << digest[i].to_s(16) }
    encoding
  end  

  def self.random_string(len)
    #generat a random password consisting of strings and digits
    chars = ("a".."z").to_a + ("A".."Z").to_a + ("0".."9").to_a
    newpass = ""
    1.upto(len) { |i| newpass << chars[rand(chars.size-1)] }
    return newpass
  end

  def username_hash(login)
    u = User.find(:first, :conditions => {:username => login})
    user_salt = u.salt.scan(/./)[0..2]
    secret_name = CoreGlobalProperty.find_by_property('server.secret_name').property_value.scan(/./)[0..2]
    user_name = login.scan(/./)
    space = CoreGlobalProperty.find_by_property('server.secret_name_space').property_value.to_i

    intermediate = Array.new(user_salt.length * space + secret_name.length * space)


    salt_position = 0
    secret_name_position = 0

    length = 0
    for length in 0..intermediate.length - 1
      if salt_position < user_salt.length && length.even?
        intermediate[length] = user_salt[salt_position]
        salt_position += 1
      elsif secret_name_position < secret_name.length && length.odd?
        intermediate[length] = secret_name[secret_name_position]
        secret_name_position +=1
      end
    end
    intermediate.compact!

    last = Array.new(intermediate.length * space + user_name.length * space)


    length = 0
    user_name_position = 0
    intermediate_position = 0

    for length in 0..last.length-1
      if user_name_position < user_name.length && length.odd?
        last[length] = user_name[user_name_position]
        user_name_position += 1
      elsif intermediate_position < intermediate.length && length.even?
        last[length] = intermediate[intermediate_position]
        intermediate_position += 1
      end
    end

    last.compact!
    return last


  end

  def self.decode_hash(login_barcode)
    space = CoreGlobalProperty.find_by_property('server.secret_name_space').property_value.to_i
    login = ''
    login_array = login_barcode.scan(/./)
    position = space - 1
    6.times{
      login += login_array[position] rescue nil
      position += space
     
    }
    return CoreUser.find(:first, :conditions => {:username => login})
  end

  def login_barcode
    barcode_to_print = self.username_hash(self.username) rescue nil
    return unless barcode_to_print
    barcode_hash = barcode_to_print.to_s
    label = ZebraPrinter::StandardLabel.new
    label.font_size = 2
    label.font_horizontal_multiplier = 2
    label.font_vertical_multiplier = 2
    label.left_margin = 50
    label.draw_barcode(50,180,0,1,3,12,120,false,"#{barcode_hash}")
    label.draw_multi_text("#{self.name.titleize}")
    label.draw_multi_text('QECH')
    label.draw_multi_text(' ')
    label.print(1)
  end

  def self.save_property(user_id, property, property_value)
    u = CoreUser.find(user_id)
    user_property = CoreUserProperty.find_by_property_and_user_id(property, user_id) rescue nil

    if user_property
      user_property.property_value = property_value
      user_property.save
    else 
      user_property = CoreUserProperty.new()
      user_property.user_id = user_id
      user_property.property = property
      user_property.property_value = property_value
      user_property.save
    end
  end

  def before_update  
    self.salt = CoreUser.random_string(10) if !self.salt?
    self.password = encrypt(self.password, self.salt) #if self.plain_password
  end

  def password_expiry
    property = last_password_update
    user_id = self.id

    expiry_date = CoreUserProperty.find_by_property_and_user_id(property, user_id).property_value rescue nil

    if expiry_date

    else
     self.save_property(user_id, property, Date.today)     
    end
    
  end

  def superuser?
    user_roles.map{|user_role| user_role.role }.include? 'superuser'
  end  

  def manager?
    user_roles.map{|user_role| user_role.role.downcase }.include? 'program manager'
  end

  def status
    CoreUserProperty.find_by_property("Status", :conditions => ["user_id = ?", self.id]) rescue nil
  end

  def status_value
    self.status.property_value rescue nil
  end

  def logged_in?
    user = CoreUserProperty.find_by_user_id_and_property(self.id, "Token") rescue nil

    if user
      return true
    else
      return false
    end
  end

  def demographics
    {
      :user_id => self.id,
      :name => self.name,
      :token => (self.user_properties.find_by_property("Token").property_value rescue nil),
      :roles => (self.user_roles.collect{|r| r.role} rescue []),
      :status => self.status_value
    }
  end

end
