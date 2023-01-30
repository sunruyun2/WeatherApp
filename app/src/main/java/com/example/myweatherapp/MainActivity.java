package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String city = "cork,ie";
    String API = "9a2df53db9e19df8f2ff4eb0cc00a36c";
    ProgressBar bar;
    RelativeLayout mainContainer;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WeatherTask().execute();
    }

    private class WeatherTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            //fill the data with api
            String response;
            try {
                //hardcode cork address and API key
                response = new URL("https://api.openweathermap.org/data/2.5/weather?lat=51.89&lon=8.47.99&appid=9a2df53db9e19df8f2ff4eb0cc00a36c")
                        .toString();

            } catch (Exception e){
                response = null;
            }
            return response;
        }


        @Override
        protected void onPreExecute() {
            //set progress bar visible before load data
            super.onPreExecute();
             bar = findViewById(R.id.loader);
             bar.setVisibility(View.VISIBLE);

             mainContainer =  findViewById(R.id.mainContainer);
             mainContainer.setGravity(View.GONE);

             errorText = findViewById(R.id.errorText);
             errorText.setVisibility(View.VISIBLE);
        }
    }
}