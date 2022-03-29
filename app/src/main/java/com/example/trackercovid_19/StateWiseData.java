package com.example.trackercovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackercovid_19.Adapter.StateAdapter;
import com.example.trackercovid_19.Model.District;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateWiseData extends AppCompatActivity {
    ListView listView;
    public static List<District> districtList = new ArrayList<>();
    District district;
    ArrayList city;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_data);
        listView = findViewById(R.id.listView);
        city= new ArrayList();


        Intent intent = getIntent();
        ArrayList state = intent.getStringArrayListExtra("state");
//        Log.d("state", state.toString());


        fetchData();

//        Toast.makeText(DistrictWiseData.this, "Hello size is "+districtList.size(), Toast.LENGTH_SHORT).show();


    }

    public  void fetchData(){
        String url = "https://data.covid19india.org/state_district_wise.json";
        Intent intent = getIntent();
        ArrayList state = intent.getStringArrayListExtra("state");

//        Toast.makeText(DistrictWiseData.this, "Hello size is "+state.size(), Toast.LENGTH_SHORT).show();

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
                        double active=0,recovered=0,confirmed=0,deaths=0;
                        for(int j=0;j<city.size();j++){

                            JSONObject jsonObject3 = jsonObject2.getJSONObject((String) city.get(j));
                            double activeA = jsonObject3.getDouble("active");
                            double recoveredA = jsonObject3.getDouble("recovered");
                            double confirmedA = jsonObject3.getDouble("confirmed");
                            double deathsA = jsonObject3.getDouble("deceased");

                            active=active+activeA;
                            recovered=recovered+recoveredA;
                            confirmed=confirmed+confirmedA;
                            deaths=deaths+deathsA;


                        }

                        String activeString = String.format ("%.0f", active);
                        String recoveredString = String.format ("%.0f", recovered);
                        String confirmedString = String.format ("%.0f", confirmed);
                        String deathsString = String.format ("%.0f", deaths);
                        Log.d("District","a="+active+" r "+recovered+" c "+confirmed+" d "+deaths+" n "+state.get(i).toString());

                        if(!state.get(i).toString().equals("State Unassigned")) {
                            district = new District((String) state.get(i), activeString, recoveredString, confirmedString, deathsString);
                            districtList.add(district);
                        }
                        city.clear();
                    }



//                           Toast.makeText(MainActivity.this, "Here is active cases of "+stateNew +" of district "+districtNew +"is "+active, Toast.LENGTH_SHORT).show();

                    adapter = new StateAdapter(StateWiseData.this, districtList);
                    listView.setAdapter((ListAdapter) adapter);

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

        RequestQueue requestQueue1 = Volley.newRequestQueue(StateWiseData.this);
        requestQueue1.add(stringRequest1);

        Log.d("District",""+districtList.size());


    }
}