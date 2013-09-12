package org.baobabhealthtrust.cvr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;
import org.baobabhealthtrust.cvr.models.AeSimpleSHA1;
import org.baobabhealthtrust.cvr.models.DdeSettings;
import org.baobabhealthtrust.cvr.models.Outcomes;
import org.baobabhealthtrust.cvr.models.People;
import org.baobabhealthtrust.cvr.models.Relationships;
import org.baobabhealthtrust.cvr.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.net.Credentials;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
	Context mContext;
	Home mParent;
	DatabaseHandler mDB;
	UserDatabaseHandler mUDB;
	private int mID;
	private int mCategory;
	private static final int KEY_PERSON = 1;
	private static final int KEY_USER = 2;

	SharedPreferences mPrefs;

	/** Instantiate the interface and set the context */
	WebAppInterface(Context c, Home parent) {
		mContext = c;
		mParent = parent;

		mDB = new DatabaseHandler(c);

		mUDB = new UserDatabaseHandler(c);

		mPrefs = c.getSharedPreferences("PrefFile", 0);

	}

	/** Show a toast from the web page */
	@JavascriptInterface
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}

	@JavascriptInterface
	public void showMsg(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setCancelable(true);
		builder.setTitle(msg);
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	@JavascriptInterface
	public void confirmDeletion(String msg, int id, int cat) {
		mID = id;
		mCategory = cat;

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setCancelable(true);
		builder.setTitle(msg);
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (mCategory) {
				case KEY_PERSON:
					deletePerson(mID);
					break;
				case KEY_USER:
					deleteUser(mID);
					break;
				}

				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	@JavascriptInterface
	public void deletePerson(int id) {
		People person = mDB.getPeople(id);

		mDB.deletePeople(person);

		showMsg("Person deleted!");

		mParent.myWebView.loadUrl("file:///android_asset/reports.html");
	}

	@JavascriptInterface
	public String doLogin(String username, String password) {

		// Log.i("LOGIN PARAMETERS", username + ", " + password);

		String token = mUDB.login(username, password);

		// Log.i("LOGIN STATUS", token);

		if (token.trim().length() > 0) {
			mParent.myWebView.loadUrl("javascript:displayUsers()");
		} else {
			showMsg("User Login Failed!");
		}

		Editor editor = mPrefs.edit();

		editor.putString("token", token);

		editor.commit();

		return token;
	}

	@JavascriptInterface
	public void doLogout(String token) {
		mUDB.logout(token);

		Editor editor = mPrefs.edit();

		editor.clear(); // .remove("token");

		editor.commit();

		mParent.myWebView.loadUrl("javascript:displayUsers()");
	}

	@JavascriptInterface
	public String getToken() {
		String token = mPrefs.getString("token", "");

		return token;
	}

	@JavascriptInterface
	public String getPref(String pref) {
		String item = mPrefs.getString(pref, "");

		return item;
	}

	@JavascriptInterface
	public void setPref(String pref, String value) {
		Editor editor = mPrefs.edit();

		editor.putString(pref, value);

		editor.commit();
	}

	@JavascriptInterface
	public void addUser(String username, String password, String date_created) {

		if (mUDB.userExists(username)) {
			showMsg("Username already taken!");
			return;
		}

		User user = new User();

		AeSimpleSHA1 sha = new AeSimpleSHA1();

		try {
			String pass = sha.SHA1(password);

			user.setUsername(username);
			user.setPassword(pass);
			user.setDateCreated(date_created);

			mUDB.addUser(user);

			showMsg("User Created!");

			mParent.myWebView.loadUrl("javascript:displayUsers()");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@JavascriptInterface
	public String getUsers() {
		JSONObject json = new JSONObject();

		List<User> users = mUDB.getAllUsers();

		for (int i = 0; i < users.size(); i++) {
			JSONObject pjson = new JSONObject();

			User user = users.get(i);

			try {
				pjson.put("user_id", user.getUserId());
				pjson.put("username", user.getUsername());
				pjson.put("password", user.getPassword());
				pjson.put("token", user.getToken());
				pjson.put("date_created", user.getDateCreated());

				json.put(user.getUserId() + "", pjson);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showMsg("Sorry, there was an error!");
			}

		}

		return json.toString();
	}

	@JavascriptInterface
	public void deleteUser(int id) {
		User user = mUDB.getUser(id);

		mUDB.deleteUser(user);

		showMsg("User deleted!");

		mParent.myWebView.loadUrl("javascript:displayUsers()");
	}

	@JavascriptInterface
	public boolean validToken(String token) {
		return mUDB.userLoggedIn(token);
	}

	@JavascriptInterface
	public String ajaxRead(String aUrl) {
		AssetManager am = mContext.getAssets();
		try {
			InputStream is = am.open(aUrl);
			String res = null;

			if (is != null) {
				// prepare the file for reading
				InputStreamReader input = new InputStreamReader(is);
				BufferedReader buffreader = new BufferedReader(input);

				res = "";
				String line;
				while ((line = buffreader.readLine()) != null) {
					res += line + "\n";
				}
				is.close();

				return res.toString();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	@JavascriptInterface
	public void debugPrint(String in) {
		Log.i("JAVASCRIPT DEBUG", in);
	}

	@JavascriptInterface
	public void savePerson(String fname, String mname, String lname,
			String gender, String age, String occupation, String yob,
			String mob, String dob, String success, String failed) {

		People person = new People();

		int identifier = mDB.getBlankNPID();

		if (identifier == 0) {

			showMsg("Sorry, no national identifiers are available!");
		} else {

			String birthdate = "";
			int birthdate_estimated = 0;

			if (yob.toLowerCase().equals("unknown")) {
				int yr = Calendar.getInstance().get(Calendar.YEAR)
						- Integer.parseInt(age);

				birthdate = yr + "-07-15";
				birthdate_estimated = 1;

			} else {

				birthdate = yob;

				if (mob.trim().toLowerCase() != "unknown") {
					birthdate = birthdate + "-"
							+ String.format("%02d", Integer.parseInt(mob));

					if (dob.trim().toLowerCase() != "unknown") {
						birthdate = birthdate + "-"
								+ String.format("%02d", Integer.parseInt(dob));

						birthdate_estimated = 0;
					} else {
						birthdate = birthdate + "-15";

						birthdate_estimated = 1;
					}
				} else {
					birthdate = birthdate + "-07-15";

					birthdate_estimated = 1;
				}
			}

			String vh = getPref("vh");
			String gvh = getPref("gvh");
			String ta = getPref("ta");

			person.setGivenName(fname);
			person.setMiddleName(mname);
			person.setFamilyName(lname);
			person.setGender(gender);
			person.setNationalId(identifier + "");

			person.setBirthdate(birthdate);

			person.setBirthdateEstimated(birthdate_estimated);

			person.setVillage(vh);
			person.setGvh(gvh);
			person.setTa(ta);

			String date = Calendar.getInstance().get(Calendar.YEAR)
					+ "-"
					+ String.format("%02d",
							(Calendar.getInstance().get(Calendar.MONTH) + 1))
					+ "-"
					+ String.format("%02d",
							Calendar.getInstance().get(Calendar.DATE))
					+ " "
					+ String.format("%02d",
							Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
					+ ":"
					+ String.format("%02d",
							Calendar.getInstance().get(Calendar.MINUTE))
					+ ":"
					+ String.format("%02d",
							Calendar.getInstance().get(Calendar.SECOND))
					+ ".000000";
			;

			person.setCreatedAt(date);

			person.setUpdatedAt(date);

			String result[] = mDB.addPeople(person);

			setPref("person id", result[0]);

			setPref("first name", result[1]);

			setPref("last name", result[2]);

			setPref("npid", result[3]);

			setPref("gender", result[4]);

			setPref("dob", result[5]);

			setPref("dobest", result[6]);

			showMsg(success);
		}
	}

	@JavascriptInterface
	public String listFirstNames(String filter) {
		JSONObject json = new JSONObject();

		List<String> names = mDB.getFirstNames(filter);

		for (int i = 0; i < names.size(); i++) {
			try {
				json.put(names.get(i), names.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
				showMsg("Sorry, there was an error!");
			}

		}

		return json.toString();
	}

	@JavascriptInterface
	public String listLastNames(String filter) {
		JSONObject json = new JSONObject();

		List<String> names = mDB.getLastNames(filter);

		for (int i = 0; i < names.size(); i++) {
			try {
				json.put(names.get(i), names.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
				showMsg("Sorry, there was an error!");
			}

		}

		return json.toString();
	}

	@JavascriptInterface
	public void updateOutcome(int person_id, String outcome,
			String date_of_event, String explanation) {

		int outcomtype = mDB.getOutcomeByType(outcome);

		Outcomes ocome = new Outcomes();

		String date_created = Calendar.getInstance().get(Calendar.YEAR)
				+ "-"
				+ String.format("%02d",
						(Calendar.getInstance().get(Calendar.MONTH) + 1))
				+ "-"
				+ String.format("%02d",
						Calendar.getInstance().get(Calendar.DATE))
				+ " "
				+ String.format("%02d",
						Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
				+ ":"
				+ String.format("%02d",
						Calendar.getInstance().get(Calendar.MINUTE))
				+ ":"
				+ String.format("%02d",
						Calendar.getInstance().get(Calendar.SECOND))
				+ ".000000";

		ocome.setCreatedAt(date_created);
		ocome.setOutcomeDate(date_of_event);
		ocome.setOutcomeType(outcomtype);
		ocome.setPersonId(person_id);

		mDB.addOutcomes(ocome);
		
		People person = mDB.getPeople(person_id);
		
		person.setOutcome(outcome);
		
		person.setOutcomeDate(date_created);
		
		mDB.updatePeople(person);
	}

	@JavascriptInterface
	public void savePersonRelationship(String person_a_id, String person_b_id,
			String relation) {

		debugPrint("person_a: " + person_a_id + "; person_b_id: " + person_b_id
				+ "; relation: " + relation);

		int rtype = mDB.getRelationByType(relation);

		debugPrint("Returned with relation type id: " + rtype);

		Relationships relationship = new Relationships();

		String date_created = Calendar.getInstance().get(Calendar.YEAR) + "-"
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-"
				+ Calendar.getInstance().get(Calendar.DATE);

		relationship.setPersonNationalId(person_a_id);
		relationship.setRelationNationalId(person_b_id);
		relationship.setPersonIsToRelation(rtype);
		relationship.setCreatedAt(date_created);

		mDB.addRelationships(relationship);

		debugPrint("Done creating relationship");
	}

	@JavascriptInterface
	public String search(String word) {
		String term = word;

		String locale = getPref("locale");

		if (locale.trim().length() == 0) {
			locale = "en";
		}

		term = mDB.search(word, locale);

		return term;
	}

	@JavascriptInterface
	public String listPeopleNames(String fname, String lname, String gender) {
		JSONObject json = new JSONObject();

		List<People> people = mDB.getPeopleNames(fname, lname, gender);

		for (int i = 0; i < people.size(); i++) {
			JSONObject pjson = new JSONObject();

			People person = people.get(i);

			try {
				String detail = person.getGivenName() + " "
						+ person.getFamilyName() + " ("
						+ person.getNationalId() + " - "
						+ search(person.getGender()) + " - DOB: "
						+ person.getBirthdate() + ")";

				pjson.put("details", detail);

				pjson.put("npid", person.getNationalId());

				json.put(person.getId() + "", pjson);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showMsg("Sorry, there was an error!");
			}

		}

		return json.toString();
	}

	@JavascriptInterface
	public boolean searchPerson(int id) {

		String result[] = mDB.getPersonById(id);

		setPref("person id", result[0]);

		setPref("first name", result[1]);

		setPref("last name", result[2]);

		setPref("npid", result[3]);

		setPref("gender", result[4]);

		setPref("dob", result[5]);

		setPref("dobest", result[6]);

		return true;
	}

	@JavascriptInterface
	public int getAvailableIds() {
		int result = 0;
		String mode = getPref("dde_mode");

		result = mDB.getAvailableIds(mode);

		return result;
	}

	@JavascriptInterface
	public int getTakenIds() {
		int result = 0;
		String mode = getPref("dde_mode");

		result = mDB.getTakenIds(mode);

		return result;
	}

	@JavascriptInterface
	public void setSettings(String username, String password, String server,
			String port, String code, String count) {
		String mode = getPref("dde_mode");

		DdeSettings settings = mUDB.getDdeSettingsByMode(mode);

		settings.setDdeUsername(username);
		settings.setDdePassword(password);
		settings.setDdeIp(server);
		settings.setDdePort(Integer.parseInt(port));
		settings.setDdeSiteCode(code);
		settings.setDdeBatchSize(count);

		int result = mUDB.updateDdeSettings(settings);

	}

	@JavascriptInterface
	public String getSettings() {
		JSONObject json = new JSONObject();

		String mode = getPref("dde_mode");

		DdeSettings settings = mUDB.getDdeSettingsByMode(mode);

		try {

			json.put("mode", mode);
			json.put("username", settings.getDdeUsername());
			json.put("password", settings.getDdePassword());
			json.put("ip", settings.getDdeIp());
			json.put("port", settings.getDdePort());
			json.put("code", settings.getDdeSiteCode());
			json.put("count", settings.getDdeBatchSize());

			setPref("dde_mode", mode);
			setPref("target_username", settings.getDdeUsername());
			setPref("target_password", settings.getDdePassword());
			setPref("target_server", settings.getDdeIp());
			setPref("target_port", settings.getDdePort() + "");
			setPref("site_code", settings.getDdeSiteCode());
			setPref("batch_count", settings.getDdeBatchSize());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMsg("Sorry, there was an error!");
		}

		return json.toString();
	}

	@JavascriptInterface
	public void getNationalIds() {

		getSettings();

		String mode = getPref("dde_mode");

		String target_username = getPref("target_username");
		String target_password = getPref("target_password");
		String target_server = getPref("target_server");
		String target_port = getPref("target_port");
		String site_code = getPref("site_code");
		String batch_count = getPref("batch_count");
		String gvh = getPref("gvh");
		String vh = getPref("vh");

		String ext = "";

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				mParent, "Posting data...");

		if (mode.equalsIgnoreCase("ta")) {
			ext = "npid_requests/get_npids";

			JSONObject json = new JSONObject();

			try {

				json.put("site_code", site_code);

				json.put("count", batch_count);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showMsg("Sorry, there was an error!");
			}

			wst.addNameValuePair("site_code", site_code);
			wst.addNameValuePair("count", batch_count);
			
			// wst.addNameValuePair("npid_request", json.toString());

			wst.targetTaskType = wst.TASK_GET_TA_NPIDS;

		} else if (mode.equalsIgnoreCase("gvh")) {
			ext = "national_identifiers/request_gvh_ids";

			wst.addNameValuePair("site_code", site_code);
			wst.addNameValuePair("count", batch_count);
			wst.addNameValuePair("gvh", gvh);

			wst.targetTaskType = wst.TASK_GET_GVH_NPIDS;

		} else if (mode.equalsIgnoreCase("vh")) {
			ext = "national_identifiers/request_village_ids";

			wst.addNameValuePair("site_code", site_code);
			wst.addNameValuePair("count", batch_count);
			wst.addNameValuePair("gvh", gvh);
			wst.addNameValuePair("vh", vh);

			wst.targetTaskType = wst.TASK_GET_VH_NPIDS;

		}

		String SERVICE_URL = "http://" + target_server + ":" + target_port
				+ "/" + ext;

		wst.mUsername = target_username;
		wst.mPassword = target_password;
		wst.mServer = target_server;
		wst.mPort = Integer.parseInt(target_port);

		wst.mGVH = gvh;
		wst.mVH = vh;
		
		wst.mDdeMode = mode;
		wst.mCount = batch_count;

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

}
