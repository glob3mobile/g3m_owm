

package com.glob3mobile.g3mowm;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.glob3mobile.g3m_owm.R;
import com.glob3mobile.g3mowm.shared.G3MOWMBuilder;
import com.glob3mobile.g3mowm.shared.G3MOWMListener;
import com.glob3mobile.g3mowm.shared.data.DataRetriever;
import com.glob3mobile.g3mowm.shared.data.Utils;
import com.glob3mobile.g3mowm.shared.data.Weather;
import com.glob3mobile.g3mowm.shared.data.WeatherForecast;
import com.glob3mobile.g3mowm.shared.data.WeatherForecastParser;


@SuppressLint("SetJavaScriptEnabled")
public class G3MOWMMainActivity
         extends
            Activity
         implements
            G3MOWMListener {

   private GPSTracker        _gpsTracker;
   private G3MWidget_Android _g3mWidget;
   private String            _location;


   public G3MOWMMainActivity() {

   }


   @Override
   protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);


      final TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);

      final RelativeLayout layout = (RelativeLayout) findViewById(R.id.g3mWidgetHolder);
      final G3MBuilder_Android builder = new G3MBuilder_Android(this);
      G3MOWMBuilder.buildG3MOWM(builder, this, G3MOWMBuilder.Platform.ANDROID);

      _g3mWidget = builder.createWidget();
      layout.addView(_g3mWidget);

      DataRetriever.getWorldWeather(_g3mWidget.getG3MContext(), G3MOWMBuilder.getWeatherMarkerLayer());


      final Spinner spinnerLayers = (Spinner) layout.findViewById(R.id.spinnerBaseLayers);
      spinnerLayers.setAdapter(new DataSourceAdapter(G3MOWMMainActivity.this, G3MOWMBuilder.getBaseLayerList()));
      spinnerLayers.bringToFront();

      spinnerLayers.setOnItemSelectedListener(new OnItemSelectedListener() {

         @Override
         public void onItemSelected(final AdapterView<?> arg0,
                                    final View arg1,
                                    final int arg2,
                                    final long arg3) {

            G3MOWMBuilder.disableAllBaseLayers();
            G3MOWMBuilder.enableLayer((String) ((TextView) arg1.findViewById(R.id.layername)).getText());
         }


         @Override
         public void onNothingSelected(final AdapterView<?> arg0) {


         }
      });


      final Spinner spinnerWeatherLayers = (Spinner) layout.findViewById(R.id.spinnerWeatherLayers);
      spinnerWeatherLayers.setAdapter(new DataSourceAdapter(G3MOWMMainActivity.this, G3MOWMBuilder.getWeatherLayerList()));
      spinnerWeatherLayers.bringToFront();


      spinnerWeatherLayers.setOnItemSelectedListener(new OnItemSelectedListener() {

         @Override
         public void onItemSelected(final AdapterView<?> arg0,
                                    final View arg1,
                                    final int arg2,
                                    final long arg3) {
            G3MOWMBuilder.disableAllWeatherLayers();
            G3MOWMBuilder.enableLayer((String) ((TextView) arg1.findViewById(R.id.layername)).getText());
         }


         @Override
         public void onNothingSelected(final AdapterView<?> arg0) {
            // TODO Auto-generated method stub

         }
      });


      _gpsTracker = new GPSTracker(G3MOWMMainActivity.this);


      if (!_gpsTracker.canGetLocation) {
         Dialogs.showDialogGPSError(_gpsTracker);
      }
      else {
         DataRetriever.getLocationName(_g3mWidget.getG3MContext(), _gpsTracker.getLatitude(), _gpsTracker.getLongitude(),
                  G3MOWMMainActivity.this);
      }


      tabs.setup();


      TabHost.TabSpec spec = tabs.newTabSpec(getString(R.string.local_weather));
      spec.setContent(R.id.tab1);
      spec.setIndicator(getString(R.string.local_weather));
      tabs.addTab(spec);

      spec = tabs.newTabSpec(getString(R.string.forecast));
      spec.setContent(R.id.tab2);
      spec.setIndicator(getString(R.string.forecast));
      tabs.addTab(spec);

      spec = tabs.newTabSpec(getString(R.string.map));
      spec.setContent(R.id.tab3);
      spec.setIndicator(getString(R.string.map));
      tabs.addTab(spec);

      tabs.setCurrentTab(0);


   }


   @Override
   public void onError(final String message) {
      // TODO Auto-generated method stub

   }


   @Override
   public void onLocation(final String location) {
      _location = location;
      runOnUiThread(new Runnable() {

         @Override
         public void run() {


            // Local Current Weather

            final TextView locationTextView = (TextView) findViewById(R.id.location);
            locationTextView.setText(location);
            final WebView localWeatherWebView = (WebView) findViewById(R.id.localWeatherWidgets);
            final WebSettings webSettings = localWeatherWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            localWeatherWebView.loadUrl("file:///android_asset/ww.html?city=" + location);

            //Forecast
            final TextView locationTextForecastView = (TextView) findViewById(R.id.locationForecast);
            locationTextForecastView.setText(location);


            final WeatherForecastParser wfp = new WeatherForecastParser();
            wfp.getWeatherForecastList(location, _g3mWidget.getG3MContext(), G3MOWMMainActivity.this);


         }
      });


   }


   private Bitmap getBitmapFromAsset(String strName) {


      if (strName == null) {
         strName = "01d.png";
      }
      final AssetManager assetManager = getAssets();

      InputStream istr;
      try {
         istr = assetManager.open(strName);

         final Bitmap bitmap = BitmapFactory.decodeStream(istr);
         istr.close();
         return bitmap;
      }
      catch (final IOException e) {

         e.printStackTrace();
         return null;
      }


   }


   @Override
   public void onWeatherForecast(final WeatherForecast forecastCollection) {

      final ArrayList<Weather> forecastArray = forecastCollection.getForecast();

      runOnUiThread(new Runnable() {

         @Override
         public void run() {
            final TextView locationTextTemperature = (TextView) findViewById(R.id.currentTemperature);
            locationTextTemperature.setText(Math.round(forecastArray.get(0).getTempC()) + " ºC");


            final TextView locationTextDescription = (TextView) findViewById(R.id.currentWeatherDescription);
            locationTextDescription.setText(forecastArray.get(0).getWeatherDescription());

            final ImageView weatherIcon = (ImageView) findViewById(R.id.weatherIcon);

            final Bitmap icon = getBitmapFromAsset(forecastArray.get(0).getIcon());
            weatherIcon.setImageBitmap(Bitmap.createScaledBitmap(icon, icon.getWidth() * 3, icon.getHeight() * 3, true));


            final HorizontalScrollView weatherForecastHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.weatherForecastScrollView);
            final LinearLayout forecastViewLayout = (LinearLayout) weatherForecastHorizontalScrollView.findViewById(R.id.weatherForecastScrollViewLayout);

            final WebView forecastWeatherWebView = (WebView) findViewById(R.id.forecastWeatherWidgets);
            final WebSettings webSettingsForecast = forecastWeatherWebView.getSettings();
            webSettingsForecast.setJavaScriptEnabled(true);
            webSettingsForecast.setAllowFileAccessFromFileURLs(true);
            webSettingsForecast.setAllowUniversalAccessFromFileURLs(true);
            forecastWeatherWebView.loadUrl("file:///android_asset/fw.html?city=" + _location);


            for (final Weather forecast : forecastArray) {

               final LayoutInflater inflater = getLayoutInflater();
               final View forecastView = inflater.inflate(R.layout.weather_forecast_item, null);

               final Calendar calendar = Utils.getCalendar(forecast.getDateText());
               final String weekDay = Utils.getWeekdayLocale(calendar.get(Calendar.DAY_OF_WEEK));
               final String dayMonth = "" + calendar.get(Calendar.DAY_OF_MONTH);


               final TextView dayOfTheWeekTextView = (TextView) forecastView.findViewById(R.id.dayOfTheWeek);
               dayOfTheWeekTextView.setText(weekDay);
               final TextView dayOfMonth = (TextView) forecastView.findViewById(R.id.dayOfTheMonth);
               dayOfMonth.setText("   " + dayMonth);
               final TextView hour = (TextView) forecastView.findViewById(R.id.hour);
               hour.setText(forecast.getHour());


               final TextView tempMaxView = (TextView) forecastView.findViewById(R.id.tempMax);
               tempMaxView.setText(Math.round(forecast.getTempMaxC()) + "ºC");
               final TextView tempMinView = (TextView) forecastView.findViewById(R.id.tempMin);
               tempMinView.setText("   /  " + Math.round(forecast.getTempMinC()) + "ºC");

               final TextView descriptionForecastView = (TextView) forecastView.findViewById(R.id.descriptionForecast);
               descriptionForecastView.setText(forecast.getWeatherDescription());

               final TextView windForecastView = (TextView) forecastView.findViewById(R.id.windForecast);
               windForecastView.setText("" + Math.round(forecast.getWindSpeed()) + " -");

               final TextView windDirectionForecastView = (TextView) forecastView.findViewById(R.id.windDirection);
               windDirectionForecastView.setText(" " + forecast.getWindDirection());

               final ImageView weatherIconForecast = (ImageView) forecastView.findViewById(R.id.weatherIconForecast);
               final Bitmap iconForecast = getBitmapFromAsset(forecast.getIcon());
               weatherIconForecast.setImageBitmap(Bitmap.createScaledBitmap(iconForecast, iconForecast.getWidth() * 4,
                        iconForecast.getHeight() * 4, true));

               forecastViewLayout.addView(forecastView);
            }


         }


      });


   }
}
