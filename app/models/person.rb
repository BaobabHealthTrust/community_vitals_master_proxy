class Person < ActiveRecord::Base
  has_one :identifier, :class_name => "NationalIdentifier", :foreign_key => :person_id
  
  def age
    (Date.today - self.birthdate).to_i / 365 rescue "?"
  end
  
  def national_id_label
    return unless self.identifier.identifier
    sex =  self.gender.match(/F/i) ? "(F)" : "(M)"
    address = self.address.strip[0..24].humanize.delete("'") rescue ""
    label = ZebraPrinter::StandardLabel.new
    label.font_size = 2
    label.font_horizontal_multiplier = 2
    label.font_vertical_multiplier = 2
    label.left_margin = 50
    label.draw_barcode(50,180,0,1,5,15,120,false,"#{self.identifier.identifier}")
    label.draw_multi_text("#{self.name.titleize.delete("'")}") #'
    label.draw_multi_text("#{self.identifier.identifier} #{self.birthdate_formatted}#{sex}")
    label.print(1)
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

  def name 
    "#{self.given_name} #{self.family_name}"
  end

  def relationships
    Relationship.all(:conditions => ["person_national_id = ?", self.identifier.identifier])
  end

  def new_relationships(type)
    case type
      when "VH"
        Relationship.all(:conditions => ["person_national_id = ? AND posted_by_vh = 0", self.identifier.identifier])
      when "GVH"
        Relationship.all(:conditions => ["person_national_id = ? AND posted_by_gvh = 0", self.identifier.identifier])
      when "TA"
        Relationship.all(:conditions => ["person_national_id = ? AND posted_by_ta = 0", self.identifier.identifier])
    end

  end

  def npid
    self.identifier.identifier
  end

  def family 
    direct = Relationship.all(:conditions => ["person_national_id = ?", self.identifier.identifier]).collect{|r| 
          "#{r.relative.name rescue nil} [#{Vocabulary.search(r.relative.gender).titleize rescue nil}" + 
          " - #{r.relative.npid rescue nil} (#{r.relative.age})]"
        }.compact
        
    relations = {
      "Parent" => "Child",
      "Child" => "Parent",
      "Spouse" => "Spouse"
    }    
        
    indirect = Relationship.all(:conditions => ["relation_national_id = ?", self.identifier.identifier]).collect{|r| 
          "#{r.person.name rescue nil} [#{Vocabulary.search(r.relative.gender).titleize rescue nil}" + 
          " - #{r.person.npid rescue nil} (#{r.person.age})]"
        }.compact
        
    others = []    
        
    relations = {
      Vocabulary.search("Parent") => Vocabulary.search("Child"),
      Vocabulary.search("Child") => Vocabulary.search("Parent"),
      Vocabulary.search("Spouse") => Vocabulary.search("Spouse")
    }    
        
    Relationship.all(:conditions => ["relation_national_id = ?", self.identifier.identifier]).each{|r| 
        relate = []
        
        r.person.family.each do |e|
        
          if !e.match(self.npid)
          
              relate << e
              
          end
          
        end 
        
        indirect += relate
    }
        
    direct + indirect + others
  end

  def outcomes
    Outcome.all(:conditions => ["person_id = ? AND voided = ?", self.id, 0])
  end

  def new_outcomes(type)
    case type
      when "VH"
        Outcome.all(:conditions => ["person_id = ? AND posted_by_vh = 0 AND voided = ?", self.id, 0])
      when "GVH"
        Outcome.all(:conditions => ["person_id = ? AND posted_by_gvh = 0 AND voided = ?", self.id, 0])
      when "TA"
        Outcome.all(:conditions => ["person_id = ? AND posted_by_ta = 0 AND voided = ?", self.id, 0])
    end

  end

  def outcome_by_date(date)
    Outcome.find(:first, :conditions => ["DATE(outcome_date) <= ? AND person_id = ?", date, self.id], :order => "outcome_date DESC").name rescue nil
  end
end
