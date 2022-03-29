package com.example.trackercovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackercovid_19.Adapter.DistrictListAdapter;
import com.example.trackercovid_19.Adapter.StateAdapter;
import com.example.trackercovid_19.Model.District;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DistrictWieDataList extends AppCompatActivity {
    ListView listViewDist;
    public static List<District> districtListD = new ArrayList<>();
    District district;
    ArrayList city;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wie_data_list);

        listViewDist = findViewById(R.id.listViewDist);
        city= new ArrayList();








        districtListD.clear();
        fetchData();



    }

    public  void fetchData(){
        String url = "https://data.covid19india.org/state_district_wise.json";
        Intent intent = getIntent();
        String stateName = intent.getStringExtra("stateName");


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(stateName);

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("districtData");
                        Iterator keys = jsonObject2.keys();
                        while (keys.hasNext()){
                            String cityN = (String) keys.next();
                            city.add(cityN);
                            Log.d("City", cityN);

                        }
                        for(int j=0;j<city.size();j++){

                            JSONObject jsonObject3 = jsonObject2.getJSONObject((String) city.get(j));
                            double activeA = jsonObject3.getDouble("active");
                            double recoveredA = jsonObject3.getDouble("recovered");
                            double confirmedA = jsonObject3.getDouble("confirmed");
                            double deathsA = jsonObject3.getDouble("deceased");

                            String activeString = String.format ("%.0f", activeA);
                            String recoveredString = String.format ("%.0f", recoveredA);
                            String confirmedString = String.format ("%.0f", confirmedA);
                            String deathsString = String.format ("%.0f", deathsA);

//                            Log.d("DList","a="+activeString+" r "+recoveredString+" c "+confirmedString+" d "+deathsString+" n "+city.get(j).toString());

                                district = new District((String) city.get(j), activeString, recoveredString, confirmedString, deathsString);
                                districtListD.add(district);
                            Log.d("DList", districtListD.toString());


                        }












//                           Toast.makeText(MainActivity.this, "Here is active cases of "+stateNew +" of district "+districtNew +"is "+active, Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "size"+districtListD.size());
                    adapter = new DistrictListAdapter(DistrictWieDataList.this, districtListD);
                    listViewDist.setAdapter((ListAdapter) adapter);

//                    districtListD.clear();
                    city.clear();

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

        RequestQueue requestQueue1 = Volley.newRequestQueue(DistrictWieDataList.this);
        requestQueue1.add(stringRequest1);

        Log.d("DistrictSize",""+districtListD.size());
//        districtList.clear();


    }

}