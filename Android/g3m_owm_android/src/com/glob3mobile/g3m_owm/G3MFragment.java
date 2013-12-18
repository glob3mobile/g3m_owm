/**
 * 
 */
package com.glob3mobile.g3m_owm;

import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import com.glob3mobile.g3mowm.shared.G3MOWMListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * @author mdelacalle
 *
 */
public class G3MFragment extends android.support.v4.app.Fragment implements G3MOWMListener {
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final G3MBuilder_Android builder = new G3MBuilder_Android(container.getContext());
		
		G3MWidget_Android g3mWidget = builder.createWidget();

	     return g3mWidget;
    }

	@Override
	public void onError(String message) {
		// TODO Auto-generated method stub
		
	}

}
