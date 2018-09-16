package benjamin.thiebaut.fr.warframealertnotifier.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.os.IBinder;

import java.util.List;

import benjamin.thiebaut.fr.warframealertnotifier.R;

public class PreferenciesManager {

    /*
    SharedPreferencies keys
    */

    // Plateform
    public static final String PREF_GAME_PLATEFORM = "plateform";

    // Alerts types
    public static final String PREF_ALERT_NITAIN = "alertnitain";
    public static final String PREF_ALERT_CATALYSER = "alertcatalyser";
    public static final String PREF_ALERT_EXILUS = "alertexilus";
    public static final String PREF_ALERT_FORMA = "alertformat";
    public static final String PREF_ALERT_OXIUM = "alertoxium";
    public static final String PREF_ALERT_REACTOR = "alertreactor";

    // Cetus timer
    public static final String PREF_CETUS_NOTIF = "cetusnotif";
    public static final String PREF_CETUS_TIMER = "cetustimer";

    private Context context;
    private static PreferenciesManager instance;

    public static PreferenciesManager getInstance(Context context){
        if (instance == null){
            instance = new PreferenciesManager(context);
        }
        return instance;
    }

    public enum Platforms {
        PC("PC"),
        PS4("PS4"),
        XBOX("XBOX")
        ;

        private String friendlyName;

        Platforms(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        @Override
        public String toString() {
            return friendlyName;
        }
    }

    private PreferenciesManager(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPref(){
        return context.getSharedPreferences(context.getString(R.string.shared_app_key), Context.MODE_PRIVATE);
    }

    public boolean isCetusEnabled(){
        return getSharedPref().getBoolean(PREF_CETUS_NOTIF, false);
    }

    public int getCetusTimer(){
        return getSharedPref().getInt(PREF_CETUS_TIMER, 0);
    }

    public Platforms getPlateform(){
        String plat = getSharedPref().getString(PREF_GAME_PLATEFORM, Platforms.PC.friendlyName);
        return Platforms.valueOf(plat);
    }

    public boolean getAlertEnabled(String prefAlert){
        return getSharedPref().getBoolean(prefAlert, false);
    }

    public void saveCetusEnabled(boolean isEnabled){
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putBoolean(PREF_CETUS_NOTIF, isEnabled);
        editor.apply();
    }

    public void saveCetusTimer(int timer){
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putInt(PREF_CETUS_TIMER, timer);
        editor.apply();
    }

    public void savePlateform(Platforms plateforms){
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putString(PREF_GAME_PLATEFORM, plateforms.friendlyName);
        editor.apply();
    }

    public void saveAlert(String alertname, boolean value){
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putBoolean(alertname, value);
        editor.apply();
    }

}
