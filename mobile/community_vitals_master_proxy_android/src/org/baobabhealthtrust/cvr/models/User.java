package org.baobabhealthtrust.cvr.models;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class User {

	// private variables
	String _password;
	String _token;
	int _user_id;
	String _username;
	String _date_created;
	String _first_name;
	String _last_name;
	String _gender;
	String _status;

	// Empty constructor
	public User() {
	}

	// constuctor
	public User(String password, String token, int user_id, String username,
			String date_created) {

		this._password = password;
		this._token = token;
		this._user_id = user_id;
		this._username = username;
		this._date_created = date_created;

	}

	// constuctor
	public User(String password, String token, int user_id, String username,
			String date_created, String fname, String lname, String gender) {

		this._password = password;
		this._token = token;
		this._user_id = user_id;
		this._username = username;
		this._date_created = date_created;
		this._first_name = fname;
		this._last_name = lname;
		this._gender = gender;

	}

	// constuctor
	public User(String password, String token, int user_id, String username,
			String date_created, String fname, String lname, String gender,
			String status) {

		this._password = password;
		this._token = token;
		this._user_id = user_id;
		this._username = username;
		this._date_created = date_created;
		this._first_name = fname;
		this._last_name = lname;
		this._gender = gender;
		this._status = status;

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

	// getting first_name
	public String getFirstName() {
		return this._first_name;
	}

	// setting first_name
	public void setFirstName(String fname) {
		this._first_name = fname;
	}

	// getting last_name
	public String getLastName() {
		return this._last_name;
	}

	// setting last_name
	public void setLastName(String lname) {
		this._last_name = lname;
	}

	// getting gender
	public String getGender() {
		return this._gender;
	}

	// setting gender
	public void setGender(String gender) {
		this._gender = gender;
	}

	// getting status
	public String getStatus() {
		return this._status;
	}

	// setting status
	public void setStatus(String status) {		
		this._status = status;
	}

}