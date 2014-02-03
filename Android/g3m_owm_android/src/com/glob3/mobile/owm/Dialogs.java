

package com.glob3.mobile.owm;

import java.util.Map;

import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.ILogger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.glob3.mobile.owm.shared.data.DataRetriever;
import com.glob3.mobile.owm.shared.data.DialogDataListener;
import com.glob3.mobile.owm.shared.data.Place;
import com.glob3.mobile.owm.shared.data.Places;


public class Dialogs {


   private static Dialog            _dialogSearchLocation;
   private static SharedPreferences _locationSaved;


   public static void showDialogGPSError(final Context context) {
      final AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Restart Message").setMessage(context.getString(R.string.gps_disabled_message)).setCancelable(false).setNegativeButton(
               "Continue", new DialogInterface.OnClickListener() {
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


   public static void showVoidTextField(final Context context) {
      final AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Message").setMessage(context.getString(R.string.location_validation)).setCancelable(false).setNegativeButton(
               "Continue", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(final DialogInterface dialog,
                                      final int id) {
                     dialog.cancel();
                  }
               });
      final AlertDialog alert = builder.create();
      alert.show();
   }


   public static void showNetworkError(final Context context) {
      final AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Network Message").setMessage(context.getString(R.string.networkError)).setCancelable(false).setNegativeButton(
               "Continue", new DialogInterface.OnClickListener() {
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


   public static void showSearchLocationDialog(final G3MOWMMainActivity context,
                                               final G3MContext g3mContext) {


      _locationSaved = context.getSharedPreferences("locationsSaved", Context.MODE_PRIVATE);

      _dialogSearchLocation = new Dialog(context);
      _dialogSearchLocation.setContentView(R.layout.customized_dialog);
      _dialogSearchLocation.setTitle(getStringFromResources(context, R.string.search_location));


      final Button go = (Button) _dialogSearchLocation.findViewById(R.id.go);

      go.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(final View v) {
            final SearchLocationAsyncTask slat = new SearchLocationAsyncTask(context, g3mContext);
            slat.execute();
         }
      });

      _dialogSearchLocation.show();

   }

   static class SearchLocationAsyncTask
            extends
               AsyncTask<Void, Integer, Boolean>
            implements
               DialogDataListener {

      private static G3MOWMMainActivity _context;
      private static G3MContext         _g3mContext;
      private static Places             _places = new Places();
      private TextView                  _loading;


      public SearchLocationAsyncTask(final G3MOWMMainActivity context,
                                     final G3MContext g3mContext) {
         _context = context;
         _g3mContext = g3mContext;

      }


      @Override
      protected Boolean doInBackground(final Void... arg0) {


         final TextView tv = (TextView) _dialogSearchLocation.findViewById(R.id.locationToSearch);
         if (tv.getText().length() < 1) {
            _context.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  _places = new Places();
                  showVoidTextField(_context);
               }
            });

         }
         else {
            DataRetriever.getPlacesByName(tv.getText().toString(), _g3mContext, SearchLocationAsyncTask.this);

            _context.runOnUiThread(new Runnable() {


               @Override
               public void run() {
                  final Button go = (Button) _dialogSearchLocation.findViewById(R.id.go);
                  go.setVisibility(View.INVISIBLE);
                  tv.setVisibility(View.INVISIBLE);

                  _loading = (TextView) _dialogSearchLocation.findViewById(R.id.loadingtext);
                  _loading.setVisibility(View.VISIBLE);
                  _loading.bringToFront();
               }
            });
            while (!_places.isDownloaded()) {
            }
         }

         return true;
      }


      @Override
      public void onFinishedDownloadPlaces(final Places places) {
         _places = places;
         ILogger.instance().logInfo("On finished downloaded:" + _places.isDownloaded());

      }


      @Override
      protected void onPostExecute(final Boolean result) {


         ILogger.instance().logInfo(_places.isDownloaded() + ", Number of location retrieved:" + _places.getPlaces().size());


         if (_places.getPlaces().size() == 0) {

         }
         else {
            final ListView locationSpinner = (ListView) _dialogSearchLocation.findViewById(R.id.spinnerLocationDialog);
            locationSpinner.setAdapter(new DataSourceAdapter(_context, _places.getPlacesAsString()));
            _loading = (TextView) _dialogSearchLocation.findViewById(R.id.loadingtext);
            _loading.setVisibility(View.INVISIBLE);
            locationSpinner.bringToFront();
            locationSpinner.setVisibility(View.VISIBLE);

            locationSpinner.setOnItemClickListener(new OnItemClickListener() {

               @Override
               public void onItemClick(final AdapterView<?> parent,
                                       final View arg1,
                                       final int position,
                                       final long arg3) {

                  final Place place = _places.getPlaces().get(position);

                  final Editor edit = _locationSaved.edit();
                  edit.putString("default", place._fullName);
                  edit.apply();
                  edit.putString(place._fullName, place.getPosition()._latitude._degrees + "#"
                                                  + place.getPosition()._longitude._degrees);
                  edit.apply();
                  _context.onLocation(place._fullName);
                  _dialogSearchLocation.dismiss();
               }
            });


            final Button cancel = (Button) _dialogSearchLocation.findViewById(R.id.cancelLocation);
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(final View v) {
                  _dialogSearchLocation.cancel();

               }
            });


         }

      }
   }


   public static void showLocationDialog(final G3MOWMMainActivity context) {

      final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
      builderSingle.setIcon(R.drawable.ic_launcher_);
      builderSingle.setTitle(getStringFromResources(context, R.string.select_location));
      final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);

      _locationSaved = context.getSharedPreferences("locationsSaved", Context.MODE_PRIVATE);
      final Map<String, ?> locationsPreferences = _locationSaved.getAll();

      for (final Map.Entry<String, ?> entry : locationsPreferences.entrySet()) {
         if (!entry.getKey().equals("default")) {
            arrayAdapter.add(entry.getKey());
         }
      }
      arrayAdapter.add(getStringFromResources(context, R.string.currentGPSLocation));

      builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

         @Override
         public void onClick(final DialogInterface dialog,
                             final int which) {
            dialog.dismiss();
         }
      });


      builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

         @Override
         public void onClick(final DialogInterface dialog,
                             final int which) {
            final String strName = arrayAdapter.getItem(which);
            final Editor edit = _locationSaved.edit();
            if (strName.equals(getStringFromResources(context, R.string.currentGPSLocation))) {
               context.currentLocation();
               edit.putString("default", "NONE");
               edit.apply();
            }
            else {
               context.onLocation(strName);

               edit.putString("default", strName);
               edit.apply();
            }
         }
      });
      builderSingle.show();

   }


   protected static String getStringFromResources(final Context context,
                                                  final int i) {
      return context.getResources().getString(i);
   }

}
