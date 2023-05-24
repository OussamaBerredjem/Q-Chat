package com.example.myapplication;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;
import static com.google.firebase.firestore.DocumentChange.Type.REMOVED;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.myapplication.Decoration.Decoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Screen_chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Screen_chat extends Fragment {

    int firstVisibleItemPosition;
    TextView Search_Activity;
    static int positiont;
    ImageView imageView,ajout_story;
    ChatInfo chatInfo = new ChatInfo("", "", null, "", "", "");
    ArrayList<String> idid;
    int numModify = 0;
   private static MediaPlayer mediaPlayer;
    LinearLayout layout;
    boolean vuetop = false;
    public static boolean onback = false;
    String ff = "";
    int index;
    ArrayList<ChatInfo> na;
    ChatInfo old;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MyAdapter adapter;
    ArrayList<String> full,story;
    private ArrayList<Integer> type;
    ChatInfo onstope;
    ImageView image;
    ArrayList<ChatInfo> chatInfosonstop;
    LinearLayout linearLayout;
    EditText editText;
    String userId,geteId;
    ConvertTimeStamp convertTimeStamp;
    bool is;
    ImageView conv,prof,notif;
    int lastVisibleItemPosition;
    MeowBottomNavigation meowBottomNavigation;

    boolean fine = false,son = false;

    public boolean isInActivity2;

    int scroll;
    int r;
    MyAdapterNew myAdapterNew;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    public Screen_chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Screen_chat.
     */
    // TODO: Rename and change types and number of parameters
    public static Screen_chat newInstance(String param1, String param2) {
        Screen_chat fragment = new Screen_chat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_screen_chat, container, false);

        imageView = view.findViewById(R.id.floatinge);
        Search_Activity = view.findViewById(R.id.search_activity);
        image = view.findViewById(R.id.SRCHE);


        Search_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.example.myapplication.Search_Activity.class);

                Bundle bundle = new Bundle();
                intent.putParcelableArrayListExtra("na",na );
                bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(),Search_Activity,"go").toBundle();

                startActivity(intent,bundle);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.example.myapplication.Search_Activity.class);
                Bundle bundle = new Bundle();
                bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(),image,"go").toBundle();

                startActivity(intent,bundle);
            }
        });
        ajout_story = view.findViewById(R.id.ajouter_story);

        ajout_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CollapsedToolbar.class));
            }
        });


       // layout = view.findViewById(R.id.bele);









        r = 0;

        is = new bool();
        is.setIsplay(true);



        type = new ArrayList<>();

        idid = new ArrayList<>();

        recyclerView = view.findViewById(R.id.chete);

        full = new ArrayList<>();

        RecyclerView recyclerViewPro = view.findViewById(R.id.recyproe);
        LinearLayoutManager layoutManagerPro = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ((LinearLayoutManager) layoutManagerPro).setStackFromEnd(false);
        recyclerViewPro.setLayoutManager(layoutManagerPro);

        story = new ArrayList<>();

        myAdapterNew = new MyAdapterNew(story,getActivity());



       /* Button srch = view.view.findViewById(R.id.SRCH);

        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

                editor.remove("MyApp");
                Intent intent = new Intent(chat_home.this,authentification_login.class);
                startActivity(intent);
                finish();

            }
        });*/
       recyclerViewPro.setAdapter(myAdapterNew);
       Decoration itemDecoration = new Decoration(16);
       recyclerViewPro.addItemDecoration(itemDecoration);

        na = new ArrayList<>();
        adapter = new MyAdapter(na,getContext());
        recyclerView.setAdapter(adapter);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.messaged);
        myAdapterNew.setOnItemClickListener(new MyAdapterNew.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(getContext(), "position : "+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), chat_home.class);
                Bundle bundle = new Bundle();
                bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), recyclerViewPro.getChildAt(position), "go").toBundle();
                startActivity(intent, bundle);}
        });
        convertTimeStamp = new ConvertTimeStamp();
        SharedPreferences prefs = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        userId = prefs.getString("userId", null);
        String Username = prefs.getString("username", null);
        String  photoUri = prefs.getString("image-Uri", null);
        // Toast.makeText(this, "user id : "+userId, Toast.LENGTH_SHORT).show();




        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();







        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("user").document(userId).collection("friends");





        collectionReference.orderBy("time-stamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            int numModifications = 0;
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(getContext(), "error "+error, Toast.LENGTH_SHORT).show();return;}
                son = false;

                if (!value.isEmpty()) {

                    for (DocumentChange change : value.getDocumentChanges()) {

                        if (change.getType() == ADDED) {



                            Map<String, Object> data = (Map<String, Object>) change.getDocument().getData();



                            if (!data.isEmpty()) {


                                chatInfo = new ChatInfo(data.get("fullname").toString(), data.get("photourl").toString(), (Timestamp) data.get("time-stamp"), data.get("message").toString(), data.get("vue").toString(), data.get("id").toString());
                                //ToastmakeText(chat_home.this, "date "+ data.get("time-stamp"), Toast.LENGTH_SHORT).show();

                                if (chatInfo.getUid().equals(userId)||idid.contains(chatInfo.uid)) {

                                    // Toast.makeText(chat_home.this, "contais this user", Toast.LENGTH_SHORT).show();

                                }else {
                                    na.add(chatInfo);
                                    type.add(Integer.valueOf(data.get("message-type").toString()));
                                    idid.add(chatInfo.getUid());
                                    story.add(chatInfo.getPhotourl());
                                    myAdapterNew.notifyDataSetChanged();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            // Do something with the data here
                            // For example, add it to a list or display it in a RecyclerView
                        }
                        if (change.getType() == MODIFIED) {

                            //  Toast.makeText(chat_home.this, "modified", Toast.LENGTH_SHORT).show();

                            Map<String, Object> data = change.getDocument().getData();
                            chatInfo = new ChatInfo(
                                    String.valueOf(data.get("fullname")),
                                    String.valueOf(data.get("photourl")),
                                    (Timestamp) data.get("time-stamp"),
                                    String.valueOf(data.get("message")),
                                    String.valueOf(data.get("vue")),
                                    String.valueOf(data.get("id"))
                            );


                            if(!idid.isEmpty()){
                                index = idid.indexOf(data.get("id"));
                                old = na.get(index);}


                            if (index >= 0 && index < na.size()&&!idid.isEmpty()&&!na.isEmpty()){

                                if (index == na.size() - 1){
                                    na.set(index,chatInfo);
                                    idid.set(index,chatInfo.getUid());
                                    adapter.notifyItemChanged(index);
                                }else if (index != na.size() - 1&&!old.getDate().equals(chatInfo.getDate())){
                                    na.remove(index);
                                    idid.remove(index);
                                    na.add(chatInfo);
                                    idid.add(chatInfo.getUid());
                                    adapter.notifyDataSetChanged();
                                }else if (index != na.size() - 1&&old.getDate().equals(chatInfo.getDate())){
                                    na.set(index,chatInfo);
                                    idid.set(index,chatInfo.getUid());
                                    adapter.notifyItemChanged(index);
                                }

                            }



                        }///fin if edit

                        if (change.getType() == REMOVED){
                            int y = idid.indexOf(change.getDocument().getId());
                            na.remove(y);
                            idid.remove(y);
                            adapter.notifyItemRemoved(y);
                        }

                    }

                    if (na.get(na.size()-1).getVue().equals("false")&&is.isIsplay()){

                        try {
                            if (mediaPlayer != null&&!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }

                    }




                    // Rest of your code here
                    // ...


                    //story.set(index,chatInfo.getPhotourl());


                }



            }






        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                 lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                 firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                int hiddenItemCount = totalItemCount - lastVisibleItemPosition - 1;

            }});


        //Toast.makeText(this, ""+ff, Toast.LENGTH_SHORT).show();

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                scroll = position;
                positiont = position;
                SharedPreferences prefss = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                userId = prefss.getString("userId", null);
                ChatInfo chatInfo = na.get(position);
                chatInfo.vue = "true";
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.putExtra("USERID",chatInfo.getUid());

                intent.putExtra("position",position);
                intent.putExtra("URL",chatInfo.getPhotourl().toString());
                // Toast.makeText(chat_home.this, "id : "+chatInfo.getUid(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(chat_home.this, "name : "+chatInfo.getFullname(), Toast.LENGTH_SHORT).show();
                intent.putExtra("FULLNAME",chatInfo.getFullname().toString());

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }


                Bundle bundle = new Bundle();
                bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), recyclerView.getChildAt(position), "go").toBundle();

                startActivity(intent,bundle);
                // firebaseFirestore.collection("user").document(userId).collection("friends").document(chatInfo.getUid()).update("vue","true");
                // firebaseFirestore.collection("user").document(chatInfo.getUid()).collection("friends").document(userId).update("vue","true");


                // firebaseFirestore.collection("user").document(userId).collection("friends").document(chatInfo.getUid()).update("vue","true");
                // firebaseFirestore.collection("user").document(chatInfo.getUid()).collection("friends").document(userId).update("vue","true");
                is.setIsplay(false);

                //Toast.makeText(chat_home.this, "item : "+position, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void load(View v){

        na.clear();
        story.clear();
        idid.clear();
        adapter.notifyDataSetChanged();
        myAdapterNew.notifyDataSetChanged();
        firebaseFirestore.collection("userid").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()){
                        // Toast.makeText(chat_home.this, "empty user", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                            Map<String, Object> data = (HashMap<String,Object>)document.getData();

                            chatInfo = new ChatInfo(data.get("fullname").toString(), data.get("photourl").toString(), (Timestamp) data.get("time-stamp"), data.get("message").toString(), data.get("vue").toString(), data.get("id").toString());
                            //Toast.makeText(chat_home.this, "date "+ data.get("time-stamp"), Toast.LENGTH_SHORT).show();
                            if (!chatInfo.getUid().equals(userId)) {
                                na.add(chatInfo);
                                type.add(Integer.valueOf(data.get("message-type").toString()));
                                idid.add(chatInfo.getUid());
                                story.add(chatInfo.getPhotourl());


                            }

                            firebaseFirestore.collection("user").document(userId).collection("friends").document(String.valueOf(data.get("id"))).set(data);}

                    }
                }

            }
        });

        myAdapterNew.notifyItemInserted(0);
        adapter.notifyItemInserted(0);
    }

    public void Get(View view){



        firebaseFirestore.collection("userid").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()){
                        // Toast.makeText(chat_home.this, "empty user", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                            Map<String, Object> data = (HashMap<String,Object>)document.getData();

                            chatInfo = new ChatInfo(data.get("fullname").toString(), data.get("photourl").toString(), (Timestamp) data.get("time-stamp"), data.get("message").toString(), data.get("vue").toString(), data.get("id").toString());
                            //Toast.makeText(chat_home.this, "date "+ data.get("time-stamp"), Toast.LENGTH_SHORT).show();
                            if (!chatInfo.getUid().equals(userId)) {


                                if (!idid.contains(chatInfo.getUid())){
                                Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
                                na.add(chatInfo);
                                type.add(Integer.valueOf(data.get("message-type").toString()));
                                idid.add(chatInfo.getUid());
                                story.add(chatInfo.getPhotourl());

                                }else {
                                    continue;
                                }




                            }

                            if (!idid.contains(chatInfo.getUid())){
                            firebaseFirestore.collection("user").document(userId).collection("friends").document(String.valueOf(data.get("id"))).set(data);}}

                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

        myAdapterNew.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        config();
        super.onResume();
      view.setVisibility(View.VISIBLE);
      recyclerView.scrollToPosition(lastVisibleItemPosition);
    }

    @Override
    public void onPause() {
        super.onPause();

     //   view.setVisibility(View.GONE);


        recyclerView.scrollToPosition(lastVisibleItemPosition);
    }

    private void config() {
        getActivity().setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getActivity().getWindow().setSharedElementsUseOverlay(true);
    }


}