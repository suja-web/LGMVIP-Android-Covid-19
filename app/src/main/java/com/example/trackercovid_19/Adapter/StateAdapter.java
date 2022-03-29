package com.example.trackercovid_19.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trackercovid_19.DistrictWieDataList;
import com.example.trackercovid_19.Model.District;
import com.example.trackercovid_19.R;

import java.util.List;

public class StateAdapter extends ArrayAdapter<District> {
    private Context context;
    private List<District> districtList;


    public StateAdapter(Context context, List<District> districtList) {
        super(context, R.layout.district_model ,districtList);
        this.context = context;
        this.districtList = districtList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.district_model, null,true);


        // In this step we connect the XML with Java File
        TextView districtName = view.findViewById(R.id.districtName);
        TextView activeD = view.findViewById(R.id.activeD);
        TextView recoveredD = view.findViewById(R.id.recoveredD);
        TextView confirmedD = view.findViewById(R.id.confirmedD);
        TextView deathsD = view.findViewById(R.id.deathsD);


        // Adding Data to modellist
        districtName.setText(districtList.get(position).getDistrictName());
        activeD.setText(districtList.get(position).getActive());
        recoveredD.setText(districtList.get(position).getRecovered());
        confirmedD.setText(districtList.get(position).getConfirmed());
        deathsD.setText(districtList.get(position).getDeaths());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DistrictWieDataList.class);
                intent.putExtra("stateName",districtName.getText().toString());
                context.startActivity(intent);
            }
        });

        return view;
    }
}

