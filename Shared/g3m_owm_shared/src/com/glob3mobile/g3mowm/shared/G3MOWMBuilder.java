

package com.glob3mobile.g3mowm.shared;

import java.util.ArrayList;

import org.glob3.mobile.generated.Color;
import org.glob3.mobile.generated.IG3MBuilder;
import org.glob3.mobile.generated.ILogger;
import org.glob3.mobile.generated.LayerSet;
import org.glob3.mobile.generated.MarksRenderer;

import com.glob3mobile.g3mowm.shared.data.SimpleRasterLayerBuilder;


public class G3MOWMBuilder {

   static IG3MBuilder               _builder;
   static G3MOWMListener            _listener;
   static Platform                  _platform;
   private static LayerSet          _layerset;
   private static MarksRenderer     _weatherMarksRenderer;
   private static ArrayList<String> _baseLayers;
   private static ArrayList<String> _weatherLayers;

   public static enum Platform {
      ANDROID,
      WEB_GL,
   }


   public static void buildG3MOWM(final IG3MBuilder builder,
                                  final G3MOWMListener listener,
                                  final Platform platform) {


      _builder = builder;
      _listener = listener;
      _platform = platform;

      _builder.setBackgroundColor(Color.fromRGBA255(175, 221, 233, 255));
      _weatherMarksRenderer = new MarksRenderer(false);
      _builder.addRenderer(_weatherMarksRenderer);
      configureLayers();

   }


   public static LayerSet getLayerset() {
      return _layerset;
   }


   public static ArrayList<String> getBaseLayerList() {
      _baseLayers = new ArrayList<String>();
      _baseLayers.add("Bing Aerial");
      _baseLayers.add("MapQuest OSM");
      _baseLayers.add("Map Box Terrain");
      _baseLayers.add("Map Box Aerial");
      _baseLayers.add("Bing Aerial With Labels");
      return _baseLayers;
   }


   public static void disableAllBaseLayers() {
      for (final String layerTitle : _baseLayers) {
         _layerset.getLayerByTitle(layerTitle).setEnable(false);
      }
   }


   public static void disableAllWeatherLayers() {
      for (final String layerTitle : _weatherLayers) {
         _layerset.getLayerByTitle(layerTitle).setEnable(false);
      }
   }


   public static ArrayList<String> getWeatherLayerList() {
      _weatherLayers = new ArrayList<String>();
      //     _weatherLayers.add("Weather");
      _weatherLayers.add("Precipitation");
      _weatherLayers.add("Pressure");
      _weatherLayers.add("Pressure Contour");
      _weatherLayers.add("Wind");
      _weatherLayers.add("Temperature");
      _weatherLayers.add("Snow");
      return _weatherLayers;
   }


   private static void configureLayers() {
      _layerset = SimpleRasterLayerBuilder.createLayerset();
      _layerset.getLayerByTitle("Bing Aerial").setEnable(true);
      _builder.getPlanetRendererBuilder().setLayerSet(_layerset);
   }


   public static MarksRenderer getWeatherMarkerLayer() {
      // TODO Auto-generated method stub
      return _weatherMarksRenderer;
   }


   public static void enableLayer(final String text) {

      ILogger.instance().logInfo("Enable Layer:" + text);
      _layerset.getLayerByTitle(text).setEnable(true);

   }


}
