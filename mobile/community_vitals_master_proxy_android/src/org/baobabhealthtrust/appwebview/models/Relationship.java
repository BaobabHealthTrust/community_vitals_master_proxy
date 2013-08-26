package org.baobabhealthtrust.appwebview.models; 

public class Relationship{

	// private variables
	int _relationship_id;
	int _relationship_type;
	String _date_created;
	int _person_a_id;
	int _person_b_id;
	
	// Empty constructor
	public Relationship() {
	}

	// constuctor
	public Relationship(int relationship_id, int relationship_type, String date_created, int person_a_id, int person_b_id) {
		
		this._relationship_id = relationship_id;
		this._relationship_type = relationship_type;
		this._date_created = date_created;
		this._person_a_id = person_a_id;
		this._person_b_id = person_b_id;
		
	}

	 
	// getting relationship_id
	public int getRelationshipId() {
		return this._relationship_id;
	}

	// setting relationship_id
	public void setRelationshipId(int relationship_id) {
		this._relationship_id = relationship_id;
	}

	// getting relationship_type
	public int getRelationshipType() {
		return this._relationship_type;
	}

	// setting relationship_type
	public void setRelationshipType(int relationship_type) {
		this._relationship_type = relationship_type;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	// getting person_a_id
	public int getPersonAId() {
		return this._person_a_id;
	}

	// setting person_a_id
	public void setPersonAId(int person_a_id) {
		this._person_a_id = person_a_id;
	}

	// getting person_b_id
	public int getPersonBId() {
		return this._person_b_id;
	}

	// setting person_b_id
	public void setPersonBId(int person_b_id) {
		this._person_b_id = person_b_id;
	}

	
}