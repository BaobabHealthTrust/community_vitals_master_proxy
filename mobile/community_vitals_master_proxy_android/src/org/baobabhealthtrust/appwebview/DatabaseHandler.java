package org.baobabhealthtrust.appwebview;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.baobabhealthtrust.appwebview.models.AeSimpleSHA1;
import org.baobabhealthtrust.appwebview.models.GlobalProperty;
import org.baobabhealthtrust.appwebview.models.Inbox;
import org.baobabhealthtrust.appwebview.models.Outbox;
import org.baobabhealthtrust.appwebview.models.Outcome;
import org.baobabhealthtrust.appwebview.models.OutcomeType;
import org.baobabhealthtrust.appwebview.models.Person;
import org.baobabhealthtrust.appwebview.models.PersonIdentifier;
import org.baobabhealthtrust.appwebview.models.PersonIdentifierType;
import org.baobabhealthtrust.appwebview.models.Relationship;
import org.baobabhealthtrust.appwebview.models.RelationshipType;
import org.baobabhealthtrust.appwebview.models.Relationship_type;
import org.baobabhealthtrust.appwebview.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "peopleDemographics";

	// Table names
	private static final String TABLE_PERSON = "person";
	private static final String TABLE_RELATIONSHIP = "relationship";
	private static final String TABLE_RELATIONSHIP_TYPE = "relationship_type";
	private static final String TABLE_PERSON_IDENTIFIER = "person_identifier";
	private static final String TABLE_PERSON_IDENTIFIER_TYPE = "person_identifier_type";
	private static final String TABLE_INBOX = "inbox";
	private static final String TABLE_OUTBOX = "outbox";
	private static final String TABLE_GLOBAL_PROPERTY = "global_property";
	private static final String TABLE_USER = "user";
	private static final String TABLE_OUTCOME = "outcome";
	private static final String TABLE_OUTCOME_TYPE = "outcome_type";

	// Table Columns names
	private static final String KEY_PERSON_ID = "person_id";
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_MIDDLE_NAME = "middle_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	private static final String KEY_DATE_OF_BIRTH_ESTIMATED = "date_of_birth_estimated";
	private static final String KEY_OCCUPATION = "occupation";
	private static final String KEY_DATE_CREATED = "date_created";
	private static final String KEY_DEAD = "dead";
	private static final String KEY_DATE_DIED = "date_died";
	private static final String KEY_YEAR_OF_BIRTH = "year_of_birth";
	private static final String KEY_MONTH_OF_BIRTH = "month_of_birth";
	private static final String KEY_DAY_OF_BIRTH = "day_of_birth";

	private static final String KEY_RELATIONSHIP_ID = "relationship_id";
	private static final String KEY_PERSON_A_ID = "person_a_id";
	private static final String KEY_PERSON_B_ID = "person_b_id";
	private static final String KEY_RELATIONSHIP_TYPE = "relationship_type";
	private static final String KEY_RELATIONSHIP_TYPE_ID = "relationship_type_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PERSON_IDENTIFIER_ID = "identifier_id";
	private static final String KEY_IDENTIFIER = "identifier";
	private static final String KEY_IDENTIFIER_TYPE = "identifier_type";
	private static final String KEY_VOIDED = "voided";
	private static final String KEY_VOID_REASON = "void_reason";
	private static final String KEY_DATE_VOIDED = "date_voided";
	private static final String KEY_PERSON_IDENTIFIER_TYPE_ID = "person_identifier_type_id";
	private static final String KEY_INBOX_ID = "inbox_id";
	private static final String KEY_SENDER = "sender";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_DATE_RECEIVED = "date_received";
	private static final String KEY_STATUS = "status";
	private static final String KEY_DATE_SENT = "date_sent";
	private static final String KEY_OUTBOX_ID = "outbox_id";
	private static final String KEY_RECIPIENT = "recipient";
	private static final String KEY_GLOBAL_PROPERTY_ID = "global_property_id";
	private static final String KEY_PROPERTY = "property";
	private static final String KEY_PROPERTY_VALUE = "property_value";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_TOKEN = "token";

	private static final String KEY_OUTCOME_DATE = "outcome_date";
	private static final String KEY_EXPLANATION = "explanation";
	private static final String KEY_OUTCOME_TYPE_ID = "outcome_type_id";
	private static final String KEY_OUTCOME_ID = "outcome_id";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String date = Calendar.getInstance().get(Calendar.YEAR) + "-"
				+ Calendar.getInstance().get(Calendar.MONTH) + "-"
				+ Calendar.getInstance().get(Calendar.DATE);

		String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + "("
				+ KEY_PERSON_ID + " INTEGER PRIMARY KEY," + KEY_FIRST_NAME
				+ " TEXT," + KEY_MIDDLE_NAME + " TEXT," + KEY_LAST_NAME
				+ " TEXT," + KEY_GENDER + " TEXT," + KEY_DATE_OF_BIRTH
				+ " TEXT," + KEY_OCCUPATION + " TEXT, " + KEY_YEAR_OF_BIRTH
				+ " REAL, " + KEY_MONTH_OF_BIRTH + " REAL, " + KEY_DAY_OF_BIRTH
				+ " REAL, " + KEY_DATE_OF_BIRTH_ESTIMATED + " REAL," + KEY_DEAD
				+ " REAL," + KEY_DATE_DIED + " TEXT," + KEY_DATE_CREATED
				+ " TEXT" + ")";

		db.execSQL(CREATE_PERSON_TABLE);

		String CREATE_RELATIONSHIP_TABLE = "CREATE TABLE " + TABLE_RELATIONSHIP
				+ "(" + KEY_RELATIONSHIP_ID + " INTEGER PRIMARY KEY,"
				+ KEY_PERSON_A_ID + " INTEGER," + KEY_PERSON_B_ID + " INTEGER,"
				+ KEY_RELATIONSHIP_TYPE + " INTEGER," + KEY_DATE_CREATED + " TEXT"
				+ ")";

		db.execSQL(CREATE_RELATIONSHIP_TABLE);

		String CREATE_RELATIONSHIP_TYPE_TABLE = "CREATE TABLE "
				+ TABLE_RELATIONSHIP_TYPE + "(" + KEY_RELATIONSHIP_TYPE_ID
				+ " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_RELATIONSHIP_TYPE_TABLE);

		String INITIALISE_RELATIONSHIP_TYPES = "INSERT INTO "
				+ TABLE_RELATIONSHIP_TYPE + "('" + KEY_NAME + "','"
				+ KEY_DATE_CREATED + "') VALUES ('Parent', '" + date
				+ "'), ('Child', '" + date + "'), ('Spouse', '" + date + "')";

		db.execSQL(INITIALISE_RELATIONSHIP_TYPES);

		String CREATE_PERSON_IDENTIFIER_TABLE = "CREATE TABLE "
				+ TABLE_PERSON_IDENTIFIER + "(" + KEY_PERSON_IDENTIFIER_ID
				+ " INTEGER PRIMARY KEY," + KEY_PERSON_ID + " INTEGER,"
				+ KEY_IDENTIFIER + " TEXT," + KEY_IDENTIFIER_TYPE + " INTEGER,"
				+ KEY_VOIDED + " INTEGER," + KEY_VOID_REASON + " TEXT,"
				+ KEY_DATE_VOIDED + " TEXT," + KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_PERSON_IDENTIFIER_TABLE);

		String CREATE_IDENTIFIER_TYPE_TABLE = "CREATE TABLE "
				+ TABLE_PERSON_IDENTIFIER_TYPE + "("
				+ KEY_PERSON_IDENTIFIER_TYPE_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT," + KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_IDENTIFIER_TYPE_TABLE);

		String INITIALISE_ID_TYPES = "INSERT INTO "
				+ TABLE_PERSON_IDENTIFIER_TYPE + "('" + KEY_NAME + "','"
				+ KEY_DATE_CREATED + "') VALUES ('National ID', '" + date
				+ "'), ('Passport Number', '" + date
				+ "'), ('Driving Licence Number', '" + date + "'), ('Other', '"
				+ date + "')";

		db.execSQL(INITIALISE_ID_TYPES);

		String CREATE_OUTCOME_TABLE = "CREATE TABLE " + TABLE_OUTCOME + "("
				+ KEY_OUTCOME_ID + " INTEGER PRIMARY KEY,"
				+ KEY_OUTCOME_TYPE_ID + " INTEGER, " + KEY_PERSON_ID
				+ " INTEGER, " + KEY_OUTCOME_DATE + " TEXT, " + KEY_EXPLANATION
				+ " TEXT, " + KEY_DATE_CREATED + " TEXT )";

		db.execSQL(CREATE_OUTCOME_TABLE);

		String CREATE_OUTCOME_TYPE_TABLE = "CREATE TABLE " + TABLE_OUTCOME_TYPE
				+ "(" + KEY_OUTCOME_TYPE_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT, " + KEY_DATE_CREATED + " TEXT )";

		db.execSQL(CREATE_OUTCOME_TYPE_TABLE);

		String INITIALISE_OUTCOME_TYPES = "INSERT INTO " + TABLE_OUTCOME_TYPE
				+ "('" + KEY_NAME + "','" + KEY_DATE_CREATED
				+ "') VALUES ('Dead', '" + date + "'), ('Transfer Out', '"
				+ date + "'), ('Transfer Back', '" + date + "'), ('Other', '"
				+ date + "')";

		db.execSQL(INITIALISE_OUTCOME_TYPES);

		String CREATE_INBOX_TABLE = "CREATE TABLE " + TABLE_INBOX + "("
				+ KEY_INBOX_ID + " INTEGER PRIMARY KEY," + KEY_SENDER
				+ " TEXT," + KEY_MESSAGE + " TEXT," + KEY_DATE_RECEIVED
				+ " TEXT," + KEY_STATUS + " TEXT," + KEY_DATE_SENT + " TEXT"
				+ ")";

		db.execSQL(CREATE_INBOX_TABLE);

		String CREATE_OUTBOX_TABLE = "CREATE TABLE " + TABLE_OUTBOX + "("
				+ KEY_OUTBOX_ID + " INTEGER PRIMARY KEY," + KEY_RECIPIENT
				+ " TEXT," + KEY_MESSAGE + " TEXT," + KEY_DATE_CREATED
				+ " TEXT," + KEY_STATUS + " TEXT," + KEY_DATE_SENT + " TEXT"
				+ ")";

		db.execSQL(CREATE_OUTBOX_TABLE);

		String CREATE_GLOBAL_PROPERTY_TABLE = "CREATE TABLE "
				+ TABLE_GLOBAL_PROPERTY + "(" + KEY_GLOBAL_PROPERTY_ID
				+ " INTEGER PRIMARY KEY," + KEY_PROPERTY + " TEXT,"
				+ KEY_PROPERTY_VALUE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
				+ KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_GLOBAL_PROPERTY_TABLE);

		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME
				+ " TEXT," + KEY_PASSWORD + " TEXT," + KEY_TOKEN + " TEXT,"
				+ KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_USER_TABLE);

		AeSimpleSHA1 sha = new AeSimpleSHA1();
		String passHash = "";

		try {

			passHash = sha.SHA1("test");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String INITIALISE_USERS = "INSERT INTO " + TABLE_USER + "("
				+ KEY_USERNAME + "," + KEY_PASSWORD + "," + KEY_TOKEN
				+ ") VALUES ('admin', '" + passHash + "', '')";

		db.execSQL(INITIALISE_USERS);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RELATIONSHIP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RELATIONSHIP_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_IDENTIFIER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_IDENTIFIER_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INBOX);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTBOX);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBAL_PROPERTY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new person
	void addPerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, person.getFirstName()); // Person First Name
		values.put(KEY_MIDDLE_NAME, person.getMiddleName()); // Person Middle
																// Name
		values.put(KEY_LAST_NAME, person.getLastName()); // Person Last Name
		values.put(KEY_GENDER, person.getGender()); // Person Gender
		values.put(KEY_DATE_OF_BIRTH, person.getDOB()); // Person DOB

		values.put(KEY_YEAR_OF_BIRTH, person.getYrOB()); // Person YOB
		values.put(KEY_MONTH_OF_BIRTH, person.getMonthOB()); // Person MOB
		values.put(KEY_DAY_OF_BIRTH, person.getDateOB()); // Person DtOB

		values.put(KEY_DATE_OF_BIRTH_ESTIMATED, person.getDOBEstimate()); // Person
																			// DOB
																			// Estimate
		values.put(KEY_DATE_CREATED, person.getDateCreated()); // Person date
																// created
		values.put(KEY_OCCUPATION, person.getOccupation()); // Person occupation
		values.put(KEY_DEAD, person.getDead()); // Person dead
		values.put(KEY_DATE_DIED, person.getDateDied()); // Person occupation

		// Inserting Row
		db.insert(TABLE_PERSON, null, values);

		db.close(); // Closing database connection
	}

	// Getting single person
	Person getPerson(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PERSON, new String[] { KEY_PERSON_ID,
				KEY_FIRST_NAME, KEY_MIDDLE_NAME, KEY_LAST_NAME, KEY_GENDER,
				KEY_DATE_OF_BIRTH, KEY_DATE_OF_BIRTH_ESTIMATED, KEY_OCCUPATION,
				KEY_DATE_CREATED, KEY_DEAD, KEY_DATE_DIED }, KEY_PERSON_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);

		if (cursor != null)
			cursor.moveToFirst();

		Person person = new Person(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7), cursor.getString(8), cursor.getString(9),
				cursor.getString(10));

		// return person
		return person;
	}

	// Getting All Persons
	public List<Person> getAllPersons() {
		List<Person> peopleList = new ArrayList<Person>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Person person = new Person();
				person.setID(Integer.parseInt(cursor.getString(0)));
				person.setFirstName(cursor.getString(1));
				person.setMiddleName(cursor.getString(2));
				person.setLastName(cursor.getString(3));
				person.setGender(cursor.getString(4));
				person.setDOB(cursor.getString(5));
				person.setOccupation(cursor.getString(6));
				person.setYrOB(cursor.getString(7));
				person.setMonthOB(cursor.getString(8));
				person.setDateOB(cursor.getString(9));
				person.setDOBEstimae(cursor.getString(10));
				person.setDead(cursor.getString(11));
				person.setDateDied(cursor.getString(12));
				person.setDateCreated(cursor.getString(13));
				// Adding person to list
				peopleList.add(person);
			} while (cursor.moveToNext());
		}

		// return person list
		return peopleList;
	}

	// Getting All Persons By Category
	public List<Person> getAllPersons(String gender) {
		List<Person> peopleList = new ArrayList<Person>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON + " WHERE "
				+ KEY_GENDER + " = '" + gender + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Person person = new Person();
				person.setID(Integer.parseInt(cursor.getString(0)));
				person.setFirstName(cursor.getString(1));
				person.setMiddleName(cursor.getString(2));
				person.setLastName(cursor.getString(3));
				person.setGender(cursor.getString(4));
				person.setDOB(cursor.getString(5));
				person.setOccupation(cursor.getString(6));
				person.setYrOB(cursor.getString(7));
				person.setMonthOB(cursor.getString(8));
				person.setDateOB(cursor.getString(9));
				person.setDOBEstimae(cursor.getString(10));
				person.setDead(cursor.getString(11));
				person.setDateDied(cursor.getString(12));
				person.setDateCreated(cursor.getString(13));
				// Adding person to list
				peopleList.add(person);
			} while (cursor.moveToNext());
		}

		// return person list
		return peopleList;
	}

	// Getting All Persons
	public List<Person> getAllPersons(String gender, int startAge, int endAge) {
		List<Person> peopleList = new ArrayList<Person>();

		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON + " WHERE "
				+ KEY_GENDER + " = '" + gender + "' AND (" + year + " - "
				+ KEY_YEAR_OF_BIRTH + " >= " + startAge + " AND " + year
				+ " - " + KEY_YEAR_OF_BIRTH + " <= " + endAge + ")";

		Log.i("SQL QUERY", selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String date = cursor.getString(5);

				Person person = new Person();
				person.setID(Integer.parseInt(cursor.getString(0)));
				person.setFirstName(cursor.getString(1));
				person.setMiddleName(cursor.getString(2));
				person.setLastName(cursor.getString(3));
				person.setGender(cursor.getString(4));
				person.setDOB(cursor.getString(5));
				person.setOccupation(cursor.getString(6));
				person.setYrOB(cursor.getString(7));
				person.setMonthOB(cursor.getString(8));
				person.setDateOB(cursor.getString(9));
				person.setDOBEstimae(cursor.getString(10));
				person.setDead(cursor.getString(11));
				person.setDateDied(cursor.getString(12));
				person.setDateCreated(cursor.getString(13));
				// Adding person to list
				peopleList.add(person);
			} while (cursor.moveToNext());
		}

		// return person list
		return peopleList;
	}

	// Getting All Persons
	public List<Person> getAllPersons(int startAge, int endAge) {
		List<Person> peopleList = new ArrayList<Person>();

		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON + " WHERE ("
				+ year + " - " + KEY_YEAR_OF_BIRTH + " >= " + startAge
				+ " AND " + year + " - " + KEY_YEAR_OF_BIRTH + " <= " + endAge
				+ ")";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String date = cursor.getString(5);

				Person person = new Person();
				person.setID(Integer.parseInt(cursor.getString(0)));
				person.setFirstName(cursor.getString(1));
				person.setMiddleName(cursor.getString(2));
				person.setLastName(cursor.getString(3));
				person.setGender(cursor.getString(4));
				person.setDOB(cursor.getString(5));
				person.setOccupation(cursor.getString(6));
				person.setYrOB(cursor.getString(7));
				person.setMonthOB(cursor.getString(8));
				person.setDateOB(cursor.getString(9));
				person.setDOBEstimae(cursor.getString(10));
				person.setDead(cursor.getString(11));
				person.setDateDied(cursor.getString(12));
				person.setDateCreated(cursor.getString(13));
				// Adding person to list
				peopleList.add(person);
			} while (cursor.moveToNext());
		}

		// return person list
		return peopleList;
	}

	// Updating single person
	public int updatePerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, person.getFirstName()); // Person First Name
		values.put(KEY_MIDDLE_NAME, person.getMiddleName()); // Person Middle
																// Name
		values.put(KEY_LAST_NAME, person.getLastName()); // Person Last Name
		values.put(KEY_GENDER, person.getGender()); // Person Gender
		values.put(KEY_DATE_OF_BIRTH, person.getDOB()); // Person DOB
		values.put(KEY_DATE_OF_BIRTH_ESTIMATED, person.getDOBEstimate()); // Person
																			// DOB
																			// Estimate
		values.put(KEY_DATE_CREATED, person.getDateCreated()); // Person date
																// created
		values.put(KEY_OCCUPATION, person.getOccupation()); // Person occupation
		values.put(KEY_DEAD, person.getDead()); // Person dead
		values.put(KEY_DATE_DIED, person.getDateDied()); // Person occupation

		// updating row
		return db.update(TABLE_PERSON, values, KEY_PERSON_ID + " = ?",
				new String[] { String.valueOf(person.getID()) });
	}

	// Deleting single person
	public void deletePerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PERSON, KEY_PERSON_ID + " = ?",
				new String[] { String.valueOf(person.getID()) });
		db.close();
	}

	// Getting people Count
	public int getPersonsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PERSON;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public String login(String username, String password) {
		AeSimpleSHA1 sha = new AeSimpleSHA1();
		String passHash = "";

		try {
			passHash = sha.SHA1(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_USERNAME + " = '" + username + "' AND " + KEY_PASSWORD
				+ " = '" + passHash + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			StringBuilder sb = new StringBuilder();

			Random random = new Random();
			for (int i = 0; i < 20; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}
			String output = sb.toString();

			User user = new User();
			user.setUserId(Integer.parseInt(cursor.getString(0)));

			ContentValues values = new ContentValues();
			values.put(KEY_TOKEN, output);

			// updating row
			db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
					new String[] { String.valueOf(user.getUserId()) });

			return output;
		} else {
			return "";
		}

	}

	public void logout(String token) {

		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_TOKEN + " = '" + token + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			User user = new User();
			user.setUserId(Integer.parseInt(cursor.getString(0)));

			ContentValues values = new ContentValues();
			values.put(KEY_TOKEN, "");

			// updating row
			db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
					new String[] { String.valueOf(user.getUserId()) });

		}
	}

	// Getting All Users
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUserId(Integer.parseInt(cursor.getString(0)));
				user.setUsername(cursor.getString(1));
				user.setPassword(cursor.getString(2));
				user.setToken(cursor.getString(3));
				// Adding person to list
				userList.add(user);
			} while (cursor.moveToNext());
		}

		// return person list
		return userList;
	}

	public boolean userExists(String username) {
		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_USERNAME + " = '" + username + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean userLoggedIn(String token) {
		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_TOKEN + " = '" + token + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	// START AUTO_GENERATED CODE

	void addInbox(Inbox inbox) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, inbox.getStatus());
		values.put(KEY_SENDER, inbox.getSender());
		values.put(KEY_DATE_SENT, inbox.getDateSent());
		values.put(KEY_MESSAGE, inbox.getMessage());
		values.put(KEY_DATE_RECEIVED, inbox.getDateReceived());

		// Insert Row
		db.insert(TABLE_INBOX, null, values);
		db.close();
	}

	void addPersonIdentifierType(PersonIdentifierType personidentifiertype) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, personidentifiertype.getName());
		values.put(KEY_DATE_CREATED, personidentifiertype.getDateCreated());

		// Insert Row
		db.insert(TABLE_PERSON_IDENTIFIER_TYPE, null, values);
		db.close();
	}

	void addRelationship(Relationship relationship) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RELATIONSHIP_TYPE, relationship.getRelationshipType());
		values.put(KEY_DATE_CREATED, relationship.getDateCreated());
		values.put(KEY_PERSON_A_ID, relationship.getPersonAId());
		values.put(KEY_PERSON_B_ID, relationship.getPersonBId());

		// Insert Row
		db.insert(TABLE_RELATIONSHIP, null, values);
		db.close();
	}

	void addGlobalProperty(GlobalProperty globalproperty) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PROPERTY_VALUE, globalproperty.getPropertyValue());
		values.put(KEY_DESCRIPTION, globalproperty.getDescription());
		values.put(KEY_DATE_CREATED, globalproperty.getDateCreated());
		values.put(KEY_PROPERTY, globalproperty.getProperty());

		// Insert Row
		db.insert(TABLE_GLOBAL_PROPERTY, null, values);
		db.close();
	}

	void addOutbox(Outbox outbox) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, outbox.getStatus());
		values.put(KEY_RECIPIENT, outbox.getRecipient());
		values.put(KEY_DATE_SENT, outbox.getDateSent());
		values.put(KEY_DATE_CREATED, outbox.getDateCreated());
		values.put(KEY_MESSAGE, outbox.getMessage());

		// Insert Row
		db.insert(TABLE_OUTBOX, null, values);
		db.close();
	}

	void addRelationshipType(Relationship_type relationship_type) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, relationship_type.getName());
		values.put(KEY_DATE_CREATED, relationship_type.getDateCreated());

		// Insert Row
		db.insert(TABLE_RELATIONSHIP_TYPE, null, values);
		db.close();
	}

	void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(KEY_TOKEN, user.getToken());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_DATE_CREATED, user.getDateCreated());

		// Insert Row
		db.insert(TABLE_USER, null, values);
		db.close();
	}

	void addPersonIdentifier(PersonIdentifier personidentifier) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE_VOIDED, personidentifier.getDateVoided());
		values.put(KEY_VOID_REASON, personidentifier.getVoidReason());
		values.put(KEY_DATE_CREATED, personidentifier.getDateCreated());
		values.put(KEY_IDENTIFIER_TYPE, personidentifier.getIdentifierType());
		values.put(KEY_IDENTIFIER, personidentifier.getIdentifier());
		values.put(KEY_VOIDED, personidentifier.getVoided());
		values.put(KEY_PERSON_ID, personidentifier.getPersonID());

		// Insert Row
		db.insert(TABLE_PERSON_IDENTIFIER, null, values);
		db.close();
	}

	Inbox getInbox(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_INBOX, new String[] { KEY_STATUS,
				KEY_SENDER, KEY_INBOX_ID, KEY_DATE_SENT, KEY_MESSAGE,
				KEY_DATE_RECEIVED }, KEY_INBOX_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Inbox inbox = new Inbox(cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				cursor.getString(4), cursor.getString(5));

		// return inbox
		return inbox;
	}

	PersonIdentifierType getPersonIdentifierType(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PERSON_IDENTIFIER_TYPE, new String[] {
				KEY_NAME, KEY_PERSON_IDENTIFIER_TYPE_ID, KEY_DATE_CREATED },
				KEY_NAME + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		PersonIdentifierType personidentifiertype = new PersonIdentifierType(
				cursor.getString(0), Integer.parseInt(cursor.getString(1)),
				cursor.getString(2));

		// return personidentifiertype
		return personidentifiertype;
	}

	Relationship getRelationship(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RELATIONSHIP, new String[] {
				KEY_RELATIONSHIP_ID, KEY_RELATIONSHIP_TYPE, KEY_DATE_CREATED,
				KEY_PERSON_A_ID, KEY_PERSON_B_ID }, KEY_RELATIONSHIP_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Relationship relationship = new Relationship(Integer.parseInt(cursor
				.getString(0)), Integer.parseInt(cursor.getString(1)),
				cursor.getString(2), Integer.parseInt(cursor.getString(3)),
				Integer.parseInt(cursor.getString(4)));

		// return relationship
		return relationship;
	}

	GlobalProperty getGlobalProperty(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_GLOBAL_PROPERTY, new String[] {
				KEY_PROPERTY_VALUE, KEY_GLOBAL_PROPERTY_ID, KEY_DESCRIPTION,
				KEY_DATE_CREATED, KEY_PROPERTY },
				KEY_GLOBAL_PROPERTY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		GlobalProperty globalproperty = new GlobalProperty(cursor.getString(0),
				Integer.parseInt(cursor.getString(1)), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));

		// return globalproperty
		return globalproperty;
	}

	Outbox getOutbox(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTBOX, new String[] { KEY_STATUS,
				KEY_OUTBOX_ID, KEY_RECIPIENT, KEY_DATE_SENT, KEY_DATE_CREATED,
				KEY_MESSAGE }, KEY_OUTBOX_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Outbox outbox = new Outbox(cursor.getString(0), Integer.parseInt(cursor
				.getString(1)), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5));

		// return outbox
		return outbox;
	}

	RelationshipType getRelationshipType(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RELATIONSHIP_TYPE, new String[] {
				KEY_NAME, KEY_DATE_CREATED, KEY_RELATIONSHIP_TYPE_ID },
				KEY_NAME + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		RelationshipType relationship_type = new RelationshipType(
				cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)));

		// return relationship_type
		return relationship_type;
	}

	RelationshipType getRelationshipType(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RELATIONSHIP_TYPE, new String[] {
				KEY_NAME, KEY_DATE_CREATED, KEY_RELATIONSHIP_TYPE_ID },
				KEY_RELATIONSHIP_TYPE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		RelationshipType relationship_type = new RelationshipType(
				cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)));

		// return relationship_type
		return relationship_type;
	}

	User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_PASSWORD,
				KEY_TOKEN, KEY_USER_ID, KEY_USERNAME, KEY_DATE_CREATED },
				KEY_USER_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				cursor.getString(4));

		// return user
		return user;
	}

	PersonIdentifier getPersonIdentifier(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PERSON_IDENTIFIER, new String[] {
				KEY_DATE_VOIDED, KEY_VOID_REASON, KEY_DATE_CREATED,
				KEY_IDENTIFIER_TYPE, KEY_IDENTIFIER, KEY_PERSON_IDENTIFIER_ID,
				KEY_VOIDED, KEY_PERSON_ID }, KEY_PERSON_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		PersonIdentifier personidentifier = new PersonIdentifier(
				cursor.getString(0), cursor.getString(1), cursor.getString(2),
				Integer.parseInt(cursor.getString(3)), cursor.getString(4),
				Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor
						.getString(6)), Integer.parseInt(cursor.getString(7)));

		// return personidentifier
		return personidentifier;
	}

	public List<PersonIdentifier> getAllPersonIdentifiers(int person_id) {
		List<PersonIdentifier> personidentifierList = new ArrayList<PersonIdentifier>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_PERSON_IDENTIFIER
				+ " WHERE " + KEY_PERSON_ID + " = " + person_id;

		Log.i("SQL DEBUGGING", selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				PersonIdentifier personidentifier = new PersonIdentifier();
				personidentifier.setDateVoided(cursor.getString(6));
				personidentifier.setVoidReason(cursor.getString(5));
				personidentifier.setDateCreated(cursor.getString(7));
				personidentifier.setIdentifierType(Integer.parseInt(cursor
						.getString(3)));
				personidentifier.setIdentifier(cursor.getString(2));
				personidentifier.setPersonIdentifierId(Integer.parseInt(cursor
						.getString(0)));
				personidentifier
						.setVoided(Integer.parseInt(cursor.getString(4)));
				// Adding personidentifier to list
				personidentifierList.add(personidentifier);

			} while (cursor.moveToNext());
		}

		// return personidentifier list
		return personidentifierList;
	}

	public List<Inbox> getAllInbox() {
		List<Inbox> inboxList = new ArrayList<Inbox>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_INBOX;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Inbox inbox = new Inbox();
				inbox.setStatus(cursor.getString(0));
				inbox.setSender(cursor.getString(1));
				inbox.setInboxId(Integer.parseInt(cursor.getString(2)));
				inbox.setDateSent(cursor.getString(3));
				inbox.setMessage(cursor.getString(4));
				inbox.setDateReceived(cursor.getString(5));
				// Adding inbox to list
				inboxList.add(inbox);
			} while (cursor.moveToNext());
		}

		// return inbox list
		return inboxList;
	}

	public List<PersonIdentifierType> getAllPersonIdentifierType() {
		List<PersonIdentifierType> personidentifiertypeList = new ArrayList<PersonIdentifierType>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON_IDENTIFIER_TYPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				PersonIdentifierType personidentifiertype = new PersonIdentifierType();
				personidentifiertype.setName(cursor.getString(0));
				personidentifiertype.setPersonIdentifierTypeId(Integer
						.parseInt(cursor.getString(1)));
				personidentifiertype.setDateCreated(cursor.getString(2));
				// Adding personidentifiertype to list
				personidentifiertypeList.add(personidentifiertype);
			} while (cursor.moveToNext());
		}

		// return personidentifiertype list
		return personidentifiertypeList;
	}

	public List<Relationship> getAllRelationship() {
		List<Relationship> relationshipList = new ArrayList<Relationship>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Relationship relationship = new Relationship();
				relationship.setRelationshipId(Integer.parseInt(cursor
						.getString(0)));
				relationship.setRelationshipType(Integer.parseInt(cursor
						.getString(1)));
				relationship.setDateCreated(cursor.getString(2));
				relationship
						.setPersonAId(Integer.parseInt(cursor.getString(3)));
				relationship
						.setPersonBId(Integer.parseInt(cursor.getString(4)));
				// Adding relationship to list
				relationshipList.add(relationship);
			} while (cursor.moveToNext());
		}

		// return relationship list
		return relationshipList;
	}

	public List<Relationship> getAllRelationships(int person_id) {
		List<Relationship> relationshipList = new ArrayList<Relationship>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_RELATIONSHIP + " WHERE "
				+ KEY_PERSON_A_ID + " = " + person_id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Relationship relationship = new Relationship();
				
				// int relationship_id, int relationship_type, String date_created, int person_a_id, int person_b_id
				
				relationship.setRelationshipId(Integer.parseInt(cursor
						.getString(0)));
				relationship.setRelationshipType(Integer.parseInt(cursor
						.getString(3)));
				relationship.setDateCreated(cursor.getString(4));
				relationship
						.setPersonAId(Integer.parseInt(cursor.getString(1)));
				relationship
						.setPersonBId(Integer.parseInt(cursor.getString(2)));
				// Adding relationship to list
				relationshipList.add(relationship);
			} while (cursor.moveToNext());
		}

		// return relationship list
		return relationshipList;
	}

	public List<GlobalProperty> getAllGlobalProperty() {
		List<GlobalProperty> globalpropertyList = new ArrayList<GlobalProperty>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_GLOBAL_PROPERTY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				GlobalProperty globalproperty = new GlobalProperty();
				globalproperty.setPropertyValue(cursor.getString(0));
				globalproperty.setGlobalPropertyId(Integer.parseInt(cursor
						.getString(1)));
				globalproperty.setDescription(cursor.getString(2));
				globalproperty.setDateCreated(cursor.getString(3));
				globalproperty.setProperty(cursor.getString(4));
				// Adding globalproperty to list
				globalpropertyList.add(globalproperty);
			} while (cursor.moveToNext());
		}

		// return globalproperty list
		return globalpropertyList;
	}

	public List<Outbox> getAllOutbox() {
		List<Outbox> outboxList = new ArrayList<Outbox>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTBOX;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Outbox outbox = new Outbox();
				outbox.setStatus(cursor.getString(0));
				outbox.setOutboxId(Integer.parseInt(cursor.getString(1)));
				outbox.setRecipient(cursor.getString(2));
				outbox.setDateSent(cursor.getString(3));
				outbox.setDateCreated(cursor.getString(4));
				outbox.setMessage(cursor.getString(5));
				// Adding outbox to list
				outboxList.add(outbox);
			} while (cursor.moveToNext());
		}

		// return outbox list
		return outboxList;
	}

	public List<RelationshipType> getAllRelationshipType() {
		List<RelationshipType> relationship_typeList = new ArrayList<RelationshipType>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP_TYPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				RelationshipType relationship_type = new RelationshipType();
				relationship_type.setName(cursor.getString(0));
				relationship_type.setDateCreated(cursor.getString(1));
				relationship_type.setRelationshipTypeId(Integer.parseInt(cursor
						.getString(2)));
				// Adding relationship_type to list
				relationship_typeList.add(relationship_type);
			} while (cursor.moveToNext());
		}

		// return relationship_type list
		return relationship_typeList;
	}

	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setPassword(cursor.getString(0));
				user.setToken(cursor.getString(1));
				user.setUserId(Integer.parseInt(cursor.getString(2)));
				user.setUsername(cursor.getString(3));
				user.setDateCreated(cursor.getString(4));
				// Adding user to list
				userList.add(user);
			} while (cursor.moveToNext());
		}

		// return user list
		return userList;
	}

	public List<PersonIdentifier> getAllPersonIdentifier() {
		List<PersonIdentifier> personidentifierList = new ArrayList<PersonIdentifier>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSON_IDENTIFIER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				PersonIdentifier personidentifier = new PersonIdentifier();
				personidentifier.setDateVoided(cursor.getString(6));
				personidentifier.setVoidReason(cursor.getString(5));
				personidentifier.setDateCreated(cursor.getString(7));
				personidentifier.setIdentifierType(Integer.parseInt(cursor
						.getString(3)));
				personidentifier.setIdentifier(cursor.getString(2));
				personidentifier.setPersonIdentifierId(Integer.parseInt(cursor
						.getString(0)));
				personidentifier
						.setVoided(Integer.parseInt(cursor.getString(4)));
				personidentifier.setPersonID(Integer.parseInt(cursor
						.getString(1)));
				// Adding personidentifier to list
				personidentifierList.add(personidentifier);
			} while (cursor.moveToNext());
		}

		// return personidentifier list
		return personidentifierList;
	}

	public int updateInbox(Inbox inbox) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, inbox.getStatus());
		values.put(KEY_SENDER, inbox.getSender());
		values.put(KEY_INBOX_ID, inbox.getInboxId());
		values.put(KEY_DATE_SENT, inbox.getDateSent());
		values.put(KEY_MESSAGE, inbox.getMessage());
		values.put(KEY_DATE_RECEIVED, inbox.getDateReceived());

		// updating row
		return db.update(TABLE_INBOX, values, KEY_INBOX_ID + " = ?",
				new String[] { String.valueOf(inbox.getInboxId()) });
	}

	public int updatePersonIdentifierType(
			PersonIdentifierType personidentifiertype) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, personidentifiertype.getName());
		values.put(KEY_PERSON_IDENTIFIER_TYPE_ID,
				personidentifiertype.getPersonIdentifierTypeId());
		values.put(KEY_DATE_CREATED, personidentifiertype.getDateCreated());

		// updating row
		return db.update(TABLE_PERSON_IDENTIFIER_TYPE, values,
				KEY_PERSON_IDENTIFIER_TYPE_ID + " = ?", new String[] { String
						.valueOf(personidentifiertype
								.getPersonIdentifierTypeId()) });
	}

	public int updateRelationship(Relationship relationship) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RELATIONSHIP_ID, relationship.getRelationshipId());
		values.put(KEY_RELATIONSHIP_TYPE, relationship.getRelationshipType());
		values.put(KEY_DATE_CREATED, relationship.getDateCreated());
		values.put(KEY_PERSON_A_ID, relationship.getPersonAId());
		values.put(KEY_PERSON_B_ID, relationship.getPersonBId());

		// updating row
		return db
				.update(TABLE_RELATIONSHIP, values, KEY_RELATIONSHIP_ID
						+ " = ?", new String[] { String.valueOf(relationship
						.getRelationshipId()) });
	}

	public int updateGlobalProperty(GlobalProperty globalproperty) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PROPERTY_VALUE, globalproperty.getPropertyValue());
		values.put(KEY_GLOBAL_PROPERTY_ID, globalproperty.getGlobalPropertyId());
		values.put(KEY_DESCRIPTION, globalproperty.getDescription());
		values.put(KEY_DATE_CREATED, globalproperty.getDateCreated());
		values.put(KEY_PROPERTY, globalproperty.getProperty());

		// updating row
		return db.update(TABLE_GLOBAL_PROPERTY, values, KEY_GLOBAL_PROPERTY_ID
				+ " = ?", new String[] { String.valueOf(globalproperty
				.getGlobalPropertyId()) });
	}

	public int updateOutbox(Outbox outbox) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, outbox.getStatus());
		values.put(KEY_OUTBOX_ID, outbox.getOutboxId());
		values.put(KEY_RECIPIENT, outbox.getRecipient());
		values.put(KEY_DATE_SENT, outbox.getDateSent());
		values.put(KEY_DATE_CREATED, outbox.getDateCreated());
		values.put(KEY_MESSAGE, outbox.getMessage());

		// updating row
		return db.update(TABLE_OUTBOX, values, KEY_OUTBOX_ID + " = ?",
				new String[] { String.valueOf(outbox.getOutboxId()) });
	}

	public int updateRelationshipType(RelationshipType relationship_type) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, relationship_type.getName());
		values.put(KEY_DATE_CREATED, relationship_type.getDateCreated());
		values.put(KEY_RELATIONSHIP_TYPE_ID,
				relationship_type.getRelationshipTypeId());

		// updating row
		return db.update(TABLE_RELATIONSHIP_TYPE, values,
				KEY_RELATIONSHIP_TYPE_ID + " = ?", new String[] { String
						.valueOf(relationship_type.getRelationshipTypeId()) });
	}

	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(KEY_TOKEN, user.getToken());
		values.put(KEY_USER_ID, user.getUserId());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_DATE_CREATED, user.getDateCreated());

		// updating row
		return db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(user.getUserId()) });
	}

	public int updatePersonIdentifier(PersonIdentifier personidentifier) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE_VOIDED, personidentifier.getDateVoided());
		values.put(KEY_VOID_REASON, personidentifier.getVoidReason());
		values.put(KEY_DATE_CREATED, personidentifier.getDateCreated());
		values.put(KEY_IDENTIFIER_TYPE, personidentifier.getIdentifierType());
		values.put(KEY_IDENTIFIER, personidentifier.getIdentifier());
		values.put(KEY_PERSON_IDENTIFIER_ID,
				personidentifier.getPersonIdentifierId());
		values.put(KEY_VOIDED, personidentifier.getVoided());

		// updating row
		return db.update(TABLE_PERSON_IDENTIFIER, values,
				KEY_PERSON_IDENTIFIER_ID + " = ?", new String[] { String
						.valueOf(personidentifier.getPersonIdentifierId()) });
	}

	public void deleteInbox(Inbox inbox) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_INBOX, KEY_INBOX_ID + " = ?",
				new String[] { String.valueOf(inbox.getInboxId()) });
		db.close();
	}

	public void deletePersonIdentifierType(
			PersonIdentifierType personidentifiertype) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PERSON_IDENTIFIER_TYPE, KEY_PERSON_IDENTIFIER_TYPE_ID
				+ " = ?", new String[] { String.valueOf(personidentifiertype
				.getPersonIdentifierTypeId()) });
		db.close();
	}

	public void deleteRelationship(Relationship relationship) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(
				TABLE_RELATIONSHIP,
				KEY_RELATIONSHIP_ID + " = ?",
				new String[] { String.valueOf(relationship.getRelationshipId()) });
		db.close();
	}

	public void deleteGlobalProperty(GlobalProperty globalproperty) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GLOBAL_PROPERTY, KEY_GLOBAL_PROPERTY_ID + " = ?",
				new String[] { String.valueOf(globalproperty
						.getGlobalPropertyId()) });
		db.close();
	}

	public void deleteOutbox(Outbox outbox) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_OUTBOX, KEY_OUTBOX_ID + " = ?",
				new String[] { String.valueOf(outbox.getOutboxId()) });
		db.close();
	}

	public void deleteRelationshipType(RelationshipType relationship_type) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_RELATIONSHIP_TYPE, KEY_RELATIONSHIP_TYPE_ID + " = ?",
				new String[] { String.valueOf(relationship_type
						.getRelationshipTypeId()) });
		db.close();
	}

	public void deleteUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(user.getUserId()) });
		db.close();
	}

	public void deletePersonIdentifier(PersonIdentifier personidentifier) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PERSON_IDENTIFIER, KEY_PERSON_IDENTIFIER_ID + " = ?",
				new String[] { String.valueOf(personidentifier
						.getPersonIdentifierId()) });
		db.close();
	}

	public int getInboxCount() {
		String countQuery = "SELECT  * FROM " + TABLE_INBOX;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getPersonIdentifierTypeCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PERSON_IDENTIFIER_TYPE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getRelationshipCount() {
		String countQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getGlobalPropertyCount() {
		String countQuery = "SELECT  * FROM " + TABLE_GLOBAL_PROPERTY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getOutboxCount() {
		String countQuery = "SELECT  * FROM " + TABLE_OUTBOX;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getRelationshipTypeCount() {
		String countQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP_TYPE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getUserCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getPersonIdentifierCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PERSON_IDENTIFIER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public List<String> getFirstNames(String filter) {
		List<String> namesList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_FIRST_NAME + " FROM "
				+ TABLE_PERSON + " WHERE " + KEY_FIRST_NAME + " LIKE '"
				+ filter + "%' " + " LIMIT 0,20";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// Adding name
				namesList.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		// return relationship_type list
		return namesList;
	}

	public List<String> getLastNames(String filter) {
		List<String> namesList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_LAST_NAME + " FROM "
				+ TABLE_PERSON + " WHERE " + KEY_LAST_NAME + " LIKE '" + filter
				+ "%' " + " LIMIT 0,20";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// Adding name
				namesList.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		// return relationship_type list
		return namesList;
	}

	public List<Person> getPeopleNames(String fname, String lname, String gender) {
		List<Person> namesList = new ArrayList<Person>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_PERSON_ID + ", " + KEY_FIRST_NAME
				+ ", " + KEY_LAST_NAME + ", " + KEY_GENDER + ", "
				+ KEY_DATE_OF_BIRTH + " FROM " + TABLE_PERSON + " WHERE "
				+ KEY_FIRST_NAME + " LIKE '" + fname + "%' AND "
				+ KEY_LAST_NAME + " LIKE '" + lname + "%' AND " + KEY_GENDER
				+ " = '" + gender + "' " + " LIMIT 0,20";

		Log.i("QUERY", selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Person person = new Person();

				person.setID(Integer.parseInt(cursor.getString(0)));
				person.setFirstName(cursor.getString(1));
				person.setLastName(cursor.getString(2));
				person.setGender(cursor.getString(3));
				person.setDOB(cursor.getString(4));

				// Adding person
				namesList.add(person);
			} while (cursor.moveToNext());
		}

		// return relationship_type list
		return namesList;
	}

	void addOutcomeType(OutcomeType outcometype) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, outcometype.getName());
		values.put(KEY_DATE_CREATED, outcometype.getDateCreated());

		// Insert Row
		db.insert(TABLE_OUTCOME_TYPE, null, values);
		db.close();
	}

	void addOutcome(Outcome outcome) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_OUTCOME_DATE, outcome.getOutcomeDate());
		values.put(KEY_EXPLANATION, outcome.getExplanation());
		values.put(KEY_PERSON_ID, outcome.getPersonId());
		values.put(KEY_DATE_CREATED, outcome.getDateCreated());
		values.put(KEY_OUTCOME_TYPE_ID, outcome.getOutcomeTypeId());

		// Insert Row
		db.insert(TABLE_OUTCOME, null, values);
		db.close();
	}

	Outcome getOutcome(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTCOME, new String[] {
				KEY_OUTCOME_DATE, KEY_EXPLANATION, KEY_PERSON_ID,
				KEY_DATE_CREATED, KEY_OUTCOME_TYPE_ID, KEY_OUTCOME_ID },
				KEY_OUTCOME_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Outcome outcome = new Outcome(cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor
						.getString(5)));

		// return outcome
		return outcome;
	}

	OutcomeType getOutcomeType(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTCOME_TYPE, new String[] { KEY_NAME,
				KEY_DATE_CREATED, KEY_OUTCOME_TYPE_ID }, KEY_NAME + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		OutcomeType outcometype = new OutcomeType(cursor.getString(0),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)));

		// return outcometype
		return outcometype;
	}

	OutcomeType getOutcomeType(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTCOME_TYPE, new String[] { KEY_NAME,
				KEY_DATE_CREATED, KEY_OUTCOME_TYPE_ID }, KEY_OUTCOME_TYPE_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);

		if (cursor != null)
			cursor.moveToFirst();

		OutcomeType outcometype = new OutcomeType(cursor.getString(0),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)));

		Log.i("OUTCOME TYPE", "got a cursor");

		// return outcometype
		return outcometype;
	}

	public List<Outcome> getAllOutcome() {
		List<Outcome> outcomeList = new ArrayList<Outcome>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTCOME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Outcome outcome = new Outcome();
				outcome.setOutcomeDate(cursor.getString(3));
				outcome.setExplanation(cursor.getString(4));
				outcome.setPersonId(Integer.parseInt(cursor.getString(2)));
				outcome.setDateCreated(cursor.getString(5));
				outcome.setOutcomeTypeId(Integer.parseInt(cursor.getString(1)));
				outcome.setOutcomeId(Integer.parseInt(cursor.getString(0)));
				// Adding outcome to list
				outcomeList.add(outcome);
			} while (cursor.moveToNext());
		}

		// return outcome list
		return outcomeList;
	}

	public List<Outcome> getAllOutcomes(int persn_id) {
		List<Outcome> outcomeList = new ArrayList<Outcome>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTCOME
				+ " WHERE person_id = " + KEY_PERSON_ID;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Outcome outcome = new Outcome();
				outcome.setOutcomeDate(cursor.getString(3));
				outcome.setExplanation(cursor.getString(4));
				outcome.setPersonId(Integer.parseInt(cursor.getString(2)));
				outcome.setDateCreated(cursor.getString(5));
				outcome.setOutcomeTypeId(Integer.parseInt(cursor.getString(1)));
				outcome.setOutcomeId(Integer.parseInt(cursor.getString(0)));
				// Adding outcome to list
				outcomeList.add(outcome);
			} while (cursor.moveToNext());
		}

		// return outcome list
		return outcomeList;
	}

	public Outcome getLastOutcome(int person_id) {

		Log.i("SQL QUERY", "Inside person_id = " + person_id);

		String selectQuery = "SELECT " + KEY_OUTCOME_ID + ", "
				+ KEY_OUTCOME_TYPE_ID + ", " + KEY_PERSON_ID + ", "
				+ KEY_OUTCOME_DATE + ", " + KEY_EXPLANATION + ", "
				+ KEY_DATE_CREATED + " FROM " + TABLE_OUTCOME + " WHERE "
				+ KEY_PERSON_ID + " = " + person_id + " ORDER BY "
				+ KEY_OUTCOME_ID + " DESC LIMIT 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		Log.i("SQL QUERY", selectQuery);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				Outcome outcome = new Outcome(
						cursor.getString(cursor
								.getColumnIndex(KEY_OUTCOME_DATE)),
						cursor.getString(cursor.getColumnIndex(KEY_EXPLANATION)),
						Integer.parseInt(cursor.getString(cursor
								.getColumnIndex(KEY_PERSON_ID))), cursor
								.getString(cursor
										.getColumnIndex(KEY_DATE_CREATED)),
						Integer.parseInt(cursor.getString(cursor
								.getColumnIndex(KEY_OUTCOME_TYPE_ID))), Integer
								.parseInt(cursor.getString(cursor
										.getColumnIndex(KEY_OUTCOME_ID))));

				Log.i("SQL QUERY", "Queried and out");
				return outcome;
			} else {
				return null;
			}
		} else {
			Log.i("SQL QUERY", "Queried and failed");
			return null;
		}

		// return outcome

	}

	public List<OutcomeType> getAllOutcomeType() {
		List<OutcomeType> outcometypeList = new ArrayList<OutcomeType>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTCOME_TYPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				OutcomeType outcometype = new OutcomeType();
				outcometype.setName(cursor.getString(0));
				outcometype.setDateCreated(cursor.getString(1));
				outcometype.setOutcomeTypeId(Integer.parseInt(cursor
						.getString(2)));
				// Adding outcometype to list
				outcometypeList.add(outcometype);
			} while (cursor.moveToNext());
		}

		// return outcometype list
		return outcometypeList;
	}

	public int updateOutcome(Outcome outcome) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_OUTCOME_DATE, outcome.getOutcomeDate());
		values.put(KEY_EXPLANATION, outcome.getExplanation());
		values.put(KEY_PERSON_ID, outcome.getPersonId());
		values.put(KEY_DATE_CREATED, outcome.getDateCreated());
		values.put(KEY_OUTCOME_TYPE_ID, outcome.getOutcomeTypeId());
		values.put(KEY_OUTCOME_ID, outcome.getOutcomeId());

		// updating row
		return db.update(TABLE_OUTCOME, values, KEY_OUTCOME_ID + " = ?",
				new String[] { String.valueOf(outcome.getOutcomeId()) });
	}

	public int updateOutcomeType(OutcomeType outcometype) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, outcometype.getName());
		values.put(KEY_DATE_CREATED, outcometype.getDateCreated());
		values.put(KEY_OUTCOME_TYPE_ID, outcometype.getOutcomeTypeId());

		// updating row
		return db
				.update(TABLE_OUTCOME_TYPE, values, KEY_OUTCOME_TYPE_ID
						+ " = ?", new String[] { String.valueOf(outcometype
						.getOutcomeTypeId()) });
	}

	public void deleteOutcome(Outcome outcome) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_OUTCOME, KEY_OUTCOME_ID + " = ?",
				new String[] { String.valueOf(outcome.getOutcomeId()) });
		db.close();
	}

	public void deleteOutcomeType(OutcomeType outcometype) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_OUTCOME_TYPE, KEY_OUTCOME_TYPE_ID + " = ?",
				new String[] { String.valueOf(outcometype.getOutcomeTypeId()) });
		db.close();
	}

	public int getOutcomeCount() {
		String countQuery = "SELECT  * FROM " + TABLE_OUTCOME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getOutcomeTypeCount() {
		String countQuery = "SELECT  * FROM " + TABLE_OUTCOME_TYPE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
