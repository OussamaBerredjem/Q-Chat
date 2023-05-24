package com.example.myapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Story_view#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Story_view extends Fragment {

    VideoView videoView;
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Story_view() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Story_view.
     */
    // TODO: Rename and change types and number of parameters
    public static Story_view newInstance(String param1, String param2) {
        Story_view fragment = new Story_view();
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
        View view = inflater.inflate(R.layout.fragment_story_view, container, false);
        // Inflate the layout for this fragment
        videoView = view.findViewById(R.id.video);

        imageView = view.findViewById(R.id.exite);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();

                // Create a FragmentTransaction
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Set the custom animations
                //transaction.setCustomAnimations(R.anim.zoom_out, R.anim.zoom_out);

// Navigate back to the previous fragment
                getActivity().getSupportFragmentManager().popBackStack();

// Commit the transaction
                transaction.commit();


                Meaow.meowBottomNavigation.setVisibility(View.VISIBLE);
            }
        });

        String videoUrl = "https://videos.pond5.com/drone-helicopter-flying-close-footage-047484714_main_xxl.mp4";
        Uri videoUri = Uri.parse(videoUrl);


        videoView.setVideoURI(videoUri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!videoView.isPlaying()){
                    videoView.start();
                    Toast.makeText(getActivity(), "not play", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "play", Toast.LENGTH_SHORT).show();
                }
            }
        });



        videoView.setOnTouchListener(new View.OnTouchListener() {
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
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }
}