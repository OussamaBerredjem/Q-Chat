package com.example.myapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class JoinApp extends AppCompatActivity {
    Button button;
    String token;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_app);

        button = findViewById(R.id.join_signup);
        textView = findViewById(R.id.signin);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                           // Toast.makeText(JoinApp.this, "failed", Toast.LENGTH_SHORT).show();                            return;
                        }

                        // Get new FCM registration token
                        //token = task.getResult();

                        // Log and/or send the token to your server
                       // Toast.makeText(JoinApp.this, "FCM registration token: " + token,Toast.LENGTH_SHORT).show();
                    }
                });

       int messageId = 12345;
        Map<String, String> payload = new HashMap<>();
        payload.put("key1", "value1");
        payload.put("key2", "value2");

// Create the FCM message
      RemoteMessage message = new RemoteMessage.Builder("/topics/all_users")
                .setMessageId(Integer.toString(messageId))
                .setData(payload)
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinApp.this,authentification_create.class);
                Bundle bundle = new Bundle();
                bundle = ActivityOptions.makeSceneTransitionAnimation(JoinApp.this,button,"go").toBundle();

                startActivity(intent,bundle);/**
              FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender(token,"oussama berredjem","hello",getApplicationContext(),JoinApp.this);
               fcmNotificationsSender.SendNotifications();
                Toast.makeText(JoinApp.this, "Message sent to all users", Toast.LENGTH_SHORT).show();**/
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinApp.this,authentification_login.class);
                Bundle bundle = new Bundle();
                bundle = ActivityOptions.makeSceneTransitionAnimation(JoinApp.this,textView,"go").toBundle();

                startActivity(intent,bundle);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });





    }

    private void sendMessage() {

try {
    FirebaseMessaging.getInstance().subscribeToTopic("myTopic");

    // Set up the FCM message
    Map<String, String> payload = new HashMap<>();
    payload.put("key1", "value1");
    payload.put("key2", "value2");

    // Create the FCM message
    RemoteMessage message = new RemoteMessage.Builder("DEVICE_TOKEN")
            .setMessageId(Integer.toString(Integer.parseInt("4443322223")))
            .setData(payload)
            .build();

// Send the FCM message
    FirebaseMessaging.getInstance().send(message);
}catch (Exception e){
    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
}




    }
    private void config() {
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);
    }
}