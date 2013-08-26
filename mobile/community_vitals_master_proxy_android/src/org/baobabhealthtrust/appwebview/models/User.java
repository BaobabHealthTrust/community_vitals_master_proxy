package org.baobabhealthtrust.appwebview.models; 

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class User{

	// private variables
	String _password;
	String _token;
	int _user_id;
	String _username;
	String _date_created;
	
	// Empty constructor
	public User() {
	}

	// constuctor
	public User(String password, String token, int user_id, String username, String date_created) {
		
		this._password = password;
		this._token = token;
		this._user_id = user_id;
		this._username = username;
		this._date_created = date_created;
		
	}

	 
	// getting password
	public String getPassword() {
		return this._password;
	}

	// setting password
	public void setPassword(String password) {
		AeSimpleSHA1 sha = new AeSimpleSHA1();
		String passHash = "";

		try {
			
			passHash = sha.SHA1(password);
			this._password = passHash;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// getting token
	public String getToken() {
		return this._token;
	}

	// setting token
	public void setToken(String token) {
		this._token = token;
	}

	// getting user_id
	public int getUserId() {
		return this._user_id;
	}

	// setting user_id
	public void setUserId(int user_id) {
		this._user_id = user_id;
	}

	// getting username
	public String getUsername() {
		return this._username;
	}

	// setting username
	public void setUsername(String username) {
		this._username = username;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	
}