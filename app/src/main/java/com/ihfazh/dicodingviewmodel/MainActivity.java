package com.ihfazh.dicodingviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private WeatherAdapter adapter;
    private RecyclerView rvWeathers;
    private ProgressBar progressBar;
    private EditText edtCity;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        rvWeathers = findViewById(R.id.recyclerView);
        rvWeathers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        rvWeathers.setAdapter(adapter);

        edtCity = findViewById(R.id.edit_city);
        btnSearch = findViewById(R.id.btn_cari);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtCity.getText().toString();

                if (TextUtils.isEmpty(city)) return;

                showLoading(true);
                setWeather(city);
            }
        });
    }

    private void setWeather(String city) {
        final ArrayList<WeatherItem> weatherItems = new ArrayList<>();
        String ApiKey = "c8fc41b3df7b8b97f4eeeb459e0c8333";
        String url =  "https://api.openweathermap.org/data/2.5/group?id=" + city + "&units=metric&appid=" + ApiKey;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItem weatherItem = new WeatherItem();
                        weatherItem.setId(weather.getInt("id"));
                        weatherItem.setName(weather.getString("name"));
                        weatherItem.setCurrentWearther(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItem.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelsius = tempInKelvin - 273;
                        weatherItem.setTemperature(new DecimalFormat("##.##").format(tempInCelsius));
                        weatherItems.add(weatherItem);
                    }
                    //set data ke adapter
                    adapter.setmData(weatherItems);
                    showLoading(false);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());

            }
        });

    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}