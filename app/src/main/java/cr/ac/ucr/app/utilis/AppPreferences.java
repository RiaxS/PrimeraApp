package cr.ac.ucr.app.utilis;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private final String PREFERENCES = "myapp_preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static AppPreferences preferences;

    public static class Keys{

        public static final String IS_LOGGED_IN = "is_logged_in";
        public static final String ITEMS = "items";
    }

    private AppPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance(Context context){
        if(preferences == null){
            preferences = new AppPreferences(context);
        }
        return preferences;
    }

    public void put(String key, String value){
        doEdit();
        editor.putString(key, value);
        commit();
    }

    //metodo put

    public void put(String key, int value){
        doEdit();
        editor.putInt(key, value);
        commit();
    }
    public void put(String key, long value){
        doEdit();
        editor.putLong(key, value);
        commit();
    }

    public void put(String key, double value){
        put(key, String.valueOf(value));
    }

    public void put(String key, boolean value){
        doEdit();
        editor.putBoolean(key, value);
        commit();
    }

    public void put(String key, float value){
        doEdit();
        editor.putFloat(key, value);
        commit();
    }

    //Metodos get

    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key, int defValue){
        return sharedPreferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue){
        return sharedPreferences.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue){
        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue){
        return sharedPreferences.getFloat(key, defValue);
    }

    public double getDouble(String key, double defValue){
        try{
            return Double.valueOf(sharedPreferences.getString(key, String.valueOf(defValue)));

        }catch (NumberFormatException e) {
            return defValue;
        }
    }

    public void clear(){
        doEdit();
        editor.clear();
        commit();
    }

    public void remove(String ... keys){//array
        doEdit();
        for (String key: keys){
            editor.remove(key);
        }
        commit();
    }


    private void doEdit(){
        if (editor == null){
            editor = sharedPreferences.edit();
        }
    }



    private void commit() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }

}
