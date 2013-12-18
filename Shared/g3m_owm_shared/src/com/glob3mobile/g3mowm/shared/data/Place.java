

package com.glob3mobile.g3mowm.shared.data;

import java.util.ArrayList;

import org.glob3.mobile.generated.Geodetic2D;
import org.glob3.mobile.generated.URL;


public class Place {

   public final static String IMPERIAL         = "imperial";
   public final static String METRIC           = "metric";
   public URL                 _icon;
   public boolean             _gpsEnable;
   public String              _unitsSystem     = "";
   public String              _iconName;
   public String              _name            = "";
   public String              _fullName        = "";
   public Geodetic2D          _position;
   public double              _temperature;
   public double              _temperatureF;
   public ArrayList<Weather>  _weatherForecast = new ArrayList<Weather>();


   public Place() {

   }


   public String getName() {
      return _name;
   }


   public void setName(final String name) {
      _name = name;
   }


   public Geodetic2D getPosition() {
      return _position;
   }


   public void setPosition(final Geodetic2D position) {
      _position = position;
   }


   public URL getIcon() {
      return _icon;
   }


   public void setIcon(final URL icon) {
      _icon = icon;
   }


   public double getTemperature() {
      return _temperature;
   }


   public void setTemperature(final double temperature) {
      _temperature = temperature;
   }


   public String getIconName() {
      return _iconName;
   }


   public void setIconName(final String iconName) {
      _iconName = iconName;
   }


   public ArrayList<Weather> getWeatherForecast() {
      return _weatherForecast;
   }


   public void setWeatherForecast(final ArrayList<Weather> weatherForecast) {
      _weatherForecast = weatherForecast;
   }


   public boolean isGpsEnable() {
      return _gpsEnable;
   }


   public void setGpsEnable(final boolean gpsEnable) {
      _gpsEnable = gpsEnable;
   }


   public String getUnitsSystem() {
      return _unitsSystem;
   }


   public void setUnitsSystem(final String unitsSystem) {
      _unitsSystem = unitsSystem;
   }


   public double getTemperatureF() {
      _temperatureF = (_temperature * 1.8000d) + 32;

      return _temperatureF;
   }


   public void setTemperatureF(final double temperatureF) {
      _temperatureF = temperatureF;
   }


   public String getFullName() {
      return _fullName;
   }


   public void setFullName(final String fullName) {
      _fullName = fullName;
   }


}
