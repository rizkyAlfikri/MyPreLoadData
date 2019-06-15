package com.dicoding.picodiploma.mypreloaddata.pref;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    // berfungsi sebagai kelas pembantu untuk menyimpan data shared preferences

    private static final String PRES_NAME = "MahasiswaPref";
    private static final String APP_FIRST_RUN = "app_first_run";
    private SharedPreferences prefs;

    public AppPreference(Context context) {
        prefs = context.getSharedPreferences(PRES_NAME, context.MODE_PRIVATE);
    }

    // method for init first run app
    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(APP_FIRST_RUN, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean(APP_FIRST_RUN, true);
    }

}
