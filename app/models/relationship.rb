class Relationship < ActiveRecord::Base
  
  def person
    NationalIdentifier.find_by_identifier(self.person_national_id).person rescue nil
  end
  
  def relative
    NationalIdentifier.find_by_identifier(self.relation_national_id).person # rescue nil
  end
  
  def relation 
    Vocabulary.search( RelationshipType.find(self.person_is_to_relation).relation ) rescue nil
  end

  def new_relationships(type)
    case type
      when "VH"
        Relationship.all(:conditions => [" posted_by_vh = ? AND voided = ?",0, 0])
      when "GVH"
        Relationship.all(:conditions => ["posted_by_gvh = ? AND voided = ?",0, 0])
      when "TA"
        Relationship.all(:conditions => ["posted_by_ta = ? AND voided = ?",0,  0])
    end
  end

end
