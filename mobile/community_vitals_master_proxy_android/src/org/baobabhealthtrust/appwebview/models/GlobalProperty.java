package org.baobabhealthtrust.appwebview.models; 

public class GlobalProperty{

	// private variables
	String _property_value;
	int _global_property_id;
	String _description;
	String _date_created;
	String _property;
	
	// Empty constructor
	public GlobalProperty() {
	}

	// constuctor
	public GlobalProperty(String property_value, int global_property_id, String description, String date_created, String property) {
		
		this._property_value = property_value;
		this._global_property_id = global_property_id;
		this._description = description;
		this._date_created = date_created;
		this._property = property;
		
	}

	 
	// getting property_value
	public String getPropertyValue() {
		return this._property_value;
	}

	// setting property_value
	public void setPropertyValue(String property_value) {
		this._property_value = property_value;
	}

	// getting global property id
	public int getGlobalPropertyId() {
		return this._global_property_id;
	}

	// setting global property id
	public void setGlobalPropertyId(int global_property_id) {
		this._global_property_id = global_property_id;
	}

	// getting description
	public String getDescription() {
		return this._description;
	}

	// setting description
	public void setDescription(String description) {
		this._description = description;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	// getting property
	public String getProperty() {
		return this._property;
	}

	// setting property
	public void setProperty(String property) {
		this._property = property;
	}

	
}