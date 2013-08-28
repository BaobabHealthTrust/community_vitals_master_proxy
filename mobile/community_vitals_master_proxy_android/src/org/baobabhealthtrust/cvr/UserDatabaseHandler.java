package org.baobabhealthtrust.cvr;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.baobabhealthtrust.cvr.models.AeSimpleSHA1;
import org.baobabhealthtrust.cvr.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "userDemographics";

	// Table names
	private static final String TABLE_USER = "user";

	// Table Columns names
	private static final String KEY_DATE_CREATED = "date_created";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_TOKEN = "token";

	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String date = Calendar.getInstance().get(Calendar.YEAR) + "-"
				+ Calendar.getInstance().get(Calendar.MONTH) + "-"
				+ Calendar.getInstance().get(Calendar.DATE);

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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

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

	public void deleteUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(user.getUserId()) });
		db.close();
	}

	public int getUserCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
