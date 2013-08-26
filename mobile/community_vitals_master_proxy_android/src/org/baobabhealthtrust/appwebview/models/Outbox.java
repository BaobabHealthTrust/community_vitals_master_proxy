package org.baobabhealthtrust.appwebview.models; 

public class Outbox{

	// private variables
	String _status;
	int _outbox_id;
	String _recipient;
	String _date_sent;
	String _date_created;
	String _message;
	
	// Empty constructor
	public Outbox() {
	}

	// constuctor
	public Outbox(String status, int outbox_id, String recipient, String date_sent, String date_created, String message) {
		
		this._status = status;
		this._outbox_id = outbox_id;
		this._recipient = recipient;
		this._date_sent = date_sent;
		this._date_created = date_created;
		this._message = message;
		
	}

	 
	// getting status
	public String getStatus() {
		return this._status;
	}

	// setting status
	public void setStatus(String status) {
		this._status = status;
	}

	// getting outbox_id
	public int getOutboxId() {
		return this._outbox_id;
	}

	// setting outbox_id
	public void setOutboxId(int outbox_id) {
		this._outbox_id = outbox_id;
	}

	// getting recipient
	public String getRecipient() {
		return this._recipient;
	}

	// setting recipient
	public void setRecipient(String recipient) {
		this._recipient = recipient;
	}

	// getting date_sent
	public String getDateSent() {
		return this._date_sent;
	}

	// setting date_sent
	public void setDateSent(String date_sent) {
		this._date_sent = date_sent;
	}

	// getting date_created
	public String getDateCreated() {
		return this._date_created;
	}

	// setting date_created
	public void setDateCreated(String date_created) {
		this._date_created = date_created;
	}

	// getting message
	public String getMessage() {
		return this._message;
	}

	// setting message
	public void setMessage(String message) {
		this._message = message;
	}

	
}