package com.warmani.gapped;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class crop_monitor extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_monitor);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.item1:return true;

            case R.id.item2:Toast.makeText(
                    getApplicationContext(),
                    "We will provide you this soon", Toast.LENGTH_LONG).show();return true;
            case R.id.item3:Toast.makeText(
                    getApplicationContext(),
                    "We will provide you this soon", Toast.LENGTH_LONG).show();return true;
            case R.id.item4:
                Toast.makeText(
                        getApplicationContext(),
                        "We will provide you this soon", Toast.LENGTH_LONG).show();return true;
            case R.id.item5:

                        ParseUser user =ParseUser.getCurrentUser();
                        user.logOut();
                        Intent intent = new Intent(crop_monitor.this, Dispatch.class);
                        startActivity(intent);
                       return true;



        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below)
            switch (position) {
                case 0:
                    return cropinfofragment.newInstance(position + 1);
                case 1:
                    return PlaceholderFragment.newInstance(position + 1);
                case 2:
                    return crophistoryfragment.newInstance(position + 1);
                case 3:
                    return deletecropfragment.newInstance(position + 1);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_crop_monitor, container, false);
//            final GPSTracker gps;
//            gps = new GPSTracker(getActivity());
            //Bundle args=getArguments();

            //int sectionNumber = args.getInt(ARG_SECTION_NUMBER);
            //((TextView)rootView.findViewById(R.id.section_label)).setText("Page with number=" + sectionNumber );

            final RadioGroup rgroup = (RadioGroup) rootView.findViewById(R.id.crop_group);
            final boolean[] status = {true};

            Button mbutton = (Button) rootView.findViewById(R.id.start_crop_button);

            final ParseUser user = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("activity");
            query.whereEqualTo("email",user.getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (list.size() > 2) status[0] = false;
                }
            });

            mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (status[0]) {
                        int id = rgroup.getCheckedRadioButtonId();
                        RadioButton rbutton = (RadioButton) rootView.findViewById(id);
                        ParseObject pobj = new ParseObject("activity");
                        pobj.put("crop", rbutton.getText().toString());
                        pobj.put("progress", 1);
                        pobj.put("email", user.getEmail());
                        pobj.put("done", false);
//                    if(gps.canGetLocation()) {
//                        double latitude = gps.getLatitude();
//                        double longitude = gps.getLongitude();
//                        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
//                        pobj.put("location",point);
//                    } else {
//                        gps.showSettingsAlert();
//                    }

                        pobj.saveInBackground();
                    } else {
                        Toast.makeText(getActivity(),"Only 3 entries are provided for free version.",Toast.LENGTH_LONG).show();
                    }
                }

            });


            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class cropinfofragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static cropinfofragment newInstance(int sectionNumber) {
            cropinfofragment fragment = new cropinfofragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public cropinfofragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.cropinfo_fragment, container, false);
            //Bundle args=getArguments();

            //int sectionNumber = args.getInt(ARG_SECTION_NUMBER);
            //((TextView)rootView.findViewById(R.id.section_label)).setText("Page with number=" + sectionNumber );


            /*final RadioGroup rgroup = (RadioGroup) rootView.findViewById(R.id.crop_group);
            Button mbutton = (Button) rootView.findViewById(R.id.start_crop_button);
            mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ParseUser user = ParseUser.getCurrentUser();
                    int id = rgroup.getCheckedRadioButtonId();
                    RadioButton rbutton = (RadioButton) rootView.findViewById(id);
                    ParseObject pobj = new ParseObject("activity");
                    pobj.put("crop", rbutton.getText().toString());
                    pobj.put("progress", 1);
                    pobj.put("email", user.getEmail());
                    pobj.saveInBackground();
                }
            });


            return rootView;
            */
            final ParseUser user = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("activity");
            query.whereEqualTo("email",user.getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void done(final List<ParseObject> list, ParseException e) {
                    if (e == null) {

                        for (int i = 0, j = 0; i < list.size(); i++, j += 80) {
                            String crop = list.get(i).getString("crop");

                            long msTime = System.currentTimeMillis();
                            Date curDateTime = new Date(msTime);
                            Format formatter = new SimpleDateFormat("dd");
                            String day = formatter.format(list.get(i).getCreatedAt());
                            String day2 = formatter.format(curDateTime);
                            int k = Integer.parseInt(day2) - Integer.parseInt(day);

                            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.relativeLayoutcrop_info);
                            Button btn = new Button(getActivity().getApplicationContext());
                            btn.setText("crop :" + crop + "\n" + "Started " + k + "  days ago");
                            LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            params.setMargins(0, 10, 0, 0);
                            params.setLayoutDirection(LinearLayout.VERTICAL);
                            btn.setId(i);
                            ll.addView(btn, params);
                            final int finalI = i;
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(getActivity(), crop.class);
                                    intent.putExtra("object_id", list.get(finalI).getObjectId());
                                    startActivity(intent);
                                }
                            });

                        }

                    } else {
                        Log.i("error code:", " error: " + e);

                    }
                }
            });

            /*query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        // object will be your game score
                        int progress=object.getInt("progress");
                        ((TextView)rootView.findViewById(R.id.textView23)).setText("progress of your crop:  "+progress);
                    } else {
                        // something went wrong
                        ((TextView)rootView.findViewById(R.id.textView23)).setText("something went wrong  ");
                        Log.i("error code:"," error: "+e);
                    }
                }
            });*/
            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class crophistoryfragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static crophistoryfragment newInstance(int sectionNumber) {
            crophistoryfragment fragment = new crophistoryfragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public crophistoryfragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.crophistoryfragment, container, false);
            //Bundle args=getArguments();

            //int sectionNumber = args.getInt(ARG_SECTION_NUMBER);
            //((TextView)rootView.findViewById(R.id.section_label)).setText("Page with number=" + sectionNumber );

            /*final RadioGroup rgroup = (RadioGroup) rootView.findViewById(R.id.crop_group);

            Button mbutton = (Button) rootView.findViewById(R.id.start_crop_button);
            mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ParseUser user = ParseUser.getCurrentUser();
                    int id = rgroup.getCheckedRadioButtonId();
                    RadioButton rbutton = (RadioButton) rootView.findViewById(id);
                    ParseObject pobj = new ParseObject("activity");
                    pobj.put("crop", rbutton.getText().toString());
                    pobj.put("progress", 1);
                    pobj.put("email", user.getEmail());
                    pobj.saveInBackground();
                }
            });*/
            final ParseUser user = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("activity");
            query.whereEqualTo("email", user.getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void done(final List<ParseObject> list, ParseException e) {
                    if (e == null) {

                        for (int i = 0, j = 0; i < list.size(); i++, j += 60) {
                            boolean harvest_stat = list.get(i).getBoolean("done");
                            if (!harvest_stat)
                                ((TextView) rootView.findViewById(R.id.historyTV2)).setText("No crops harvested yet");
                            else
                                ((TextView) rootView.findViewById(R.id.historyTV2)).setText("your crops harvested are :" + list.get(i).getString("crop"));

                        }

                    } else {
                        Log.i("error code:", " error: " + e);

                    }
                }
            });



            ((TextView)rootView.findViewById(R.id.historyTV1)).setText("Crop History ....");


            return rootView;
        }
    }

    public static class deletecropfragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static deletecropfragment newInstance(int sectionNumber) {
            deletecropfragment fragment = new deletecropfragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public deletecropfragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.cropinfo_fragment, container, false);
            //Bundle args=getArguments();

            //int sectionNumber = args.getInt(ARG_SECTION_NUMBER);
            //((TextView)rootView.findViewById(R.id.section_label)).setText("Page with number=" + sectionNumber );


            /*final RadioGroup rgroup = (RadioGroup) rootView.findViewById(R.id.crop_group);
            Button mbutton = (Button) rootView.findViewById(R.id.start_crop_button);
            mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ParseUser user = ParseUser.getCurrentUser();
                    int id = rgroup.getCheckedRadioButtonId();
                    RadioButton rbutton = (RadioButton) rootView.findViewById(id);
                    ParseObject pobj = new ParseObject("activity");
                    pobj.put("crop", rbutton.getText().toString());
                    pobj.put("progress", 1);
                    pobj.put("email", user.getEmail());
                    pobj.saveInBackground();
                }
            });


            return rootView;
            */
            final ParseUser user = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("activity");
            query.whereEqualTo("email",user.getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void done(final List<ParseObject> list, ParseException e) {
                    if (e == null) {

                        for (int i = 0, j = 0; i < list.size(); i++, j += 60) {
                            String crop = list.get(i).getString("crop");

                            long msTime = System.currentTimeMillis();
                            Date curDateTime = new Date(msTime);
                            Format formatter = new SimpleDateFormat("dd");
                            String day = formatter.format(list.get(i).getCreatedAt());
                            String day2 = formatter.format(curDateTime);
                            int k = Integer.parseInt(day2) - Integer.parseInt(day);

                            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.relativeLayoutcrop_info);
                            Button btn = new Button(getActivity().getApplicationContext());
                            btn.setText("crop :" + crop + "\n" + "Started " + k + "  days ago");
                            LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            params.setMargins(0, 10, 0, 0);
                            params.setLayoutDirection(LinearLayout.VERTICAL);
                            btn.setId(i);
                            ll.addView(btn, params);
                            final int finalI = i;
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    list.get(finalI).deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(getActivity()
                                                        ,"deleted successfully", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });

                        }

                    } else {
                        Log.i("error code:", " error: " + e);

                    }
                }
            });

            /*query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        // object will be your game score
                        int progress=object.getInt("progress");
                        ((TextView)rootView.findViewById(R.id.textView23)).setText("progress of your crop:  "+progress);
                    } else {
                        // something went wrong
                        ((TextView)rootView.findViewById(R.id.textView23)).setText("something went wrong  ");
                        Log.i("error code:"," error: "+e);
                    }
                }
            });*/
            return rootView;
        }
    }



}
