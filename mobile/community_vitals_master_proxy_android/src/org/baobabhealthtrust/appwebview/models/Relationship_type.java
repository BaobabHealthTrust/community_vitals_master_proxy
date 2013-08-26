package org.baobabhealthtrust.appwebview.models; 

public class Relationship_type{

	// private variables
	int _relationship_type_id;
	String _date_created;
	String _name;
	
	// Empty constructor
	public Relationship_type() {
	}

	// constuctor
	public Relationship_type(int relationship_type_id, String date_created, String name) {
		
		this._relationship_type_id = relationship_type_id;
		this._date_created = date_created;
		this._name = name;
		
	}

	 
	// getting relationship_type_id
	public int getRelationshipTypeId() {
		return this._relationship_type_id;
	}

	// setting relationship_type_id
	public void setRelationshipTypeId(int relationship_type_id) {
		this._relationship_type_id = relationship_type_id;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
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

	
}