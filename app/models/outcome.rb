class Outcome < ActiveRecord::Base

  def name
    OutcomeType.find(self.outcome_type).name
  end

end
