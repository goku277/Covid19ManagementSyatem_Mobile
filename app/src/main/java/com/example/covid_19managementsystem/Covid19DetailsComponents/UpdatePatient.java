package com.example.covid_19managementsystem.Covid19DetailsComponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covid_19managementsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class UpdatePatient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Map<String, Set<String>> mapIdToDetails;

    Spinner spin;

    EditText name, age, status;

    Button update;

    String covidStatus[]= {"Choose between the three", "+ve", "-ve", "Discharge"};

    ArrayList<String> list= new ArrayList<>();

    String specialization="";

    FirebaseDatabase database1;
    DatabaseReference ref1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        Intent getData= getIntent();
        mapIdToDetails= (Map<String, Set<String>>) getData.getSerializableExtra("id");

        database1= FirebaseDatabase.getInstance();

        System.out.println("From UpdatePatient mapIdToDetails: " + mapIdToDetails);

        spin= (Spinner) findViewById(R.id.spinner_id);

        list.add("Choose among the id you want to update");

        for (Map.Entry<String, Set<String>> e1: mapIdToDetails.entrySet()) {
            list.add(e1.getKey());
        }

        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(UpdatePatient.this,android.R.layout.simple_spinner_item, list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        name= (EditText) findViewById(R.id.name_id);
        age= (EditText) findViewById(R.id.age_id);
        status= (EditText) findViewById(R.id.status_id);

        update= (Button) findViewById(R.id.update_id);

        System.out.println("From UpdatePatient onCreate() is: " + specialization);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        try {
            ref1 = database1.getInstance().getReference("Upload Patient Details");

            String key = "Id: "+specialization;

           // String key1 = universityDetailsList.get(0).replace("universityName:", "").trim() + " " + universityDetailsList.get(2).replace("universityName:", "").replace("universityId:", "").trim();

            ref1.child(key).child("age").setValue("Age: " + age.getText().toString().trim());
            ref1.child(key).child("id").setValue("Id: " + specialization);
            ref1.child(key).child("name").setValue("Name: " + name.getText().toString().trim());
            ref1.child(key).child("status").setValue("Status: " + status.getText().toString().trim());

            Toast.makeText(UpdatePatient.this, "Data updated", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(UpdatePatient.this, MainActivity.class));

        } catch (Exception e) {}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(UpdatePatient.this, MainActivity.class));
        finish();
    }

    private void updateDetails() {
        System.out.println("From UpdatePatient specialization is: " + specialization);
        String s="";
       for (Map.Entry<String, Set<String>> e1: mapIdToDetails.entrySet()) {
           if (e1.getKey().trim().equals(specialization.trim())) {
               System.out.println("e1.getKey()== specialization");
               fillData(e1.getValue());
           }
       }
    }

    private void fillData(Set<String> value) {
        ArrayList<String> list1= new ArrayList<>();
        list1.addAll(value);

        age.setText(list1.get(0).replace("Age:", "").trim());
        status.setText(list1.get(1).replace("Status:", "").trim());
        name.setText(list1.get(2).replace("Name:", "").trim());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!list.get(position).equals("Choose among the id you want to update")) {
            Toast.makeText(UpdatePatient.this, list.get(position), Toast.LENGTH_LONG).show();
            specialization = list.get(position);

            updateDetails();
        }
        else Toast.makeText(UpdatePatient.this, "Please choose a valid options", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}