/**
 * 
 */


package com.glob3mobile.g3mowm;

import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glob3mobile.g3mowm.shared.G3MOWMListener;


/**
 * @author mdelacalle
 * 
 */
public class G3MFragment
         extends
            android.support.v4.app.Fragment
         implements
            G3MOWMListener {

   public G3MFragment() {
      super();
      // TODO Auto-generated constructor stub
   }


   @Override
   public View onCreateView(final LayoutInflater inflater,
                            final ViewGroup container,
                            final Bundle savedInstanceState) {
      final G3MBuilder_Android builder = new G3MBuilder_Android(container.getContext());
      final G3MWidget_Android g3mWidget = builder.createWidget();
      return g3mWidget;
   }


   @Override
   public void onError(final String message) {
      // TODO Auto-generated method stub

   }

}
