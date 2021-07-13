package com.example.covid_19managementsystem.Credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19managementsystem.Covid19DetailsComponents.MainActivity;
import com.example.covid_19managementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    EditText name, email, password, cnfPassword;
    CheckBox c1;
    TextView login;
    Button createAccount;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name= (EditText) findViewById(R.id.name_id);
        email= (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password_id);
        cnfPassword= (EditText) findViewById(R.id.cnf_password);

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);

        c1= (CheckBox) findViewById(R.id.check_box_id);

        login= (TextView) findViewById(R.id.login_id);

        createAccount= (Button) findViewById(R.id.create_btn_id);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, Signin.class));
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        String getEmail= email.getText().toString().trim();
        String getPassword= password.getText().toString().trim();
        String getConfirmPassword= cnfPassword.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (TextUtils.isEmpty(getConfirmPassword)) {
            cnfPassword.setError("Please input this confirm password field!");
            return;
        }
        if (!getPassword.equals(getConfirmPassword)) {
            cnfPassword.setError("Passwords donot match, please try again!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            email.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }
        if (getConfirmPassword.length()< 6) {
            password.setError("Incorrect password! p;ease try again");
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signup.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this, MainActivity.class));
                }
                else {
                    Toast.makeText(signup.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Task failed due to: " + task.getException());
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}