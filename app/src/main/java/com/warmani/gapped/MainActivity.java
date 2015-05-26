package com.warmani.gapped;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by demo on 11-May-15.
 */
public class MainActivity extends ActionBarActivity {


    Button btnShowLocation;

    GPSTracker gps;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(MainActivity.this);
        final ParseUser user = ParseUser.getCurrentUser();
        String username = user.getUsername();
        String email = user.getEmail();
        String name=user.getString("name");
        TextView text = (TextView) findViewById(R.id.text_field);
        text.setText("Welcome " + name + "  " + "Your registered records are\n" + "USERNAME: " + username + "\n" + "EMAIL: " + email + "\n"+"please go and check status of your crop");


        /*if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(
                    getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }*/


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
            case R.id.item1:Intent intent1 = new Intent(MainActivity.this, crop_monitor.class);
                startActivity(intent1);
                return true;

            case R.id.item2:
                Toast.makeText(
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
                Intent intent = new Intent(MainActivity.this, Dispatch.class);
                startActivity(intent);
                return true;



        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
