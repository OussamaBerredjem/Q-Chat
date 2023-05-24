package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Timer;
import java.util.TimerTask;

public class CollapsedToolbar extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_FILE = 1;
    private Uri mSelectedFileUri;
    private StorageReference mStorageRef;
    ImageView selected_image;
    VideoView selected_video;
    EditText geter_link;
    boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsed_toolbar);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        selected_image = findViewById(R.id.selected_image);
        geter_link = findViewById(R.id.geter_link);
        selected_video = findViewById(R.id.selected_video);

        selected_video.setVisibility(View.INVISIBLE);
        selected_image.setVisibility(View.INVISIBLE);

        Button selectButton = findViewById(R.id.button_select);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // Allow any file type to be selected
                startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
            }
        });

        Button uploadButton = findViewById(R.id.upload_select);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFileExistence(mSelectedFileUri)){
                    uploadImage();
                    Toast.makeText(CollapsedToolbar.this, "not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });
        uploadButton.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK) {
            mSelectedFileUri = data.getData();

            String fileType = getContentResolver().getType(mSelectedFileUri);
            if (fileType.startsWith("image/") || fileType.startsWith("video/")) {
                if (fileType.startsWith("image/")){
                    Toast.makeText(this, "image", Toast.LENGTH_SHORT).show();
                    selected_video.pause();
                    selected_video.setVisibility(View.INVISIBLE);
                    selected_image.setVisibility(View.VISIBLE);
                    selected_image.setImageURI(mSelectedFileUri);
                }else {
                    selected_video.setVisibility(View.VISIBLE);
                    selected_image.setVisibility(View.INVISIBLE);
                    selected_video.setVideoURI(mSelectedFileUri);
                    selected_video.start();
                    Toast.makeText(this, "video", Toast.LENGTH_SHORT).show();
                }
                Button uploadButton = findViewById(R.id.upload_select);
                uploadButton.setEnabled(true);
            } else {
                Toast.makeText(this, "Selected file is not an image or video", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadImage() {
        // Get a reference to the image file

        boolean result = checkFileExistence(mSelectedFileUri);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {


            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,200);

        if (result) {
            Toast.makeText(CollapsedToolbar.this, "existe file", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(CollapsedToolbar.this, "uploading bro", Toast.LENGTH_SHORT).show();

            StorageReference fileRef = mStorageRef.child(mSelectedFileUri.getLastPathSegment());
            UploadTask uploadTask = fileRef.putFile(mSelectedFileUri);

            // Show a progress bar while uploading
            ProgressDialog progressDialog = new ProgressDialog(CollapsedToolbar.this);
            progressDialog.setTitle("Uploading image...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();

            // Upload the file

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Get the download URL
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // URL of the image file
                            String imageUrl = uri.toString();
                            geter_link.setText(imageUrl);
                            Toast.makeText(CollapsedToolbar.this, "link " + imageUrl, Toast.LENGTH_SHORT).show();
                            // Do something with the URL
                            progressDialog.dismiss(); // Dismiss progress dialog
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle any errors
                    progressDialog.dismiss(); // Dismiss progress dialog
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Update progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress((int) progress);
                }
            });
        }


        }

    private boolean checkFileExistence(Uri fileUri) {
        StorageReference fileRef = mStorageRef.child(fileUri.getLastPathSegment());

        Task<StorageMetadata> task = fileRef.getMetadata();
        while (!task.isComplete()) {}

        if (task.isSuccessful()) {
            // File exists
            Toast.makeText(CollapsedToolbar.this, "existe file", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            // File does not exist
            return false;
        }
    }




}
