package org.baobabhealthtrust.cvr.models; 

public class Outcomes{

	// private variables
	String _created_at;
	String _uuid;
	int _person_id;
	String _updated_at;
	String _date_voided;
	int _outcome_type;
	int _id;
	int _voided;
	String _void_reason;
	String _outcome_date;
	
	// Empty constructor
	public Outcomes() {
	}

	// constuctor
	public Outcomes(String created_at, String uuid, int person_id, String updated_at, String date_voided, int outcome_type, int id, int voided, String void_reason, String outcome_date) {
		
		this._created_at = created_at;
		this._uuid = uuid;
		this._person_id = person_id;
		this._updated_at = updated_at;
		this._date_voided = date_voided;
		this._outcome_type = outcome_type;
		this._id = id;
		this._voided = voided;
		this._void_reason = void_reason;
		this._outcome_date = outcome_date;
		
	}

	 
	// getting created_at
	public String getCreatedAt() {
		return this._created_at;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
	}

	// getting uuid
	public String getUuid() {
		return this._uuid;
	}

	// setting uuid
	public void setUuid(String uuid) {
		this._uuid = uuid;
	}

	// getting person_id
	public int getPersonId() {
		return this._person_id;
	}

	// setting person_id
	public void setPersonId(int person_id) {
		this._person_id = person_id;
	}

	// getting updated_at
	public String getUpdatedAt() {
		return this._updated_at;
	}

	// setting updated_at
	public void setUpdatedAt(String updated_at) {
		this._updated_at = updated_at;
	}

	// getting date_voided
	public String getDateVoided() {
		return this._date_voided;
	}

	// setting date_voided
	public void setDateVoided(String date_voided) {
		this._date_voided = date_voided;
	}

	// getting outcome_type
	public int getOutcomeType() {
		return this._outcome_type;
	}

	// setting outcome_type
	public void setOutcomeType(int outcome_type) {
		this._outcome_type = outcome_type;
	}

	// getting id
	public int getId() {
		return this._id;
	}

	// setting id
	public void setId(int id) {
		this._id = id;
	}

	// getting voided
	public int getVoided() {
		return this._voided;
	}

	// setting voided
	public void setVoided(int voided) {
		this._voided = voided;
	}

	// getting void_reason
	public String getVoidReason() {
		return this._void_reason;
	}

	// setting void_reason
	public void setVoidReason(String void_reason) {
		this._void_reason = void_reason;
	}

	// getting outcome_date
	public String getOutcomeDate() {
		return this._outcome_date;
	}

	// setting outcome_date
	public void setOutcomeDate(String outcome_date) {
		this._outcome_date = outcome_date;
	}

	
}