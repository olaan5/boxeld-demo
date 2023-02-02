package com.miniweam.boxeld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    private Button browseBtn, uploadBtn;
    private ImageView imageView;
    TextView selectAudioTv;
    //Uri indicates where the image will be picked from
    private Uri filePath;
    //Uri indicates where the audio will be picked from
    private Uri AudioUri;
    //request code
    private final int PICK_IMAGE_REQUEST = 22;
    private final int PICK_AUDIO = 8;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //initialise views
        browseBtn = findViewById(R.id.browse_btn);
        uploadBtn = findViewById(R.id.upload_btn);
        imageView = findViewById(R.id.imageView3);
        selectAudioTv = findViewById(R.id.select_audio_tv);

        //get the Firebase storage reference
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();

        //setting onClickListener -- on TextView click to take audio input
        selectAudioTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent audio = new Intent();
                audio.setType("audio/*");
                audio.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO);
            }
        });

        browseBtn.setOnClickListener(View -> selectImage());
        uploadBtn.setOnClickListener(View -> uploadImage());

    }

    private void selectImage() {
        //Defining implicit intent to local storage
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //checking requestCode and resultCode
        //if request code is PICK_IMAGE_REQUEST and result code is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //Get the Uri of data
            filePath = data.getData();
            try {
                //setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                //Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            //Audio is picked in format of URI
            AudioUri = data.getData();
            selectAudioTv.setText("Audio");
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            //code to show progress dialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //defining the child of storageReference
            StorageReference ref = mStorageRef.child("image/*" + UUID.randomUUID().toString());

            //adding listeners on upload or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //image uploaded successfully
                    //dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(UploadActivity.this, "File uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Error, image not uploaded. Handle unsuccessful upload
                    progressDialog.dismiss();
                    Toast.makeText(UploadActivity.this, "Failed " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                //Progress Listener for loading percentage on the dialog box
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });

//            Uri file = Uri.fromFile(new File("path/to/audioFile.mp3"));
//            StorageReference audioRef = mStorageRef.child("audioFiles/" + file.getLastPathSegment());
//            audioRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    // File uploaded successfully
//                    Toast.makeText(getContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    // Handle unsuccessful upload
//                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}