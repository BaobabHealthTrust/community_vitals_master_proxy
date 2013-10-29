class Outcome < ActiveRecord::Base
  belongs_to :person, :foreign_key => :person_id

  def name
    OutcomeType.find(self.outcome_type).name
  end

  def self.new_outcomes(type)
    case type
      when "VH"
        Outcome.all(:conditions => [" posted_by_vh = ? AND voided = ?",0, 0])
      when "GVH"
        Outcome.all(:conditions => ["posted_by_gvh = ? AND voided = ?",0, 0])
      when "TA"
        Outcome.all(:conditions => ["posted_by_ta = ? AND voided = ?",0,  0])
    end
  end

end
