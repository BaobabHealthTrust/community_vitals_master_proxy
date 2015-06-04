package org.baobabhealthtrust.cvr.models; 

public class Sites{

	// private variables
	String _created_at;
	String _updated_at;
	String _date_voided;
	int _id;
	int _voided;
	String _name;
	String _void_reason;
	
	// Empty constructor
	public Sites() {
	}

	// constuctor
	public Sites(String created_at, String updated_at, String date_voided, int id, int voided, String name, String void_reason) {
		
		this._created_at = created_at;
		this._updated_at = updated_at;
		this._date_voided = date_voided;
		this._id = id;
		this._voided = voided;
		this._name = name;
		this._void_reason = void_reason;
		
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

	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	// getting void_reason
	public String getVoidReason() {
		return this._void_reason;
	}

	// setting void_reason
	public void setVoidReason(String void_reason) {
		this._void_reason = void_reason;
	}

	
}