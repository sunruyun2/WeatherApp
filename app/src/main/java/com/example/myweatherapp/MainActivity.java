package com.example.myweatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String city = "cork,ie";
    String API = "9a2df53db9e19df8f2ff4eb0cc00a36c";
    ProgressBar bar;
    RelativeLayout mainContainer;
    TextView errorText , addressView, status, updated_atView, statusView, tempView,tempMinView,tempMaxView,
            sunriseView, sunsetView, windView,pressureView,humidityView;

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
            String response = "";
            try {
                //hardcode cork address and API key
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=51.89&lon=-8.47&appid=9a2df53db9e19df8f2ff4eb0cc00a36c");
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

                String inputLine;
                while ((inputLine = in.readLine())!= null){
                    response = response + inputLine;
                }
                in.close();


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
             mainContainer.setVisibility(View.GONE);

             errorText = findViewById(R.id.errorText);
             errorText.setVisibility(View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.i("DEBUGGER","0");
                Log.i("DEBUGGER",result);
               JSONObject jsonObj = new JSONObject(result);
                Log.i("DEBUGGER","0.1");
               JSONObject main = jsonObj.getJSONObject("main");
                Log.i("DEBUGGER","0.2");
               JSONObject sys = jsonObj.getJSONObject("sys");
               JSONObject wind = jsonObj.getJSONObject("wind");
               JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
               long updatedAt = jsonObj.getLong("dt");
               String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/YYYY hh:MM: a", Locale.ENGLISH).format(new Date(updatedAt*1000));
               String temp = main.getString("temp") + "°C";
                Log.i("DEBUGGER","0.3");
               String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
               String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
               String pressure = main.getString("pressure");
               String humidity = main.getString("humidity");
                Log.i("DEBUGGER","0.4");
               long sunrise = sys.getLong("sunrise");
               long sunset = sys.getLong("sunset");
               String windSpeed = wind.getString("speed");
                Log.i("DEBUGGER","0.5");
               String weatherDescription = weather.getString("description");
                Log.i("DEBUGGER","0.6");
               String address = jsonObj.getString("name") + sys.getString("country");
                Log.i("DEBUGGER","6");

               addressView = findViewById(R.id.address);
               addressView.setText(address);
               updated_atView = findViewById(R.id.updated_at);
               updated_atView.setText(weatherDescription.toUpperCase());
               statusView = findViewById(R.id.status);
               statusView.setText(updatedAtText);
               tempView = findViewById(R.id.temp);
               tempView.setText(temp);
               tempMaxView = findViewById(R.id.temp_max);
               tempMaxView.setText(tempMax);
               tempMinView = findViewById(R.id.temp_min);
               tempMinView.setText(tempMin);
               sunriseView = findViewById(R.id.sunrise);
               sunriseView.setText(new SimpleDateFormat("hh:MM a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
               sunsetView = findViewById(R.id.sunset);
               sunsetView.setText(new SimpleDateFormat("hh:MM a", Locale.ENGLISH).format(new Date(sunset * 1000)));
               windView = findViewById(R.id.wind);
               windView.setText(windSpeed);
               pressureView = findViewById(R.id.pressure);
               pressureView.setText(pressure);
               humidityView = findViewById(R.id.humid);
               humidityView.setText(humidity);

               Log.i("DEBUGGER","2");

               bar.setVisibility(View.GONE);
               mainContainer.setVisibility(View.VISIBLE);
            } catch (Exception e){
                bar.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
            }
        }
    }
}