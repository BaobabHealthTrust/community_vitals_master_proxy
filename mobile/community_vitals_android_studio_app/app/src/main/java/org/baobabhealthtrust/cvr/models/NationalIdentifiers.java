package org.baobabhealthtrust.cvr.models; 

public class NationalIdentifiers{

	// private variables
	String _created_at;
	int _requested_by_gvh;
	String _assigned_vh;
	String _site_id;
	int _request_vh_notified;
	int _post_vh_notified;
	int _person_id;
	String _updated_at;
	String _date_voided;
	int _requested_by_vh;
	int _posted_by_vh;
	String _identifier;
	String _assigned_gvh;
	int _id;
	int _voided;
	int _post_gvh_notified;
	int _request_gvh_notified;
	String _void_reason;
	int _posted_by_gvh;
	String _blank_id;
	
	// Empty constructor
	public NationalIdentifiers() {
	}

	// constuctor
	public NationalIdentifiers(String created_at, int requested_by_gvh, String assigned_vh, String site_id, int request_vh_notified, int post_vh_notified, int person_id, String updated_at, String date_voided, int requested_by_vh, int posted_by_vh, String identifier, String assigned_gvh, int id, int voided, int post_gvh_notified, int request_gvh_notified, String void_reason, int posted_by_gvh) {
		
		this._created_at = created_at;
		this._requested_by_gvh = requested_by_gvh;
		this._assigned_vh = assigned_vh;
		this._site_id = site_id;
		this._request_vh_notified = request_vh_notified;
		this._post_vh_notified = post_vh_notified;
		this._person_id = person_id;
		this._updated_at = updated_at;
		this._date_voided = date_voided;
		this._requested_by_vh = requested_by_vh;
		this._posted_by_vh = posted_by_vh;
		this._identifier = identifier;
		this._assigned_gvh = assigned_gvh;
		this._id = id;
		this._voided = voided;
		this._post_gvh_notified = post_gvh_notified;
		this._request_gvh_notified = request_gvh_notified;
		this._void_reason = void_reason;
		this._posted_by_gvh = posted_by_gvh;
		
	}

	 
	// getting created_at
	public String getCreatedAt() {
		return this._created_at;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
	}

	// getting requested_by_gvh
	public int getRequestedByGvh() {
		return this._requested_by_gvh;
	}

	// setting requested_by_gvh
	public void setRequestedByGvh(int requested_by_gvh) {
		this._requested_by_gvh = requested_by_gvh;
	}

	// getting assigned_vh
	public String getAssignedVh() {
		return this._assigned_vh;
	}

	// setting assigned_vh
	public void setAssignedVh(String assigned_vh) {
		this._assigned_vh = assigned_vh;
	}

	// getting site_id
	public String getSiteId() {
		return this._site_id;
	}

	// setting site_id
	public void setSiteId(String site_id) {
		this._site_id = site_id;
	}

	// getting request_vh_notified
	public int getRequestVhNotified() {
		return this._request_vh_notified;
	}

	// setting request_vh_notified
	public void setRequestVhNotified(int request_vh_notified) {
		this._request_vh_notified = request_vh_notified;
	}

	// getting post_vh_notified
	public int getPostVhNotified() {
		return this._post_vh_notified;
	}

	// setting post_vh_notified
	public void setPostVhNotified(int post_vh_notified) {
		this._post_vh_notified = post_vh_notified;
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

	// getting requested_by_vh
	public int getRequestedByVh() {
		return this._requested_by_vh;
	}

	// setting requested_by_vh
	public void setRequestedByVh(int requested_by_vh) {
		this._requested_by_vh = requested_by_vh;
	}

	// getting posted_by_vh
	public int getPostedByVh() {
		return this._posted_by_vh;
	}

	// setting posted_by_vh
	public void setPostedByVh(int posted_by_vh) {
		this._posted_by_vh = posted_by_vh;
	}

	// getting identifier
	public String getIdentifier() {
		return this._identifier;
	}

	// setting identifier
	public void setIdentifier(String identifier) {
		this._identifier = identifier;
	}

	// getting assigned_gvh
	public String getAssignedGvh() {
		return this._assigned_gvh;
	}

	// setting assigned_gvh
	public void setAssignedGvh(String assigned_gvh) {
		this._assigned_gvh = assigned_gvh;
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

	// getting post_gvh_notified
	public int getPostGvhNotified() {
		return this._post_gvh_notified;
	}

	// setting post_gvh_notified
	public void setPostGvhNotified(int post_gvh_notified) {
		this._post_gvh_notified = post_gvh_notified;
	}

	// getting request_gvh_notified
	public int getRequestGvhNotified() {
		return this._request_gvh_notified;
	}

	// setting request_gvh_notified
	public void setRequestGvhNotified(int request_gvh_notified) {
		this._request_gvh_notified = request_gvh_notified;
	}

	// getting void_reason
	public String getVoidReason() {
		return this._void_reason;
	}

	// setting void_reason
	public void setVoidReason(String void_reason) {
		this._void_reason = void_reason;
	}

	// getting posted_by_gvh
	public int getPostedByGvh() {
		return this._posted_by_gvh;
	}

	// setting posted_by_gvh
	public void setPostedByGvh(int posted_by_gvh) {
		this._posted_by_gvh = posted_by_gvh;
	}

	// setting posted_by_gvh
	public void setBlankId(String npid) {
		this._blank_id = npid;
	}
	
	public String getBlankId(){		
		return this._blank_id;	
	}
}