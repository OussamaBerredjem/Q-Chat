package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Meaow extends AppCompatActivity {

    static int position;

   public static MeowBottomNavigation meowBottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaow);

         meowBottomNavigation= findViewById(R.id.meowep);

      // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.message_square));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.notification_bottom));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.account_avatar));


        meowBottomNavigation.show(1,true);

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()){
                    case 1:



                        break;
                    case 2:

                        break;
                    case 3:





                }
                return null;
            }
        });

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                Fragment fragment = null;
                switch (model.getId()) {
                    case 1:
                        fragment = new Screen_chat();
                        break;
                    case 2:
                        fragment = new BlankFragment();

                        break;
                    case 3:
                        fragment = new Profile_info();
                        break;
                }

                // Add the fragment to the back stack and show it
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containero, fragment,null)
                             // Add the fragment to the back stack
                            .commit();
                    //meowBottomNavigation.show(model.getId(),true);
                }


                return null;

            }
        });

        //meowBottomNavigation.show(2,true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}