package org.baobabhealthtrust.appwebview.models; 

public class Inbox{

	// private variables
	String _status;
	String _sender;
	int _inbox_id;
	String _date_sent;
	String _message;
	String _date_received;
	
	// Empty constructor
	public Inbox() {
	}

	// constuctor
	public Inbox(String status, String sender, int inbox_id, String date_sent, String message, String date_received) {
		
		this._status = status;
		this._sender = sender;
		this._inbox_id = inbox_id;
		this._date_sent = date_sent;
		this._message = message;
		this._date_received = date_received;
		
	}

	 
	// getting status
	public String getStatus() {
		return this._status;
	}

	// setting status
	public void setStatus(String status) {
		this._status = status;
	}

	// getting sender
	public String getSender() {
		return this._sender;
	}

	// setting sender
	public void setSender(String sender) {
		this._sender = sender;
	}

	// getting inbox_id
	public int getInboxId() {
		return this._inbox_id;
	}

	// setting inbox_id
	public void setInboxId(int inbox_id) {
		this._inbox_id = inbox_id;
	}

	// getting date_sent
	public String getDateSent() {
		return this._date_sent;
	}

	// setting date_sent
	public void setDateSent(String date_sent) {
		this._date_sent = date_sent;
	}

	// getting message
	public String getMessage() {
		return this._message;
	}

	// setting message
	public void setMessage(String message) {
		this._message = message;
	}

	// getting date_received
	public String getDateReceived() {
		return this._date_received;
	}

	// setting date_received
	public void setDateReceived(String date_received) {
		this._date_received = date_received;
	}

	
}