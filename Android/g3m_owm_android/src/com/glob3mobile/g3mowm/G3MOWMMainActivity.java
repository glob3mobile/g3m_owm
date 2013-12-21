

package com.glob3mobile.g3mowm;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.glob3mobile.g3m_owm.R;
import com.glob3mobile.g3mowm.shared.G3MOWMListener;
import com.glob3mobile.g3mowm.shared.data.DataRetriever;
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


   public G3MOWMMainActivity() {

   }


   @Override
   protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);

      final G3MBuilder_Android builder = new G3MBuilder_Android(this);
      _g3mWidget = builder.createWidget();

      final RelativeLayout layout = (RelativeLayout) findViewById(R.id.g3mWidgetHolder);
      layout.addView(_g3mWidget);


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

      runOnUiThread(new Runnable() {

         @Override
         public void run() {
            final TextView locationTextView = (TextView) findViewById(R.id.location);
            locationTextView.setText(location);
            final WebView localWeatherWebView = (WebView) findViewById(R.id.localWeatherWidgets);
            // localWeatherWebView.loadUrl("http://openweathermap.org/help/widgets.html");
            final WebSettings webSettings = localWeatherWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true); //Maybe you don't need this rule
            webSettings.setAllowUniversalAccessFromFileURLs(true);

            //  localWeatherWebView.loadUrl("http://openweathermap.org/help/widgets.html");


            localWeatherWebView.loadUrl("file:///android_asset/ww.html?city=" + location);


            final WeatherForecastParser wfp = new WeatherForecastParser();
            wfp.getWeatherForecastList(location, _g3mWidget.getG3MContext(), G3MOWMMainActivity.this);
            //wfp.getWeatherForecastListNow(location, _g3mWidget.getG3MContext(), G3MOWMMainActivity.this);


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
   public void onWeatherForecast(final WeatherForecast forecast) {

      final ArrayList<Weather> forecastArray = forecast.getForecast();

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


         }
      });


   }
}
