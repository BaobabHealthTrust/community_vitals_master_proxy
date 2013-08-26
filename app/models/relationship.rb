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
  
end
