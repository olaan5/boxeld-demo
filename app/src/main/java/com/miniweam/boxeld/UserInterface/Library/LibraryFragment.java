package com.miniweam.boxeld.UserInterface.Library;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.miniweam.boxeld.R;
import com.miniweam.boxeld.RecordAudioActivity;
import com.miniweam.boxeld.UploadActivity;

public class LibraryFragment extends Fragment {

    Toolbar toolbar;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        toolbar = view.findViewById(R.id.toolbar6);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.library_menu_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id  == R.id.action_offline) {
            Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
        }

        if (id  == R.id.action_menu) {
            menuDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.menu_bottom_sheet);

        AppCompatImageButton cancelClearAib = dialog.findViewById(R.id.cancel_clear_aib);
        AppCompatButton importAudio_Acb = dialog.findViewById(R.id.import_audio_acb);
        AppCompatButton recordAudio_Acb = dialog.findViewById(R.id.record_audio_acb);

        cancelClearAib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        importAudio_Acb.setOnClickListener(View ->
                startActivity(new Intent(getActivity(), UploadActivity.class)));

        recordAudio_Acb.setOnClickListener(View ->
                startActivity(new Intent(getActivity(), RecordAudioActivity.class)));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}