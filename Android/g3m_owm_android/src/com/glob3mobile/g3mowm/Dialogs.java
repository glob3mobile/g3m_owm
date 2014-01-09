

package com.glob3mobile.g3mowm;

import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.ILogger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.glob3mobile.g3m_owm.R;
import com.glob3mobile.g3mowm.shared.data.DataRetriever;
import com.glob3mobile.g3mowm.shared.data.DialogDataListener;
import com.glob3mobile.g3mowm.shared.data.Places;


public class Dialogs {


   private static Dialog _dialogSearchLocation;


   public static void showDialogGPSError(final Context context) {
      final AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Restart Message").setMessage(context.getString(R.string.gps_disabled_message)).setCancelable(false).setNegativeButton(
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

      private static Activity   _context;
      private static G3MContext _g3mContext;
      private static Places     _places = new Places();


      public SearchLocationAsyncTask(final G3MOWMMainActivity context,
                                     final G3MContext g3mContext) {
         _context = context;
         _g3mContext = g3mContext;
      }


      @Override
      protected Boolean doInBackground(final Void... arg0) {


         final TextView tv = (TextView) _dialogSearchLocation.findViewById(R.id.locationToSearch);
         if (tv.getText().length() < 1) {
         }
         else {
            DataRetriever.getPlacesByName(tv.getText().toString(), _g3mContext, SearchLocationAsyncTask.this);
         }
         while (!_places.isDownloaded()) {

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

         //         while (!_places.isDownloaded()) {
         //
         //            ILogger.instance().logInfo("" + _places.isDownloaded());
         //
         //
         //            _context.runOnUiThread(new Runnable() {
         //               @Override
         //               public void run() {
         //                  final RelativeLayout loading = (RelativeLayout) _dialogSearchLocation.findViewById(R.id.loading);
         //                  loading.setVisibility(View.VISIBLE);
         //                  loading.bringToFront();
         //               }
         //            });
         //         }
         //
         ILogger.instance().logInfo(_places.isDownloaded() + ", Number of location retrieved:" + _places.getPlaces().size());
      }

   }


   public static void showLocationDialog(final G3MOWMMainActivity context) {

      final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
      builderSingle.setIcon(R.drawable.ic_launcher);
      builderSingle.setTitle(getStringFromResources(context, R.string.select_location));
      final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);

      arrayAdapter.add("moscow");
      arrayAdapter.add("leesburg");
      arrayAdapter.add("paris");
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

            context.onLocation(strName);
         }
      });
      builderSingle.show();

   }


   protected static String getStringFromResources(final Context context,
                                                  final int i) {
      return context.getResources().getString(i);
   }

}
