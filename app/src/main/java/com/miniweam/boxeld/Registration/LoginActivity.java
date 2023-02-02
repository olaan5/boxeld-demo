package com.miniweam.boxeld.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.miniweam.boxeld.ForgotPasswordActivity;
import com.miniweam.boxeld.MainActivity2;
import com.miniweam.boxeld.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        setSupportActionBar(loginBinding.toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginBinding.toolbar2.setNavigationOnClickListener(View -> finish());

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait \nValidation in progress.");

        loginBinding.enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginBinding.emailTiet.getText().toString().isEmpty() &&
                        !loginBinding.userPasswordTiet.getText().toString().isEmpty()) {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(loginBinding.emailTiet.getText().toString(),
                            loginBinding.userPasswordTiet.getText().toString()).addOnCompleteListener
                            (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                checkMailVerification();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed!! Enter Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (mAuth.getCurrentUser() !=null) {
            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
        }
        loginBinding.fPasswordTv.setOnClickListener(View ->
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));

        loginBinding.registerTv.setOnClickListener(View ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void checkMailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified()) {
            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
        } else {
            Toast.makeText(LoginActivity.this, "Verify your email first",
                    Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
}