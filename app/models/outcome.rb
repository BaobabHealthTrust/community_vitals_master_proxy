class Outcome < ActiveRecord::Base
  belongs_to :person, :foreign_key => :person_id

  def name
    OutcomeType.find(self.outcome_type).name
  end

end
