package com.example.myapplication;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_info extends Fragment {

    LinearLayout layout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String username,fullnamee;
    String join;
    Timestamp timestamp;
    ImageView conv,prof,notif;
    private ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_info.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_info newInstance(String param1, String param2) {
        Profile_info fragment = new Profile_info();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        imageView = view.findViewById(R.id.circleImageViewb);
        TextView fullname = view.findViewById(R.id.textView4b);
        TextView joindate = view.findViewById(R.id.joindateb);
        TextView usernami = view.findViewById(R.id.usernamib);
        TextView emaili = view.findViewById(R.id.emailib);


        String name = firebaseAuth.getCurrentUser().getDisplayName();


        String email = firebaseAuth.getCurrentUser().getEmail();
        String id = firebaseAuth.getCurrentUser().getUid();


        emaili.setText(email);



        firebaseFirestore.collection("userid").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap = (HashMap<String, Object>) value.getData();

                    username = hashMap.get("username").toString();
                    fullnamee = hashMap.get("fullname").toString();
                    timestamp = (Timestamp) hashMap.get("join-date");
                    String photouri = hashMap.get("photourl").toString();

                    Date date = timestamp.toDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

// Format the date to display the hour
                    join = formatter.format(date);
                    joindate.setText(join);
                    usernami.setText(username);
                    fullname.setText(fullnamee);
                    try {
                        Glide.with(getActivity())
                                .load(photouri)
                                .into(imageView);
                    }catch (Exception e){}


                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}