package com.magnumnayan.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private Adapter adapter;
    private List<DataModel> list = new ArrayList<>();
    ProgressBar progressBar;
    public static final String URL = "https://api.github.com/search/users?q=saransh&page=";
    private int currentItems, totalItems, scrolledOutItems, pagenumber=1;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.usersRecyclerview);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        progressBar = findViewById(R.id.progressBar);

        fetchData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                scrolledOutItems = manager.findFirstCompletelyVisibleItemPosition();
                totalItems = manager.getItemCount();

                    if (!isLoading) {
                        if ((currentItems + scrolledOutItems) == totalItems) {
                            // list has reached to last element
                            pagenumber++;
                            fetchData();
                        }
                    }
                }

        });

    }

    private void fetchData() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(URL + "" + pagenumber, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                try {
                        JSONObject object = new JSONObject(response);
                        final JSONArray array = object.getJSONArray("items");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject user = array.getJSONObject(i);
                            list.add(new DataModel(
                                    user.getString("avatar_url"),
                                    user.getString("login")
                            ));
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Page no. " + pagenumber , Toast.LENGTH_SHORT).show();
                                adapter = new Adapter(MainActivity.this, list);
                                recyclerView.setAdapter(adapter);
                                manager.scrollToPositionWithOffset((adapter.getItemCount()) - (array.length() + 2), 0);
                                adapter.notifyDataSetChanged();
                                isLoading = false;
                                progressBar.setVisibility(View.GONE);
                            }

                        }, 3000);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON error\n" + e.toString(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internet Error \n" + error.toString(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}