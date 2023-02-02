package com.miniweam.boxeld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.miniweam.boxeld.Registration.LoginActivity;
import com.miniweam.boxeld.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding forgotPasswordBinding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(forgotPasswordBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(forgotPasswordBinding.toolbar3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forgotPasswordBinding.toolbar3.setNavigationOnClickListener(View -> finish());

        forgotPasswordBinding.recoverPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotPasswordBinding.emailTiet.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kindly enter your email first",
                            Toast.LENGTH_LONG).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Check your Email. " +
                                        "Please check the email address for instructions to reset " +
                                        "your password.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgotPasswordActivity.this,
                                        LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Email is wrong, provide" +
                                        " a valid email used for registration ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}