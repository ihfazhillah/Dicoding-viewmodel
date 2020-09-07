package com.ihfazh.dicodingviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
    private MainViewModel mainViewModel;

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

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtCity.getText().toString();

                if (TextUtils.isEmpty(city)) return;

                showLoading(true);
                mainViewModel.setWeather(city);
            }
        });

        mainViewModel.getListWeathers().observe(this, new Observer<ArrayList<WeatherItem>>() {
            @Override
            public void onChanged(ArrayList<WeatherItem> weatherItems) {
                if (weatherItems != null){
                    adapter.setmData(weatherItems);
                    showLoading(false);
                }
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