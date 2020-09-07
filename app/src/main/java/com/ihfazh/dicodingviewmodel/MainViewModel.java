package com.ihfazh.dicodingviewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<WeatherItem>> listWeathers = new MutableLiveData<>();

    public MutableLiveData<ArrayList<WeatherItem>> getListWeathers() {
        return listWeathers;
    }

    public void setListWeathers(MutableLiveData<ArrayList<WeatherItem>> listWeathers) {
        this.listWeathers = listWeathers;
    }

    public void setWeather(String city) {
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
                    listWeathers.postValue(weatherItems);
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
}
