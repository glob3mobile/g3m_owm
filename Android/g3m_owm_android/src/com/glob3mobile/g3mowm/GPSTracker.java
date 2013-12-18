

package com.glob3mobile.g3mowm;

import org.glob3.mobile.generated.ILogger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class GPSTracker
         extends
            Service
         implements
            LocationListener {

   private final Context     mContext;

   // flag for GPS status
   boolean                   isGPSEnabled                    = false;

   // flag for network status
   boolean                   isNetworkEnabled                = false;

   // flag for GPS status
   boolean                   canGetLocation                  = false;

   Location                  location;                               // location
   double                    latitude;                               // latitude
   double                    longitude;                              // longitude

   // The minimum distance to change Updates in meters
   private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3;    // 10 meters

   // The minimum time between updates in milliseconds
   private static final long MIN_TIME_BW_UPDATES             = 1000; // 1 sec

   // Declaring a Location Manager
   protected LocationManager locationManager;


   public GPSTracker(final Context context) {
      this.mContext = context;
      getLocation();
   }


   private Location getLocation() {
      try {
         locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

         // getting GPS status
         isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

         // getting network status
         isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

         if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
         }
         else {
            this.canGetLocation = true;
            // First get location from Network Provider
            if (isNetworkEnabled) {
               locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
               Log.d(GPSTracker.this.toString(), "Network");
               if (locationManager != null) {
                  location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                  if (location != null) {
                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                  }
               }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
               if (location == null) {
                  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                           MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                  Log.d(GPSTracker.this.toString(), "GPS Enabled");
                  if (locationManager != null) {
                     location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                     if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                     }
                  }
               }
            }
         }

      }
      catch (final Exception e) {
         e.printStackTrace();
      }

      return location;

   }


   public void stopUsingGPS() {
      if (locationManager != null) {
         locationManager.removeUpdates(GPSTracker.this);
      }
   }


   public double getLatitude() {
      if (location != null) {
         latitude = location.getLatitude();
      }
      return latitude;
   }


   public double getFakeLatitude() {
      return -17.145695;
   }


   public double getFakeLongitude() {
      return 144.97030;
   }


   public double getLongitude() {
      if (location != null) {
         longitude = location.getLongitude();
      }
      return longitude;
   }


   /**
    * Function to check GPS/wifi enabled
    * 
    * @return boolean
    * */
   public boolean canGetLocation() {
      return this.canGetLocation;
   }


   @Override
   public void onLocationChanged(final Location arg0) {
      ILogger.instance().logError("LOCATION CHANGED LON:" + arg0.getLongitude() + ",LAT:" + arg0.getLatitude());
   }


   @Override
   public void onProviderDisabled(final String arg0) {
      // TODO Auto-generated method stub

      ILogger.instance().logError("THE GPS IS NOT WORKING, RELEASE VIEW AND DISABLE BUTTON");
   }


   @Override
   public void onProviderEnabled(final String arg0) {
      // TODO Auto-generated method stub
      ILogger.instance().logError("THE GPS IS ENABLE");
   }


   @Override
   public void onStatusChanged(final String arg0,
                               final int arg1,
                               final Bundle arg2) {
      // TODO Auto-generated method stub
      ILogger.instance().logError("THE GPS STATUS IS CHANGED");

   }


   @Override
   public IBinder onBind(final Intent arg0) {
      // TODO Auto-generated method stub
      return null;
   }

}
