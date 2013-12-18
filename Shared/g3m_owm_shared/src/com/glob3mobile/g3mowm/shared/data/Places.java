

package com.glob3mobile.g3mowm.shared.data;

import java.util.ArrayList;


public class Places {

   ArrayList<Place> _places       = new ArrayList<Place>();
   boolean          _isDownloaded = false;


   public ArrayList<Place> getPlaces() {
      return _places;
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
