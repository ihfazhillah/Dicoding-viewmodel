package com.ihfazh.dicodingviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}