package com.example.apnipathshaala.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.common.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button signup;
    Switch sswitch;
    EditText sname, semail, spassword, scpassword;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.signup);
        sname = findViewById(R.id.sname);
        semail = findViewById(R.id.semail);
        spassword = findViewById(R.id.spassword);
        sswitch=findViewById(R.id.showpassword);
        scpassword = findViewById(R.id.cpassword);
        progressbar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        sswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    scpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if(b){
                    spassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if(!b){
                    scpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (!b) {
                    spassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password, cpassword, name;
        name = sname.getText().toString();
        email = semail.getText().toString();
        password = spassword.getText().toString();
        cpassword = scpassword.getText().toString();


        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Name!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(cpassword)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter the password correctly!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        if (password.equals(cpassword)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful! Check your email for verification link",
                                        Toast.LENGTH_LONG)
                                        .show();
                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);

                                // if the user created intent to login activity
                                Intent intent
                                        = new Intent(SignupActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {

                        // Registration failed
                        Toast.makeText(
                                getApplicationContext(),
                                "Registration failed!!"
                                        + " Account with this email already exists",
                                Toast.LENGTH_LONG)
                                .show();

                        // hide the progress bar
                        progressbar.setVisibility(View.GONE);
                    }
                }
            });
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    "Registration failed!!"
                            + " Because the password is incorrect",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}