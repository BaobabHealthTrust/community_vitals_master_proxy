package org.baobabhealthtrust.appwebview.models;

public class PersonIdentifier {

	// private variables
	String _date_voided;
	String _void_reason;
	String _date_created;
	int _identifier_type;
	String _identifier;
	int _person_identifier_id;
	int _voided;
	int _person_id;

	// Empty constructor
	public PersonIdentifier() {
	}

	// constuctor
	public PersonIdentifier(String date_voided, String void_reason,
			String date_created, int identifier_type, String identifier,
			int person_identifier_id, int voided, int person_id) {

		this._date_voided = date_voided;
		this._void_reason = void_reason;
		this._date_created = date_created;
		this._identifier_type = identifier_type;
		this._identifier = identifier;
		this._person_identifier_id = person_identifier_id;
		this._voided = voided;
		this._person_id = person_id;
	}

	// getting date_voided
	public String getDateVoided() {
		return this._date_voided;
	}

	// setting date_voided
	public void setDateVoided(String date_voided) {
		this._date_voided = date_voided;
	}

	// getting void_reason
	public String getVoidReason() {
		return this._void_reason;
	}

	// setting void_reason
	public void setVoidReason(String void_reason) {
		this._void_reason = void_reason;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	// setting person_id
	public void setPersonID(int id) {
		this._person_id = id;
	}

	// getting person_id
	public int getPersonID() {
		return this._person_id;
	}

	// getting identifier_type
	public int getIdentifierType() {
		return this._identifier_type;
	}

	// setting identifier_type
	public void setIdentifierType(int identifier_type) {
		this._identifier_type = identifier_type;
	}

	// getting identifier
	public String getIdentifier() {
		return this._identifier;
	}

	// setting identifier
	public void setIdentifier(String identifier) {
		this._identifier = identifier;
	}

	// getting person_identifier_id
	public int getPersonIdentifierId() {
		return this._person_identifier_id;
	}

	// setting person_identifier_id
	public void setPersonIdentifierId(int person_identifier_id) {
		this._person_identifier_id = person_identifier_id;
	}

	// getting voided
	public int getVoided() {
		return this._voided;
	}

	// setting voided
	public void setVoided(int voided) {
		this._voided = voided;
	}

}