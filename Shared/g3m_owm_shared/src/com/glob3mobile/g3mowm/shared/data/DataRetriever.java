

package com.glob3mobile.g3mowm.shared.data;

import org.glob3.mobile.generated.G3MContext;
import org.glob3.mobile.generated.IBufferDownloadListener;
import org.glob3.mobile.generated.IByteBuffer;
import org.glob3.mobile.generated.IDownloader;
import org.glob3.mobile.generated.IJSONParser;
import org.glob3.mobile.generated.JSONArray;
import org.glob3.mobile.generated.JSONBaseObject;
import org.glob3.mobile.generated.JSONObject;
import org.glob3.mobile.generated.TimeInterval;
import org.glob3.mobile.generated.URL;
import org.glob3.mobile.specific.JSONParser_Android;

import com.glob3mobile.g3mowm.shared.G3MOWMListener;


public class DataRetriever {

   static G3MOWMListener _listener;


   public static void getLocationName(final G3MContext context,
                                      final double lat,
                                      final double lon,
                                      final G3MOWMListener listener) {

      _listener = listener;

      final IDownloader downloaderLocation = context.getDownloader();

      final IBufferDownloadListener listenerLocation = new IBufferDownloadListener() {

         @Override
         public void onDownload(final URL url,
                                final IByteBuffer buffer,
                                final boolean expired) {


            final String response = buffer.getAsString();
            final IJSONParser parser = new JSONParser_Android();
            final JSONBaseObject jsonObject = parser.parse(response);
            final JSONObject object = jsonObject.asObject();
            final JSONArray list = object.getAsArray("geonames");

            if (list != null) {

               final JSONObject geonames = list.getAsObject(0);
               _listener.onLocation(geonames.getAsString("name", "LOCATION_ERROR"));
            }

         }


         @Override
         public void onError(final URL url) {
            _listener.onError("LOCATION_ERROR");
         }


         @Override
         public void onCancel(final URL url) {
            // TODO Auto-generated method stub

         }


         @Override
         public void onCanceledDownload(final URL url,
                                        final IByteBuffer data,
                                        final boolean expired) {
            // TODO Auto-generated method stub

         }

      };

      downloaderLocation.requestBuffer(new URL("http://api.geonames.org/citiesJSON?north=" + (lat + 0.2d) + "&south="
                                               + (lat - 0.2d) + "&east=" + (lon + 0.2d) + "&west=" + (lon - 0.2d)
                                               + "&lang=en&username=mdelacalle", false), 0, TimeInterval.fromHours(1.0), false,
               listenerLocation, false);

   }

}
