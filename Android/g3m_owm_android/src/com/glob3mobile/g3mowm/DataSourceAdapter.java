/**
 * 
 */


package com.glob3mobile.g3mowm;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.glob3mobile.g3m_owm.R;


/**
 * @author mdelacalle
 * 
 */

@SuppressLint("DefaultLocale")
public class DataSourceAdapter
         extends
            ArrayAdapter {

   private final Activity          _context;
   private final ArrayList<String> _itemList;


   /**
    * @param context
    * @param textViewResourceId
    */
   public DataSourceAdapter(final Activity context,
                            final ArrayList<String> dataSourceList) {
      super(context, R.layout.adapterrow);
      this._context = context;
      this._itemList = dataSourceList;
   }


   @Override
   public View getView(final int position,
                       final View convertView,
                       final ViewGroup parent) {

      final LayoutInflater inflater = _context.getLayoutInflater();
      final View row = inflater.inflate(R.layout.adapterrow, null);
      final TextView var = (TextView) row.findViewById(R.id.layername);
      var.setText(_itemList.get(position));
      var.setGravity(Gravity.CENTER_VERTICAL);
      var.setTextSize(15);
      var.setTextColor(Color.BLACK);


      var.setBackgroundColor(_context.getResources().getColor(R.color.transparent_background));
      return row;
   }


   @Override
   public int getCount() {
      return _itemList.size();
   }


   @Override
   public View getDropDownView(final int position,
                               final View convertView,
                               final ViewGroup parent) {

      final LayoutInflater inflater = _context.getLayoutInflater();
      final View row = inflater.inflate(R.layout.adapterrow, null);
      final TextView var = (TextView) row.findViewById(R.id.layername);
      var.setText(_itemList.get(position));
      var.setGravity(Gravity.CENTER_VERTICAL);
      var.setTextSize(15);
      var.setTextColor(Color.BLACK);
      var.setBackgroundColor(_context.getResources().getColor(R.color.transparent_background));
      return row;
   }


   @Override
   public String getItem(final int position) {
      return _itemList.get(position);
   }
}
