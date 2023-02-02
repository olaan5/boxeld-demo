package com.miniweam.boxeld;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class RecordAudioActivity extends AppCompatActivity {

    //initializing all variables
    private ImageButton playIb, recordIb, pauseIb, stopIb;
    private TextView recStatusTv;
    //creating a variable for media recorder object class
    private MediaRecorder mRecorder;
    //creating a variable for media player class
    private MediaPlayer mPlayer;
    //string variable is created for storing file name
    private static String mFileName = null;
    // private static String mFileName fileName;
    //boolean isRecording;
    //File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BoxeldAudioRecord";)
    Chronometer timeRec;
    //constant for storing audio permission
    private static final int REQUEST_AUDIO_PERMISSION_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);

        //initializing all variables with their layout items
        recordIb = findViewById(R.id.record_ib);
        stopIb = findViewById(R.id.stop_ib);
        playIb = findViewById(R.id.play_ib);
        pauseIb = findViewById(R.id.pause_ib);
        recStatusTv = findViewById(R.id.rec_status_tv);
        timeRec = findViewById(R.id.time_rec);
        stopIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        playIb.setBackgroundColor(getResources().getColor(R.color.teal_700));
        pauseIb.setBackgroundColor(getResources().getColor(R.color.sienna));

//        SimpleDateFormat format = new SimpleDateFormat("ddMMyy_ssmmHH", Locale.getDefault());
//        String date = format.format(new Date());
//
//        mFileName = path + "/recording_" + date + ".amr";
//        if (!path.exists()) {
//            path.mkdirs();
//        }

        //isRecording = false;
        recordIb.setOnClickListener(View ->
                //start recording method will start recording of audio
                startRecording());
        stopIb.setOnClickListener(View ->
                //pause recording method will pause recording of audio
                pauseRecording());
                //stopRecording());
        playIb.setOnClickListener(View ->
                //play audio method will play the audio which we have recorded
                playAudio()
        );
        pauseIb.setOnClickListener(v ->
                //pause play method will pause the play of audio
                pausePlaying()
        );
    }

    private void startRecording() {
        //check permission method is used to check that the user has granted permission to record and store audio
        if (CheckPermissions()) {
            //setBackgroundColor method will change the background color of TextView
            recordIb.setBackgroundColor(getResources().getColor(R.color.sienna));
            stopIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
            playIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
            pauseIb.setBackgroundColor(getResources().getColor(R.color.teal_200));

            //we are here initializing our filename variable with the path of the recorded audio file
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Boxeld Audio Recorder";

            //below method is used to initialize the media recorder class
            mRecorder = new MediaRecorder();

            //below method is used to set the audio source which we are using a mic
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //below method is used to set the output format of the audio
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);

            //below method is used to set the audio encoder for our recorded audio
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            //below method is used to set the output file location for our recorded audio
            mRecorder.setOutputFile(mFileName);

            try {
                //below method will prepare our recorder class
                mRecorder.prepare();
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e("TAG", "prepare() failed");
            }
            //start method will start the audio recording
            mRecorder.start();
            timeRec.setBase(SystemClock.elapsedRealtime());
            timeRec.start();
            recordIb.setImageResource(R.drawable.ic_stop);
            recStatusTv.setText(getResources().getText(R.string.recording_started));
        } else {
            //if audio recording permissions are not granted by user, below method will ask for
            // runtime permission for mic and storage
            RequestPermissions();
            //pauseRecording();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called when user will grant the permission for audio recording
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission granted",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private boolean CheckPermissions() {
        //this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        //this method is used to request the permission for audio recording and storage
        ActivityCompat.requestPermissions(RecordAudioActivity.this, new String[]
                {RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    private void playAudio() {
        recordIb.setBackgroundColor(getResources().getColor(R.color.sienna));
        stopIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        playIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        pauseIb.setBackgroundColor(getResources().getColor(R.color.teal_200));

        // for playing our recorded audio we are using media player class
        mPlayer = new MediaPlayer();

        try {
            // below method is used to set the data source which will be our file name
            mPlayer.setDataSource(mFileName);

            // below method will prepare our media player

            mPlayer.prepare();

            // below method will start our media player.

            mPlayer.start();

            recStatusTv.setText(getResources().getText(R.string.recording_started_playing));

        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    private void pauseRecording() {
        recordIb.setBackgroundColor(getResources().getColor(R.color.sienna));
        stopIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        playIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        pauseIb.setBackgroundColor(getResources().getColor(R.color.teal_200));

        // below method will stop the audio recording.

        mRecorder.stop();

        // below method will release the media recorder class.

        mRecorder.release();

        mRecorder = null;

        recStatusTv.setText(getResources().getText(R.string.recording_stopped));
    }

    private void pausePlaying() {
        // this method will release the media player class and pause the playing of our recorded audio.

        mPlayer.release();

        mPlayer = null;

        recordIb.setBackgroundColor(getResources().getColor(R.color.sienna));
        stopIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        playIb.setBackgroundColor(getResources().getColor(R.color.teal_200));
        pauseIb.setBackgroundColor(getResources().getColor(R.color.teal_200));

        recStatusTv.setText(getResources().getText(R.string.recording_play_stopped));
    }
}