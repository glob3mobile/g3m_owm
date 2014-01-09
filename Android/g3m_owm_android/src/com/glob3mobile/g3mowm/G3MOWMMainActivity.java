

package com.glob3mobile.g3mowm;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.glob3.mobile.generated.Angle;
import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.Geodetic3D;
import org.glob3.mobile.generated.MarksRenderer;
import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

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
   private TabHost           _tabs;
   private SharedPreferences _sharedPrefs;
   private String            _unitSystem;
   private G3MContext        _g3mContext;


   public G3MOWMMainActivity() {

   }


   @Override
   protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);


      PreferenceManager.setDefaultValues(this, R.xml.settings, true);
      _sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
      _unitSystem = _sharedPrefs.getString(getResources().getString(R.string.unit_system), "International");


      _tabs = (TabHost) findViewById(android.R.id.tabhost);

      final RelativeLayout layout = (RelativeLayout) findViewById(R.id.g3mWidgetHolder);
      final G3MBuilder_Android builder = new G3MBuilder_Android(this);
      G3MOWMBuilder.buildG3MOWM(builder, this, G3MOWMBuilder.Platform.ANDROID);

      _g3mWidget = builder.createWidget();
      _g3mContext = _g3mWidget.getG3MContext();
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
         }
      });


      _gpsTracker = new GPSTracker(G3MOWMMainActivity.this);


      if (!_gpsTracker.canGetLocation) {
         Dialogs.showDialogGPSError(_gpsTracker);
      }
      else {

         if (!isOnline()) {
            Dialogs.showNetworkError(G3MOWMMainActivity.this);
         }


         DataRetriever.getWeatherForLocation(_g3mWidget.getG3MContext(), _gpsTracker.getLatitude(), _gpsTracker.getLongitude(),
                  G3MOWMMainActivity.this);
      }

      final ToggleButton weatherIcons = (ToggleButton) layout.findViewById(R.id.weatherIcons);
      weatherIcons.bringToFront();
      weatherIcons.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(final View arg0) {
            final MarksRenderer mr = G3MOWMBuilder.getWeatherMarkerLayer();
            if (mr.isEnable()) {
               mr.setEnable(false);
            }
            else {
               mr.setEnable(true);
            }

         }
      });


      final ImageButton goLocalWeather = (ImageButton) layout.findViewById(R.id.locationButton);
      goLocalWeather.setBackgroundColor(getResources().getColor(R.color.transparent_background));
      goLocalWeather.bringToFront();

      goLocalWeather.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(final View v) {
            DataRetriever.getLocalWeather(_g3mWidget.getG3MContext(), _gpsTracker.getLatitude(), _gpsTracker.getLongitude(),
                     G3MOWMBuilder.getWeatherMarkerLayer());
            _g3mWidget.setAnimatedCameraPosition(new Geodetic3D(Angle.fromDegrees(_gpsTracker.getLatitude()),
                     Angle.fromDegrees(_gpsTracker.getLongitude()), 50000));
         }
      });


      _tabs.setup();


      TabHost.TabSpec spec = _tabs.newTabSpec(getString(R.string.local_weather));
      spec.setContent(R.id.tab1);
      spec.setIndicator(getString(R.string.local_weather));
      _tabs.addTab(spec);

      spec = _tabs.newTabSpec(getString(R.string.forecast));
      spec.setContent(R.id.tab2);
      spec.setIndicator(getString(R.string.forecast));
      _tabs.addTab(spec);

      spec = _tabs.newTabSpec(getString(R.string.map));
      spec.setContent(R.id.tab3);
      spec.setIndicator(getString(R.string.map));
      _tabs.addTab(spec);

      _tabs.setCurrentTab(0);


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


            final Button selectLocationButton = (Button) findViewById(R.id.selectLocation);

            selectLocationButton.setOnClickListener(new OnClickListener() {

               @Override
               public void onClick(final View v) {
                  Dialogs.showLocationDialog(G3MOWMMainActivity.this);
               }
            });

            final Button searchLocationButton = (Button) findViewById(R.id.addNewLocation);

            searchLocationButton.setOnClickListener(new OnClickListener() {

               @Override
               public void onClick(final View v) {
                  Dialogs.showSearchLocationDialog(G3MOWMMainActivity.this, _g3mContext);
               }
            });


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
   public boolean onCreateOptionsMenu(final Menu menu) {

      getMenuInflater().inflate(R.menu.main, menu);

      final MenuItem settings = menu.findItem(R.id.action_settings);
      settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {

         @Override
         public boolean onMenuItemClick(final MenuItem item) {
            final Intent intent = new Intent(G3MOWMMainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return false;
         }
      });

      final MenuItem refresh = menu.findItem(R.id.update);
      refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {

         @Override
         public boolean onMenuItemClick(final MenuItem item) {

            _tabs.setVisibility(View.INVISIBLE);
            final RelativeLayout loading = (RelativeLayout) findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
            loading.bringToFront();
            onLocation(_location);
            return false;
         }
      });


      return true;
   }


   public boolean isOnline() {
      final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
   }


   @Override
   public void onWeatherForecast(final WeatherForecast forecastCollection) {


      final ArrayList<Weather> forecastArray = forecastCollection.getForecast();

      runOnUiThread(new Runnable() {

         @Override
         public void run() {
            final TextView locationTextTemperature = (TextView) findViewById(R.id.currentTemperature);


            if (_unitSystem.equals(getResources().getString(R.string.international))) {
               locationTextTemperature.setText(Math.round(forecastArray.get(0).getTempC()) + " ºC");
            }
            else {
               locationTextTemperature.setText(Math.round(forecastArray.get(0).getTempF()) + " ºF");
            }


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


               if (_unitSystem.equals(getResources().getString(R.string.international))) {
                  tempMaxView.setText(Math.round(forecast.getTempMaxC()) + "ºC");
               }
               else {
                  tempMaxView.setText(Math.round(forecast.getTempMaxF()) + "ºF");
               }


               final TextView tempMinView = (TextView) forecastView.findViewById(R.id.tempMin);

               if (_unitSystem.equals(getResources().getString(R.string.international))) {
                  tempMinView.setText("   /  " + Math.round(forecast.getTempMinC()) + "ºC");
               }
               else {
                  tempMinView.setText("   /  " + Math.round(forecast.getTempMinF()) + "ºF");
               }

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


            //Weather donwloaded:
            final RelativeLayout loading = (RelativeLayout) findViewById(R.id.loading);
            loading.setVisibility(View.INVISIBLE);
            _tabs.setVisibility(View.VISIBLE);


         }


      });


   }
}
