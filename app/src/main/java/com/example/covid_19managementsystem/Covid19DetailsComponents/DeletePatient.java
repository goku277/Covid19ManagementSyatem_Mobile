package com.example.covid_19managementsystem.Covid19DetailsComponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covid_19managementsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class DeletePatient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spin;
    ArrayList<String> list= new ArrayList<>();

    Map<String, Set<String>> mapIdToDetails;

    String specialization="";

    FirebaseDatabase database1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_patient);

        Intent getData= getIntent();
        mapIdToDetails= (Map<String, Set<String>>) getData.getSerializableExtra("id");

        database1= FirebaseDatabase.getInstance();

        spin= (Spinner) findViewById(R.id.spinner_id);

        list.add("Choose among the id you want to delete");

        for (Map.Entry<String, Set<String>> e1: mapIdToDetails.entrySet()) {
            list.add(e1.getKey());
        }

        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(DeletePatient.this,android.R.layout.simple_spinner_item, list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!list.get(position).equals("Choose among the id you want to delete")) {
            Toast.makeText(DeletePatient.this, list.get(position), Toast.LENGTH_LONG).show();
            specialization = list.get(position);

            delete(specialization);
        }
        else Toast.makeText(DeletePatient.this, "Please choose a valid options", Toast.LENGTH_SHORT).show();
    }

    private void delete(String specialization) {
        String key= "Id: " + specialization;
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Upload Patient Details").child(key);
        ref.removeValue();

        Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();

        recreate();

        startActivity(new Intent(DeletePatient.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DeletePatient.this, MainActivity.class));
        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}