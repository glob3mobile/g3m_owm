

package com.glob3mobile.g3mowm.shared.data;

import org.glob3.mobile.generated.AltitudeMode;
import org.glob3.mobile.generated.Angle;
import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.Geodetic2D;
import org.glob3.mobile.generated.Geodetic3D;
import org.glob3.mobile.generated.IBufferDownloadListener;
import org.glob3.mobile.generated.IByteBuffer;
import org.glob3.mobile.generated.IDownloader;
import org.glob3.mobile.generated.IJSONParser;
import org.glob3.mobile.generated.JSONArray;
import org.glob3.mobile.generated.JSONBaseObject;
import org.glob3.mobile.generated.JSONObject;
import org.glob3.mobile.generated.Mark;
import org.glob3.mobile.generated.MarksRenderer;
import org.glob3.mobile.generated.TimeInterval;
import org.glob3.mobile.generated.URL;
import org.glob3.mobile.specific.JSONParser_Android;

import com.glob3mobile.g3mowm.shared.G3MOWMListener;


public class DataRetriever {

   static G3MOWMListener _listener;


   public static void getWeatherForLocation(final G3MContext context,
                                      final double lat,
                                      final double lon,
                                      final G3MOWMListener listener) {

      _listener = listener;

      final IDownloader downloaderLocation = context.getDownloader();

      final IBufferDownloadListener listenerLocation = new IBufferDownloadListener() {

         @Override
         public void onDownload(final URL url,
                                final IByteBuffer buffer,
                                final boolean expired) {


            final String response = buffer.getAsString();
            final IJSONParser parser = new JSONParser_Android();
            final JSONBaseObject jsonObject = parser.parse(response);
            final JSONObject object = jsonObject.asObject();
            final JSONArray list = object.getAsArray("geonames");

            if (list != null) {

               final JSONObject geonames = list.getAsObject(0);
               _listener.onLocation(geonames.getAsString("name", "LOCATION_ERROR"));
            }

         }


         @Override
         public void onError(final URL url) {
            _listener.onError("LOCATION_ERROR");
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

      downloaderLocation.requestBuffer(new URL("http://api.geonames.org/citiesJSON?north=" + (lat + 0.2d) + "&south="
                                               + (lat - 0.2d) + "&east=" + (lon + 0.2d) + "&west=" + (lon - 0.2d)
                                               + "&lang=en&username=mdelacalle", false), 0, TimeInterval.fromHours(1.0), false,
               listenerLocation, false);

   }


   private static void paintWeatherIcons(final MarksRenderer _weatherMarkers,
                                         final IByteBuffer buffer) {
      final String response = buffer.getAsString();
      final IJSONParser parser = new JSONParser_Android();
      final JSONBaseObject jsonObject = parser.parse(response);
      final JSONObject object = jsonObject.asObject();
      final JSONArray list = object.getAsArray("list");

      for (int i = 0; i < list.size(); i++) {

         final JSONObject city = list.getAsObject(i);

         final JSONObject coords = city.getAsObject("coord");
         final Geodetic2D position = new Geodetic2D(Angle.fromDegrees(coords.getAsNumber("lat").value()),
                  Angle.fromDegrees(coords.getAsNumber("lon").value()));
         final JSONArray weather = city.getAsArray("weather");
         final JSONObject weatherObject = weather.getAsObject(0);


         String icon = "";
         if (weatherObject.getAsString("icon", "DOUBLE").equals("DOUBLE")) {
            icon = "" + (int) weatherObject.getAsNumber("icon").value() + "d.png";
            if (icon.length() < 7) {
               icon = "0" + icon;
            }
         }
         else {
            icon = weatherObject.getAsString("icon", "DOUBLE") + ".png";
         }


         _weatherMarkers.addMark(new Mark(city.getAsString("name", ""), new URL("file:///" + icon, false), new Geodetic3D(
                  position, 0), AltitudeMode.RELATIVE_TO_GROUND, 0d, true, 12, org.glob3.mobile.generated.Color.white(),
                  org.glob3.mobile.generated.Color.black(), 2));

      }
   }


   public static void getWorldWeather(final G3MContext context,
                                      final MarksRenderer _weatherMarkers) {
      final IDownloader downloader = context.getDownloader();

      final IBufferDownloadListener listener = new IBufferDownloadListener() {

         @Override
         public void onDownload(final URL url,
                                final IByteBuffer buffer,
                                final boolean expired) {

            paintWeatherIcons(_weatherMarkers, buffer);
         }


         @Override
         public void onError(final URL url) {
            //  Toast.makeText(getApplicationContext(), "Error retrieving  weather data", Toast.LENGTH_SHORT).show();

         }


         @Override
         public void onCancel(final URL url) {
            //DO Nothing
         }


         @Override
         public void onCanceledDownload(final URL url,
                                        final IByteBuffer data,
                                        final boolean expired) {
            //Do Nothing
         }
      };

      downloader.requestBuffer(new URL("http://openweathermap.org/data/2.1/find/city?bbox=-180,-90,180,90,2&cluster=yes", false),
               2000000000, TimeInterval.fromHours(1.0), false, listener, false);
   }


   public static void getLocalWeather(final G3MContext g3mContext,
                                      final double gpsLat,
                                      final double gpsLon,
                                      final MarksRenderer weatherMarkerLayer) {

      final IDownloader downloaderLocalWeather = g3mContext.getDownloader();

      final IBufferDownloadListener listenerLocalWeather = new IBufferDownloadListener() {

         @Override
         public void onDownload(final URL url,
                                final IByteBuffer buffer,
                                final boolean expired) {
            paintWeatherIcons(weatherMarkerLayer, buffer);

         }


         @Override
         public void onError(final URL url) {
            // TODO Auto-generated method stub

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

      final double bboxSize = 1d;
      downloaderLocalWeather.requestBuffer(new URL("http://openweathermap.org/data/2.1/find/city?bbox=" + (gpsLon - bboxSize)
                                                   + "," + (gpsLat - bboxSize) + "," + (gpsLon + bboxSize) + ","
                                                   + (gpsLat + bboxSize) + "," + "10" + "&cluster=yes", false), 2000000000,
               TimeInterval.zero(), false, listenerLocalWeather, false);

   }
}
