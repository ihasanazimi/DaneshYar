package ir.ha.daneshyar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private final SharedPreferences boughtPref;
    private final SharedPreferences ratePref;
    public static final String RATE_KEY = " RATE";
    public static final String RATE_DIALOG_KEY = " RATE_DIALOG";
    public static final String BOUGHT_KEY = " Bought";

    public PrefManager(Context context){
        boughtPref = context.getSharedPreferences(BOUGHT_KEY,Context.MODE_PRIVATE);
        ratePref = context.getSharedPreferences(RATE_DIALOG_KEY,Context.MODE_PRIVATE);
    }

    public boolean getBoughtState(){
        return boughtPref.getBoolean(BOUGHT_KEY , false);
    }

    public void setBoughtState(boolean value){
        boughtPref.edit().putBoolean(BOUGHT_KEY , value).apply();
    }

    public boolean rating(){
        return ratePref.getBoolean(RATE_KEY , false);
    }

    public void setRatingState(boolean value){
        ratePref.edit().putBoolean(RATE_KEY , value).apply();
    }

}
