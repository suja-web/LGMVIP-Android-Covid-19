package com.example.trackercovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DistrictDetails extends AppCompatActivity {

    TextView districtName, activeDistrict, recoveredDistrict, confirmedDistrict, deathsDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_details);

        districtName = findViewById(R.id.districtName);
        activeDistrict = findViewById(R.id.activeDistrict);
        recoveredDistrict = findViewById(R.id.recoveredDistrict);
        confirmedDistrict = findViewById(R.id.confirmedDistrict);
        deathsDistrict = findViewById(R.id.deceasedDistrict);

        Intent intent = getIntent();

        String districtNameD = intent.getStringExtra("districtName");
        String active = intent.getStringExtra("activeDistrict");
        String confirmed = intent.getStringExtra("confirmedDistrict");
        String recovered = intent.getStringExtra("recoveredDistrict");
        String deaths = intent.getStringExtra("deathsDistrict");

        Log.d("Details","a "+active+" c "+confirmed);


        districtName.setText(districtNameD);
        activeDistrict.setText(active);
        recoveredDistrict.setText(recovered);
        confirmedDistrict.setText(confirmed);
        deathsDistrict.setText(deaths);

        double activeG = Double.parseDouble(active);
        double recoveredG = Double.parseDouble(recovered);
        double confirmedG = Double.parseDouble(confirmed);
        double deathsG = Double.parseDouble(deaths);


        PieChart mPieChart = (PieChart) findViewById(R.id.piechartDistrict);
        mPieChart.clearChart();

        mPieChart.addPieSlice(new PieModel("Active", (float) activeG, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Confirmed", (float) confirmedG, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Recovered", (float) recoveredG, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Deceased", (float)deathsG, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();
    }
}