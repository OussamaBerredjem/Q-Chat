package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.transition.ChangeBounds;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import java.util.ArrayList;
import java.util.Locale;


public class Search_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> fullname;
    int index = 0;
    String r;
    MySearchAdapter myAdapter,myresult;
    ArrayList<ChatInfo> suggest;
    ArrayList<Search_info> result,first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        result = new ArrayList<>();

        myresult = new MySearchAdapter(result,this);

        Bundle bundle = getIntent().getExtras();

        EditText editText = findViewById(R.id.edit_search);

        fullname = new ArrayList<>();

        suggest = bundle.getParcelableArrayList("na");


        first = new ArrayList<>();




        Toast.makeText(this, "size : "+suggest.size(), Toast.LENGTH_SHORT).show();

        if (suggest != null) {

            first = redifiner(first,suggest);

            recyclerView = findViewById(R.id.suggest_search);

            recyclerView.setVisibility(View.INVISIBLE);

            myAdapter = new MySearchAdapter(result, this);
            LinearLayoutManager layoutManagerPro = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            ((LinearLayoutManager) layoutManagerPro).setStackFromEnd(true);
            layoutManagerPro.setReverseLayout(true);
            recyclerView.setLayoutManager(layoutManagerPro);
            result.addAll(first);
            recyclerView.setAdapter(myAdapter);

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Search_Activity.this, "is showing", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
            }
        },400);

        for (ChatInfo chatInfo : suggest){

        fullname.add(chatInfo.getFullname());


        }



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {


                    if (editText.getText().toString().isEmpty()) {
                        first = redifiner(first,suggest);
                        result.clear();
                        result.addAll(first);
                        myAdapter.notifyDataSetChanged();

                    } else {
                        for (Search_info chatInfo : first) {
                            if (chatInfo.getFullname().toString().toLowerCase(Locale.ROOT).replace("<font color = #cc0029>", "").replace("</font>", "").contains(s.toString().toLowerCase(Locale.ROOT)) && !result.contains(chatInfo)) {
                                String text = chatInfo.getFullname().toString();
                                String text2 = text.toLowerCase(Locale.ROOT);
                                String searchQuery = editText.getText().toString().toLowerCase(Locale.ROOT);

                                SpannableString spannableString = new SpannableString(text);
                                int startIndex = text2.indexOf(searchQuery);
                                int endIndex = startIndex + searchQuery.length();

                                if (startIndex >= 0) {
                                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                                chatInfo.setFullname(spannableString);
                                result.add(chatInfo);
                                myAdapter.notifyItemInserted(result.size() - 1);


                            } else if (!chatInfo.getFullname().toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))) {

                                int i = result.indexOf(chatInfo);
                                result.remove(chatInfo);
                                myAdapter.notifyDataSetChanged();

                            } else if (chatInfo.getFullname().toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT)) && result.contains(chatInfo)) {

                                int index = result.indexOf(chatInfo);

                                String text = chatInfo.getFullname().toString();
                                String text2 = text.toLowerCase(Locale.ROOT);
                                String searchQuery = editText.getText().toString().toLowerCase(Locale.ROOT);

                                SpannableString spannableString = new SpannableString(text);
                                int startIndex = text2.indexOf(searchQuery);
                                int endIndex = startIndex + searchQuery.length();

                                if (startIndex >= 0) {
                                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                                chatInfo.setFullname(spannableString);
                                result.set(index, chatInfo);
                                myAdapter.notifyItemChanged(index);
                                myAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                }catch (Exception e){}


            }

            @Override
            public void afterTextChanged(Editable s) {
            myAdapter.notifyDataSetChanged();
            }
        });




    }

    private ArrayList<Search_info> redifiner(ArrayList<Search_info> first, ArrayList<ChatInfo> suggest) {
        first.clear();
        for (ChatInfo chatInfo : suggest){
            SpannableString spannableString = new SpannableString(chatInfo.getFullname());
            first.add(new Search_info(spannableString,chatInfo.getPhotourl(),chatInfo.getDate(),chatInfo.getMessage(),chatInfo.getVue(),chatInfo.uid));
        }
        return first;
    }

    private void config() {
        findViewById(android.R.id.content).setTransitionName("go");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        ChangeBounds bounds = new ChangeBounds();
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();

        materialContainerTransform.addTarget(android.R.id.content);

        materialContainerTransform.setDuration(500);

        getWindow().setSharedElementEnterTransition(materialContainerTransform);
        getWindow().setSharedElementExitTransition(materialContainerTransform);
    }



    public void notification(View view){

    }
    /**public void coversation(View view){
        Intent intent = new Intent(this,chat_home.class);
        Bundle bundle = new Bundle();
        bundle = ActivityOptions.makeSceneTransitionAnimation(Profile_amis.this,layout,"go").toBundle();

        startActivity(intent,bundle);
    }**/

}