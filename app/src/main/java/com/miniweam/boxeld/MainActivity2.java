package com.miniweam.boxeld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.miniweam.boxeld.UserInterface.Explore.ExploreFragment;
import com.miniweam.boxeld.UserInterface.Library.LibraryFragment;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ExploreFragment exploreFragment = new ExploreFragment();
    LibraryFragment libraryFragment = new LibraryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, exploreFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_menu_bottom_sheet_open);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(14);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_explore:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, exploreFragment).commit();
                        return true;
                    case R.id.navigation_library:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, libraryFragment).commit();
                        return true;
                    case R.id.navigation_menu_bottom_sheet_open:
                        menuBottomSheetDialog();
                        return true;
                }
                return false;
            }
        });
    }

    private void menuBottomSheetDialog() {
        final Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.menu_open_bottom_sheet);

        ImageButton spaceDashboardIb = dialog.findViewById(R.id.space_dashboard_ib);
        ImageButton notificationsIb = dialog.findViewById(R.id.notifications_ib);
        ImageButton settingsIb = dialog.findViewById(R.id.settings_ib);
        ImageButton shareIb = dialog.findViewById(R.id.share_ib);
        ImageButton infoIb = dialog.findViewById(R.id.info_ib);
        ImageButton helpIb = dialog.findViewById(R.id.help_ib);

        spaceDashboardIb.setOnClickListener(View ->
                startActivity(new Intent(MainActivity2.this, DashboardActivity.class)));

        notificationsIb.setOnClickListener(View ->
                startActivity(new Intent(MainActivity2.this, NotificationsActivity.class)));

        settingsIb.setOnClickListener(View ->
                startActivity(new Intent(MainActivity2.this, SettingsActivity.class)));

        shareIb.setOnClickListener(View ->
                Toast.makeText(MainActivity2.this, "Share clicked", Toast.LENGTH_SHORT)
                        .show());

        infoIb.setOnClickListener(View ->
                startActivity(new Intent(MainActivity2.this, AppInfoActivity.class)));

        helpIb.setOnClickListener(View ->
                startActivity(new Intent(MainActivity2.this, HelpActivity.class)));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}