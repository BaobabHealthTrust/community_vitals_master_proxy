package org.baobabhealthtrust.cvr.models; 

public class DdeSettings{

	// private variables
	String _dde_batch_size;
	String _mode;
	int _id;
	String _dde_site_code;
	String _dde_password;
	int _dde_port;
	String _dde_ip;
	String _dde_username;
	
	// Empty constructor
	public DdeSettings() {
	}

	// constuctor
	public DdeSettings(String dde_batch_size, String mode, int id, String dde_site_code, String dde_password, int dde_port, String dde_ip, String dde_username) {
		
		this._dde_batch_size = dde_batch_size;
		this._mode = mode;
		this._id = id;
		this._dde_site_code = dde_site_code;
		this._dde_password = dde_password;
		this._dde_port = dde_port;
		this._dde_ip = dde_ip;
		this._dde_username = dde_username;
		
	}

	 
	// getting dde_batch_size
	public String getDdeBatchSize() {
		return this._dde_batch_size;
	}

	// setting dde_batch_size
	public void setDdeBatchSize(String dde_batch_size) {
		this._dde_batch_size = dde_batch_size;
	}

	// getting mode
	public String getMode() {
		return this._mode;
	}

	// setting mode
	public void setMode(String mode) {
		this._mode = mode;
	}

	// getting id
	public int getId() {
		return this._id;
	}

	// setting id
	public void setId(int id) {
		this._id = id;
	}

	// getting dde_site_code
	public String getDdeSiteCode() {
		return this._dde_site_code;
	}

	// setting dde_site_code
	public void setDdeSiteCode(String dde_site_code) {
		this._dde_site_code = dde_site_code;
	}

	// getting dde_password
	public String getDdePassword() {
		return this._dde_password;
	}

	// setting dde_password
	public void setDdePassword(String dde_password) {
		this._dde_password = dde_password;
	}

	// getting dde_port
	public int getDdePort() {
		return this._dde_port;
	}

	// setting dde_port
	public void setDdePort(int dde_port) {
		this._dde_port = dde_port;
	}

	// getting dde_ip
	public String getDdeIp() {
		return this._dde_ip;
	}

	// setting dde_ip
	public void setDdeIp(String dde_ip) {
		this._dde_ip = dde_ip;
	}

	// getting dde_username
	public String getDdeUsername() {
		return this._dde_username;
	}

	// setting dde_username
	public void setDdeUsername(String dde_username) {
		this._dde_username = dde_username;
	}

	
}