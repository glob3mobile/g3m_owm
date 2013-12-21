

package com.glob3mobile.g3mowm.shared;

import com.glob3mobile.g3mowm.shared.data.WeatherForecast;


public interface G3MOWMListener {

   void onError(String message);


   void onWeatherForecast(WeatherForecast forecast);


   void onLocation(String location);
}
