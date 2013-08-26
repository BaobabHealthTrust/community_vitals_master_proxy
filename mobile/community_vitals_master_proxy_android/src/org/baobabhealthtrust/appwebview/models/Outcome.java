package org.baobabhealthtrust.appwebview.models; 

public class Outcome{

	// private variables
	String _outcome_date;
	String _explanation;
	int _person_id;
	String _date_created;
	int _outcome_type_id;
	int _outcome_id;
	
	// Empty constructor
	public Outcome() {
	}

	// constuctor
	public Outcome(String outcome_date, String explanation, int person_id, String date_created, int outcome_type_id, int outcome_id) {
		
		this._outcome_date = outcome_date;
		this._explanation = explanation;
		this._person_id = person_id;
		this._date_created = date_created;
		this._outcome_type_id = outcome_type_id;
		this._outcome_id = outcome_id;
		
	}

	 
	// getting outcome_date
	public String getOutcomeDate() {
		return this._outcome_date;
	}

	// setting outcome_date
	public void setOutcomeDate(String outcome_date) {
		this._outcome_date = outcome_date;
	}

	// getting explanation
	public String getExplanation() {
		return this._explanation;
	}

	// setting explanation
	public void setExplanation(String explanation) {
		this._explanation = explanation;
	}

	// getting person_id
	public int getPersonId() {
		return this._person_id;
	}

	// setting person_id
	public void setPersonId(int person_id) {
		this._person_id = person_id;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	// getting outcome_type_id
	public int getOutcomeTypeId() {
		return this._outcome_type_id;
	}

	// setting outcome_type_id
	public void setOutcomeTypeId(int outcome_type_id) {
		this._outcome_type_id = outcome_type_id;
	}

	// getting outcome_id
	public int getOutcomeId() {
		return this._outcome_id;
	}

	// setting outcome_id
	public void setOutcomeId(int outcome_id) {
		this._outcome_id = outcome_id;
	}

	
}