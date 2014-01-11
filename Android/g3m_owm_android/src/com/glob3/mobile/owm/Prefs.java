

package com.glob3.mobile.owm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Prefs {

   Context myContext;


   public Prefs(final Context ctx) {
      myContext = ctx;
   }


   /*
    * Store a preference via key -> value
    */
   public void setPreference(final String key,
                             final String value) {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
      final SharedPreferences.Editor editor = prefs.edit();
      editor.putString(key, value);
      editor.commit();
   }


   public void clearAllPreferences() {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);

      final SharedPreferences.Editor editor = prefs.edit();
      editor.clear();
      editor.commit();
   }


   public String getPreference(final String key) {
      String val = "";
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
      val = prefs.getString(key, "");
      return val;
   }


}
