package org.baobabhealthtrust.appwebview.models; 

public class RelationshipType{

	// private variables
	String _name;
	String _date_created;
	int _relationship_type_id;
	
	// Empty constructor
	public RelationshipType() {
	}

	// constuctor
	public RelationshipType(String name, String date_created, int relationship_type_id) {
		
		this._name = name;
		this._date_created = date_created;
		this._relationship_type_id = relationship_type_id;
		
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

	// getting relationship_type_id
	public int getRelationshipTypeId() {
		return this._relationship_type_id;
	}

	// setting relationship_type_id
	public void setRelationshipTypeId(int relationship_type_id) {
		this._relationship_type_id = relationship_type_id;
	}

	
}