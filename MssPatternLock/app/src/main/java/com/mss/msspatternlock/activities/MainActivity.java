package com.mss.msspatternlock.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.msspatternlock.R;
import com.mss.msspatternlock.services.GPSTracker;
import com.mss.msspatternlock.utils.AppPreferences;
import com.mss.msspatternlock.utils.Constants;

public class MainActivity extends AppCompatActivity {

    Button btnReset;
    AppPreferences mSession;
    Toolbar toolbar;
    Context mContext;
    private GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imgIcon = (ImageView) findViewById(R.id.mss);
        imgIcon.setImageResource(R.drawable.app_icon);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Pattern Lock");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mContext = this;
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            gps = new GPSTracker(mContext, MainActivity.this);
            // Check if GPS enabled
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                mSession.setPrefrenceString(Constants.CURRENT_LATITUDE, "" + latitude);
                double longitude = gps.getLongitude();
                mSession.setPrefrenceString(Constants.CURRENT_LONGITUDE, "" + longitude);
            } else {
                gps.showSettingsAlert();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.contact_us) {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initUI() {
        mSession = new AppPreferences(this);
        btnReset = (Button) findViewById(R.id.btn_reset);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    gps = new GPSTracker(mContext, MainActivity.this);
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        mSession.setPrefrenceString(Constants.CURRENT_LATITUDE, "" + latitude);
                        double longitude = gps.getLongitude();
                        mSession.setPrefrenceString(Constants.CURRENT_LONGITUDE, "" + longitude);
                    } else {
                        gps.showSettingsAlert();
                    }
                } else {
                }
                return;
            }
        }
    }

    public void resetPattern(View v) {
        mSession.setPrefrenceString(Constants.PATTERN_SHA_VALUE, "");
        Intent resetPatternIntent = new Intent(MainActivity.this, SetPatternActivity.class);
        startActivity(resetPatternIntent);
        finish();
    }
}
