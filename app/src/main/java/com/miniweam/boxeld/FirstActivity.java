package com.miniweam.boxeld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.miniweam.boxeld.Registration.LoginActivity;
import com.miniweam.boxeld.Registration.RegisterActivity;
import com.miniweam.boxeld.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    
    ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.emailRegisterBtn.setOnClickListener(View ->
                startActivity(new Intent(FirstActivity.this, RegisterActivity.class)));

        binding.loginBtn.setOnClickListener(View ->
                startActivity(new Intent(FirstActivity.this, LoginActivity.class)));

        binding.datBtn.setOnClickListener(view -> {
            startActivity(new Intent(FirstActivity.this, MainActivity2.class));
        });
    }
}