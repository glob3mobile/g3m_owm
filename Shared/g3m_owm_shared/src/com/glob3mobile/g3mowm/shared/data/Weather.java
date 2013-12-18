

package com.glob3mobile.g3mowm.shared.data;

public class Weather {
   int    _id;
   double _distance;
   double _tempC;
   double _tempK;
   double _tempF;
   double _tempMinK;
   double _tempMinC;
   double _tempMinF;
   double _tempMaxC;
   double _tempMaxF;
   double _tempMaxK;
   double _pressure;
   double _tempKfC;
   double _tempKfF;
   double _tempKfK;
   double _humidity;
   String _dateText;
   double _windSpeed;
   int    _windDeg;
   String _windDirection;
   double _windGust;
   int    _cloudsAll;
   int    _cloudsLow;
   int    _cloudsMiddle;
   int    _cloudsHigh;
   int    _high;
   double _rain3h;
   double _snow3h;
   int    _weatherId;
   String _icon;
   String _weatherMain;
   String _weatherDescription;
   String _hour;
   String _date;


   public int getId() {
      return _id;
   }


   public void setId(final int id) {
      _id = id;
   }


   public double getDistance() {
      return _distance;
   }


   public void setDistance(final double distance) {
      _distance = distance;
   }


   public double getPressure() {
      return _pressure;
   }


   public void setPressure(final double pressure) {
      _pressure = pressure;
   }


   public double getHumidity() {
      return _humidity;
   }


   public void setHumidity(final double humidity) {
      _humidity = humidity;
   }


   public String getDateText() {
      return _dateText;
   }


   public void setDateText(final String dateText) {
      _dateText = dateText;
   }


   public double getWindSpeed() {
      return _windSpeed;
   }


   public void setWindSpeed(final double windSpeed) {
      _windSpeed = windSpeed;
   }


   public int getWindDeg() {
      return _windDeg;
   }


   public void setWindDeg(final int windDeg) {
      _windDeg = windDeg;
   }


   public double getWindGust() {
      return _windGust;
   }


   public void setWindGust(final double windGust) {
      _windGust = windGust;
   }


   public int getCloudsAll() {
      return _cloudsAll;
   }


   public void setCloudsAll(final int cloudsAll) {
      _cloudsAll = cloudsAll;
   }


   public int getCloudsLow() {
      return _cloudsLow;
   }


   public void setCloudsLow(final int cloudsLow) {
      _cloudsLow = cloudsLow;
   }


   public int getCloudsMiddle() {
      return _cloudsMiddle;
   }


   public void setCloudsMiddle(final int cloudsMiddle) {
      _cloudsMiddle = cloudsMiddle;
   }


   public int getHigh() {
      return _high;
   }


   public void setHigh(final int high) {
      _high = high;
   }


   public double getRain3h() {
      return _rain3h;
   }


   public void setRain3h(final double rain3h) {
      _rain3h = rain3h;
   }


   public int getWeatherId() {
      return _weatherId;
   }


   public void setWeatherId(final int weatherId) {
      _weatherId = weatherId;
   }


   public String getIcon() {
      return _icon;
   }


   public void setIcon(final String icon) {
      _icon = icon;
   }


   public String getWeatherMain() {
      return _weatherMain;
   }


   public void setWeatherMain(final String weatherMain) {
      _weatherMain = weatherMain;
   }


   public String getWeatherDescription() {
      return _weatherDescription;
   }


   public void setWeatherDescription(final String weatherDescription) {
      _weatherDescription = weatherDescription;
   }


   public int getCloudsHigh() {
      return _cloudsHigh;
   }


   public void setCloudsHigh(final int cloudsHigh) {
      _cloudsHigh = cloudsHigh;
   }


   public double getSnow3h() {
      return _snow3h;
   }


   public void setSnow3h(final double snow3h) {
      _snow3h = snow3h;
   }


   public double getTempC() {
      _tempC = _tempK - 273.15d;
      return _tempC;
   }


   public void setTempC(final double tempC) {
      _tempC = tempC;
   }


   public double getTempK() {
      return _tempK;
   }


   public void setTempK(final double tempK) {
      _tempK = tempK;
   }


   public double getTempF() {
      _tempF = ((_tempK - 273.15d) * 1.8000d) + 32;
      return _tempF;
   }


   public void setTempF(final double tempF) {
      _tempF = tempF;
   }


   public double getTempMinK() {
      return _tempMinK;
   }


   public void setTempMinK(final double tempMinK) {
      _tempMinK = tempMinK;
   }


   public double getTempMinC() {
      _tempMinC = _tempMinK - 273.15d;
      return _tempMinC;
   }


   public void setTempMinC(final double tempMinC) {
      _tempMinC = tempMinC;
   }


   public double getTempMinF() {
      _tempMinF = ((_tempMinK - 273.15d) * 1.8000d) + 32;
      return _tempMinF;
   }


   public void setTempMinF(final double tempMinF) {
      _tempMinF = tempMinF;
   }


   public double getTempMaxC() {
      _tempMaxC = _tempMaxK - 273.15d;
      return _tempMaxC;
   }


   public void setTempMaxC(final double tempMaxC) {
      _tempMaxC = tempMaxC;
   }


   public double getTempMaxF() {
      _tempMaxF = ((_tempMaxK - 273.15d) * 1.8000d) + 32;
      return _tempMaxF;
   }


   public void setTempMaxF(final double tempMaxF) {
      _tempMaxF = tempMaxF;
   }


   public double getTempMaxK() {
      return _tempMaxK;
   }


   public void setTempMaxK(final double tempMaxK) {
      _tempMaxK = tempMaxK;
   }


   public double getTempKfC() {
      _tempKfC = _tempKfK - 273.15d;
      return _tempKfC;
   }


   public void setTempKfC(final double tempKfC) {
      _tempKfC = tempKfC;
   }


   public double getTempKfF() {
      _tempKfF = ((_tempKfK - 273.15d) * 1.8000d) + 32;
      return _tempKfF;
   }


   public void setTempKfF(final double tempKfF) {
      _tempKfF = tempKfF;
   }


   public double getTempKfK() {
      return _tempKfK;
   }


   public void setTempKfK(final double tempKfK) {
      _tempKfK = tempKfK;
   }


   public String getHour() {
      _hour = _dateText.substring(11, 16);
      return _hour;
   }


   public void setHour(final String hour) {
      _hour = hour;
   }


   public String getDate() {

      _date = _dateText.substring(0, 10);
      return _date;
   }


   public void setDate(final String date) {
      _date = date;
   }


   public String getWindDirection() {


      if (_windDeg > 180) {
         _windDeg = 180 - _windDeg;
      }
      _windDirection = "ERR";

      if ((_windDeg > -11.25) && (_windDeg < (11.25))) {
         return "N";
      }
      else if ((_windDeg > 11.25) && (_windDeg < 33.75)) {
         return "NNE";
      }
      else if ((_windDeg > 33.75) && (_windDeg < 56.25)) {
         return "NE";
      }
      else if ((_windDeg > 56.25) && (_windDeg < 78.75)) {
         return "ENE";
      }
      else if ((_windDeg > 78.75) && (_windDeg < 101.25)) {
         return "E";
      }
      else if ((_windDeg > 101.25) && (_windDeg < 123.75)) {
         return "ESE";
      }
      else if ((_windDeg > 123.75) && (_windDeg < 146.25)) {
         return "SE";
      }
      else if ((_windDeg > 146.25) && (_windDeg < 168.75)) {
         return "SSE";
      }
      else if (((_windDeg > 168.75) && (_windDeg < 180)) || ((_windDeg > -180) && (_windDeg < -168.75))) {
         return "S";
      }
      else if ((_windDeg > -168.75) && (_windDeg < -146.25)) {
         return "SSW";
      }
      else if ((_windDeg > -146.25) && (_windDeg < -123.75)) {
         return "SW";
      }
      else if ((_windDeg > -123.75) && (_windDeg < -101.25)) {
         return "WSW";
      }
      else if ((_windDeg > -101.25) && (_windDeg < -78.75)) {
         return "W";
      }
      else if ((_windDeg > -78.75) && (_windDeg < -56.25)) {
         return "WNW";
      }
      else if ((_windDeg > -56.25) && (_windDeg < -33.75)) {
         return "NW";
      }
      else if ((_windDeg > -33.75) && (_windDeg < -11.25)) {
         return "NNW";
      }
      else {
         return _windDirection;
      }
   }


   public void setWindDirection(final String windDirection) {
      _windDirection = windDirection;
   }


}
