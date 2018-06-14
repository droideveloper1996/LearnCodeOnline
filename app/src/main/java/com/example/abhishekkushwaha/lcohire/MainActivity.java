package com.example.abhishekkushwaha.lcohire;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    ArrayList<InterView> arrayList;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    ImageView connection_status;
    public static final String INTENT_URI = "https://courses.learncodeonline.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection_status = (ImageView) findViewById(R.id.connection_status);

        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.advertisement);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new InterViewRecyclerAdapter(this, arrayList));
        arrayList = new ArrayList<>();
        coordinatorLayout = findViewById(R.id.coordinator);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeIntent();

                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Taking You to Learn Code Online", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        getInterviewQuestions();
        if (!isNetworkConnected()) {
            connection_status.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        if (isNetworkConnected()) {
            connection_status.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        }

    }


    void getInterviewQuestions() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ConstantUtils.API_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("questions");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject setObject = jsonArray.getJSONObject(i);
                                String ques = setObject.optString("question");
                                String answer = setObject.optString("Answer");
                                InterView interView = new InterView();
                                interView.setmAnswer(answer);
                                interView.setmQuestion(ques);
                                arrayList.add(interView);
                            }
                            InterViewRecyclerAdapter interViewRecyclerAdapter = new InterViewRecyclerAdapter(MainActivity.this, arrayList);
                            recyclerView.setAdapter(interViewRecyclerAdapter);
                            if (interViewRecyclerAdapter.getItemCount() > 0) {
                                progressBar.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Something Went Wrong", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        queue.add(stringRequest);
    }

    void makeIntent() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(INTENT_URI));
        startActivity(i);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onBackPressed() {

    }
}
