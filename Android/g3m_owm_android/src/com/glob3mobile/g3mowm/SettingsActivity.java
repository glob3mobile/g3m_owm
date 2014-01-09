

package com.glob3mobile.g3mowm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.glob3mobile.g3m_owm.R;


public class SettingsActivity
         extends
            PreferenceActivity
         implements
            OnSharedPreferenceChangeListener {


   @Override
   protected void onPostCreate(final Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);

      setupSimplePreferencesScreen();
   }


   @SuppressWarnings("deprecation")
   public void setupSimplePreferencesScreen() {

      // In the simplified UI, fragments are not used at all and we instead
      // use the older PreferenceActivity APIs.

      // Add 'general' preferences.
      addPreferencesFromResource(R.xml.settings);

      PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);


   }


   @Override
   public void onSharedPreferenceChanged(final SharedPreferences sp,
                                         final String st) {
      sp.edit().putString(st, sp.getString(st, "NO VALUE"));
      final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
      builder.setTitle("Restart Message").setMessage("You need restart the app to apply preferences changes").setCancelable(false).setNegativeButton(
               "Close", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(final DialogInterface dialog,
                                      final int id) {
                     dialog.cancel();
                     System.exit(0);
                  }
               });
      final AlertDialog alert = builder.create();
      alert.show();
   }


}
