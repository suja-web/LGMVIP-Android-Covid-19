package com.example.trackercovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StartActivity extends AppCompatActivity {
    ImageView buttonImg;
    ArrayList city;
    ArrayList state;
    double active, recovered, confirmed, deaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        buttonImg = findViewById(R.id.startButton);
        city = new ArrayList();
        state = new ArrayList();

        String url = "https://data.covid19india.org/state_district_wise.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("TAG", response);
                    Iterator keys = jsonObject.keys();
                    while (keys.hasNext()){
                        String stateNew = (String) keys.next();
                        state.add(stateNew);
                        Log.d("Atr", stateNew);


                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(StartActivity.this);
        requestQueue.add(stringRequest);
        fetchData();


        
         buttonImg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(StartActivity.this, MainActivity.class);
                 intent.putExtra("activeI", active);
                 intent.putExtra("recoveredI", recovered);
                 intent.putExtra("confirmedI", confirmed);
                 intent.putExtra("deathsI", deaths);
                 intent.putStringArrayListExtra("state", state);
                 Log.d("Value", active + " r " + recovered);
                 startActivity(intent);

             }
         });
     }



    public  void fetchData(){
        active=0;
        recovered=0;
        confirmed=0;
        deaths=0;
        String url = "https://data.covid19india.org/state_district_wise.json";


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
//                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    for(int i=0;i<state.size();i++){
                        JSONObject jsonObject1 = jsonObject.getJSONObject((String) state.get(i));

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("districtData");
                        Iterator keys = jsonObject2.keys();
                        while (keys.hasNext()){
                            String cityN = (String) keys.next();
                            city.add(cityN);
                            Log.d("City", cityN);

                        }
                        for(int j=0;j<city.size();j++){

                            JSONObject jsonObject3 = jsonObject2.getJSONObject((String) city.get(j));
                            float activeA = jsonObject3.getInt("active");
                            float recoveredA = jsonObject3.getInt("recovered");
                            float confirmedA = jsonObject3.getInt("confirmed");
                            float deathsA = jsonObject3.getInt("deceased");
                            active=active+activeA;
                            recovered=recovered+recoveredA;
                            deaths=deaths+deathsA;
                            confirmed=confirmed+confirmedA;

                        }
                        city.clear();
                    }



//                           Toast.makeText(MainActivity.this, "Here is active cases of "+stateNew +" of district "+districtNew +"is "+active, Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue1 = Volley.newRequestQueue(StartActivity.this);
        requestQueue1.add(stringRequest1);


    }

}