package org.baobabhealthtrust.cvr;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.baobabhealthtrust.cvr.models.DdeSettings;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class CVRSyncServices extends IntentService {

	public static int count = 0;

	SharedPreferences mPrefs;
	DatabaseHandler mDB;
	UserDatabaseHandler mUDB;
	boolean mRunning = false;

	Context mContext;

	public CVRSyncServices() {
		super("CVR Syncing Services");
		// TODO Auto-generated constructor stub
		mContext = this;
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub

		if (mRunning)
			return;

		Log.i("SYNCING", "Attempting to sync");

		mPrefs = getSharedPreferences("PrefFile", 0);

		mRunning = true;

		mDB = new DatabaseHandler(mContext);

		mUDB = new UserDatabaseHandler(mContext);

		String mode = getPref("dde_mode");

		getSettings();

		String host = getPref("target_server");
		int timeOut = 3000;

		if (host.equalsIgnoreCase("unknown"))
			return;

		boolean conn = checkConnection(host, timeOut);

		if (conn) {
			int threshold = mUDB.getThreshold(mode);
			int availableIDs = mDB.getAvailableIds(mode);

			Log.i("SERVICE THREAD", "have been called to duty " + mode
					+ "; conn: " + conn + "; threshold: " + threshold
					+ "; availableIDs: " + availableIDs);

			if (availableIDs <= threshold) {
				getNationalIds();
			}
		}

		mRunning = false;
	}

	public String getPref(String pref) {
		String item = mPrefs.getString(pref, "");

		return item;
	}

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
		}

		return json.toString();
	}

	public void setPref(String pref, String value) {
		Editor editor = mPrefs.edit();

		editor.putString(pref, value);

		editor.commit();
	}

	public void getNationalIds() {

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
				mContext, "");

		if (mode.equalsIgnoreCase("ta")) {
			ext = "npid_requests/get_npids";

			JSONObject json = new JSONObject();

			try {

				json.put("site_code", site_code);

				json.put("count", batch_count);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	public boolean checkConnection(String host, int timeOut) {
		boolean result = false;
		try {
			result = InetAddress.getByName(host).isReachable(timeOut);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// result = mDB.getAgegroupCount(date_selected, age_group);
		return result;
	}

}
