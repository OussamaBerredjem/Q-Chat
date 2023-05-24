package com.example.myapplication;

import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;


public class chat_home extends AppCompatActivity {

    VideoView videoView;
    ImageView imageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float MIN_SCALE_FACTOR = 1.0f;
    private float MAX_SCALE_FACTOR = 3.0f;
    private Matrix mMatrix = new Matrix();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        videoView = findViewById(R.id.videos);

        imageView = findViewById(R.id.exites);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVisibility(View.GONE);
                videoView.pause();
                onBackPressed();
            }
        });

        String videoUrl = "https://videos.pond5.com/drone-helicopter-flying-close-footage-047484714_main_xxl.mp4";
        Uri videoUri = Uri.parse(videoUrl);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    videoView.setVideoURI(videoUri);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.start();
                } catch (Exception e) {
                    Toast.makeText(chat_home.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, 230);




        /**videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!mp.isPlaying()) {
                    videoView.start();
                    Toast.makeText(getApplicationContext(), "not play", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
                }
            }
        });**/




        /**videoView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // User touched the screen, pause the video
                    if (videoView.isPlaying()) {
                        videoView.pause();

                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // User released the screen, resume the video
                    if (!videoView.isPlaying()) {

                        videoView.start();
                    }
                }
                return true;
            }
        });**/



    }





    @Override
    protected void onStart() {
        super.onStart();

        //v
    }

    void play(){


    }

    private void config() {
        findViewById(android.R.id.content).setTransitionName("go");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        ChangeBounds bounds = new ChangeBounds();
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();

        materialContainerTransform.addTarget(android.R.id.content);

        materialContainerTransform.setDuration(200);

        getWindow().setSharedElementEnterTransition(materialContainerTransform);
        getWindow().setSharedElementExitTransition(materialContainerTransform);
    }


}
