package com.example.covid_19managementsystem.Covid19DetailsComponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.covid_19managementsystem.Adapter.DisplayAdapter;
import com.example.covid_19managementsystem.Model.DisplayPatientDetails;
import com.example.covid_19managementsystem.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ViewData extends AppCompatActivity {

    Map<String, Set<String>> mapIdToDetails;

    ArrayList<DisplayPatientDetails> list1;

    RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Intent getData= getIntent();
        mapIdToDetails= (Map<String, Set<String>>) getData.getSerializableExtra("id");

        list1= new ArrayList<>();

        recyclerView1= (RecyclerView) findViewById(R.id.rec_id);

        for (Map.Entry<String, Set<String>> e1: mapIdToDetails.entrySet()) {
            ArrayList<String> list= new ArrayList<>();
            list.addAll(e1.getValue());

            populateData(list);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewData.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        DisplayAdapter displayAdapter= new DisplayAdapter(ViewData.this, list1);

        recyclerView1.setAdapter(displayAdapter);
    }

    private void populateData(ArrayList<String> list) {
        System.out.println("From ViewData populateData() list is: " + list);

        DisplayPatientDetails displaypatientDetails= new DisplayPatientDetails(list.get(2), list.get(0), list.get(1), "");
        list1.add(displaypatientDetails);
    }
}