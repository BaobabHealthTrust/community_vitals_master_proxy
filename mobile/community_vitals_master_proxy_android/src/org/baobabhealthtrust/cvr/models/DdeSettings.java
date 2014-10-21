package org.baobabhealthtrust.cvr.models; 

public class DdeSettings{

	// private variables
	String _dde_batch_size;
	String _dde_threshold_size;
	String _dde_site_code;
	String _dde_username;
	String _dde_password;
	String _mode;
	int _dde_port;
	int _id;
	String _dde_ip;
	String _ta;
	String _gvh;
	String _vh;
	
	// Empty constructor
	public DdeSettings() {
	}

	// constuctor
	public DdeSettings(String dde_batch_size, String dde_threshold_size, String dde_site_code, String dde_username, String dde_password, String mode,String ta, String gvh, String vh ,int dde_port, int id, String dde_ip) {
		
		this._dde_batch_size = dde_batch_size;
		this._dde_threshold_size = dde_threshold_size;
		this._dde_site_code = dde_site_code;
		this._dde_username = dde_username;
		this._dde_password = dde_password;
		this._mode = mode;
		this._dde_port = dde_port;
		this._id = id;
		this._dde_ip = dde_ip;
		this._ta = ta;
		this._gvh = gvh;
		this._vh = vh;
	}

	 
	// getting dde_batch_size
	public String getDdeBatchSize() {
		return this._dde_batch_size;
	}

	// setting dde_batch_size
	public void setDdeBatchSize(String dde_batch_size) {
		this._dde_batch_size = dde_batch_size;
	}

	// getting dde_threshold_size
	public String getDdeThresholdSize() {
		return this._dde_threshold_size;
	}

	// setting dde_threshold_size
	public void setDdeThresholdSize(String dde_threshold_size) {
		this._dde_threshold_size = dde_threshold_size;
	}

	// getting dde_site_code
	public String getDdeSiteCode() {
		return this._dde_site_code;
	}

	// setting dde_site_code
	public void setDdeSiteCode(String dde_site_code) {
		this._dde_site_code = dde_site_code;
	}

	// getting dde_username
	public String getDdeUsername() {
		return this._dde_username;
	}

	// setting dde_username
	public void setDdeUsername(String dde_username) {
		this._dde_username = dde_username;
	}

	// getting dde_password
	public String getDdePassword() {
		return this._dde_password;
	}

	// setting dde_password
	public void setDdePassword(String dde_password) {
		this._dde_password = dde_password;
	}

	// getting mode
	public String getMode() {
		return this._mode;
	}

	// setting mode
	public void setMode(String mode) {
		this._mode = mode;
	}

	// getting dde_port
	public int getDdePort() {
		return this._dde_port;
	}

	// setting dde_port
	public void setDdePort(int dde_port) {
		this._dde_port = dde_port;
	}

	// getting id
	public int getId() {
		return this._id;
	}

	// setting id
	public void setId(int id) {
		this._id = id;
	}

	// getting dde_ip
	public String getDdeIp() {
		return this._dde_ip;
	}

	// setting dde_ip
	public void setDdeIp(String dde_ip) {
		this._dde_ip = dde_ip;
	}

	// getting traditional authority 
	public String getTa(){
		return this._ta;
	}
	
	// setting traditional authority
	public void setTa(String ta){
		this._ta = ta;
	}
	// getting group village head-man 
	public String getGvh(){
		return this._gvh;
	}
	
	// setting group village head-man
	public void setGvh(String gvh){
		this._gvh = gvh;
	}

	// getting village head-man 
	public String getVh(){
		return this._vh;
	}
	
	// setting village head-man
	public void setVh(String vh){
		this._vh = vh;
	}

}