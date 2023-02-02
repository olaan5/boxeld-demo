package com.miniweam.boxeld.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.miniweam.boxeld.Models.Users;
import com.miniweam.boxeld.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        setSupportActionBar(registerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerBinding.toolbar.setNavigationOnClickListener(View -> finish());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account.");

        registerBinding.registerBtn.setOnClickListener(View -> {
                    if(!registerBinding.emailTiet.getText().toString().isEmpty() &&
                            !registerBinding.usernameTiet.getText().toString().isEmpty() &&
                            !registerBinding.passwordTiet.getText().toString().isEmpty()) {
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(registerBinding.emailTiet.getText().toString(),
                                registerBinding.passwordTiet.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Users user = new Users(registerBinding.emailTiet.getText().toString(),
                                            registerBinding.usernameTiet.getText().toString(),
                                            registerBinding.passwordTiet.getText().toString());
                                    //to access Uid, first create a string type
                                    String id = task.getResult().getUser().getUid();
                                    //create a table
                                    firebaseDatabase.getReference().child("Boxeld Users").child(id).setValue(user);

                                    //creating acc. is done
                                    Toast.makeText(RegisterActivity.this, "Registration successful, " +
                                            "kindly check email to verify", Toast.LENGTH_LONG).show();
                                    sendEmailVerification();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter Credential",
                                Toast.LENGTH_SHORT).show();
                    }
        });

        registerBinding.loginBtn.setOnClickListener(View ->
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RegisterActivity.this, "Verification email sent",
                            Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "Failed to send verification email",
                    Toast.LENGTH_LONG).show();
        }
    }
}