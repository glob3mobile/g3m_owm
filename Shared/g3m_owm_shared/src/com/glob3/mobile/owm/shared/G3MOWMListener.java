

package com.glob3.mobile.owm.shared;

import com.glob3.mobile.owm.shared.data.WeatherForecast;


public interface G3MOWMListener {

   void onError(String message);


   void currentLocation();


   void onWeatherForecast(WeatherForecast forecast);


   void onLocation(String location);
}
