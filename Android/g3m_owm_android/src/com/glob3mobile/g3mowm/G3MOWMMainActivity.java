

package com.glob3mobile.g3mowm;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.glob3mobile.g3m_owm.R;


public class G3MOWMMainActivity
         extends
            FragmentActivity
         implements
            ActionBar.TabListener {

   /**
    * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a
    * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this
    * becomes too memory intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
    */
   SectionsPagerAdapter                      mSectionsPagerAdapter;

   /**
    * The {@link ViewPager} that will host the section contents.
    */
   ViewPager                                 mViewPager;

   private com.glob3mobile.g3mowm.GPSTracker _gpsTracker;


   @Override
   protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      _gpsTracker = new GPSTracker(getApplicationContext());

      if (!_gpsTracker.canGetLocation()) {
         Dialogs.showDialogGPSError(G3MOWMMainActivity.this);
      }
      else {

      }

      // Set up the action bar.
      final ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

      // Create the adapter that will return a fragment for each of the three
      // primary sections of the app.
      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

      // Set up the ViewPager with the sections adapter.
      mViewPager = (ViewPager) findViewById(R.id.pager);
      mViewPager.setAdapter(mSectionsPagerAdapter);

      // When swiping between different sections, select the corresponding
      // tab. We can also use ActionBar.Tab#select() to do this if we have
      // a reference to the Tab.
      mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
         @Override
         public void onPageSelected(final int position) {
            actionBar.setSelectedNavigationItem(position);
         }
      });

      // For each of the sections in the app, add a tab to the action bar.
      for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
         // Create a tab with text corresponding to the page title defined by
         // the adapter. Also specify this Activity object, which implements
         // the TabListener interface, as the callback (listener) for when
         // this tab is selected.
         actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
      }
   }


   @Override
   public boolean onCreateOptionsMenu(final Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }


   @Override
   public void onTabSelected(final ActionBar.Tab tab,
                             final FragmentTransaction fragmentTransaction) {
      // When the given tab is selected, switch to the corresponding page in
      // the ViewPager.
      mViewPager.setCurrentItem(tab.getPosition());
   }


   @Override
   public void onTabUnselected(final ActionBar.Tab tab,
                               final FragmentTransaction fragmentTransaction) {
   }


   @Override
   public void onTabReselected(final ActionBar.Tab tab,
                               final FragmentTransaction fragmentTransaction) {
   }

   /**
    * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
    */
   public class SectionsPagerAdapter
            extends
               FragmentPagerAdapter {


      public SectionsPagerAdapter(final FragmentManager fm) {
         super(fm);

      }


      @Override
      public Fragment getItem(final int position) {

         if (position == 0) {
            return new LocalWeatherFragment();
         }

         final Fragment f = new G3MFragment();
         return f;


      }


      @Override
      public int getCount() {
         return 2;
      }


      @Override
      public CharSequence getPageTitle(final int position) {
         switch (position) {
            case 0:
               return "Local Weather";
            case 1:
               return "Map";
               //getString(R.string.title_section2).toUpperCase(l);

         }
         return null;
      }
   }


}
