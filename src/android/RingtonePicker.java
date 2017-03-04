package org.apache.cordova.plugin;

import android.app.Activity;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException; 
import org.json.JSONObject;
import android.content.Intent;

public class RingtonePicker extends CordovaPlugin {
	
	private String LOG = "RINGTONEPICKER";
	private int PICK_RINGTONE_REQ_CODE = 100;

	private int ERR_USER_CANCELED	= 1;
	private int ERR_UNKNOWN			= 2;

	private CallbackContext mCallBackContext;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		mCallBackContext = callbackContext;

		if(action.equals("pickRingtone"))
		{
			pickRingtone();
			return true;
		}
		if(action.equals("getSoundsList"))
		{
			String soundsList = getSoundsList();
			callbackContext.success(soundsList);
			return true;
		}
		else if(action.equals("playSound"))
		{
			String uri = args.getString(0);
			playSound(uri);
			callbackContext.success();
			return true;
		}

		return false;
	}

	public String getSoundsList()
	{
		Log.d(LOG,"Enter getSoundsList");

		JSONArray soundsList = new JSONArray();

		try{

			RingtoneManager manager = new RingtoneManager(cordova.getActivity());
    		manager.setType(RingtoneManager.TYPE_NOTIFICATION);
    		Cursor cursor = manager.getCursor();

    		while (cursor.moveToNext()) {

				JSONObject tmp = new JSONObject();

        		String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
        		String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
				int id = cursor.getInt(RingtoneManager.ID_COLUMN_INDEX);

				tmp.put("title",title);

				String urii = uri + "/" + String.valueOf(id);

				tmp.put("uri",urii);

				soundsList.put(tmp);
        	}

		}
		catch (JSONException e)
		{
			Log.d(LOG,"JSONException "+e.getMessage());
		}
		catch(Exception e)
		{
			Log.d(LOG,"Exception "+e.getMessage());
		}

		Log.d(LOG,"Exit getSoundsList");

		return soundsList.toString();
	}

	public void playSound(String uri)
	{
		Log.d(LOG,"Enter playSound uri = "+uri);

		RingtoneManager.getRingtone(cordova.getActivity(), Uri.parse(uri)).play();
	}

	public void pickRingtone()
	{
		Log.d(LOG,"Enter pickRingtone");
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);

		cordova.startActivityForResult(this, intent, PICK_RINGTONE_REQ_CODE);

		PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
		pluginResult.setKeepCallback(true);
		mCallBackContext.sendPluginResult(pluginResult);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_RINGTONE_REQ_CODE && mCallBackContext != null) {

			if (resultCode == Activity.RESULT_OK) {

				Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

				if (uri != null) {

					mCallBackContext.success(uri.toString());

				} else {
					mCallBackContext.error(ERR_UNKNOWN);
				}

			} else if (resultCode == Activity.RESULT_CANCELED) {

				mCallBackContext.error(ERR_USER_CANCELED);

			} else {

				mCallBackContext.error(ERR_UNKNOWN);
			}
		}
	}
}