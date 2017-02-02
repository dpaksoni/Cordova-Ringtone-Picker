package org.apache.cordova.plugin;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException; 
import org.json.JSONObject;

public class RingtonePicker extends CordovaPlugin {
	
	private String LOG = "RINGTONEPICKER";
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
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
}