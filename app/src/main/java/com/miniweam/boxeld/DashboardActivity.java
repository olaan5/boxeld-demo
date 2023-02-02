package com.miniweam.boxeld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    CardView accountInfoCardView;

    TextView username, email;
    Button yourSpaceBtn, analyticsBtn, manageAudiosBtn, boxeldPremiumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.toolbar7);

        username = findViewById(R.id.username_tv);
        email = findViewById(R.id.email_tv);

        AppCompatActivity appCompatActivity = (AppCompatActivity) DashboardActivity.this;
        appCompatActivity.setSupportActionBar(toolbar);

        accountInfoCardView = findViewById(R.id.account_info_cardView);
        accountInfoCardView.setOnClickListener(View ->
                startActivity(new Intent(DashboardActivity.this, AccountInfoActivity.class)));

//        yourSpaceBtn = findViewById(R.id.your_space_btn);
//        yourSpaceBtn.setOnClickListener(View -> {
//            // in this line of code, we check if user already have a space or not
//            Toast.makeText(DashboardActivity.this, "Coming Soon...", Toast.LENGTH_LONG).show();
//        });
//
//        analyticsBtn = findViewById(R.id.analytics_btn);
//        analyticsBtn.setOnClickListener(View ->
//                Toast.makeText(DashboardActivity.this, "Coming Soon...", Toast.LENGTH_LONG).show());
//
//        boxeldPremiumBtn = findViewById(R.id.boxeld_premium_btn);
//        boxeldPremiumBtn.setOnClickListener(View ->
//                startActivity(new Intent(DashboardActivity.this, BoxeldPremiumActivity.class)));
    }

//    private void showDialog() {
//        Dialog dialog = new Dialog(DashboardActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.space_dialog);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.show();
//    }
}