package com.miniweam.boxeld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.miniweam.boxeld.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding settingsBinding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(settingsBinding.getRoot());

        setSupportActionBar(settingsBinding.toolbar4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsBinding.toolbar4.setNavigationOnClickListener(View -> finish());

        mAuth = FirebaseAuth.getInstance();

        settingsBinding.accountInfoCardView.setOnClickListener(View ->
                startActivity(new Intent(SettingsActivity.this, AccountInfoActivity.class)));

//        settingsBinding.notificationsSettingsBtn.setOnClickListener(View ->
//                startActivity(new Intent(SettingsActivity.this, NotificationsSettingsActivity.class)));

        settingsBinding.appearanceBtn.setOnClickListener(View ->
                showDialog());

        settingsBinding.privacyPolicyBtn.setOnClickListener(View ->
                Toast.makeText(SettingsActivity.this, "Coming Soon...",Toast.LENGTH_SHORT).show());

        settingsBinding.contactUsBtn.setOnClickListener(View ->
                startActivity(new Intent(SettingsActivity.this, ContactUsActivity.class)));

        settingsBinding.aboutUsBtn.setOnClickListener(View ->
                Snackbar.make(settingsBinding.aboutUsBtn, "Coming Soon...", Snackbar.LENGTH_LONG).show());

        settingsBinding.whatNewBtn.setOnClickListener(View ->
                Snackbar.make(settingsBinding.whatNewBtn, "Coming Soon...", Snackbar.LENGTH_LONG).show());

        settingsBinding.termsOfServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(settingsBinding.termsOfServiceBtn, "Coming Soon...",
                        Snackbar.LENGTH_SHORT);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(settingsBinding.termsOfServiceBtn, "OK Pressed", Snackbar.LENGTH_LONG).show();
                    }
                });
                snackbar.show();
            }
        });

        settingsBinding.logoutBtn.setOnClickListener(View -> {
            mAuth.signOut();
            startActivity(new Intent(SettingsActivity.this, FirstActivity.class));
        });
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_appearance);

        RadioButton deviceSettingsRadioButton = dialog.findViewById(R.id.device_settings_radio_btn);
        RadioButton lightRadioButton = dialog.findViewById(R.id.light_radio_btn);
        RadioButton darkRadioButton = dialog.findViewById(R.id.dark_radio_btn);

        deviceSettingsRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = ((RadioButton) compoundButton). isChecked();
                if (checked)
                    //Device settings mode clicked
                    displayToast(getString(R.string.device_settings_mode_clicked));
            }
        });

        lightRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = ((RadioButton) compoundButton). isChecked();
                if (checked)
                    //Light mode clicked
                    displayToast(getString(R.string.light_mode_clicked));
            }
        });

        darkRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = ((RadioButton) compoundButton). isChecked();
                if (checked)
                    //Dark mode clicked
                    displayToast(getString(R.string.dark_mode_clicked));
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void displayToast (String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

