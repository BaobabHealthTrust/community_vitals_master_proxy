package org.baobabhealthtrust.appwebview.models; 

public class OutcomeType{

	// private variables
	String _name;
	String _date_created;
	int _outcome_type_id;
	
	// Empty constructor
	public OutcomeType() {
	}

	// constuctor
	public OutcomeType(String name, String date_created, int outcome_type_id) {
		
		this._name = name;
		this._date_created = date_created;
		this._outcome_type_id = outcome_type_id;
		
	}

	 
	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
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

	
}