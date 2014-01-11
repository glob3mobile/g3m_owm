//


package com.glob3.mobile.owm.shared.data;

import java.util.ArrayList;

import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.IBufferDownloadListener;
import org.glob3.mobile.generated.IByteBuffer;
import org.glob3.mobile.generated.IDownloader;
import org.glob3.mobile.generated.IJSONParser;
import org.glob3.mobile.generated.JSONArray;
import org.glob3.mobile.generated.JSONBaseObject;
import org.glob3.mobile.generated.JSONObject;
import org.glob3.mobile.generated.TimeInterval;
import org.glob3.mobile.generated.URL;
import org.glob3.mobile.specific.JSONParser_Android;

import com.glob3.mobile.owm.shared.G3MOWMListener;


public class WeatherForecastParser {

   /*
      public void getWeatherForecastListNow(String cityName,
                                            final G3MContext context,
                                            final G3MOWMListener listener) {
         final WeatherForecast weatherForecast = new WeatherForecast();
         weatherForecast.setDownloaded(false);
         final ArrayList<Weather> weatherForecastList = new ArrayList<Weather>();

         final IDownloader downloaderForecast = context.getDownloader();

         final IBufferDownloadListener listenerDownloaderWeather = new IBufferDownloadListener() {

            @Override
            public void onDownload(final URL url,
                                   final IByteBuffer buffer,
                                   final boolean expired) {


               final String response = buffer.getAsString();
               final IJSONParser parser = new JSONParser_Android();
               final JSONBaseObject jsonObject = parser.parse(response);
               final JSONObject object = jsonObject.asObject();
               final JSONArray list = object.getAsArray("list");

               for (int i = 0; i < 9; i++) {
                  final Weather w = new Weather();
                  final JSONObject weatherObject = list.getAsObject(i);

                  final JSONObject main = weatherObject.getAsObject("main");
                  w.setTempK(main.getAsNumber("temp", 0.0));

                  //Optional Param:
                  if (main.get("temp_min") != null) {
                     w.setTempMinK(main.getAsNumber("temp_min", 0.0));
                  }
                  if (main.get("temp_max") != null) {
                     w.setTempMaxK(main.getAsNumber("temp_max", 0.0));
                  }
                  if (main.get("temp_kf") != null) {
                     w.setTempKfK(main.getAsNumber("temp_kf", 0.0));
                  }
                  w.setHumidity(main.getAsNumber("humidity", 0.0));
                  w.setPressure(main.getAsNumber("pressure", 0.0));
                  final JSONObject wind = weatherObject.getAsObject("wind");
                  w.setWindSpeed(wind.getAsNumber("speed", 0.0));
                  w.setWindDeg((int) wind.getAsNumber("deg", 0.0));
                  w.setWindSpeed(wind.getAsNumber("speed", 0.0));
                  if (weatherObject.get("weather") != null) {
                     final JSONArray weatherArray = weatherObject.getAsArray("weather");
                     final JSONObject weather = weatherArray.getAsObject(0);
                     w.setWeatherId((int) weather.getAsNumber("id", 0.0));
                     w.setWeatherMain(weather.getAsString("main", ""));
                     w.setWeatherDescription(weather.getAsString("description", ""));
                     String icon = "";
                     if (weather.getAsString("icon", "DOUBLE").equals("DOUBLE")) {
                        icon = "" + (int) weather.getAsNumber("icon").value() + "d.png";
                        if (icon.length() < 7) {
                           icon = "0" + icon;
                        }
                     }
                     else {
                        icon = weather.getAsString("icon", "DOUBLE") + ".png";
                     }
                     w.setIcon(icon);
                  }
                  final JSONObject clouds = weatherObject.getAsObject("clouds");
                  w.setCloudsAll((int) clouds.getAsNumber("all", 0.0));
                  w.setCloudsLow((int) clouds.getAsNumber("low", 0));
                  w.setCloudsMiddle((int) clouds.getAsNumber("middle", 0.0));
                  w.setCloudsHigh((int) clouds.getAsNumber("high", 0.0));
                  if (weatherObject.getAsObject("rain") != null) {
                     final JSONObject rain = weatherObject.getAsObject("rain");
                     w.setRain3h(rain.getAsNumber("3h", 0.0));
                  }
                  if (weatherObject.getAsObject("snow") != null) {
                     final JSONObject snow = weatherObject.getAsObject("snow");
                     w.setSnow3h(snow.getAsNumber("3h", 0.0));
                  }

                  w.setDateText(weatherObject.getAsString("dt_txt", "No data"));

                  weatherForecastList.add(w);
               }

               weatherForecast.setForecast(weatherForecastList);
               weatherForecast.setDownloaded(true);
               listener.onWeatherForecast(weatherForecast);
            }


            @Override
            public void onError(final URL url) {
               weatherForecast.setDownloaded(true);
            }


            @Override
            public void onCancel(final URL url) {
               // TODO Auto-generated method stub

            }


            @Override
            public void onCanceledDownload(final URL url,
                                           final IByteBuffer buffer,
                                           final boolean expired) {
               // TODO Auto-generated method stub

            }

         };


         cityName = cityName.replaceAll(" ", "%20");
         final String location = Utils.removeSpecialCharacters(cityName);
         final String urlPath = "http://openweathermap.org/data/2.5/forecast/city?q=";
         final URL path = new URL(urlPath + location, true);
         downloaderForecast.requestBuffer(path, 0, TimeInterval.zero(), false, listenerDownloaderWeather, false);
      }
   */

   public void getWeatherForecastList(String cityName,
                                      final G3MContext context,
                                      final G3MOWMListener listener) {
      final WeatherForecast weatherForecast = new WeatherForecast();
      weatherForecast.setDownloaded(false);
      final ArrayList<Weather> weatherForecastList = new ArrayList<Weather>();

      final IDownloader downloaderForecast = context.getDownloader();

      final IBufferDownloadListener listenerDownloaderWeather = new IBufferDownloadListener() {

         @Override
         public void onDownload(final URL url,
                                final IByteBuffer buffer,
                                final boolean expired) {


            final String response = buffer.getAsString();
            final IJSONParser parser = new JSONParser_Android();
            final JSONBaseObject jsonObject = parser.parse(response);
            final JSONObject object = jsonObject.asObject();
            final JSONArray list = object.getAsArray("list");

            for (int i = 0; i < list.size(); i++) {
               final Weather w = new Weather();
               final JSONObject weatherObject = list.getAsObject(i);

               final JSONObject main = weatherObject.getAsObject("main");
               w.setTempK(main.getAsNumber("temp", 0.0));

               //Optional Param:
               if (main.get("temp_min") != null) {
                  w.setTempMinK(main.getAsNumber("temp_min", 0.0));
               }
               if (main.get("temp_max") != null) {
                  w.setTempMaxK(main.getAsNumber("temp_max", 0.0));
               }
               if (main.get("temp_kf") != null) {
                  w.setTempKfK(main.getAsNumber("temp_kf", 0.0));
               }
               w.setHumidity(main.getAsNumber("humidity", 0.0));
               w.setPressure(main.getAsNumber("pressure", 0.0));
               final JSONObject wind = weatherObject.getAsObject("wind");
               w.setWindSpeed(wind.getAsNumber("speed", 0.0));
               w.setWindDeg((int) wind.getAsNumber("deg", 0.0));
               w.setWindSpeed(wind.getAsNumber("speed", 0.0));
               if (weatherObject.get("weather") != null) {
                  final JSONArray weatherArray = weatherObject.getAsArray("weather");
                  final JSONObject weather = weatherArray.getAsObject(0);
                  w.setWeatherId((int) weather.getAsNumber("id", 0.0));
                  w.setWeatherMain(weather.getAsString("main", ""));
                  w.setWeatherDescription(weather.getAsString("description", ""));
                  String icon = "";
                  if (weather.getAsString("icon", "DOUBLE").equals("DOUBLE")) {
                     icon = "" + (int) weather.getAsNumber("icon").value() + "d.png";
                     if (icon.length() < 7) {
                        icon = "0" + icon;
                     }
                  }
                  else {
                     icon = weather.getAsString("icon", "DOUBLE") + ".png";
                  }
                  w.setIcon(icon);
               }
               final JSONObject clouds = weatherObject.getAsObject("clouds");
               w.setCloudsAll((int) clouds.getAsNumber("all", 0.0));
               w.setCloudsLow((int) clouds.getAsNumber("low", 0));
               w.setCloudsMiddle((int) clouds.getAsNumber("middle", 0.0));
               w.setCloudsHigh((int) clouds.getAsNumber("high", 0.0));
               if (weatherObject.getAsObject("rain") != null) {
                  final JSONObject rain = weatherObject.getAsObject("rain");
                  w.setRain3h(rain.getAsNumber("3h", 0.0));
               }
               if (weatherObject.getAsObject("snow") != null) {
                  final JSONObject snow = weatherObject.getAsObject("snow");
                  w.setSnow3h(snow.getAsNumber("3h", 0.0));
               }

               w.setDateText(weatherObject.getAsString("dt_txt", "No data"));

               weatherForecastList.add(w);
            }

            weatherForecast.setForecast(weatherForecastList);
            weatherForecast.setDownloaded(true);
            listener.onWeatherForecast(weatherForecast);
         }


         @Override
         public void onError(final URL url) {
            weatherForecast.setDownloaded(true);
         }


         @Override
         public void onCancel(final URL url) {
            // TODO Auto-generated method stub

         }


         @Override
         public void onCanceledDownload(final URL url,
                                        final IByteBuffer data,
                                        final boolean expired) {

            // TODO Auto-generated method stub

         }

      };

      cityName = cityName.replaceAll(" ", "%20");
      final String location = Utils.removeSpecialCharacters(cityName);
      final String urlPath = "http://openweathermap.org/data/2.5/forecast/city?q=";
      final URL path = new URL(new StringBuilder(urlPath).append(location).toString(), false);
      downloaderForecast.requestBuffer(path, 0, TimeInterval.zero(), false, listenerDownloaderWeather, false);


   }
}
