

package com.glob3mobile.g3mowm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.glob3mobile.g3m_owm.R;


public class Dialogs {


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

}
