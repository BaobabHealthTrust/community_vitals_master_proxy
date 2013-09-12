package org.baobabhealthtrust.cvr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.baobabhealthtrust.cvr.models.NationalIdentifiers;
import org.baobabhealthtrust.cvr.models.OutcomeTypes;
import org.baobabhealthtrust.cvr.models.Outcomes;
import org.baobabhealthtrust.cvr.models.People;
import org.baobabhealthtrust.cvr.models.RelationshipTypes;
import org.baobabhealthtrust.cvr.models.Relationships;
import org.baobabhealthtrust.cvr.models.Sites;
import org.baobabhealthtrust.cvr.models.Vocabularies;
import org.baobabhealthtrust.cvr.models.Words;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables

	private static final String TAG = "Database Helper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "cvr";

	private static final String SP_KEY_DB_VER = "db_ver";

	// Table names
	private static final String TABLE_SITES = "sites";
	private static final String TABLE_PEOPLE = "people";
	private static final String TABLE_NATIONAL_IDENTIFIERS = "national_identifiers";
	private static final String TABLE_WORDS = "words";
	private static final String TABLE_RELATIONSHIP_TYPES = "relationship_types";
	private static final String TABLE_OUTCOME_TYPES = "outcome_types";
	private static final String TABLE_OUTCOMES = "outcomes";
	private static final String TABLE_RELATIONSHIPS = "relationships";
	private static final String TABLE_VOCABULARIES = "vocabularies";

	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_UPDATED_AT = "updated_at";
	private static final String KEY_DATE_VOIDED = "date_voided";
	private static final String KEY_ID = "id";
	private static final String KEY_VOIDED = "voided";
	private static final String KEY_NAME = "name";
	private static final String KEY_VOID_REASON = "void_reason";
	private static final String KEY_TA = "ta";
	private static final String KEY_OUTCOME = "outcome";
	private static final String KEY_BIRTHDATE = "birthdate";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_VILLAGE = "village";
	private static final String KEY_CITY_VILLAGE = "city_village";
	private static final String KEY_CREATOR_SITE_ID = "creator_site_id";
	private static final String KEY_MAIDEN_NAME = "maiden_name";
	private static final String KEY_NEIGHBOURHOOD_CELL = "neighbourhood_cell";
	private static final String KEY_CREATOR_ID = "creator_id";
	private static final String KEY_ADRRESS1 = "adrress1";
	private static final String KEY_GVH = "gvh";
	private static final String KEY_CELL_PHONE_NUMBER = "cell_phone_number";
	private static final String KEY_FAMILY_NAME = "family_name";
	private static final String KEY_COUNTY_DISTRICT = "county_district";
	private static final String KEY_OCCUPATION = "occupation";
	private static final String KEY_STATE_PROVINCE = "state_province";
	private static final String KEY_BIRTHDATE_ESTIMATED = "birthdate_estimated";
	private static final String KEY_GIVEN_NAME = "given_name";
	private static final String KEY_NATIONAL_ID = "national_id";
	private static final String KEY_OUTCOME_DATE = "outcome_date";
	private static final String KEY_ADDRESS2 = "address2";
	private static final String KEY_MIDDLE_NAME = "middle_name";
	private static final String KEY_REQUESTED_BY_GVH = "requested_by_gvh";
	private static final String KEY_ASSIGNED_VH = "assigned_vh";
	private static final String KEY_SITE_ID = "site_id";
	private static final String KEY_REQUEST_VH_NOTIFIED = "request_vh_notified";
	private static final String KEY_POST_VH_NOTIFIED = "post_vh_notified";
	private static final String KEY_PERSON_ID = "person_id";
	private static final String KEY_REQUESTED_BY_VH = "requested_by_vh";
	private static final String KEY_POSTED_BY_VH = "posted_by_vh";
	private static final String KEY_IDENTIFIER = "identifier";
	private static final String KEY_ASSIGNED_GVH = "assigned_gvh";
	private static final String KEY_POST_GVH_NOTIFIED = "post_gvh_notified";
	private static final String KEY_REQUEST_GVH_NOTIFIED = "request_gvh_notified";
	private static final String KEY_POSTED_BY_GVH = "posted_by_gvh";
	private static final String KEY_LOCALE = "locale";
	private static final String KEY_VALUE = "value";
	private static final String KEY_VOCABULARY_ID = "vocabulary_id";
	private static final String KEY_RELATION = "relation";
	private static final String KEY_UUID = "uuid";
	private static final String KEY_OUTCOME_TYPE = "outcome_type";
	private static final String KEY_PERSON_IS_TO_RELATION = "person_is_to_relation";
	private static final String KEY_PERSON_NATIONAL_ID = "person_national_id";
	private static final String KEY_RELATION_NATIONAL_ID = "relation_national_id";

	private Context mContext;

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public DatabaseHandler(Context context) {
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
	private boolean databaseExists() {
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

	void addSites(Sites sites) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, sites.getCreatedAt());
		values.put(KEY_UPDATED_AT, sites.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, sites.getDateVoided());
		values.put(KEY_VOIDED, sites.getVoided());
		values.put(KEY_NAME, sites.getName());
		values.put(KEY_VOID_REASON, sites.getVoidReason());

		// Insert Row
		db.insert(TABLE_SITES, null, values);
		db.close();
	}

	String[] addPeople(People people) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, people.getCreatedAt());
		values.put(KEY_TA, people.getTa());
		values.put(KEY_OUTCOME, people.getOutcome());
		values.put(KEY_BIRTHDATE, people.getBirthdate());
		values.put(KEY_GENDER, people.getGender());
		values.put(KEY_VILLAGE, people.getVillage());
		values.put(KEY_CITY_VILLAGE, people.getCityVillage());
		values.put(KEY_CREATOR_SITE_ID, people.getCreatorSiteId());
		values.put(KEY_UPDATED_AT, people.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, people.getDateVoided());
		values.put(KEY_MAIDEN_NAME, people.getMaidenName());
		values.put(KEY_NEIGHBOURHOOD_CELL, people.getNeighbourhoodCell());
		values.put(KEY_CREATOR_ID, people.getCreatorId());
		values.put(KEY_VOIDED, people.getVoided());
		values.put(KEY_ADRRESS1, people.getAdrress1());
		values.put(KEY_GVH, people.getGvh());
		values.put(KEY_CELL_PHONE_NUMBER, people.getCellPhoneNumber());
		values.put(KEY_FAMILY_NAME, people.getFamilyName());
		values.put(KEY_COUNTY_DISTRICT, people.getCountyDistrict());
		values.put(KEY_OCCUPATION, people.getOccupation());
		values.put(KEY_STATE_PROVINCE, people.getStateProvince());
		values.put(KEY_BIRTHDATE_ESTIMATED, people.getBirthdateEstimated());
		values.put(KEY_GIVEN_NAME, people.getGivenName());
		values.put(KEY_NATIONAL_ID, people.getNationalId());
		values.put(KEY_VOID_REASON, people.getVoidReason());
		values.put(KEY_OUTCOME_DATE, people.getOutcomeDate());
		values.put(KEY_ADDRESS2, people.getAddress2());
		values.put(KEY_MIDDLE_NAME, people.getMiddleName());

		// Insert Row
		db.insert(TABLE_PEOPLE, null, values);
		db.close();

		// int id = assignNationalId(Integer.parseInt(people.getNationalId()));

		int npid = Integer.parseInt(people.getNationalId());

		NationalIdentifiers identifier = getNationalIdentifiers(npid);

		identifier.setPersonId(npid);

		updateNationalIdentifiers(identifier);

		String result[] = { people.getId() + "", people.getGivenName(),
				people.getFamilyName(), identifier.getIdentifier(),
				people.getGender(), people.getBirthdate(),
				people.getBirthdateEstimated() + "" };

		return result;
	}

	int assignNationalId(int id) {
		People person = getPersonByNpid(id);

		String selectQuery = "UPDATE " + TABLE_NATIONAL_IDENTIFIERS + " SET "
				+ KEY_PERSON_ID + " = '" + person.getId() + "' WHERE " + KEY_ID
				+ " = " + id;

		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL(selectQuery);

		int pid = person.getId();

		return pid;
	}

	People getPersonByNpid(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PEOPLE, new String[] { KEY_CREATED_AT,
				KEY_TA, KEY_OUTCOME, KEY_BIRTHDATE, KEY_GENDER, KEY_VILLAGE,
				KEY_CITY_VILLAGE, KEY_CREATOR_SITE_ID, KEY_UPDATED_AT,
				KEY_DATE_VOIDED, KEY_MAIDEN_NAME, KEY_NEIGHBOURHOOD_CELL,
				KEY_CREATOR_ID, KEY_ID, KEY_VOIDED, KEY_ADRRESS1, KEY_GVH,
				KEY_CELL_PHONE_NUMBER, KEY_FAMILY_NAME, KEY_COUNTY_DISTRICT,
				KEY_OCCUPATION, KEY_STATE_PROVINCE, KEY_BIRTHDATE_ESTIMATED,
				KEY_GIVEN_NAME, KEY_NATIONAL_ID, KEY_VOID_REASON,
				KEY_OUTCOME_DATE, KEY_ADDRESS2, KEY_MIDDLE_NAME },
				KEY_NATIONAL_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		People people = new People(cursor.getString(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6),
				Integer.parseInt(cursor.getString(7)), cursor.getString(8),
				cursor.getString(9), cursor.getString(10),
				cursor.getString(11), Integer.parseInt(cursor.getString(12)),
				Integer.parseInt(cursor.getString(13)), Integer.parseInt(cursor
						.getString(14)), cursor.getString(15),
				cursor.getString(16), cursor.getString(17),
				cursor.getString(18), cursor.getString(19),
				cursor.getString(20), cursor.getString(21),
				Integer.parseInt(cursor.getString(22)), cursor.getString(23),
				cursor.getString(24), cursor.getString(25),
				cursor.getString(26), cursor.getString(27),
				cursor.getString(28));

		// return people
		return people;
	}

	void addNationalIdentifiers(NationalIdentifiers national_identifiers) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, national_identifiers.getCreatedAt());
		values.put(KEY_REQUESTED_BY_GVH,
				national_identifiers.getRequestedByGvh());
		values.put(KEY_ASSIGNED_VH, national_identifiers.getAssignedVh());
		values.put(KEY_SITE_ID, national_identifiers.getSiteId());
		values.put(KEY_REQUEST_VH_NOTIFIED,
				national_identifiers.getRequestVhNotified());
		values.put(KEY_POST_VH_NOTIFIED,
				national_identifiers.getPostVhNotified());
		values.put(KEY_PERSON_ID, national_identifiers.getPersonId());
		values.put(KEY_UPDATED_AT, national_identifiers.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, national_identifiers.getDateVoided());
		values.put(KEY_REQUESTED_BY_VH, national_identifiers.getRequestedByVh());
		values.put(KEY_POSTED_BY_VH, national_identifiers.getPostedByVh());
		values.put(KEY_IDENTIFIER, national_identifiers.getIdentifier());
		values.put(KEY_ASSIGNED_GVH, national_identifiers.getAssignedGvh());
		values.put(KEY_VOIDED, national_identifiers.getVoided());
		values.put(KEY_POST_GVH_NOTIFIED,
				national_identifiers.getPostGvhNotified());
		values.put(KEY_REQUEST_GVH_NOTIFIED,
				national_identifiers.getRequestGvhNotified());
		values.put(KEY_VOID_REASON, national_identifiers.getVoidReason());
		values.put(KEY_POSTED_BY_GVH, national_identifiers.getPostedByGvh());

		// Insert Row
		db.insert(TABLE_NATIONAL_IDENTIFIERS, null, values);
		db.close();
	}

	void addWords(Words words) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, words.getCreatedAt());
		values.put(KEY_LOCALE, words.getLocale());
		values.put(KEY_UPDATED_AT, words.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, words.getDateVoided());
		values.put(KEY_VALUE, words.getValue());
		values.put(KEY_VOCABULARY_ID, words.getVocabularyId());
		values.put(KEY_VOIDED, words.getVoided());
		values.put(KEY_VOID_REASON, words.getVoidReason());

		// Insert Row
		db.insert(TABLE_WORDS, null, values);
		db.close();
	}

	void addRelationshipTypes(RelationshipTypes relationship_types) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, relationship_types.getCreatedAt());
		values.put(KEY_UPDATED_AT, relationship_types.getUpdatedAt());
		values.put(KEY_RELATION, relationship_types.getRelation());
		values.put(KEY_VOCABULARY_ID, relationship_types.getVocabularyId());

		// Insert Row
		db.insert(TABLE_RELATIONSHIP_TYPES, null, values);
		db.close();
	}

	void addOutcomeTypes(OutcomeTypes outcome_types) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, outcome_types.getCreatedAt());
		values.put(KEY_UPDATED_AT, outcome_types.getUpdatedAt());
		values.put(KEY_VOCABULARY_ID, outcome_types.getVocabularyId());
		values.put(KEY_NAME, outcome_types.getName());

		// Insert Row
		db.insert(TABLE_OUTCOME_TYPES, null, values);
		db.close();
	}

	void addOutcomes(Outcomes outcomes) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, outcomes.getCreatedAt());
		values.put(KEY_UUID, outcomes.getUuid());
		values.put(KEY_PERSON_ID, outcomes.getPersonId());
		values.put(KEY_UPDATED_AT, outcomes.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, outcomes.getDateVoided());
		values.put(KEY_OUTCOME_TYPE, outcomes.getOutcomeType());
		values.put(KEY_VOIDED, outcomes.getVoided());
		values.put(KEY_VOID_REASON, outcomes.getVoidReason());
		values.put(KEY_OUTCOME_DATE, outcomes.getOutcomeDate());

		// Insert Row
		db.insert(TABLE_OUTCOMES, null, values);
		db.close();
	}

	void addRelationships(Relationships relationships) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, relationships.getCreatedAt());
		values.put(KEY_UPDATED_AT, relationships.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, relationships.getDateVoided());
		values.put(KEY_PERSON_IS_TO_RELATION,
				relationships.getPersonIsToRelation());
		values.put(KEY_PERSON_NATIONAL_ID, relationships.getPersonNationalId());
		values.put(KEY_VOIDED, relationships.getVoided());
		values.put(KEY_VOID_REASON, relationships.getVoidReason());
		values.put(KEY_RELATION_NATIONAL_ID,
				relationships.getRelationNationalId());

		// Insert Row
		db.insert(TABLE_RELATIONSHIPS, null, values);
		db.close();
	}

	void addVocabularies(Vocabularies vocabularies) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, vocabularies.getCreatedAt());
		values.put(KEY_UPDATED_AT, vocabularies.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, vocabularies.getDateVoided());
		values.put(KEY_VALUE, vocabularies.getValue());
		values.put(KEY_VOIDED, vocabularies.getVoided());
		values.put(KEY_VOID_REASON, vocabularies.getVoidReason());

		// Insert Row
		db.insert(TABLE_VOCABULARIES, null, values);
		db.close();
	}

	Sites getSites(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SITES, new String[] { KEY_CREATED_AT,
				KEY_UPDATED_AT, KEY_DATE_VOIDED, KEY_ID, KEY_VOIDED, KEY_NAME,
				KEY_VOID_REASON }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Sites sites = new Sites(cursor.getString(0), cursor.getString(1),
				cursor.getString(2), Integer.parseInt(cursor.getString(3)),
				Integer.parseInt(cursor.getString(4)), cursor.getString(5),
				cursor.getString(6));

		// return sites
		return sites;
	}

	People getPeople(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PEOPLE, new String[] { KEY_CREATED_AT,
				KEY_TA, KEY_OUTCOME, KEY_BIRTHDATE, KEY_GENDER, KEY_VILLAGE,
				KEY_CITY_VILLAGE, KEY_CREATOR_SITE_ID, KEY_UPDATED_AT,
				KEY_DATE_VOIDED, KEY_MAIDEN_NAME, KEY_NEIGHBOURHOOD_CELL,
				KEY_CREATOR_ID, KEY_ID, KEY_VOIDED, KEY_ADRRESS1, KEY_GVH,
				KEY_CELL_PHONE_NUMBER, KEY_FAMILY_NAME, KEY_COUNTY_DISTRICT,
				KEY_OCCUPATION, KEY_STATE_PROVINCE, KEY_BIRTHDATE_ESTIMATED,
				KEY_GIVEN_NAME, KEY_NATIONAL_ID, KEY_VOID_REASON,
				KEY_OUTCOME_DATE, KEY_ADDRESS2, KEY_MIDDLE_NAME }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);

		if (cursor != null)
			cursor.moveToFirst();

		People people = new People(cursor.getString(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5), cursor.getString(6),
				Integer.parseInt(cursor.getString(7)), cursor.getString(8),
				cursor.getString(9), cursor.getString(10),
				cursor.getString(11), Integer.parseInt(cursor.getString(12)),
				Integer.parseInt(cursor.getString(13)), Integer.parseInt(cursor
						.getString(14)), cursor.getString(15),
				cursor.getString(16), cursor.getString(17),
				cursor.getString(18), cursor.getString(19),
				cursor.getString(20), cursor.getString(21),
				Integer.parseInt(cursor.getString(22)), cursor.getString(23),
				cursor.getString(24), cursor.getString(25),
				cursor.getString(26), cursor.getString(27),
				cursor.getString(28));

		// return people
		return people;
	}

	NationalIdentifiers getNationalIdentifiers(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NATIONAL_IDENTIFIERS, new String[] {
				KEY_CREATED_AT, "COALESCE(" + KEY_REQUESTED_BY_GVH + ",0)",
				KEY_ASSIGNED_VH, KEY_SITE_ID,
				"COALESCE(" + KEY_REQUEST_VH_NOTIFIED + ",0)",
				"COALESCE(" + KEY_POST_VH_NOTIFIED + ",0)",
				"COALESCE(" + KEY_PERSON_ID + ",0)", KEY_UPDATED_AT,
				KEY_DATE_VOIDED, "COALESCE(" + KEY_REQUESTED_BY_VH + ",0)",
				"COALESCE(" + KEY_POSTED_BY_VH + ",0)", KEY_IDENTIFIER,
				"COALESCE(" + KEY_ASSIGNED_GVH + ",0)",
				"COALESCE(" + KEY_ID + ",0)", "COALESCE(" + KEY_VOIDED + ",0)",
				"COALESCE(" + KEY_POST_GVH_NOTIFIED + ",0)",
				"COALESCE(" + KEY_REQUEST_GVH_NOTIFIED + ",0)",
				KEY_VOID_REASON, "COALESCE(" + KEY_POSTED_BY_GVH + ",0)" },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);

		if (cursor != null)
			cursor.moveToFirst();

		NationalIdentifiers national_identifiers = new NationalIdentifiers(
				cursor.getString(0), Integer.parseInt(cursor.getString(1)),
				cursor.getString(2), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor
						.getString(5)), Integer.parseInt(cursor.getString(6)),
				cursor.getString(7), cursor.getString(8),
				Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor
						.getString(10)), cursor.getString(11),
				cursor.getString(12), Integer.parseInt(cursor.getString(13)),
				Integer.parseInt(cursor.getString(14)), Integer.parseInt(cursor
						.getString(15)),
				Integer.parseInt(cursor.getString(16)), cursor.getString(17),
				Integer.parseInt(cursor.getString(18)));

		// return national_identifiers
		return national_identifiers;
	}

	Words getWords(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_CREATED_AT,
				KEY_LOCALE, KEY_UPDATED_AT, KEY_DATE_VOIDED, KEY_VALUE,
				KEY_VOCABULARY_ID, KEY_ID, KEY_VOIDED, KEY_VOID_REASON },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Words words = new Words(cursor.getString(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor
						.getString(6)), Integer.parseInt(cursor.getString(7)),
				cursor.getString(8));

		// return words
		return words;
	}

	RelationshipTypes getRelationshipTypes(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RELATIONSHIP_TYPES, new String[] {
				KEY_CREATED_AT, KEY_UPDATED_AT, KEY_RELATION,
				KEY_VOCABULARY_ID, KEY_ID }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		RelationshipTypes relationship_types = new RelationshipTypes(
				cursor.getString(0), cursor.getString(1), cursor.getString(2),
				Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor
						.getString(4)));

		// return relationship_types
		return relationship_types;
	}

	OutcomeTypes getOutcomeTypes(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTCOME_TYPES, new String[] {
				KEY_CREATED_AT, KEY_UPDATED_AT, KEY_VOCABULARY_ID, KEY_ID,
				KEY_NAME }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		OutcomeTypes outcome_types = new OutcomeTypes(cursor.getString(0),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)),
				Integer.parseInt(cursor.getString(3)), cursor.getString(4));

		// return outcome_types
		return outcome_types;
	}

	Outcomes getOutcomes(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_OUTCOMES, new String[] { KEY_CREATED_AT,
				KEY_UUID, KEY_PERSON_ID, KEY_UPDATED_AT, KEY_DATE_VOIDED,
				KEY_OUTCOME_TYPE, KEY_ID, KEY_VOIDED, KEY_VOID_REASON,
				KEY_OUTCOME_DATE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Outcomes outcomes = new Outcomes(cursor.getString(0),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)),
				cursor.getString(3), cursor.getString(4),
				Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor
						.getString(6)), Integer.parseInt(cursor.getString(7)),
				cursor.getString(8), cursor.getString(9));

		// return outcomes
		return outcomes;
	}

	Relationships getRelationships(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RELATIONSHIPS, new String[] {
				KEY_CREATED_AT, KEY_UPDATED_AT, KEY_DATE_VOIDED,
				KEY_PERSON_IS_TO_RELATION, KEY_PERSON_NATIONAL_ID, KEY_ID,
				KEY_VOIDED, KEY_VOID_REASON, KEY_RELATION_NATIONAL_ID }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);

		if (cursor != null)
			cursor.moveToFirst();

		Relationships relationships = new Relationships(cursor.getString(0),
				cursor.getString(1), cursor.getString(2),
				Integer.parseInt(cursor.getString(3)), cursor.getString(4),
				Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor
						.getString(6)), cursor.getString(7),
				cursor.getString(8));

		// return relationships
		return relationships;
	}

	Vocabularies getVocabularies(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_VOCABULARIES, new String[] {
				KEY_CREATED_AT, KEY_UPDATED_AT, KEY_DATE_VOIDED, KEY_VALUE,
				KEY_ID, KEY_VOIDED, KEY_VOID_REASON }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Vocabularies vocabularies = new Vocabularies(cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor
						.getString(5)), cursor.getString(6));

		// return vocabularies
		return vocabularies;
	}

	public List<Sites> getAllSites() {
		List<Sites> sitesList = new ArrayList<Sites>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SITES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Sites sites = new Sites();
				sites.setCreatedAt(cursor.getString(0));
				sites.setUpdatedAt(cursor.getString(1));
				sites.setDateVoided(cursor.getString(2));
				sites.setId(Integer.parseInt(cursor.getString(3)));
				sites.setVoided(Integer.parseInt(cursor.getString(4)));
				sites.setName(cursor.getString(5));
				sites.setVoidReason(cursor.getString(6));
				// Adding sites to list
				sitesList.add(sites);
			} while (cursor.moveToNext());
		}

		// return sites list
		return sitesList;
	}

	public List<People> getAllPeople() {
		List<People> peopleList = new ArrayList<People>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PEOPLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				People people = new People();
				people.setCreatedAt(cursor.getString(0));
				people.setTa(cursor.getString(1));
				people.setOutcome(cursor.getString(2));
				people.setBirthdate(cursor.getString(3));
				people.setGender(cursor.getString(4));
				people.setVillage(cursor.getString(5));
				people.setCityVillage(cursor.getString(6));
				people.setCreatorSiteId(Integer.parseInt(cursor.getString(7)));
				people.setUpdatedAt(cursor.getString(8));
				people.setDateVoided(cursor.getString(9));
				people.setMaidenName(cursor.getString(10));
				people.setNeighbourhoodCell(cursor.getString(11));
				people.setCreatorId(Integer.parseInt(cursor.getString(12)));
				people.setId(Integer.parseInt(cursor.getString(13)));
				people.setVoided(Integer.parseInt(cursor.getString(14)));
				people.setAdrress1(cursor.getString(15));
				people.setGvh(cursor.getString(16));
				people.setCellPhoneNumber(cursor.getString(17));
				people.setFamilyName(cursor.getString(18));
				people.setCountyDistrict(cursor.getString(19));
				people.setOccupation(cursor.getString(20));
				people.setStateProvince(cursor.getString(21));
				people.setBirthdateEstimated(Integer.parseInt(cursor
						.getString(22)));
				people.setGivenName(cursor.getString(23));
				people.setNationalId(cursor.getString(24));
				people.setVoidReason(cursor.getString(25));
				people.setOutcomeDate(cursor.getString(26));
				people.setAddress2(cursor.getString(27));
				people.setMiddleName(cursor.getString(28));
				// Adding people to list
				peopleList.add(people);
			} while (cursor.moveToNext());
		}

		// return people list
		return peopleList;
	}

	public List<NationalIdentifiers> getAllNationalIdentifiers() {
		List<NationalIdentifiers> national_identifiersList = new ArrayList<NationalIdentifiers>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NATIONAL_IDENTIFIERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				NationalIdentifiers national_identifiers = new NationalIdentifiers();
				national_identifiers.setCreatedAt(cursor.getString(0));
				national_identifiers.setRequestedByGvh(Integer.parseInt(cursor
						.getString(1)));
				national_identifiers.setAssignedVh(cursor.getString(2));
				national_identifiers.setSiteId(cursor.getString(3));
				national_identifiers.setRequestVhNotified(Integer
						.parseInt(cursor.getString(4)));
				national_identifiers.setPostVhNotified(Integer.parseInt(cursor
						.getString(5)));
				national_identifiers.setPersonId(Integer.parseInt(cursor
						.getString(6)));
				national_identifiers.setUpdatedAt(cursor.getString(7));
				national_identifiers.setDateVoided(cursor.getString(8));
				national_identifiers.setRequestedByVh(Integer.parseInt(cursor
						.getString(9)));
				national_identifiers.setPostedByVh(Integer.parseInt(cursor
						.getString(10)));
				national_identifiers.setIdentifier(cursor.getString(11));
				national_identifiers.setAssignedGvh(cursor.getString(12));
				national_identifiers.setId(Integer.parseInt(cursor
						.getString(13)));
				national_identifiers.setVoided(Integer.parseInt(cursor
						.getString(14)));
				national_identifiers.setPostGvhNotified(Integer.parseInt(cursor
						.getString(15)));
				national_identifiers.setRequestGvhNotified(Integer
						.parseInt(cursor.getString(16)));
				national_identifiers.setVoidReason(cursor.getString(17));
				national_identifiers.setPostedByGvh(Integer.parseInt(cursor
						.getString(18)));
				// Adding national_identifiers to list
				national_identifiersList.add(national_identifiers);
			} while (cursor.moveToNext());
		}

		// return national_identifiers list
		return national_identifiersList;
	}

	public List<Words> getAllWords() {
		List<Words> wordsList = new ArrayList<Words>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Words words = new Words();
				words.setCreatedAt(cursor.getString(0));
				words.setLocale(cursor.getString(1));
				words.setUpdatedAt(cursor.getString(2));
				words.setDateVoided(cursor.getString(3));
				words.setValue(cursor.getString(4));
				words.setVocabularyId(Integer.parseInt(cursor.getString(5)));
				words.setId(Integer.parseInt(cursor.getString(6)));
				words.setVoided(Integer.parseInt(cursor.getString(7)));
				words.setVoidReason(cursor.getString(8));
				// Adding words to list
				wordsList.add(words);
			} while (cursor.moveToNext());
		}

		// return words list
		return wordsList;
	}

	public List<RelationshipTypes> getAllRelationshipTypes() {
		List<RelationshipTypes> relationship_typesList = new ArrayList<RelationshipTypes>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP_TYPES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				RelationshipTypes relationship_types = new RelationshipTypes();
				relationship_types.setCreatedAt(cursor.getString(0));
				relationship_types.setUpdatedAt(cursor.getString(1));
				relationship_types.setRelation(cursor.getString(2));
				relationship_types.setVocabularyId(Integer.parseInt(cursor
						.getString(3)));
				relationship_types.setId(Integer.parseInt(cursor.getString(4)));
				// Adding relationship_types to list
				relationship_typesList.add(relationship_types);
			} while (cursor.moveToNext());
		}

		// return relationship_types list
		return relationship_typesList;
	}

	public List<OutcomeTypes> getAllOutcomeTypes() {
		List<OutcomeTypes> outcome_typesList = new ArrayList<OutcomeTypes>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTCOME_TYPES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				OutcomeTypes outcome_types = new OutcomeTypes();
				outcome_types.setCreatedAt(cursor.getString(0));
				outcome_types.setUpdatedAt(cursor.getString(1));
				outcome_types.setVocabularyId(Integer.parseInt(cursor
						.getString(2)));
				outcome_types.setId(Integer.parseInt(cursor.getString(3)));
				outcome_types.setName(cursor.getString(4));
				// Adding outcome_types to list
				outcome_typesList.add(outcome_types);
			} while (cursor.moveToNext());
		}

		// return outcome_types list
		return outcome_typesList;
	}

	public List<Outcomes> getAllOutcomes() {
		List<Outcomes> outcomesList = new ArrayList<Outcomes>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_OUTCOMES;

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Outcomes outcomes = new Outcomes();
				outcomes.setCreatedAt(cursor.getString(0));
				outcomes.setUuid(cursor.getString(1));
				outcomes.setPersonId(Integer.parseInt(cursor.getString(2)));
				outcomes.setUpdatedAt(cursor.getString(3));
				outcomes.setDateVoided(cursor.getString(4));
				outcomes.setOutcomeType(Integer.parseInt(cursor.getString(5)));
				outcomes.setId(Integer.parseInt(cursor.getString(6)));
				outcomes.setVoided(Integer.parseInt(cursor.getString(7)));
				outcomes.setVoidReason(cursor.getString(8));
				outcomes.setOutcomeDate(cursor.getString(9));
				// Adding outcomes to list
				outcomesList.add(outcomes);
			} while (cursor.moveToNext());
		}

		// return outcomes list
		return outcomesList;
	}

	public List<Relationships> getAllRelationships() {
		List<Relationships> relationshipsList = new ArrayList<Relationships>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RELATIONSHIPS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Relationships relationships = new Relationships();
				relationships.setCreatedAt(cursor.getString(0));
				relationships.setUpdatedAt(cursor.getString(1));
				relationships.setDateVoided(cursor.getString(2));
				relationships.setPersonIsToRelation(Integer.parseInt(cursor
						.getString(3)));
				relationships.setPersonNationalId(cursor.getString(4));
				relationships.setId(Integer.parseInt(cursor.getString(5)));
				relationships.setVoided(Integer.parseInt(cursor.getString(6)));
				relationships.setVoidReason(cursor.getString(7));
				relationships.setRelationNationalId(cursor.getString(8));
				// Adding relationships to list
				relationshipsList.add(relationships);
			} while (cursor.moveToNext());
		}

		// return relationships list
		return relationshipsList;
	}

	public List<Vocabularies> getAllVocabularies() {
		List<Vocabularies> vocabulariesList = new ArrayList<Vocabularies>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_VOCABULARIES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Vocabularies vocabularies = new Vocabularies();
				vocabularies.setCreatedAt(cursor.getString(0));
				vocabularies.setUpdatedAt(cursor.getString(1));
				vocabularies.setDateVoided(cursor.getString(2));
				vocabularies.setValue(cursor.getString(3));
				vocabularies.setId(Integer.parseInt(cursor.getString(4)));
				vocabularies.setVoided(Integer.parseInt(cursor.getString(5)));
				vocabularies.setVoidReason(cursor.getString(6));
				// Adding vocabularies to list
				vocabulariesList.add(vocabularies);
			} while (cursor.moveToNext());
		}

		// return vocabularies list
		return vocabulariesList;
	}

	public int updateSites(Sites sites) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, sites.getCreatedAt());
		values.put(KEY_UPDATED_AT, sites.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, sites.getDateVoided());
		values.put(KEY_ID, sites.getId());
		values.put(KEY_VOIDED, sites.getVoided());
		values.put(KEY_NAME, sites.getName());
		values.put(KEY_VOID_REASON, sites.getVoidReason());

		// updating row
		return db.update(TABLE_SITES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(sites.getId()) });
	}

	public int updatePeople(People people) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, people.getCreatedAt());
		values.put(KEY_TA, people.getTa());
		values.put(KEY_OUTCOME, people.getOutcome());
		values.put(KEY_BIRTHDATE, people.getBirthdate());
		values.put(KEY_GENDER, people.getGender());
		values.put(KEY_VILLAGE, people.getVillage());
		values.put(KEY_CITY_VILLAGE, people.getCityVillage());
		values.put(KEY_CREATOR_SITE_ID, people.getCreatorSiteId());
		values.put(KEY_UPDATED_AT, people.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, people.getDateVoided());
		values.put(KEY_MAIDEN_NAME, people.getMaidenName());
		values.put(KEY_NEIGHBOURHOOD_CELL, people.getNeighbourhoodCell());
		values.put(KEY_CREATOR_ID, people.getCreatorId());
		values.put(KEY_ID, people.getId());
		values.put(KEY_VOIDED, people.getVoided());
		values.put(KEY_ADRRESS1, people.getAdrress1());
		values.put(KEY_GVH, people.getGvh());
		values.put(KEY_CELL_PHONE_NUMBER, people.getCellPhoneNumber());
		values.put(KEY_FAMILY_NAME, people.getFamilyName());
		values.put(KEY_COUNTY_DISTRICT, people.getCountyDistrict());
		values.put(KEY_OCCUPATION, people.getOccupation());
		values.put(KEY_STATE_PROVINCE, people.getStateProvince());
		values.put(KEY_BIRTHDATE_ESTIMATED, people.getBirthdateEstimated());
		values.put(KEY_GIVEN_NAME, people.getGivenName());
		values.put(KEY_NATIONAL_ID, people.getNationalId());
		values.put(KEY_VOID_REASON, people.getVoidReason());
		values.put(KEY_OUTCOME_DATE, people.getOutcomeDate());
		values.put(KEY_ADDRESS2, people.getAddress2());
		values.put(KEY_MIDDLE_NAME, people.getMiddleName());

		// updating row
		return db.update(TABLE_PEOPLE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(people.getId()) });
	}

	public int updateNationalIdentifiers(
			NationalIdentifiers national_identifiers) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, national_identifiers.getCreatedAt());
		values.put(KEY_REQUESTED_BY_GVH,
				national_identifiers.getRequestedByGvh());
		values.put(KEY_ASSIGNED_VH, national_identifiers.getAssignedVh());
		values.put(KEY_SITE_ID, national_identifiers.getSiteId());
		values.put(KEY_REQUEST_VH_NOTIFIED,
				national_identifiers.getRequestVhNotified());
		values.put(KEY_POST_VH_NOTIFIED,
				national_identifiers.getPostVhNotified());
		values.put(KEY_PERSON_ID, national_identifiers.getPersonId());
		values.put(KEY_UPDATED_AT, national_identifiers.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, national_identifiers.getDateVoided());
		values.put(KEY_REQUESTED_BY_VH, national_identifiers.getRequestedByVh());
		values.put(KEY_POSTED_BY_VH, national_identifiers.getPostedByVh());
		values.put(KEY_IDENTIFIER, national_identifiers.getIdentifier());
		values.put(KEY_ASSIGNED_GVH, national_identifiers.getAssignedGvh());
		values.put(KEY_ID, national_identifiers.getId());
		values.put(KEY_VOIDED, national_identifiers.getVoided());
		values.put(KEY_POST_GVH_NOTIFIED,
				national_identifiers.getPostGvhNotified());
		values.put(KEY_REQUEST_GVH_NOTIFIED,
				national_identifiers.getRequestGvhNotified());
		values.put(KEY_VOID_REASON, national_identifiers.getVoidReason());
		values.put(KEY_POSTED_BY_GVH, national_identifiers.getPostedByGvh());

		// updating row
		return db.update(TABLE_NATIONAL_IDENTIFIERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(national_identifiers.getId()) });
	}

	public int updateWords(Words words) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, words.getCreatedAt());
		values.put(KEY_LOCALE, words.getLocale());
		values.put(KEY_UPDATED_AT, words.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, words.getDateVoided());
		values.put(KEY_VALUE, words.getValue());
		values.put(KEY_VOCABULARY_ID, words.getVocabularyId());
		values.put(KEY_ID, words.getId());
		values.put(KEY_VOIDED, words.getVoided());
		values.put(KEY_VOID_REASON, words.getVoidReason());

		// updating row
		return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(words.getId()) });
	}

	public int updateRelationshipTypes(RelationshipTypes relationship_types) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, relationship_types.getCreatedAt());
		values.put(KEY_UPDATED_AT, relationship_types.getUpdatedAt());
		values.put(KEY_RELATION, relationship_types.getRelation());
		values.put(KEY_VOCABULARY_ID, relationship_types.getVocabularyId());
		values.put(KEY_ID, relationship_types.getId());

		// updating row
		return db.update(TABLE_RELATIONSHIP_TYPES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(relationship_types.getId()) });
	}

	public int updateOutcomeTypes(OutcomeTypes outcome_types) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, outcome_types.getCreatedAt());
		values.put(KEY_UPDATED_AT, outcome_types.getUpdatedAt());
		values.put(KEY_VOCABULARY_ID, outcome_types.getVocabularyId());
		values.put(KEY_ID, outcome_types.getId());
		values.put(KEY_NAME, outcome_types.getName());

		// updating row
		return db.update(TABLE_OUTCOME_TYPES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(outcome_types.getId()) });
	}

	public int updateOutcomes(Outcomes outcomes) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, outcomes.getCreatedAt());
		values.put(KEY_UUID, outcomes.getUuid());
		values.put(KEY_PERSON_ID, outcomes.getPersonId());
		values.put(KEY_UPDATED_AT, outcomes.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, outcomes.getDateVoided());
		values.put(KEY_OUTCOME_TYPE, outcomes.getOutcomeType());
		values.put(KEY_ID, outcomes.getId());
		values.put(KEY_VOIDED, outcomes.getVoided());
		values.put(KEY_VOID_REASON, outcomes.getVoidReason());
		values.put(KEY_OUTCOME_DATE, outcomes.getOutcomeDate());

		// updating row
		return db.update(TABLE_OUTCOMES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(outcomes.getId()) });
	}

	public int updateRelationships(Relationships relationships) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, relationships.getCreatedAt());
		values.put(KEY_UPDATED_AT, relationships.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, relationships.getDateVoided());
		values.put(KEY_PERSON_IS_TO_RELATION,
				relationships.getPersonIsToRelation());
		values.put(KEY_PERSON_NATIONAL_ID, relationships.getPersonNationalId());
		values.put(KEY_ID, relationships.getId());
		values.put(KEY_VOIDED, relationships.getVoided());
		values.put(KEY_VOID_REASON, relationships.getVoidReason());
		values.put(KEY_RELATION_NATIONAL_ID,
				relationships.getRelationNationalId());

		// updating row
		return db.update(TABLE_RELATIONSHIPS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(relationships.getId()) });
	}

	public int updateVocabularies(Vocabularies vocabularies) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, vocabularies.getCreatedAt());
		values.put(KEY_UPDATED_AT, vocabularies.getUpdatedAt());
		values.put(KEY_DATE_VOIDED, vocabularies.getDateVoided());
		values.put(KEY_VALUE, vocabularies.getValue());
		values.put(KEY_ID, vocabularies.getId());
		values.put(KEY_VOIDED, vocabularies.getVoided());
		values.put(KEY_VOID_REASON, vocabularies.getVoidReason());

		// updating row
		return db.update(TABLE_VOCABULARIES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(vocabularies.getId()) });
	}

	public void deleteSites(Sites sites) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SITES, KEY_ID + " = ?",
				new String[] { String.valueOf(sites.getId()) });
		db.close();
	}

	public void deletePeople(People people) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PEOPLE, KEY_ID + " = ?",
				new String[] { String.valueOf(people.getId()) });
		db.close();
	}

	public void deleteNationalIdentifiers(
			NationalIdentifiers national_identifiers) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NATIONAL_IDENTIFIERS, KEY_ID + " = ?",
				new String[] { String.valueOf(national_identifiers.getId()) });
		db.close();
	}

	public void deleteWords(Words words) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WORDS, KEY_ID + " = ?",
				new String[] { String.valueOf(words.getId()) });
		db.close();
	}

	public void deleteRelationshipTypes(RelationshipTypes relationship_types) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_RELATIONSHIP_TYPES, KEY_ID + " = ?",
				new String[] { String.valueOf(relationship_types.getId()) });
		db.close();
	}

	public void deleteOutcomeTypes(OutcomeTypes outcome_types) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_OUTCOME_TYPES, KEY_ID + " = ?",
				new String[] { String.valueOf(outcome_types.getId()) });
		db.close();
	}

	public void deleteOutcomes(Outcomes outcomes) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_OUTCOMES, KEY_ID + " = ?",
				new String[] { String.valueOf(outcomes.getId()) });
		db.close();
	}

	public void deleteRelationships(Relationships relationships) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_RELATIONSHIPS, KEY_ID + " = ?",
				new String[] { String.valueOf(relationships.getId()) });
		db.close();
	}

	public void deleteVocabularies(Vocabularies vocabularies) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_VOCABULARIES, KEY_ID + " = ?",
				new String[] { String.valueOf(vocabularies.getId()) });
		db.close();
	}

	public int getSitesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SITES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getPeopleCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PEOPLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getNationalIdentifiersCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NATIONAL_IDENTIFIERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getWordsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_WORDS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getRelationshipTypesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_RELATIONSHIP_TYPES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getOutcomeTypesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_OUTCOME_TYPES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getOutcomesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_OUTCOMES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getRelationshipsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_RELATIONSHIPS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getVocabulariesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_VOCABULARIES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getBlankNPID() {
		int id = 0;

		String selectQuery = "SELECT id FROM " + TABLE_NATIONAL_IDENTIFIERS
				+ " WHERE COALESCE(person_id, 0) = 0 LIMIT 1";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}

		return id;
	}

	public List<String> getFirstNames(String filter) {
		List<String> namesList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_GIVEN_NAME + " FROM "
				+ TABLE_PEOPLE + " WHERE " + KEY_GIVEN_NAME + " LIKE '"
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
		String selectQuery = "SELECT " + KEY_FAMILY_NAME + " FROM "
				+ TABLE_PEOPLE + " WHERE " + KEY_FAMILY_NAME + " LIKE '"
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

		return namesList;
	}

	public String search(String word, String locale) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT w." + KEY_VALUE + " FROM "
				+ TABLE_VOCABULARIES + " v LEFT OUTER JOIN " + TABLE_WORDS
				+ " w ON w." + KEY_VOCABULARY_ID + " = v." + KEY_ID
				+ " WHERE v." + KEY_VALUE + " = '" + word + "' AND w."
				+ KEY_LOCALE + " = '" + locale + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		String term = word;

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				term = cursor.getString(0);
			} else {
				Log.i("SEARCH DEBUGGING", selectQuery);
			}
		}

		return term;
	}

	public List<People> getPeopleNames(String fname, String lname, String gender) {
		List<People> namesList = new ArrayList<People>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_ID + ", " + KEY_GIVEN_NAME + ", "
				+ KEY_FAMILY_NAME + ", " + KEY_GENDER + ", " + KEY_BIRTHDATE
				+ " FROM " + TABLE_PEOPLE + " WHERE " + KEY_GIVEN_NAME
				+ " LIKE '" + fname + "%' AND " + KEY_FAMILY_NAME + " LIKE '"
				+ lname + "%' AND " + KEY_GENDER + " = '" + gender + "' "
				+ " LIMIT 0,20";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				People person = new People();

				NationalIdentifiers identifier = getNationalIdentifiers(Integer
						.parseInt(cursor.getString(0)));

				person.setId(Integer.parseInt(cursor.getString(0)));
				person.setGivenName(cursor.getString(1));
				person.setFamilyName(cursor.getString(2));
				person.setGender(cursor.getString(3));
				person.setBirthdate(cursor.getString(4));
				person.setNationalId(identifier.getIdentifier());

				// Adding person
				namesList.add(person);
			} while (cursor.moveToNext());
		}

		// return relationship_type list
		return namesList;
	}

	public int getRelationByType(String relation) {
		int result = 0;

		// Select All Query
		String selectQuery = "SELECT " + KEY_ID + " FROM "
				+ TABLE_RELATIONSHIP_TYPES + " WHERE UPPER(" + KEY_RELATION
				+ ") = UPPER('" + relation + "')";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}

		return result;
	}

	public String[] getPersonById(int id) {
		Log.i("SEARCHING", "Got in with " + id);

		People people = getPeople(id);

		int npid = Integer.parseInt(people.getNationalId());

		NationalIdentifiers identifier = getNationalIdentifiers(npid);

		String result[] = { people.getId() + "", people.getGivenName(),
				people.getFamilyName(), identifier.getIdentifier(),
				people.getGender(), people.getBirthdate(),
				people.getBirthdateEstimated() + "" };

		return result;
	}

	public int getOutcomeByType(String relation) {
		int result = 0;

		// Select All Query
		String selectQuery = "SELECT " + KEY_ID + " FROM "
				+ TABLE_OUTCOME_TYPES + " WHERE UPPER(" + KEY_NAME
				+ ") = UPPER('" + relation + "')";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}

		return result;
	}

	public int getAvailableIds(String mode) {
		int result = 0;

		// Select All Query
		String selectQuery = "SELECT COUNT(*) FROM "
				+ TABLE_NATIONAL_IDENTIFIERS + " WHERE COALESCE(person_id, 0) = 0";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}

		return result;
	}

	public int getTakenIds(String mode) {
		int result = 0;

		// Select All Query
		String selectQuery = "SELECT COUNT(*) FROM "
				+ TABLE_NATIONAL_IDENTIFIERS
				+ " WHERE COALESCE(person_id, 0) != 0";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}

		return result;
	}
	
	public int getGenderCount(String date_selected, String gender){
		int result = 0;
		// Select All Query
		String selectQuery = "SELECT COUNT(*) FROM "
				+ TABLE_PEOPLE + " WHERE voided != 1 AND gender ='" + gender +"'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}
		
		return result;
	}
	
	public int getOutcomeCount(String date_selected, String outcome){
		int result = 0;
		// Select All Query
		String selectQuery = "SELECT COUNT(*) FROM "
				+ TABLE_PEOPLE + " WHERE voided != 1 AND outcome ='" + outcome +"'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}
		
		return result;
	}
	
	public int getAgegroupCount(String date_selected , String [] age_group){
		int result = 0;
		String selectQuery = "SELECT COUNT(*) FROM "
				+ TABLE_PEOPLE + " WHERE voided != 1 AND "
				+"(strftime('%Y','now')-strftime('%Y', birthdate )) IN " + age_group ;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			result = Integer.parseInt(cursor.getString(0));
		}
		
		
		return result;
	}
}
