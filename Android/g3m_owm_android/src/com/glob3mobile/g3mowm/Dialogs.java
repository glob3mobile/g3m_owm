

package com.glob3mobile.g3mowm;

import com.glob3mobile.g3m_owm.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


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


}
