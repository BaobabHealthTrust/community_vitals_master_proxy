package org.baobabhealthtrust.cvr;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.baobabhealthtrust.cvr.models.AeSimpleSHA1;
import org.baobabhealthtrust.cvr.models.DdeSettings;
import org.baobabhealthtrust.cvr.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "userDemographics";

	// Table names
	private static final String TABLE_USER = "user";
	private static final String TABLE_DDE_SETTINGS = "dde_settings";
	private static final String TABLE_ROLES = "roles";
	private static final String TABLE_USER_ROLES = "user_roles";

	// Table Columns names
	private static final String KEY_DATE_CREATED = "date_created";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_TOKEN = "token";
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_ROLE = "role";
	private static final String KEY_ROLE_ID = "role_id";
	private static final String KEY_USER_STATUS = "user_status";
	private static final String KEY_VOCABULARY_ID = "vocabulary_id";

	private static final String KEY_ID = "id";
	private static final String KEY_MODE = "mode";
	private static final String KEY_DDE_USERNAME = "dde_username";
	private static final String KEY_DDE_PASSWORD = "dde_password";
	private static final String KEY_DDE_IP = "dde_ip";
	private static final String KEY_DDE_PORT = "dde_port";
	private static final String KEY_DDE_SITE_CODE = "dde_site_code";
	private static final String KEY_DDE_BATCH_SIZE = "dde_batch_size";
	private static final String KEY_DDE_THRESHOLD_SIZE = "dde_threshold_size";

	public static int mCurrentUserId = 0;

	// Fetch size per page
	public static final int PAGE_SIZE = 10;

	private Context mContext;

	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		mContext = context;
	}

	/**
	 * Returns true if database file exists, false otherwise.
	 * 
	 * @return
	 */
	public boolean databaseExists() {
		File dbFile = mContext.getDatabasePath(DATABASE_NAME);
		return dbFile.exists();
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_DDE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_DDE_SETTINGS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MODE + " TEXT,"
				+ KEY_DDE_USERNAME + " TEXT," + KEY_DDE_PASSWORD + " TEXT,"
				+ KEY_DDE_IP + " TEXT," + KEY_DDE_PORT + " INTEGER,"
				+ KEY_DDE_SITE_CODE + " TEXT," + KEY_DDE_BATCH_SIZE
				+ " INTEGER, " + KEY_DDE_THRESHOLD_SIZE + " INTEGER, "
				+ KEY_DATE_CREATED + " TEXT" + ")";

		db.execSQL(CREATE_DDE_SETTINGS_TABLE);

		String INITIALISE_SETTINGS = "INSERT INTO "
				+ TABLE_DDE_SETTINGS
				+ "("
				+ KEY_MODE
				+ ", "
				+ KEY_DDE_USERNAME
				+ " ,"
				+ KEY_DDE_PASSWORD
				+ " ,"
				+ KEY_DDE_IP
				+ " ,"
				+ KEY_DDE_PORT
				+ " ,"
				+ KEY_DDE_SITE_CODE
				+ " ,"
				+ KEY_DDE_BATCH_SIZE
				+ ", "
				+ KEY_DDE_THRESHOLD_SIZE
				+ ") VALUES ('ta', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 0 ), ('gvh', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 0), ('vh', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 0)";

		db.execSQL(INITIALISE_SETTINGS);

		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME
				+ " TEXT," + KEY_PASSWORD + " TEXT," + KEY_TOKEN + " TEXT,"
				+ KEY_DATE_CREATED + " TEXT, " + KEY_FIRST_NAME + " TEXT, "
				+ KEY_LAST_NAME + " TEXT, " + KEY_GENDER + " TEXT, "
				+ KEY_USER_STATUS + " TEXT )";

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
				+ KEY_USERNAME + "," + KEY_PASSWORD + "," + KEY_TOKEN + ", '"
				+ KEY_FIRST_NAME + "', '" + KEY_LAST_NAME + "', '" + KEY_GENDER
				+ "', '" + KEY_USER_STATUS + "') VALUES ('admin', '" + passHash
				+ "', '', 'Super', 'User', 'Male', 'Active')";

		db.execSQL(INITIALISE_USERS);

		String CREATE_ROLES_TABLE = "CREATE TABLE " + TABLE_ROLES + "("
				+ KEY_ROLE_ID + " INTEGER PRIMARY KEY, " + KEY_ROLE + " TEXT, "
				+ KEY_VOCABULARY_ID + " INTEGER )";

		db.execSQL(CREATE_ROLES_TABLE);

		String INITIALISE_ROLES = "INSERT INTO "
				+ TABLE_ROLES
				+ "("
				+ KEY_ROLE
				+ ", "
				+ KEY_VOCABULARY_ID
				+ ") VALUES ('Superuser', 87), "
				+ "('Traditional Authority', 12), ('Group Village Headman', 8), "
				+ "('Village Headman', 20)";

		db.execSQL(INITIALISE_ROLES);

		String CREATE_USER_ROLES_TABLE = "CREATE TABLE " + TABLE_USER_ROLES
				+ "(" + KEY_USER_ID + " INTEGER, " + KEY_ROLE_ID + " INTEGER )";

		db.execSQL(CREATE_USER_ROLES_TABLE);

		String INITIALISE_USER_ROLES = "INSERT INTO " + TABLE_USER_ROLES + "("
				+ KEY_USER_ID + ", " + KEY_ROLE_ID
				+ ") VALUES (1, 1), (1, 2), (1, 3), (1, 4)";

		db.execSQL(INITIALISE_USER_ROLES);

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

			mCurrentUserId = user.getUserId();

			cursor.close();

			return output;
		} else {
			cursor.close();

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
		cursor.close();
	}

	// Getting All Users
	public List<User> getAllUsers(int page) {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		// String selectQuery = "SELECT * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getWritableDatabase();
		// Cursor cursor = db.rawQuery(selectQuery, null);

		Cursor cursor = db.query(TABLE_USER, null, null, null, null, null,
				null, ((page - 1) * PAGE_SIZE) + ", " + PAGE_SIZE);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUserId(Integer.parseInt(cursor.getString(0)));
				user.setUsername(cursor.getString(1));
				user.setPassword(cursor.getString(2));
				user.setToken(cursor.getString(3));
				user.setFirstName(cursor.getString(5));
				user.setLastName(cursor.getString(6));
				user.setGender(cursor.getString(7));
				user.setStatus(cursor.getString(8));
				// Adding person to list
				userList.add(user);
			} while (cursor.moveToNext());
		}

		cursor.close();

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
			cursor.close();

			return true;
		} else {
			cursor.close();

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
			cursor.close();

			return true;
		} else {
			cursor.close();

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
		values.put(KEY_FIRST_NAME, user.getFirstName());
		values.put(KEY_LAST_NAME, user.getLastName());
		values.put(KEY_GENDER, user.getGender());

		values.put(KEY_USER_STATUS, user.getStatus());

		// Insert Row
		db.insert(TABLE_USER, null, values);
		db.close();
	}

	User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_PASSWORD,
				KEY_TOKEN, KEY_USER_ID, KEY_USERNAME, KEY_DATE_CREATED,
				KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER },
				KEY_USER_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7));

		cursor.close();

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
				user.setFirstName(cursor.getString(5));
				user.setLastName(cursor.getString(6));
				user.setGender(cursor.getString(7));
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
		values.put(KEY_FIRST_NAME, user.getFirstName());
		values.put(KEY_LAST_NAME, user.getLastName());
		values.put(KEY_GENDER, user.getGender());

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
		String countQuery = "SELECT * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	DdeSettings getDdeSettingsByMode(String mode) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DDE_SETTINGS, new String[] {
				KEY_DDE_BATCH_SIZE, KEY_DDE_THRESHOLD_SIZE, KEY_DDE_SITE_CODE,
				KEY_DDE_USERNAME, KEY_DDE_PASSWORD, KEY_MODE, KEY_DDE_PORT,
				KEY_ID, KEY_DDE_IP }, KEY_MODE + "=?",
				new String[] { String.valueOf(mode) }, null, null, null, null);

		Log.i("",
				"$$$$$$$$$$$$$$$$$$$$$$$$$$$$ in settings " + cursor.getCount()
						+ " mode: " + mode);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				DdeSettings dde_settings = new DdeSettings(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), Integer.parseInt(cursor
								.getString(6)), Integer.parseInt(cursor
								.getString(7)), cursor.getString(8));

				cursor.close();

				return dde_settings;
			}
		}

		cursor.close();

		// return dde_settings
		return null;
	}

	void addDdeSettings(DdeSettings dde_settings) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DDE_BATCH_SIZE, dde_settings.getDdeBatchSize());
		values.put(KEY_DDE_THRESHOLD_SIZE, dde_settings.getDdeThresholdSize());
		values.put(KEY_DDE_SITE_CODE, dde_settings.getDdeSiteCode());
		values.put(KEY_DDE_USERNAME, dde_settings.getDdeUsername());
		values.put(KEY_DDE_PASSWORD, dde_settings.getDdePassword());
		values.put(KEY_MODE, dde_settings.getMode());
		values.put(KEY_DDE_PORT, dde_settings.getDdePort());
		values.put(KEY_DDE_IP, dde_settings.getDdeIp());

		// Insert Row
		db.insert(TABLE_DDE_SETTINGS, null, values);
		db.close();
	}

	DdeSettings getDdeSettings(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DDE_SETTINGS, new String[] {
				KEY_DDE_BATCH_SIZE, KEY_DDE_THRESHOLD_SIZE, KEY_DDE_SITE_CODE,
				KEY_DDE_USERNAME, KEY_DDE_PASSWORD, KEY_MODE, KEY_DDE_PORT,
				KEY_ID, KEY_DDE_IP }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		DdeSettings dde_settings = new DdeSettings(cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor
						.getString(7)), cursor.getString(8));

		cursor.close();

		// return dde_settings
		return dde_settings;
	}

	public List<DdeSettings> getAllDdeSettings() {
		List<DdeSettings> dde_settingsList = new ArrayList<DdeSettings>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_DDE_SETTINGS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DdeSettings dde_settings = new DdeSettings();
				dde_settings.setDdeBatchSize(cursor.getString(0));
				dde_settings.setDdeThresholdSize(cursor.getString(1));
				dde_settings.setDdeSiteCode(cursor.getString(2));
				dde_settings.setDdeUsername(cursor.getString(3));
				dde_settings.setDdePassword(cursor.getString(4));
				dde_settings.setMode(cursor.getString(5));
				dde_settings.setDdePort(Integer.parseInt(cursor.getString(6)));
				dde_settings.setId(Integer.parseInt(cursor.getString(7)));
				dde_settings.setDdeIp(cursor.getString(8));
				// Adding dde_settings to list
				dde_settingsList.add(dde_settings);
			} while (cursor.moveToNext());
		}

		cursor.close();

		// return dde_settings list
		return dde_settingsList;
	}

	public int updateDdeSettings(DdeSettings dde_settings) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DDE_BATCH_SIZE, dde_settings.getDdeBatchSize());
		values.put(KEY_DDE_THRESHOLD_SIZE, dde_settings.getDdeThresholdSize());
		values.put(KEY_DDE_SITE_CODE, dde_settings.getDdeSiteCode());
		values.put(KEY_DDE_USERNAME, dde_settings.getDdeUsername());
		values.put(KEY_DDE_PASSWORD, dde_settings.getDdePassword());
		values.put(KEY_MODE, dde_settings.getMode());
		values.put(KEY_DDE_PORT, dde_settings.getDdePort());
		values.put(KEY_ID, dde_settings.getId());
		values.put(KEY_DDE_IP, dde_settings.getDdeIp());

		// updating row
		return db.update(TABLE_DDE_SETTINGS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(dde_settings.getId()) });
	}

	public void deleteDdeSettings(DdeSettings dde_settings) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_DDE_SETTINGS, KEY_ID + " = ?",
				new String[] { String.valueOf(dde_settings.getId()) });
		db.close();
	}

	public int getDdeSettingsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_DDE_SETTINGS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();

		cursor.close();

		// return count
		return count;
	}

	public int getThreshold(String mode) {
		int result = 0;

		// Select All Query
		String selectQuery = "SELECT " + KEY_DDE_THRESHOLD_SIZE + " FROM "
				+ TABLE_DDE_SETTINGS + " WHERE " + KEY_MODE + " = '" + mode
				+ "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}

		cursor.close();

		return result;
	}

	public String getDDESetting(String mode, String setting) {
		String result = "";

		// Select All Query
		String selectQuery = "SELECT " + setting + " FROM "
				+ TABLE_DDE_SETTINGS + " WHERE " + KEY_MODE + " = '" + mode
				+ "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = cursor.getString(0);
		}

		cursor.close();

		return result;
	}
}
