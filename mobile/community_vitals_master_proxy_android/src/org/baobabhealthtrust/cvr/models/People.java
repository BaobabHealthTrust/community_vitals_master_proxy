package org.baobabhealthtrust.cvr.models; 

public class People{

	// private variables
	String _created_at;
	String _ta;
	String _outcome;
	String _birthdate;
	String _gender;
	String _village;
	String _city_village;
	int _creator_site_id;
	String _updated_at;
	String _date_voided;
	String _maiden_name;
	String _neighbourhood_cell;
	int _creator_id;
	int _id;
	int _voided;
	String _adrress1;
	String _gvh;
	String _cell_phone_number;
	String _family_name;
	String _county_district;
	String _occupation;
	String _state_province;
	int _birthdate_estimated;
	String _given_name;
	String _national_id;
	String _void_reason;
	String _outcome_date;
	String _address2;
	String _middle_name;
	
	// Empty constructor
	public People() {
	}

	// constuctor
	public People(String created_at, String ta, String outcome, String birthdate, String gender, String village, String city_village, int creator_site_id, String updated_at, String date_voided, String maiden_name, String neighbourhood_cell, int creator_id, int id, int voided, String adrress1, String gvh, String cell_phone_number, String family_name, String county_district, String occupation, String state_province, int birthdate_estimated, String given_name, String national_id, String void_reason, String outcome_date, String address2, String middle_name) {
		
		this._created_at = created_at;
		this._ta = ta;
		this._outcome = outcome;
		this._birthdate = birthdate;
		this._gender = gender;
		this._village = village;
		this._city_village = city_village;
		this._creator_site_id = creator_site_id;
		this._updated_at = updated_at;
		this._date_voided = date_voided;
		this._maiden_name = maiden_name;
		this._neighbourhood_cell = neighbourhood_cell;
		this._creator_id = creator_id;
		this._id = id;
		this._voided = voided;
		this._adrress1 = adrress1;
		this._gvh = gvh;
		this._cell_phone_number = cell_phone_number;
		this._family_name = family_name;
		this._county_district = county_district;
		this._occupation = occupation;
		this._state_province = state_province;
		this._birthdate_estimated = birthdate_estimated;
		this._given_name = given_name;
		this._national_id = national_id;
		this._void_reason = void_reason;
		this._outcome_date = outcome_date;
		this._address2 = address2;
		this._middle_name = middle_name;
		
	}

	 
	// getting created_at
	public String getCreatedAt() {
		return this._created_at;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
	}

	// getting ta
	public String getTa() {
		return this._ta;
	}

	// setting ta
	public void setTa(String ta) {
		this._ta = ta;
	}

	// getting outcome
	public String getOutcome() {
		return this._outcome;
	}

	// setting outcome
	public void setOutcome(String outcome) {
		this._outcome = outcome;
	}

	// getting birthdate
	public String getBirthdate() {
		return this._birthdate;
	}

	// setting birthdate
	public void setBirthdate(String birthdate) {
		this._birthdate = birthdate;
	}

	// getting gender
	public String getGender() {
		return this._gender;
	}

	// setting gender
	public void setGender(String gender) {
		this._gender = gender;
	}

	// getting village
	public String getVillage() {
		return this._village;
	}

	// setting village
	public void setVillage(String village) {
		this._village = village;
	}

	// getting city_village
	public String getCityVillage() {
		return this._city_village;
	}

	// setting city_village
	public void setCityVillage(String city_village) {
		this._city_village = city_village;
	}

	// getting creator_site_id
	public int getCreatorSiteId() {
		return this._creator_site_id;
	}

	// setting creator_site_id
	public void setCreatorSiteId(int creator_site_id) {
		this._creator_site_id = creator_site_id;
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

	// getting maiden_name
	public String getMaidenName() {
		return this._maiden_name;
	}

	// setting maiden_name
	public void setMaidenName(String maiden_name) {
		this._maiden_name = maiden_name;
	}

	// getting neighbourhood_cell
	public String getNeighbourhoodCell() {
		return this._neighbourhood_cell;
	}

	// setting neighbourhood_cell
	public void setNeighbourhoodCell(String neighbourhood_cell) {
		this._neighbourhood_cell = neighbourhood_cell;
	}

	// getting creator_id
	public int getCreatorId() {
		return this._creator_id;
	}

	// setting creator_id
	public void setCreatorId(int creator_id) {
		this._creator_id = creator_id;
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

	// getting adrress1
	public String getAdrress1() {
		return this._adrress1;
	}

	// setting adrress1
	public void setAdrress1(String adrress1) {
		this._adrress1 = adrress1;
	}

	// getting gvh
	public String getGvh() {
		return this._gvh;
	}

	// setting gvh
	public void setGvh(String gvh) {
		this._gvh = gvh;
	}

	// getting cell_phone_number
	public String getCellPhoneNumber() {
		return this._cell_phone_number;
	}

	// setting cell_phone_number
	public void setCellPhoneNumber(String cell_phone_number) {
		this._cell_phone_number = cell_phone_number;
	}

	// getting family_name
	public String getFamilyName() {
		return this._family_name;
	}

	// setting family_name
	public void setFamilyName(String family_name) {
		this._family_name = family_name;
	}

	// getting county_district
	public String getCountyDistrict() {
		return this._county_district;
	}

	// setting county_district
	public void setCountyDistrict(String county_district) {
		this._county_district = county_district;
	}

	// getting occupation
	public String getOccupation() {
		return this._occupation;
	}

	// setting occupation
	public void setOccupation(String occupation) {
		this._occupation = occupation;
	}

	// getting state_province
	public String getStateProvince() {
		return this._state_province;
	}

	// setting state_province
	public void setStateProvince(String state_province) {
		this._state_province = state_province;
	}

	// getting birthdate_estimated
	public int getBirthdateEstimated() {
		return this._birthdate_estimated;
	}

	// setting birthdate_estimated
	public void setBirthdateEstimated(int birthdate_estimated) {
		this._birthdate_estimated = birthdate_estimated;
	}

	// getting given_name
	public String getGivenName() {
		return this._given_name;
	}

	// setting given_name
	public void setGivenName(String given_name) {
		this._given_name = given_name;
	}

	// getting national_id
	public String getNationalId() {
		return this._national_id;
	}

	// setting national_id
	public void setNationalId(String national_id) {
		this._national_id = national_id;
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

	// getting address2
	public String getAddress2() {
		return this._address2;
	}

	// setting address2
	public void setAddress2(String address2) {
		this._address2 = address2;
	}

	// getting middle_name
	public String getMiddleName() {
		return this._middle_name;
	}

	// setting middle_name
	public void setMiddleName(String middle_name) {
		this._middle_name = middle_name;
	}

	
}