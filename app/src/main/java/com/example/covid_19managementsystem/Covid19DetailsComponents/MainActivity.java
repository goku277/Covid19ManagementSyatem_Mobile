package com.example.covid_19managementsystem.Covid19DetailsComponents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19managementsystem.Credentials.Signin;
import com.example.covid_19managementsystem.CustomAlert.AddPatient;
import com.example.covid_19managementsystem.Model.DisplayPatientDetails;
import com.example.covid_19managementsystem.Model.DumpPatientDetails;
import com.example.covid_19managementsystem.Model.PatientDetails;
import com.example.covid_19managementsystem.Model.SavePatientDetails;
import com.example.covid_19managementsystem.R;
import com.example.covid_19managementsystem.Utility.CheckDetails;
import com.example.covid_19managementsystem.Utility.Details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddPatient.Doctor_profile_Listener {

    ImageView add, delete, update, logout;

    TextView add_text, delete_text, update_text, logout_text;

    PatientDetails pd;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    ArrayList<String> getPatientList;

    Details details;

    CheckDetails checkDetails;

    Map<String, Set<String>> mapIdToDetails;

    Map<String, Set<String>> mapIdToCheckedDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd= new PatientDetails();

        getPatientList= new ArrayList<>();

        database1= FirebaseDatabase.getInstance();

        details= new Details();

        checkDetails= new CheckDetails();

        mapIdToDetails= new LinkedHashMap<>();

        mapIdToCheckedDetails= new LinkedHashMap<>();

        Toast.makeText(this, "Welcome to the MainActivity", Toast.LENGTH_SHORT).show();

        add= (ImageView) findViewById(R.id.image_add_id);
        add_text= (TextView) findViewById(R.id.add_patient_id);

        delete= (ImageView) findViewById(R.id.delete_image_id);
        delete_text= (TextView) findViewById(R.id.delete_patient_id);

        update= (ImageView) findViewById(R.id.update_img_id);
        update_text= (TextView) findViewById(R.id.update_patient_id);

        logout= (ImageView) findViewById(R.id.logout_img_id);
        logout_text= (TextView) findViewById(R.id.logout_text_id);

        add.setOnClickListener(this);
        add_text.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_text.setOnClickListener(this);

        update.setOnClickListener(this);
        update_text.setOnClickListener(this);

        logout.setOnClickListener(this);
        logout_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add_id:
                addPatient();
                break;
            case R.id.add_patient_id:
                addPatient();
                break;
            case R.id.delete_image_id:
                deletePatient();
                break;
            case R.id.delete_patient_id:
                deletePatient();
                break;
            case R.id.update_img_id:
                updatePatient();
                break;
            case R.id.update_patient_id:
                updatePatient();
                break;
            case R.id.logout_img_id:
                logout();
                break;
            case R.id.logout_text_id:
                logout();
                break;
        }
    }

    private void logout() {
        startActivity(new Intent(MainActivity.this, Signin.class));
        finish();
    }

    private void deletePatient() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload Patient Details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SavePatientDetails retrievePatientDetails = new SavePatientDetails((String) userData.get("age"), (String) userData.get("id"), (String) userData.get("name"), (String) userData.get("status"));

                            getPatientList.add(retrievePatientDetails.getAge());
                            getPatientList.add(retrievePatientDetails.getName());
                            getPatientList.add(retrievePatientDetails.getId());
                            getPatientList.add(retrievePatientDetails.getStatus());

                            System.out.println("From MainActivity getPatientList is: " + getPatientList);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    String val1 = "";

                    for (String s : getPatientList) {
                        val1 += s + " ";
                    }
                    System.out.println("From MainActivity updatePatient() val1 is: " + val1);

                  //  mapIdToDetails = details.init(val1);

                    mapIdToCheckedDetails= checkDetails.init(val1);

                    System.out.println("From MainActivity mapIdToCheckedDetails: " + mapIdToCheckedDetails);

                    Intent sendData = new Intent(MainActivity.this, DeletePatient.class);
                    sendData.putExtra("id", (Serializable) mapIdToCheckedDetails);
                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void updatePatient() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload Patient Details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            DumpPatientDetails retrievePatientDetails = new DumpPatientDetails((String) userData.get("age"), (String) userData.get("id"), (String) userData.get("name"), (String) userData.get("status"));

                            getPatientList.add(retrievePatientDetails.getAge());
                            getPatientList.add(retrievePatientDetails.getName());
                            getPatientList.add(retrievePatientDetails.getId());
                            getPatientList.add(retrievePatientDetails.getStatus());

                            System.out.println("From MainActivity getPatientList is: " + getPatientList);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    String val1= "";

                    for (String s: getPatientList) {
                        val1+= s + " ";
                    }
                    System.out.println("From MainActivity updatePatient() val1 is: " + val1);

                    mapIdToDetails= details.init(val1);

                    System.out.println("From MainActivity mapIdToDetails: " + mapIdToDetails);

                    Intent sendData= new Intent(MainActivity.this, UpdatePatient.class);
                    sendData.putExtra("id", (Serializable) mapIdToDetails);
                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });
    }

    private void addPatient() {
        AlertDialog.Builder a11= new AlertDialog.Builder(MainActivity.this);
        a11.setCancelable(false);
        a11.setTitle("Alert");
        a11.setMessage("Choose between view data and add data");
        a11.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ViewData();
            }
        });
        a11.setNegativeButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddPatient ad= new AddPatient();
                ad.show(getSupportFragmentManager(), "Add Patient");
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    private void ViewData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload Patient Details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            DisplayPatientDetails retrievePatientDetails = new DisplayPatientDetails((String) userData.get("age"), (String) userData.get("id"), (String) userData.get("name"), (String) userData.get("status"));

                            getPatientList.add(retrievePatientDetails.getAge());
                            getPatientList.add(retrievePatientDetails.getName());
                            getPatientList.add(retrievePatientDetails.getId());
                            getPatientList.add(retrievePatientDetails.getStatus());

                            System.out.println("From MainActivity getPatientList is: " + getPatientList);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    String val1= "";

                    for (String s: getPatientList) {
                        val1+= s + " ";
                    }
                    System.out.println("From MainActivity updatePatient() val1 is: " + val1);

                    mapIdToDetails= details.init(val1);

                    System.out.println("From MainActivity mapIdToDetails: " + mapIdToDetails);

                    Intent sendData= new Intent(MainActivity.this, ViewData.class);
                    sendData.putExtra("id", (Serializable) mapIdToDetails);
                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });
    }

    @Override
    public void applyFields(String name1, String specialization1, String age1) {
        System.out.println("From MainActivity name1 is: " + name1 + "\tage: " + age1 + "\tCovid status is: " + specialization1);

        uploadData(name1, specialization1, age1);
    }

    private void uploadData(String name1, String specialization1, String age1) {
        String Name= "Name: " + name1;
        String Age= "Age: " + age1;
        String Status= "Status: " + specialization1;
        pd.setName(Name);
        pd.setAge(Age);
        pd.setStatus(Status);

        String id= "Id: " + UUID.randomUUID().toString();

        pd.setId(id);

        ref1= database1.getInstance().getReference().child("Upload Patient Details");
        ref1.child(id).setValue(pd);

        Toast.makeText(this, "Patient details uploaded successfully", Toast.LENGTH_SHORT).show();
    }
}