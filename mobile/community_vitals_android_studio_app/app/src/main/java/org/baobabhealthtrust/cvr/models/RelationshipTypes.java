package org.baobabhealthtrust.cvr.models; 

public class RelationshipTypes{

	// private variables
	String _created_at;
	String _updated_at;
	String _relation;
	int _vocabulary_id;
	int _id;
	
	// Empty constructor
	public RelationshipTypes() {
	}

	// constuctor
	public RelationshipTypes(String created_at, String updated_at, String relation, int vocabulary_id, int id) {
		
		this._created_at = created_at;
		this._updated_at = updated_at;
		this._relation = relation;
		this._vocabulary_id = vocabulary_id;
		this._id = id;
		
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

	// getting relation
	public String getRelation() {
		return this._relation;
	}

	// setting relation
	public void setRelation(String relation) {
		this._relation = relation;
	}

	// getting vocabulary_id
	public int getVocabularyId() {
		return this._vocabulary_id;
	}

	// setting vocabulary_id
	public void setVocabularyId(int vocabulary_id) {
		this._vocabulary_id = vocabulary_id;
	}

	// getting id
	public int getId() {
		return this._id;
	}

	// setting id
	public void setId(int id) {
		this._id = id;
	}

	
}