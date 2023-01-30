package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    String city = "cork,ie";
    String API = "9a2df53db9e19df8f2ff4eb0cc00a36c";
    ProgressBar bar;
    RelativeLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WeatherTask().execute();
    }

    private class WeatherTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }


        @Override
        protected void onPreExecute() {
            //set progress bar visible before load data
            super.onPreExecute();
             bar = findViewById(R.id.loader);
             bar.setVisibility(View.VISIBLE);

             mainContainer =  findViewById(R.id.mainContainer);
             mainContainer.setGravity(View.GONE);
        }
    }
}