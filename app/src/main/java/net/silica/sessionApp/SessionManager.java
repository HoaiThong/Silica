package net.silica.sessionApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private  String PREF_NAME = "settingMode";

    private  String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }



    public void setReading(String key, String value) {

        editor.putString(key, value);

        // commit changes
        editor.commit();

        Log.d(TAG, "Session modified!");
    }

    public String getReaded(String key) {
        return pref.getString(key, "");
    }

}