package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemClickListener{
    ImageButton button,jujo,vocal,imojis;
    ImageButton imageView;
    MediaPlayer mediaPlayer_send,mediaPlayer_get;
    int CHECKER;
    int spam = 0,ui;
    DocumentSnapshot lastDocumentSnapshot;
    boolean great = false;
    int stop = 40;
    int yize = 60;
    boolean added = false;
    Map<String, Object> datas;
    String tstt,id;
    AlertDialog.Builder alertdialogbuilder;
    AlertDialog alertDialog;
    int pos = 0;
    int limit = 40;
    int hide = 0;
    boolean term = false;
    chat_home chathome;
    CollectionReference cv;
    EditText editText;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList,arrayList_send;
    MessageModel messageModel,compared,removed_message_model,messageModel1;
    RecyclerView recyclerView;
    CircleImageView floatingActionButton,profile_pic;
    int i,index_old,index_new;
    String mess,moved_one;
    CustomAdapter customadapter;
    ArrayList<String> smessages,document_id;

    boolean jaime = true;


    ArrayList<Timestamp> messagesList_timer;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    boolean removed;
    int index_removed;

    ArrayList<MessageModel> messagesList,message_Newer,removed_message;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference dcRef;
    CollectionReference Cr;
    Query query;

    String userId,GeterId;
    String Username ,popcorn;
    String photoUri,mes;
    TextView name;
    int distance;
    private MainActivity mainActivity;

    DatabaseReference myRef;
    LinearSmoothScroller scroller;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1);



}

    @Override
    protected void onStart() {
        super.onStart();

        Handler mHandler = new Handler();

        //mHandler.postDelayed(new Runnable() {
          //  @Override
          //  public void run() {



        //Toast.makeText(MainActivity.this, "oncreate", Toast.LENGTH_SHORT).show();

        datas = new HashMap<>();


        smessages = new ArrayList<>();

        name = findViewById(R.id.fullnam);

        floatingActionButton = findViewById(R.id.floating);
        floatingActionButton.setVisibility(View.INVISIBLE);


        vocal = findViewById(R.id.vocal);
        imojis = findViewById(R.id.imojie);

        profile_pic = findViewById(R.id.profile_pic);

        /***********************************************************************************/

        SharedPreferences prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        userId = prefs.getString("userId", null);
        Username = prefs.getString("username", null);
        photoUri = prefs.getString("image-Uri", null);
        Intent intent = getIntent();
        GeterId = intent.getStringExtra("USERID");
        String ur = intent.getStringExtra("URL");
        String all = intent.getStringExtra("FULLNAME");

        name.setText(all);

        // //Toast.makeText(this, "GETER : "+GeterId, //Toast.LENGTH_SHORT).show();


        Glide.with(MainActivity.this)
                .load(ur)
                .into(profile_pic);


        /***********************************************************************************/

        imageView = findViewById(R.id.camera);


        mediaPlayer_send = MediaPlayer.create(MainActivity.this, R.raw.iphb);
        mediaPlayer_get = MediaPlayer.create(MainActivity.this, R.raw.ipha);

        message_Newer = new ArrayList<>();

        button = findViewById(R.id.Button);

        jujo = findViewById(R.id.jujo);

        editText = findViewById(R.id.editTextTextPersonName);

        message_Newer = new ArrayList<>();
        messagesList = new ArrayList<>();

        removed_message = new ArrayList<>();



        messagesList_timer = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);


        // saved();

        // if (//getMessages(this)!=null) {
        try {
            //messagesList.clear();
            // messagesList.addAll(getMessages(this));
        }catch (Exception e){
            // //Toast.makeText(this, "error", //Toast.LENGTH_SHORT).show();
        }

        // }


       // //Toast.makeText(this, "userId : "+userId, //Toast.LENGTH_SHORT).show();


// create a LinearSmoothScroller object to control the scrolling animation
        scroller  = new LinearSmoothScroller(recyclerView.getContext()) {
            // override the calculateSpeedPerPixel() method to set the scrolling speed
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 400f / displayMetrics.densityDpi;
            }
        };

        message_Newer = new ArrayList<>();
        messagesList = new ArrayList<>();



        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

// Set the reverseLayout and stackFromEnd properties to true
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(true);

// Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(layoutManager);



        customadapter = new CustomAdapter(MainActivity.this,messagesList);
        recyclerView.setAdapter(customadapter);


        customadapter.setOnItemClickListener(MainActivity.this);


        customadapter.setOnItemLongClickListener(new CustomAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                // handle long click event for item at position

                pos = position;

                Alert(position);


                ////Toast.makeText(MainActivity.this, "long : "+position, //Toast.LENGTH_SHORT).show();
            }
        });


        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(50); // duration for item addition animation
        animator.setRemoveDuration(300); // duration for item removal animation
        animator.setChangeDuration(200); // duration for item change animation
        recyclerView.setItemAnimator(animator);

        removed_message = new ArrayList<>();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                imageView.setImageDrawable(getDrawable(R.drawable.ic_camera));

            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if (editText.getText().toString().isEmpty()){ button.setImageDrawable(getDrawable(R.drawable.like_svgrepo_com));}
                else {
                    button.setImageDrawable(getDrawable(R.drawable.ic_send));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        HashMap<String,Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap_geter = new HashMap<>();


        document_id = new ArrayList<>();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newDocId = db.collection("user").document("oussama").collection("messages").document().getId();



                if (editText.getText().toString().isEmpty()) {
                    //   messagesList.add(new MessageModel(name, CustomAdapter.MESSAGE_TYPE_OUT,++i));
                    mes = "jaime";
                    hashMap.put("message", mes);
                    hashMap.put("message-type", CustomAdapter.MESSAGE_TYPE_JAIME);
                    hashMap.put("time-stamp", Timestamp.now());
                    hashMap.put("index",messagesList.size()+0);
                    hashMap.put("ID",userId);
                    hashMap.put("document",newDocId);

                    hashMap_geter.put("message", mes);
                    hashMap_geter.put("message-type", CustomAdapter.MESSAGE_TYPE_OUT);
                    hashMap_geter.put("time-stamp", Timestamp.now());
                    hashMap_geter.put("index",messagesList.size()+0);
                    hashMap_geter.put("ID",userId);
                    hashMap_geter.put("document",newDocId);

                    jaime = true;

                   DocumentReference send_documentReference = db.collection("user").document(userId).collection("friends").document(GeterId);

                    DocumentReference get_documentReference = db.collection("user").document(GeterId).collection("friends").document(userId);

                    send_documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Access the data in the document
                                    Map<String, Object> data = document.getData();
                                    data.put("message","you : jaime");
                                    data.put("vue","true");
                                    data.put("ID",userId);
                                    data.put("time-stamp",Timestamp.now());
                                    db.collection("user").document(userId).collection("friends").document(GeterId).set(data);

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                    get_documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                     // Access the data in the document
                                    Map<String, Object> data = document.getData();
                                    data.put("message","jaime");
                                    data.put("vue","false");
                                    data.put("ID",userId);
                                    data.put("time-stamp",Timestamp.now());
                                    db.collection("user").document(GeterId).collection("friends").document(userId).set(data);

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                  /*db.collection("user").document(userId).collection("friends").document(GeterId).update("ID",userId);
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("ID",userId);
                    db.collection("user").document(userId).collection("friends").document(GeterId).update("message","you : jaime");
                    db.collection("user").document(userId).collection("friends").document(GeterId).update("vue","true");
                    db.collection("user").document(userId).collection("friends").document(GeterId).update("time-stamp",Timestamp.now());


                    db.collection("user").document(GeterId).collection("friends").document(userId).update("message","jaime");
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("vue","false");
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("time-stamp",Timestamp.now());
                    */
                } else {
                    mes  = editText.getText().toString();
                    hashMap.put("index",messagesList.size()+0);
                    hashMap.put("message", mes);
                    hashMap.put("message-type", 0);
                    hashMap.put("time-stamp", Timestamp.now());
                    hashMap.put("ID",userId);
                    hashMap.put("document",newDocId);
                    hashMap_geter.put("index",messagesList.size()+0);
                    hashMap_geter.put("message", mes);
                    hashMap_geter.put("message-type", 1);
                    hashMap_geter.put("time-stamp", Timestamp.now());
                    hashMap_geter.put("ID",userId);
                    hashMap_geter.put("document",newDocId);

                    DocumentReference send_documentReference = db.collection("user").document(userId).collection("friends").document(GeterId);

                    DocumentReference get_documentReference = db.collection("user").document(GeterId).collection("friends").document(userId);

                    get_documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Access the data in the document
                                    Map<String, Object> data = document.getData();
                                    data.put("message",mes);
                                    data.put("vue","false");
                                    data.put("ID",userId);
                                    data.put("time-stamp",Timestamp.now());
                                    db.collection("user").document(GeterId).collection("friends").document(userId).set(data);


                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });


                    send_documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Access the data in the document
                                    Map<String, Object> data = document.getData();
                                    data.put("message","you : "+mes);
                                    data.put("vue","true");
                                    data.put("ID",userId);
                                    data.put("time-stamp",Timestamp.now());
                                    db.collection("user").document(userId).collection("friends").document(GeterId).set(data);


                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });




                    //messagesList.add(new MessageModel(name, CustomAdapter.MESSAGE_TYPE_IN,Timestamp.now()));

                   /* db.collection("user").document(userId).collection("friends").document(GeterId).update("ID",userId);
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("ID",userId);

                    db.collection("user").document(userId).collection("friends").document(GeterId).update("message","you : "+mes);
                    db.collection("user").document(userId).collection("friends").document(GeterId).update("vue","true");
                    db.collection("user").document(userId).collection("friends").document(GeterId).update("time-stamp",Timestamp.now());


                    db.collection("user").document(GeterId).collection("friends").document(userId).update("message",""+mes);
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("vue","false");
                    db.collection("user").document(GeterId).collection("friends").document(userId).update("time-stamp",Timestamp.now());*/

                }

// Set the user map as a child of the "users" node with the generated key


                //  customadapter.notifyDataSetChanged();


                //CustomAdapter customadapter = new CustomAdapter(MainActivity.this, messagesList);

                // recyclerView.setAdapter(customadapter);
                //recyclerView.scrollToPosition(messagesList.size()-1);

                // document_id.add(newDocId);
                //Toast.makeText(MainActivity.this, "id : "+newDocId, //Toast.LENGTH_SHORT).show();
                db.collection("user").document(userId).collection("messages").document(GeterId).collection("message").document(newDocId).set(hashMap);
                db.collection("user").document(GeterId).collection("messages").document(userId).collection("message").document(newDocId).set(hashMap_geter);


                editText.setText("");

                button.setImageDrawable(getDrawable(R.drawable.like_svgrepo_com));


            }
 });
        cv = db.collection("user").document(userId).collection("messages").document(GeterId).collection("message");


        cv.get().addOnSuccessListener(querySnapshot -> {
            yize = querySnapshot.size();
            // do something with the number of documents
        });

        /*******************************/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagesList.clear();
                messagesList_timer.clear();
                new CustomAdapter(MainActivity.this, messagesList);

                recyclerView.setAdapter(customadapter);
                //if (recyclerView.computeVerticalScrollOffset() < messagesList.size() - 2){
                ////Toast.makeText(MainActivity.this, "scroll num : "+recyclerView.computeVerticalScrollOffset(), //Toast.LENGTH_SHORT).show();
                // //Toast.makeText(MainActivity.this, "scroll max num : "+(recyclerView.computeVerticalScrollRange() - recyclerView.getHeight()), //Toast.LENGTH_SHORT).show();
                int max = recyclerView.computeVerticalScrollRange() - recyclerView.getHeight();
                if (recyclerView.computeVerticalScrollOffset() < max-600){
                    floatingActionButton.setVisibility(View.VISIBLE);

                    //Toast.makeText(MainActivity.this, "error while show", //Toast.LENGTH_SHORT).show();
                }
                else {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // //Toast.makeText(MainActivity.this, "new state : "+newState, //Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                int hiddenItemCount = totalItemCount - lastVisibleItemPosition - 1;
                // //Toast.makeText(MainActivity.this, ""+recyclerView.getAdapter().getItemCount(), //Toast.LENGTH_SHORT).show();
                // //Toast.makeText(MainActivity.this, "scrolled : "+hiddenItemCount, //Toast.LENGTH_SHORT).show();
                /****/




                if (messagesList.size() >= 40 && hiddenItemCount >= messagesList.size() - 11 && messagesList.size() < yize) {
                    hide = hiddenItemCount;
                    int remainingDocs = 0;
                    if (yize-messagesList.size()>0){
                     remainingDocs = yize - messagesList.size(); }

                    //Toast.makeText(MainActivity.this, "getting : "+Math.min(40, remainingDocs), Toast.LENGTH_SHORT).show();

                    limit = limit + Math.min(40, remainingDocs); // set initial limit to 150 or remaining docs if less than 40



                    message_Newer.clear();

                    cv.get().addOnSuccessListener(querySnapshot -> {
                        yize = querySnapshot.size();
                        // do something with the number of documents
                    });

                    Query query = cv.orderBy("time-stamp").limitToLast(limit);

                    query.get().addOnSuccessListener(querySnapshot -> {
                        // get the fifth document before the last document
                        DocumentSnapshot fifthBeforeLast = querySnapshot.getDocuments().get(limit - stop);

                        // get the 5 documents before the fifthBeforeLast document
                        cv.orderBy("time-stamp").endBefore(fifthBeforeLast).limitToLast(limit - stop)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot querySnapshot) {
                                        // process the results
                                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                            HashMap<String, Object> date = new HashMap<>();
                                            date = (HashMap<String, Object>) documentSnapshot.getData();
                                            messageModel = new MessageModel(
                                                    date.get("message").toString(),
                                                    Integer.valueOf(date.get("message-type").toString()),
                                                    (Timestamp) date.get("time-stamp"), String.valueOf(date.get("ID")), String.valueOf(date.get("document"))
                                            );

                                            message_Newer.add(messageModel);
                                        }

                                        Collections.sort(message_Newer, new Comparator<MessageModel>() {
                                            @Override
                                            public int compare(MessageModel m1, MessageModel m2) {
                                                return m1.messageTime.compareTo(m2.messageTime);
                                            }
                                        });
                                        Collections.reverse(message_Newer);
                                        for (int i = 0; i < message_Newer.size(); i++) {
                                            messagesList.add(0, message_Newer.get(i));
                                            customadapter.notifyItemInserted(0);
                                        }
                                    }
                                });
                    });
                }else {
                   // Toast.makeText(MainActivity.this, "stop yize : "+yize, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this, "stop list size : "+messagesList.size(), Toast.LENGTH_SHORT).show();

                }

                            /****/


                if(hiddenItemCount >=5){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }else {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(messagesList.size()-1);
            }
        });

        jujo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 onBackPressed();

            }

        });

        messageModel = new MessageModel(
                "message",
                0,
                Timestamp.now(), "id", "data");

        /*******************************************************************************************/

       //** add here code coupier last time**/

        CollectionReference nRef = FirebaseFirestore.getInstance().collection("user").document(userId).collection("messages").document(GeterId).collection("message");

        nRef.orderBy("time-stamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle the error
                    Toast.makeText(MainActivity.this, "error"+error, Toast.LENGTH_SHORT).show();
                }

               //db.collection("user").document(GeterId).collection("friends").document(userId).update("vue","true");


                // Create a temporary list to hold the new messages
                List<MessageModel> newMessages = new ArrayList<>();

                for (DocumentChange change : value.getDocumentChanges()) {
                    switch (change.getType()) {
                        case ADDED:
                          try {
                           db.collection("user").document(userId).collection("friends").document(GeterId).update("vue","true");
                          }catch (Exception e){

                          }



                            // Handle added messages
                            HashMap<String, Object> messagesData = (HashMap<String, Object>) change.getDocument().getData();
                            document_id.add(messagesData.get("ID").toString());

                            removed_message_model = messageModel;
                            messageModel = new MessageModel(
                                    messagesData.get("message").toString(),
                                    Integer.valueOf(messagesData.get("message-type").toString()),
                                    (Timestamp) messagesData.get("time-stamp"), String.valueOf(messagesData.get("ID")), String.valueOf(messagesData.get("document"))
                            );

                            if (!removed_message_model.getMessageTime().equals(messageModel.getMessageTime())){
                            added = true;


                            messagesList.add(messageModel);
                            id = messageModel.getId();

                            if (!id.equals(userId) && !mediaPlayer_get.isPlaying() && !mediaPlayer_send.isPlaying()) {

                             //   Toast.makeText(MainActivity.this, "getet", Toast.LENGTH_SHORT).show();
                                mediaPlayer_get.start();
                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        mediaPlayer_get.pause();
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(timerTask, 1000);

                            }

                            if (id.equals(userId) && !mediaPlayer_send.isPlaying() && !mediaPlayer_get.isPlaying()) {

                               // Toast.makeText(MainActivity.this, "get", Toast.LENGTH_SHORT).show();

                                mediaPlayer_send.start();

                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        mediaPlayer_send.pause();
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(timerTask, 1000);

                            }


                        }else {
                                added = false;
                            }


                            break;

                        case MODIFIED: {

                            String ch = change.getDocument().getId();

                            HashMap<String, Object> mo = new HashMap<>();

                            mo = (HashMap<String, Object>) change.getDocument().getData();

                            // //Toast.makeText(MainActivity.this, "modified", //Toast.LENGTH_SHORT).show();
                            document_id.clear();
                            for (int e = 0; e < messagesList.size(); e++) {
                                document_id.add(messagesList.get(e).GetDocument());
                            }

                            if (document_id.contains(ch)) {
                                ui = document_id.indexOf(ch);
                                //  //Toast.makeText(MainActivity.this, "exist : "+ui, //Toast.LENGTH_SHORT).show();
                            }
                            MessageModel n = messagesList.get(ui);
                            MessageModel m = new MessageModel(mo.get("message").toString(), Integer.valueOf(mo.get("message-type").toString()), (Timestamp) mo.get("time-stamp"), mo.get("ID").toString(), mo.get("document").toString());


                            if (n.getMessageTime() != m.getMessageTime()) {
                                document_id.clear();
                                messagesList.remove(ui);
                                messagesList.add(m);


                                Collections.sort(messagesList, new Comparator<MessageModel>() {
                                    @Override
                                    public int compare(MessageModel m1, MessageModel m2) {
                                        return m1.messageTime.compareTo(m2.messageTime);
                                    }
                                });


                                customadapter.notifyDataSetChanged();
                            } else {
                                messagesList.set(ui, m);
                                customadapter.notifyDataSetChanged();
                            }


                            break;
                        }

                        case REMOVED:
                            // Handle removed messages
                            int index = change.getOldIndex();
                         //   Toast.makeText(MainActivity.this, "index "+index, Toast.LENGTH_SHORT).show();
                            messagesList.remove(index);
                           customadapter.notifyItemRemoved(index);
                          //  Toast.makeText(MainActivity.this, "removed", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }





                if (!messagesList.isEmpty()&&added) {
                    // Add new messages to the message list
                    added = false;

                    //messagesList.addAll(newMessages);

                    // calculate the distance to scroll


                        customadapter.notifyItemInserted(messagesList.size() - 1);

                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                 distance = Math.abs(messagesList.size() - 1 - ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition());


                                // set the target position to scroll to
                                scroller.setTargetPosition(messagesList.size() - 1);

                                // start the smooth scrolling animation
                                recyclerView.getLayoutManager().startSmoothScroll(scroller);
                            }
                        }, 0);






                }
            }
        });



       /* query = collectionRef.orderBy("time-stamp", Query.Direction.ASCENDING);
// Add a snapshot listener to the collection
        query.addSnapshotListener((querySnapshot, error) -> {
            if (error != null) {
                //Toast.makeText(this, "error", //Toast.LENGTH_SHORT).show();

            }else {
                String //ToastMessage = "";

                // Iterate through the document changes in the query snapshot and check for new documents
                try {

                    for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            Map<String, Object> ddata = dc.getDocument().getData();
                            //ToastMessage += "Mesages : " + ddata.get("message") + "\n";
                            MessageModel messageModel = new MessageModel(ddata.get("message").toString(), Integer.valueOf(ddata.get("message-type").toString()), (Timestamp) ddata.get("time-stamp"));
                            messagesList.add(messageModel);
                            messagesList_timer.add((Timestamp) ddata.get("time-stamp"));
                            //Toast.makeText(this, "new element", //Toast.LENGTH_SHORT).show();
                        }


                          //int y =  messagesList_modifier.indexOf(new MessageModel(ddata.get("message").toString(), Integer.valueOf(ddata.get("message-type").toString())));
                         // messagesList.set(y,messagesList_modifier.get(y));

                        }

                    customadapter = new CustomAdapter(MainActivity.this, messagesList);

                    recyclerView.setAdapter(customadapter);
                    recyclerView.scrollToPosition(messagesList.size()-1);

                    } catch (NumberFormatException e) {
                    //Toast.makeText(this, "error update : "+e, //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

        });*/
            //}
        //}, 1);

    }






    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }

    public void Alert(int position){


            index_removed = position;




            alertdialogbuilder = new AlertDialog.Builder(this);
            if (messagesList.get(position).getId().equals(userId)) {

                View pop = getLayoutInflater().inflate(R.layout.removemessage, null);
                TextView delete = pop.findViewById(R.id.delete);
                CheckBox checkBox = pop.findViewById(R.id.checkBox);
                alertdialogbuilder.setView(pop);
                alertDialog = alertdialogbuilder.create();
                alertDialog.show();

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageModel mo;
                        HashMap<String,Object> map = new HashMap<>();

                        String doc = messagesList.get(pos).GetDocument();

                        if (pos == messagesList.size()-1){

                            mo = messagesList.get(pos);




                        }


                        if (!checkBox.isChecked()) {
                            try {
                                FirebaseFirestore.getInstance().collection("user").document(userId).collection("messages").document(GeterId).collection("message").document(doc).delete();

                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            try {
                                FirebaseFirestore.getInstance().collection("user").document(GeterId).collection("messages").document(userId).collection("message").document(doc).delete();

                                FirebaseFirestore.getInstance().collection("user").document(userId).collection("messages").document(GeterId).collection("message").document(doc).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (value.exists()){
                                            FirebaseFirestore.getInstance().collection("user").document(userId).collection("messages").document(GeterId).collection("message").document(doc).delete();

                                            }
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }

                        }

                        alertDialog.dismiss();


                    }
                });
            }else {

                View pop = getLayoutInflater().inflate(R.layout.removeme, null);
                TextView deleteme = pop.findViewById(R.id.deleteme);
                alertdialogbuilder.setView(pop);
                alertDialog = alertdialogbuilder.create();
                alertDialog.show();

                deleteme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String doc = messagesList.get(pos).GetDocument();

                            try {
                                FirebaseFirestore.getInstance().collection("user").document(userId).collection("messages").document(GeterId).collection("message").document(doc).delete();

                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }


                        alertDialog.dismiss();
                    }
                });
            }
    }

    public void delete(View v){




    }
    public void cancel(View v){
        alertDialog.dismiss();
    }

    public void gogo(View v){

    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    public void onItemClick(int position) {
        //Toast.makeText(this, ""+position, //Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


        db.collection("user").document(userId).collection("friends").document(GeterId).update("vue","true");


        recyclerView.setAdapter(customadapter);
    }
    private void config() {
        findViewById(android.R.id.content).setTransitionName("gogo");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        ChangeBounds bounds = new ChangeBounds();
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();

        materialContainerTransform.addTarget(android.R.id.content);

        materialContainerTransform.setDuration(400);

        getWindow().setSharedElementEnterTransition(materialContainerTransform);
        getWindow().setSharedElementExitTransition(materialContainerTransform);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_out_right);
    }
}