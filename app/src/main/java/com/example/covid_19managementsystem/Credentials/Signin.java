package com.example.covid_19managementsystem.Credentials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19managementsystem.Covid19DetailsComponents.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.example.covid_19managementsystem.R;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signin extends AppCompatActivity {

    EditText name, email, password;
    CheckBox c1;
    TextView signUp;

    ProgressDialog progressDialog;

    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;

    ImageButton google;
    LoginButton facebook;
    Button login;

    FirebaseAuth firebaseAuth, mAuth;

    private final static int RC_SIGN_IN= 123;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog= new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();

        mAuth= FirebaseAuth.getInstance();

        mFirebaseAuth= FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        createRequest();

        name= (EditText) findViewById(R.id.name_id);
        email= (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password_id);

        signUp= (TextView) findViewById(R.id.signup_id);

        login= (Button) findViewById(R.id.login_id);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        c1= (CheckBox) findViewById(R.id.check_box_id);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, signup.class));
            }
        });

        google= (ImageButton) findViewById(R.id.google_login_btn_id);
        facebook= (LoginButton) findViewById(R.id.facebook_login_btn_id);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

      //  facebook.setReadPermissions("email", "public-profile");

        mCallbackManager= CallbackManager.Factory.create();
        facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("From Facebook Callback Login failed due to: " + error.getMessage().toString());
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("914560277921-cs0ip6f520e1nvekh7pnukor0l23bvui.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
    }

    private void signIn() {
        Intent signInIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                           // FirebaseUser user= firebaseAuth.getCurrentUser();

                        }
                        else {
                            Toast.makeText(Signin.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void Login() {
        String getEmail= email.getText().toString().trim();
        String getPassword= password.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            email.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }
    /*    if (getEmail.equals("pogo.aman1234@gmail.com") && getPassword.equals("Im@12345678")) {
            startActivity(new Intent(SignIn.this, Admin.class));
            finish();
        }   */
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signin.this, "SignIn Successfull!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }
            }
        });
        }


    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signin.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    FirebaseUser user= mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    Toast.makeText(Signin.this, "Login Failed! " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("From Firebase Login failed due to: " + task.getException().toString());
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        String getProfilePic= "", getProfileName= "";
        if (user!=null) {
            if (user.getPhotoUrl()!=null) {
                getProfilePic= user.getPhotoUrl().toString();
            }
            if (user.getDisplayName()!=null) {
                getProfileName= user.getDisplayName().toLowerCase();
            }
            Toast.makeText(this, "username is: " + getProfileName + "\tuserprofilepic: " + getProfilePic, Toast.LENGTH_SHORT).show();
            System.out.println("username is: " + getProfileName + "\tuserprofilepic: " + getProfilePic);
            Intent sendData= new Intent(Signin.this, MainActivity.class);
            sendData.putExtra("pic", getProfilePic);
            sendData.putExtra("name", getProfileName);
            startActivity(sendData);

            finish();
        }
    }
}