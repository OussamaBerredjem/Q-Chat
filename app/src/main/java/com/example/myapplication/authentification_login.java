package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class authentification_login extends AppCompatActivity {

   private AlertDialog.Builder alertdialogbuilder;
   private AlertDialog alertDialog;
    EditText email,password;
    Button button;
    TextView textView;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification_login);

        SharedPreferences prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", null);
        String Username = prefs.getString("username", null);
        String photoUri = prefs.getString("image-Uri", null);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // code to be executed after a certain time
                if (userId!=null&&Username!=null&&photoUri!=null){
                    Intent intent = new Intent(authentification_login.this,Meaow.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 10);


        progressBar = findViewById(R.id.progressBar2);
        email = findViewById(R.id.email);
        button = (Button) findViewById(R.id.log);
        password = findViewById(R.id.password);
        textView = findViewById(R.id.sign_up);

        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userId!=null&&Username!=null&&photoUri!=null){
                    Intent intent = new Intent(authentification_login.this,Meaow.class);
                    intent.putExtra("USERID", userId);
                    startActivity(intent);
                    finish();
                }

                button.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        SharedPreferences prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("userId", user.getUid());
                        editor.putString("image-Uri", user.getPhotoUrl().toString());
                        editor.putString("username", user.getDisplayName());
                        editor.apply();
                        Toast.makeText(authentification_login.this, "success login", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                       Intent intent = new Intent(authentification_login.this,Meaow.class);
                        intent.putExtra("USERID",user.getUid());
                        intent.putExtra("FULLNAME",user.getDisplayName());
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        button.setVisibility(View.VISIBLE);
                        Toast.makeText(authentification_login.this, "login failed"+e, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDiag();
                Intent intente = new Intent(authentification_login.this,authentification_create.class);
               startActivity(intente);
            }
        });


    }
    public void AlertDiag(){



        alertdialogbuilder = new AlertDialog.Builder(this);
        View pop = getLayoutInflater().inflate(R.layout.popup,null);
        alertdialogbuilder.setView(pop);
        alertDialog =  alertdialogbuilder.create();
        alertDialog.show();


    }

    private void config() {
        findViewById(android.R.id.content).setTransitionName("go");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());


        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();

        materialContainerTransform.addTarget(android.R.id.content);

        materialContainerTransform.setDuration(600);

        getWindow().setSharedElementEnterTransition(materialContainerTransform);
        getWindow().setSharedElementExitTransition(materialContainerTransform);
    }

    public void click(View v){
        alertDialog.dismiss();
    }
}