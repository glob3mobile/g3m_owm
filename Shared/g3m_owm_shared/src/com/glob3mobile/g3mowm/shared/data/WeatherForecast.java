

package com.glob3mobile.g3mowm.shared.data;

import java.util.ArrayList;


public class WeatherForecast {

   ArrayList<Weather> _forecast     = new ArrayList<Weather>();
   boolean            _isDownloaded = false;


   public ArrayList<Weather> getForecast() {
      return _forecast;
   }


   public synchronized void setForecast(final ArrayList<Weather> forecast) {
      _forecast = forecast;
   }


   public synchronized boolean isDownloaded() {
      return _isDownloaded;
   }


   public void setDownloaded(final boolean isDownloaded) {
      _isDownloaded = isDownloaded;
   }


}
