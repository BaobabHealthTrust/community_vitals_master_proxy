package org.baobabhealthtrust.appwebview.models;

public class Person {

	// private variables
	int _id;
	String _first_name;
	String _middle_name;
	String _last_name;
	String _gender;
	String _dob;
	String _yrob;
	String _mob;
	String _dtob;
	String _occupation;
	String _dead;
	String _date_died;
	String _date_created;
	String _dob_estimate;

	// Empty constructor
	public Person() {

	}

	// constructor
	public Person(int person_id, String first_name, String middle_name,
			String last_name, String gender, String dob, String occupation,
			String dead, String date_died, String date_created,
			String dob_estimate) {
		this._id = person_id;
		this._first_name = first_name;
		this._middle_name = middle_name;
		this._last_name = last_name;
		this._gender = gender;
		this._dob = dob;
		this._occupation = occupation;
		this._dead = dead;
		this._date_died = date_died;
		this._date_created = date_created;
		this._dob_estimate = dob_estimate;
	}

	// constructor
	public Person(String first_name, String middle_name, String last_name,
			String gender, String dob, String occupation, String dead,
			String date_died) {
		this._first_name = first_name;
		this._middle_name = middle_name;
		this._last_name = last_name;
		this._gender = gender;
		this._dob = dob;
		this._occupation = occupation;
		this._dead = dead;
		this._date_died = date_died;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting first name
	public String getFirstName() {
		return this._first_name;
	}

	// setting first name
	public void setFirstName(String name) {
		this._first_name = name;
	}

	// getting middle name
	public String getMiddleName() {
		return this._middle_name;
	}

	// setting middle name
	public void setMiddleName(String name) {
		this._middle_name = name;
	}

	// getting last name
	public String getLastName() {
		return this._last_name;
	}

	// setting last name
	public void setLastName(String name) {
		this._last_name = name;
	}

	// getting gender
	public String getGender() {
		return this._gender;
	}

	// setting gender
	public void setGender(String name) {
		this._gender = name;
	}

	// getting date of birth
	public String getDOB() {
		return this._dob;
	}

	// setting date of birth
	public void setDOB(String name) {
		this._dob = name;
	}

	// getting date of birth
	public String getDateOB() {
		return this._dtob;
	}

	// setting date of birth
	public void setDateOB(String name) {
		this._dtob = name;
	}

	// getting month of birth
	public String getMonthOB() {
		return this._mob;
	}

	// setting month of birth
	public void setMonthOB(String name) {
		this._mob = name;
	}

	// getting year of birth
	public String getYrOB() {
		return this._yrob;
	}

	// setting year of birth
	public void setYrOB(String name) {
		this._yrob = name;
	}

	// getting date of birth estimate
	public String getDOBEstimate() {
		return this._dob_estimate;
	}

	// setting date of birth estimate
	public void setDOBEstimae(String estimated) {
		this._dob_estimate = estimated;
	}

	// getting dead
	public String getDead() {
		return this._dead;
	}

	// setting dead
	public void setDead(String name) {
		this._dead = name;
	}

	// getting occupation
	public String getOccupation() {
		return this._occupation;
	}

	// setting occupation
	public void setOccupation(String name) {
		this._occupation = name;
	}

	// getting date died
	public String getDateDied() {
		return this._date_died;
	}

	// setting dead
	public void setDateDied(String date_died) {
		this._date_died = date_died;
	}

	// getting date created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date created
	public void setDateCreated(String date) {
		this._date_created = date;
	}
	
}
