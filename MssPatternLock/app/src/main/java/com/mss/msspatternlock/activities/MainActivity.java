package com.mss.msspatternlock.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mss.msspatternlock.R;
import com.mss.msspatternlock.utils.AppPreferences;
import com.mss.msspatternlock.utils.Constants;

public class MainActivity extends AppCompatActivity {

    Button btnReset;
    AppPreferences mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mSession = new AppPreferences(this);
        btnReset = (Button) findViewById(R.id.btn_reset);
    }


    public void resetPattern(View v) {
        mSession.setPrefrenceString(Constants.PATTERN_SHA_VALUE, "");
        Intent resetPatternIntent = new Intent(MainActivity.this, SetPatternActivity.class);
        startActivity(resetPatternIntent);
        finish();
    }
}
