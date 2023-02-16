package com.cp.project_food;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    //Global Variables

    ProgressBar progressBar; SearchView searchView;
    Thread thread; ArrayList<Food_Item> lists;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setting_UI();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                //generating data from api
               generate_data(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
            //non_functional
        });



    }


    private void setData()
    {
        if(lists.size()==0)
        Toast.makeText(this,"No food item available",Toast.LENGTH_LONG).show();
        else
        {
            Log.d("printing", "data");
            //Seting Adapter

            rvMovies.setAdapter(new FoodAdapter(this, lists));

        }
        progressBar.setVisibility(View.GONE);
    }

    private void generate_data(String query)
    {
        OkHttpClient client = new OkHttpClient();
        //query.replace(' ','%20');
            query=query.replaceAll(" ","%20");

          //Building Request
        Request request = new Request.Builder()
                .url("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes&q="+query)
                .get()
                .addHeader("X-RapidAPI-Key", "cbd34c7726mshe58944005b0401fp183ab5jsn147e4be7594a")
                .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                .build();

        //Initialize Responses
        final Response[] response = {null};
         lists=new ArrayList<>();

        thread=new Thread(() -> {
            try {
                response[0] = client.newCall(request).execute();

                // If Successful
                Log.d("gh", String.valueOf(response[0].isSuccessful()));

                String result= Objects.requireNonNull(response[0].body()).string();
                JSONObject myObject = new JSONObject(result);

                //getting result in json array
                JSONArray jsonArray = (JSONArray) myObject.get("results");

                for(int x=0;x<jsonArray.length();x++) {
                    lists.add(new Food_Item(
                            String.valueOf(new JSONObject(jsonArray.get(x).toString()).get("name")),
                            String.valueOf(new JSONObject(jsonArray.get(x).toString()).get("thumbnail_url")),
                            String.valueOf(new JSONObject(jsonArray.get(x).toString()).get("created_at"))));

                    //Setting DATA On UI Thread
                    MainActivity.this.runOnUiThread(this::setData);
                }

                //checking for Unavailability
                if(lists.size()==0)
                    MainActivity.this.runOnUiThread(this::setData);

            } catch (IOException | JSONException e) {
                //Logging error
                Log.d("Display_error",e.getMessage());
            }});

        //Starting the Above Backend(API CALLING Thread)
        thread.start();

    }

    void setting_UI()
    {
        progressBar=findViewById(R.id.progressBar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        progressBar.setVisibility(View.GONE);
        rvMovies=findViewById(R.id.recyle);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
       searchView=findViewById(R.id.search);
        searchView.setQueryHint("Search any food here ");

    }
}

