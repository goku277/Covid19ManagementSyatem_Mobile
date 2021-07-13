package com.example.covid_19managementsystem.CustomAlert;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.covid_19managementsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AddPatient extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText name, patient, fees;
    private FloatingActionButton add;
  //  private CircleImageView circleImageView;
    String specialization="";

    private Doctor_profile_Listener listener;

    Spinner spin;

    String covidStatus[]= {"Choose between the three", "+ve", "-ve", "Discharge"};

    String[] doctorOccupation = { "Cardiologist", "Dentist", "Surgeon", "ENT Specialist", "Child Specialist","Urologist", "Veterinarian", "Optometrist",
            "Chiropractor","Dermatologist","Gynaecologist","Herbalist","Paramedic","Psychiatrist"};

    Uri imageUri;

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // createDialog();
        return createDialog();
    }

    private Dialog createDialog() {
        AlertDialog.Builder profileDialog= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.create_patient_layout,null);

        profileDialog.setView(view)
                .setTitle("Create profile")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Name= name.getText().toString().trim();
                        // String Specialization= specialization.getText().toString().trim();
                        String age= patient.getText().toString().trim();
                      //  String Fees= fees.getText().toString().trim();
                        if (age.length() > 10) {
                            if (Integer.parseInt(age) < 1) {
                                patient.setError("Please enter a valid mobile number");
                            }
                        }
                        else {
                            listener.applyFields(Name, specialization, age);
                        }
                    }
                });

        name= (EditText) view.findViewById(R.id.name_id);

        patient= (EditText) view.findViewById(R.id.patient_age_id);

        spin= (Spinner) view.findViewById(R.id.spinner_id);

        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, covidStatus);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

      //  specialization= (EditText) view.findViewById(R.id.specialization_inputfield_id);

        return profileDialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (Doctor_profile_Listener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (!covidStatus[position].equals("Choose between the two")) {
            Toast.makeText(getActivity(), covidStatus[position], Toast.LENGTH_LONG).show();
            specialization = covidStatus[position];
        }
        else Toast.makeText(getActivity(), "Please choose a valid options", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface Doctor_profile_Listener {
        public void applyFields(String name1, String specialization1, String age1);
    }
}