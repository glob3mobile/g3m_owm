

package com.glob3.mobile.owm.shared.data;

import java.util.ArrayList;


public class Places {

   ArrayList<Place>  _places       = new ArrayList<Place>();
   ArrayList<String> _placesString = new ArrayList<String>();
   boolean           _isDownloaded = false;


   public ArrayList<Place> getPlaces() {
      return _places;
   }


   public ArrayList<String> getPlacesAsString() {
      return _placesString;
   }


   public void setPlacesAsString(final ArrayList<String> placesString) {
      _placesString = placesString;
   }


   public void setPlaces(final ArrayList<Place> places) {
      _places = places;
   }


   public boolean isDownloaded() {
      return _isDownloaded;
   }


   public void setDownloaded(final boolean isDownloaded) {
      _isDownloaded = isDownloaded;
   }


}
