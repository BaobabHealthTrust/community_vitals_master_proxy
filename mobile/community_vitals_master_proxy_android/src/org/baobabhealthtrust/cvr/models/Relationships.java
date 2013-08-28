package org.baobabhealthtrust.cvr.models; 

public class Relationships{

	// private variables
	String _created_at;
	String _updated_at;
	String _date_voided;
	int _person_is_to_relation;
	String _person_national_id;
	int _id;
	int _voided;
	String _void_reason;
	String _relation_national_id;
	
	// Empty constructor
	public Relationships() {
	}

	// constuctor
	public Relationships(String created_at, String updated_at, String date_voided, int person_is_to_relation, String person_national_id, int id, int voided, String void_reason, String relation_national_id) {
		
		this._created_at = created_at;
		this._updated_at = updated_at;
		this._date_voided = date_voided;
		this._person_is_to_relation = person_is_to_relation;
		this._person_national_id = person_national_id;
		this._id = id;
		this._voided = voided;
		this._void_reason = void_reason;
		this._relation_national_id = relation_national_id;
		
	}

	 
	// getting created_at
	public String getCreatedAt() {
		return this._created_at;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
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

	// getting person_is_to_relation
	public int getPersonIsToRelation() {
		return this._person_is_to_relation;
	}

	// setting person_is_to_relation
	public void setPersonIsToRelation(int person_is_to_relation) {
		this._person_is_to_relation = person_is_to_relation;
	}

	// getting person_national_id
	public String getPersonNationalId() {
		return this._person_national_id;
	}

	// setting person_national_id
	public void setPersonNationalId(String person_national_id) {
		this._person_national_id = person_national_id;
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

	// getting relation_national_id
	public String getRelationNationalId() {
		return this._relation_national_id;
	}

	// setting relation_national_id
	public void setRelationNationalId(String relation_national_id) {
		this._relation_national_id = relation_national_id;
	}

	
}