package com.example.trackercovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String url;
    TextView textView2, textView3, textView4, textView5;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = findViewById(R.id.active);
        textView3 = findViewById(R.id.recovered);
        textView4 = findViewById(R.id.confirmed);
        textView5 = findViewById(R.id.deceased);
        button =  findViewById(R.id.button);

        Intent intent = getIntent();

        double active = intent.getDoubleExtra("activeI", 0);
        Log.d("Active",""+active);
        double confirmed = intent.getDoubleExtra("confirmedI", 0);
        double recovered = intent.getDoubleExtra("recoveredI", 0);
        double deaths = intent.getDoubleExtra("deathsI", 0);
        ArrayList state = intent.getStringArrayListExtra("state");

        String activeString = String.format ("%.0f", active);
        String recoveredString = String.format ("%.0f", recovered);
        String confirmedString = String.format ("%.0f", confirmed);
        String deathsString = String.format ("%.0f", deaths);


        textView2.setText(activeString);
        textView3.setText(recoveredString);
        textView4.setText(confirmedString);
        textView5.setText(deathsString);

        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        mPieChart.clearChart();

        mPieChart.addPieSlice(new PieModel("Active", (float) active, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Confirmed", (float) confirmed, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Recovered", (float) recovered, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Deceased", (float)deaths, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, StateWiseData.class);
                intent1.putStringArrayListExtra("state", state);
                startActivity(intent1);

            }
        });




    }

}