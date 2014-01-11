

package com.glob3.mobile.owm.shared.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.glob3.mobile.generated.Angle;
import org.glob3.mobile.generated.Geodetic2D;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.View;


public class Utils {

   public static String removeSpecialCharacters(final String input) {
      // Cadena de caracteres original a sustituir.
      final String original = "áàäéèëíìïóòöúùüñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑç Ç";
      // Cadena de caracteres ASCII que reemplazarán los originales.
      final String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNc+C";
      String output = input;
      for (int i = 0; i < original.length(); i++) {
         // Reemplazamos los caracteres especiales.
         output = output.replace(original.charAt(i), ascii.charAt(i));
      }//for i
      return output;
   }//remove1


   public static ArrayList<Weather> getListFromDate(final ArrayList<ArrayList<Weather>> forecast,
                                                    final String date) {
      for (int i = 0; i < forecast.size(); i++) {
         if (date.equals(forecast.get(i).get(0).getDateText())) {
            return forecast.get(i);
         }
      }
      return null;
   }


   public static Calendar getCalendar(final String date) {
      final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      final Calendar calendar = Calendar.getInstance();
      try {
         calendar.setTime(sdf.parse(date.substring(0, 10)));
      }
      catch (final ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return calendar;
   }


   public static String[] toPlacesNames(final ArrayList<Place> places) {
      final String[] placenames = new String[places.size()];
      int i = 0;
      for (final Place place : places) {
         placenames[i] = place.getFullName();
         i++;
      }
      return placenames;
   }


   public static ArrayList<ArrayList<Weather>> divideForecastInDays(final ArrayList<Weather> forecast) {

      final ArrayList<ArrayList<Weather>> forecastDaily = new ArrayList<ArrayList<Weather>>();

      String date = forecast.get(0).getDate();

      ArrayList<Weather> dayWeatherForecast = new ArrayList<Weather>();

      for (int i = 0; i < forecast.size(); i++) {

         final Weather weather = forecast.get(i);

         if (!date.equals(weather.getDate())) {
            forecastDaily.add(dayWeatherForecast);
            dayWeatherForecast = new ArrayList<Weather>();
            date = weather.getDate();
            dayWeatherForecast.add(weather);
         }
         else {
            dayWeatherForecast.add(weather);
         }
         if (i == (forecast.size() - 1)) {
            forecastDaily.add(dayWeatherForecast);
         }
      }
      return forecastDaily;
   }


   public static String getWeekdayLocale(final int weekday) {

      //      if (Locale.getDefault().getDisplayLanguage().equals("en")) {
      switch (weekday) {
         case 2:
            return "Monday";
         case 3:
            return "Tuesday";
         case 4:
            return "Wednesday";
         case 5:
            return "Thursday";
         case 6:
            return "Friday";
         case 7:
            return "Saturday";
         case 1:
            return "Sunday";
         default:
            break;
      }
      //    }
      return null;
   }


   public static Place readInitFile(final File file) {
      final Place place = new Place();
      String lat = "0.0";
      String lon = "0.0";
      try {

         final BufferedReader br = new BufferedReader(new FileReader(file));
         String line;

         while ((line = br.readLine()) != null) {
            final String param = line.substring(0, line.indexOf("="));
            final String value = line.substring(line.indexOf("=") + 1, line.length());
            if (param.equals("gpsenabled")) {
               place.setGpsEnable(Boolean.parseBoolean(value));
            }
            if (param.equals("city")) {
               place.setName(value);
            }
            if (param.equals("units")) {
               place.setUnitsSystem(value);
            }
            if (param.equals("lon")) {
               lon = value;
            }
            if (param.equals("lat")) {
               lat = value;
            }
         }
         br.close();

      }
      catch (final IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      Log.d(Utils.class.toString(), place.getName());

      final Geodetic2D position = new Geodetic2D(Angle.fromDegrees(Double.parseDouble(lat)),
               Angle.fromDegrees(Double.parseDouble(lon)));
      place.setPosition(position);
      return place;
   }


   public static void setBorderToLayout(final View view,
                                        final int color,
                                        final int width) {
      final ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class

      // get paint
      final Paint paint = rectShapeDrawable.getPaint();

      // set border color, stroke and stroke width
      paint.setColor(color);
      paint.setStyle(Style.STROKE);
      paint.setStrokeWidth(width); // you can change the value of 5
      view.setBackground(rectShapeDrawable);
   }
}
