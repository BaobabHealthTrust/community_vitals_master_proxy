package org.baobabhealthtrust.cvr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.baobabhealthtrust.cvr.models.AeSimpleSHA1;
import org.baobabhealthtrust.cvr.models.DdeSettings;
import org.baobabhealthtrust.cvr.models.User;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	private static final String TAG = "User Database Helper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	private static final String SP_KEY_DB_VER = "db_ver";

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
	private static final String KEY_TA = "ta";
	private static final String KEY_GVH = "group_village_headman";
	private static final String KEY_VH = "village_headman";
	
	public static int mCurrentUserId = 0;

	// Fetch size per page
	public static final int PAGE_SIZE = 10;

	private Context mContext;

	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		mContext = context;
		initialize();
	}

	/**
	 * Initializes database. Creates database if doesn't exist.
	 */
	private void initialize() {
		if (databaseExists()) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
			if (DATABASE_VERSION != dbVersion) {
				File dbFile = mContext.getDatabasePath(DATABASE_NAME);
				if (!dbFile.delete()) {
					Log.w(TAG, "Unable to update database");
				}
			}
		}
		if (!databaseExists()) {
			createDatabase();
		}
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

	/**
	 * Creates database by copying it from assets directory.
	 */
	private void createDatabase() {
		String parentPath = mContext.getDatabasePath(DATABASE_NAME).getParent();
		String path = mContext.getDatabasePath(DATABASE_NAME).getPath();

		File file = new File(parentPath);
		if (!file.exists()) {
			if (!file.mkdir()) {
				Log.w(TAG, "Unable to create database directory");
				return;
			}
		}

		InputStream is = null;
		OutputStream os = null;
		try {
			is = mContext.getAssets().open(DATABASE_NAME);
			os = new FileOutputStream(path);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		/*String CREATE_DDE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_DDE_SETTINGS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MODE + " TEXT,"
				+ KEY_DDE_USERNAME + " TEXT," + KEY_DDE_PASSWORD + " TEXT,"
				+ KEY_DDE_IP + " TEXT," + KEY_DDE_PORT + " INTEGER,"
				+ KEY_DDE_SITE_CODE + " TEXT," + KEY_DDE_BATCH_SIZE
				+ " INTEGER, " + KEY_DDE_THRESHOLD_SIZE + " INTEGER, "
				+ KEY_TA + " TEXT," + KEY_GVH + " TEXT," + KEY_VH + " TEXT," 
				+KEY_DATE_CREATED + " TEXT" + ")";

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
				+ KEY_TA
				+ ", "
				+ KEY_GVH
				+ ", "
				+ KEY_VH
				+ ", "
				+ KEY_DDE_THRESHOLD_SIZE
				+ ") VALUES ('ta', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 'unknown','unknown','unknown', 0 ),"
				+" ('gvh', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 'unknown','unknown','unknown', 0),"
				+" ('vh', 'unknown', 'unknown', 'unknown', 80, "
				+ "'unknown', 0, 'unknown','unknown','unknown',0)";

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
				+ ") VALUES (1, 1), (1, 3), (1, 4)";

		db.execSQL(INITIALISE_USER_ROLES);*/

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		/*db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);*/
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

		Log.i("login", "$$$$$$$$$$$ got passHash: " + passHash);

		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_USERNAME + " = '" + username + "' AND " + KEY_PASSWORD
				+ " = '" + passHash + "'";

		Log.i("login", "$$$$$$$$$$$ got selectQuery: " + selectQuery);

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

			db.close();

			return output;
		} else {
			cursor.close();

			db.close();

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

		db.close();

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

		db.close();

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

			db.close();

			return true;
		} else {
			cursor.close();

			db.close();

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

			db.close();

			return true;
		} else {
			cursor.close();

			db.close();

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
				KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_USER_STATUS },
				KEY_USER_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(cursor.getString(0), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7), cursor.getString(8));

		cursor.close();

		db.close();

		// return user
		return user;
	}

	User getUserByUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_PASSWORD,
				KEY_TOKEN, KEY_USER_ID, KEY_USERNAME, KEY_DATE_CREATED,
				KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_USER_STATUS },
				KEY_USERNAME + "=?", new String[] { username }, null, null,
				null, null);

		User user = null;

		if (cursor != null)
			if (cursor.moveToFirst()) {

				user = new User(cursor.getString(0), cursor.getString(1),
						Integer.parseInt(cursor.getString(2)),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6),
						cursor.getString(7), cursor.getString(8));
			}

		cursor.close();

		db.close();

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

		db.close();

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
		values.put(KEY_USER_STATUS, user.getStatus());

		// updating row
		int result = db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(user.getUserId()) });

		db.close();

		return result;
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
		int result = cursor.getCount();

		cursor.close();

		db.close();

		return result;
	}

	DdeSettings getDdeSettingsByMode(String mode) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DDE_SETTINGS, new String[] {
				KEY_DDE_BATCH_SIZE, KEY_DDE_THRESHOLD_SIZE, KEY_DDE_SITE_CODE,
				KEY_DDE_USERNAME, KEY_DDE_PASSWORD, KEY_MODE, KEY_DDE_PORT,
				KEY_ID, KEY_DDE_IP, KEY_TA, KEY_GVH, KEY_VH }, KEY_MODE + "=?",
				new String[] { String.valueOf(mode) }, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				DdeSettings dde_settings = new DdeSettings(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5),cursor.getString(9),
						cursor.getString(10),cursor.getString(11) ,
						Integer.parseInt(cursor.getString(6)), 
						Integer.parseInt(cursor.getString(7)), 
						cursor.getString(8));

				cursor.close();

				db.close();

				return dde_settings;
			}
		}

		cursor.close();

		db.close();

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
		values.put(KEY_TA, dde_settings.getTa());
		values.put(KEY_GVH, dde_settings.getGvh());
		values.put(KEY_VH, dde_settings.getVh());
		
		// Insert Row
		db.insert(TABLE_DDE_SETTINGS, null, values);
		db.close();
	}

	DdeSettings getDdeSettings(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DDE_SETTINGS, new String[] {
				KEY_DDE_BATCH_SIZE, KEY_DDE_THRESHOLD_SIZE, KEY_DDE_SITE_CODE,
				KEY_DDE_USERNAME, KEY_DDE_PASSWORD, KEY_MODE, KEY_DDE_PORT,
				KEY_ID, KEY_DDE_IP, KEY_TA, KEY_GVH, KEY_VH }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		DdeSettings dde_settings = new DdeSettings(cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5),cursor.getString(9),
				cursor.getString(10),cursor.getString(11),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor
						.getString(7)), cursor.getString(8));

		cursor.close();

		db.close();

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
				dde_settings.setTa(cursor.getString(9));
				dde_settings.setGvh(cursor.getString(10));
				dde_settings.setVh(cursor.getString(11));
				// Adding dde_settings to list
				dde_settingsList.add(dde_settings);
			} while (cursor.moveToNext());
		}

		cursor.close();

		db.close();

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
		values.put(KEY_TA, dde_settings.getTa());
		values.put(KEY_GVH, dde_settings.getGvh());
		values.put(KEY_VH, dde_settings.getVh());

		// updating row
		int result = db.update(TABLE_DDE_SETTINGS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(dde_settings.getId()) });

		db.close();

		return result;
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

		db.close();

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

		db.close();

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

		db.close();

		return result;
	}

	public String getRoles(int user_id) {
		JSONArray roles = new JSONArray();

		// Select All Query
		String selectQuery = "SELECT " + KEY_ROLE + " FROM " + TABLE_USER_ROLES
				+ " LEFT OUTER JOIN " + TABLE_ROLES + " ON " + TABLE_ROLES
				+ "." + KEY_ROLE_ID + " = " + TABLE_USER_ROLES + "."
				+ KEY_ROLE_ID + " WHERE " + KEY_USER_ID + " = " + user_id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				roles.put(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		cursor.close();

		db.close();

		return roles.toString();
	}

	void addUserRole(User user, String role) {
		SQLiteDatabase db = this.getWritableDatabase();

		int role_id = 0;

		String selectQuery = "SELECT " + KEY_ROLE_ID + " FROM " + TABLE_ROLES
				+ " WHERE " + KEY_ROLE + " = '" + role + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			role_id = Integer.parseInt(cursor.getString(0));
		}

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user.getUserId());
		values.put(KEY_ROLE_ID, role_id);

		// Insert Row
		db.insert(TABLE_USER_ROLES, null, values);
		db.close();
	}

	void revokeUserRole(User user, String role) {
		SQLiteDatabase db = this.getWritableDatabase();

		int role_id = 0;

		String selectQuery = "SELECT " + KEY_ROLE_ID + " FROM " + TABLE_ROLES
				+ " WHERE " + KEY_ROLE + " = '" + role + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			role_id = Integer.parseInt(cursor.getString(0));
		}

		if (role_id > 0) {
			// Delete Row
			db.delete(TABLE_USER_ROLES, KEY_USER_ID + " = ? AND " + KEY_ROLE_ID
					+ " = ?", new String[] { String.valueOf(user.getUserId()),
					String.valueOf(role_id) });
		}

		db.close();
	}

	int get_current_user()
	{
		return mCurrentUserId;
	}
}
