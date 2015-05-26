package com.warmani.gapped;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class crop extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        String obj_id= getIntent().getStringExtra("object_id");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("activity");
// First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
// Execute the query to find the object with ID
        query.getInBackground(obj_id, new GetCallback<ParseObject>() {
            public void done(ParseObject item, ParseException e) {
                if (e == null) {
                    // item was found
                    String crop=item.getString("crop");
                    long msTime = System.currentTimeMillis();
                    Date curDateTime = new Date(msTime);
                    Format formatter=new SimpleDateFormat("dd");
                    String day=formatter.format(item.getCreatedAt());
                    String day2=formatter.format(curDateTime);
                    int k=Integer.parseInt(day2)-Integer.parseInt(day);


                            ((TextView) findViewById(R.id.textview1)).setText("You're Cultivating " + crop + "\n started " + k +"days  ago ");

                }
            }
        });
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

            case R.id.item1:Intent intent1 = new Intent(crop.this, crop_monitor.class);
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
                Intent intent = new Intent(crop.this, Dispatch.class);
                startActivity(intent);
                return true;



        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
