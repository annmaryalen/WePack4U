package com.example.wepack4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavMenu extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_menu);

        bottomNavigationView=findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new StorePageFragment()).commit();
    }

    //Bottom Nav Bar navigates to where if any of the item is selected
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                    Fragment fragment=null;

                    switch (menuitem.getItemId())
                    {
                        case R.id.storepage:
                            fragment = new StorePageFragment();
                            break;

                        case R.id.cart:
                            fragment = new CartFragment();
                            break;

                        case R.id.aboutyou:
                            fragment = new AboutYouFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();

                    return true;
                }
            };

}