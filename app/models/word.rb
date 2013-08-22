class Word < ActiveRecord::Base

  belongs_to :vocabulary, :class_name => 'Vocabulary', :foreign_key => :vocabulary_id
  
end
