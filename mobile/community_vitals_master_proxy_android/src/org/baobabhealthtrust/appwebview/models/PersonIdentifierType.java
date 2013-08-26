package org.baobabhealthtrust.appwebview.models; 

public class PersonIdentifierType{

	// private variables
	String _name;
	int _person_identifier_type_id;
	String _date_created;
	
	// Empty constructor
	public PersonIdentifierType() {
	}

	// constuctor
	public PersonIdentifierType(String name, int person_identifier_type_id, String date_created) {
		
		this._name = name;
		this._person_identifier_type_id = person_identifier_type_id;
		this._date_created = date_created;
		
	}

	 
	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	// getting person_identifier_type_id
	public int getPersonIdentifierTypeId() {
		return this._person_identifier_type_id;
	}

	// setting person_identifier_type_id
	public void setPersonIdentifierTypeId(int person_identifier_type_id) {
		this._person_identifier_type_id = person_identifier_type_id;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	
}