/**
 * 
 */
package com.glob3mobile.g3mowm;

import com.glob3mobile.g3m_owm.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author mdelacalle
 *
 */
public class LocalWeatherFragment extends android.support.v4.app.Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
 
        return inflater.inflate(R.layout.local_weather_fragment, container, false);
    }

}
