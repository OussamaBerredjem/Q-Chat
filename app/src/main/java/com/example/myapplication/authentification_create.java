package com.example.myapplication;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class authentification_create extends AppCompatActivity {

    EditText email,password,confirme_password,fullname,username;
    Button button;

    private AlertDialog alertDialog;
    private AlertDialog.Builder alertdialogbuilder;

    Map<String,Object> user_data = new HashMap<>();

    ProgressBar progressBare;
    boolean error_fullnamme = true;

    boolean error_usernamme = true;

    boolean error_email = true;

    boolean error_password = true;

    boolean error_confirme_password = true;





    ArrayList<String> users;
    ArrayList<String> emails;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification_create);

        email = findViewById(R.id.cemail);
        button = (Button) findViewById(R.id.create);

       // button.setActivated(false);

        password = findViewById(R.id.cpassword);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        confirme_password = findViewById(R.id.confirme_password);

        Map<String,Object> datauser = new HashMap<>();

        users = new ArrayList<>();

        progressBare = findViewById(R.id.progressBar3);

        progressBare.setVisibility(View.INVISIBLE);

        emails = new ArrayList<>();

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                verify();

                if (username.getText().toString().isEmpty()){

                    error_usernamme = true;
                    username.setBackground(getDrawable(R.drawable.auth));
                    username.setCompoundDrawables(null,null,null,null);
                }
                if (username.getText().toString().length() < 6 || username.getText().toString().contains(" ")){

                    error_usernamme = true;

                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    username.setCompoundDrawables(null, null, drawable, null);

                    username.setBackground(getDrawable(R.drawable.auth_error));


                }else {
                    error_usernamme = false;

                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    username.setCompoundDrawables(null, null, drawable, null);

                    username.setBackground(getDrawable(R.drawable.auth_correct));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify();
               if (password.getText().toString().isEmpty()){

                    error_password = true;
                    password.setBackground(getDrawable(R.drawable.auth));
                    password.setCompoundDrawables(null,null,null,null);
                }
                if (password.getText().toString().length() < 8){

                    error_password = true;


                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    password.setCompoundDrawables(null, null, drawable, null);

                    password.setBackground(getDrawable(R.drawable.auth_error));


                }
                else {

                    error_password = false;


                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    password.setCompoundDrawables(null, null, drawable, null);

                    password.setBackground(getDrawable(R.drawable.auth_correct));

                }
                if (!confirme_password.getText().toString().isEmpty()&&!confirme_password.getText().toString().equals(password.getText().toString())){
                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

                    error_confirme_password = true;
// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    confirme_password.setCompoundDrawables(null, null, drawable, null);

                    confirme_password.setBackground(getDrawable(R.drawable.auth_error));

                }else if(confirme_password.getText().toString().equals(password.getText().toString())){
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

                    error_confirme_password = true;
// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    confirme_password.setCompoundDrawables(null, null, drawable, null);

                    confirme_password.setBackground(getDrawable(R.drawable.auth_correct));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirme_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify();

                if (confirme_password.getText().toString().isEmpty()){

                    error_confirme_password = true;

                    confirme_password.setBackground(getDrawable(R.drawable.auth));
                    confirme_password.setCompoundDrawables(null,null,null,null);
                }
                if (!password.getText().toString().equals(confirme_password.getText().toString())||confirme_password.getText().toString().isEmpty()){

                    error_confirme_password = true;

                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    confirme_password.setCompoundDrawables(null, null, drawable, null);

                    confirme_password.setBackground(getDrawable(R.drawable.auth_error));


                }else {
                    error_confirme_password = false;

                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    confirme_password.setCompoundDrawables(null, null, drawable, null);

                    confirme_password.setBackground(getDrawable(R.drawable.auth_correct));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                verify();

                if (fullname.getText().toString().isEmpty()){

                    error_fullnamme = true;

                    fullname.setBackground(getDrawable(R.drawable.auth));
                    fullname.setCompoundDrawables(null,null,null,null);
                }
                if (fullname.getText().toString().length() < 10 || (!fullname.getText().toString().contains(" ")&&!fullname.getText().toString().contains(" "))){


                    error_fullnamme = true;

                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    fullname.setCompoundDrawables(null, null, drawable, null);

                    fullname.setBackground(getDrawable(R.drawable.auth_error));


                }else {

                    error_confirme_password = false;

                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    fullname.setCompoundDrawables(null, null, drawable, null);

                    fullname.setBackground(getDrawable(R.drawable.auth_correct));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify();

                if (email.getText().toString().isEmpty()){

                    error_email = true;

                    email.setBackground(getDrawable(R.drawable.auth));
                    email.setCompoundDrawables(null,null,null,null);
                }
                if (email.getText().toString().length() < 18 || (!email.getText().toString().contains("@gmail.com")&&!email.getText().toString().contains("@yahoo.com"))){

                    error_email = true;

                    Drawable drawable = getResources().getDrawable(R.drawable.error_f);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    email.setCompoundDrawables(null, null, drawable, null);

                    email.setBackground(getDrawable(R.drawable.auth_error));


                }else {

                    error_email = false;

                    Drawable drawable = getResources().getDrawable(R.drawable.ic_check);

// Set the bounds of the drawable
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// Set the drawable to the right of the EditText
                    email.setCompoundDrawables(null, null, drawable, null);

                    email.setBackground(getDrawable(R.drawable.auth_correct));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                button.setVisibility(View.INVISIBLE);
                progressBare.setVisibility(View.VISIBLE);
                String semail,spassword,sconfirm_password,sfullname,susername;

                semail = email.getText().toString();

                spassword = password.getText().toString();

                sconfirm_password = confirme_password.getText().toString();

                sfullname = fullname.getText().toString();

                susername = username.getText().toString();

                if (semail.isEmpty()||spassword.isEmpty()||sfullname.isEmpty()||susername.isEmpty()||sconfirm_password.isEmpty()){
                    Toast.makeText(authentification_create.this, "please enter all data", Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.VISIBLE);
                    progressBare.setVisibility(View.INVISIBLE);
                    return;
                }else if (!spassword.equals(sconfirm_password)){
                    button.setVisibility(View.VISIBLE);
                    progressBare.setVisibility(View.INVISIBLE);
                    Toast.makeText(authentification_create.this, "password and confirm password must be same", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("email").document(semail);

                    docRef.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document exists
                                Toast.makeText(authentification_create.this, "exist", Toast.LENGTH_SHORT).show();
                                button.setVisibility(View.VISIBLE);
                                progressBare.setVisibility(View.INVISIBLE);
                            } else {
                                // Document does not exist

                            }
                        } else {
                            // Failed to get document
                            button.setVisibility(View.VISIBLE);
                            progressBare.setVisibility(View.INVISIBLE);
                        }
                    });


                }

                    if (users.contains(susername)) {
                        button.setVisibility(View.VISIBLE);
                        progressBare.setVisibility(View.INVISIBLE);
                        Toast.makeText(authentification_create.this, "username already exist", Toast.LENGTH_SHORT).show();

                    } else if (emails.contains(semail)) {
                        button.setVisibility(View.VISIBLE);
                        progressBare.setVisibility(View.INVISIBLE);
                        Toast.makeText(authentification_create.this, "email already exist", Toast.LENGTH_SHORT).show();

                    } else {

                        firebaseAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Uri uri = Uri.parse("https://th.bing.com/th/id/R.ab5de0df53da6094b6b322360ca4d424?rik=gvH2PJb5bTNNWw&pid=ImgRaw&r=0");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(susername).setPhotoUri(uri)
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                               /* SharedPreferences prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putString("userId", user.getUid());
                                                editor.putString("image-Uri", user.getPhotoUrl().toString());
                                                editor.putString("username", user.getDisplayName());
                                                editor.apply();*/

                                                datauser.clear();
                                                datauser.put("email", user.getEmail());
                                                datauser.put("id", user.getUid());
                                                datauser.put("fullname", user.getDisplayName());
                                                datauser.put("photourl", String.valueOf(user.getPhotoUrl()));
                                                datauser.put("password", spassword);
                                                datauser.put("username", susername);
                                                datauser.put("join-date", Timestamp.now());
                                                datauser.put("time-stamp", Timestamp.now());
                                                datauser.put("index", 0);
                                                datauser.put("message", "you are friends now");
                                                datauser.put("vue", "false");
                                                datauser.put("message-type",0);


                                                firebaseFirestore.collection("userid").document(user.getUid()).set(datauser);
                                                firebaseFirestore.collection("email").document(semail).set(datauser);

                                                firebaseFirestore.collection("userid").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                        if (task.isSuccessful()) {

                                                            QuerySnapshot querySnapshot = task.getResult();
                                                            if (querySnapshot.isEmpty()){
                                                                Toast.makeText(authentification_create.this, "empty user", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else {
                                                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                                                                    Map<String, Object> data = (HashMap<String,Object>)document.getData();

                                                                    firebaseFirestore.collection("user").document(user.getUid()).collection("friends").document(String.valueOf(data.get("id"))).set(data);
                                                                }
                                                            }
                                                        }

                                                    }
                                                });


                                                button.setVisibility(View.VISIBLE);
                                                progressBare.setVisibility(View.INVISIBLE);
                                                Toast.makeText(authentification_create.this, "greate success : " + user.getDisplayName().toString(), Toast.LENGTH_SHORT).show();

                                            } else {
                                                button.setVisibility(View.VISIBLE);
                                                progressBare.setVisibility(View.INVISIBLE);
                                                Toast.makeText(authentification_create.this, "failed create username", Toast.LENGTH_SHORT).show();
                                                // User creation failed, handle the error
                                            }
                                        });
                            }

                            //FirebaseUser user = firebaseAuth.getCurrentUser();

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                button.setVisibility(View.VISIBLE);
                                progressBare.setVisibility(View.INVISIBLE);
                                Toast.makeText(authentification_create.this, "failed signup", Toast.LENGTH_SHORT).show();
                            }
                        });

                        /*firebaseAuth.sendPasswordResetEmail("bjmabdou239@gmail.com").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(authentification_create.this, "mail sending", Toast.LENGTH_SHORT).show();
                            }
                        });*/


                    }

                }

        });



    }

    private void verify(){

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

}