package com.itver.alayon.chatapplicationtest.activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itver.alayon.chatapplicationtest.R;
import com.itver.alayon.chatapplicationtest.adapters.SectionPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //FIREBASE
    private FirebaseAuth authentication;

    //UI ELEMENTS
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //ADAPTERS
    private SectionPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = authentication.getCurrentUser();
        if(currentUser == null){
            //Regresarlo al start activity
            returnToStartActivity();
        }
    }

    private void initComponents(){

        authentication = FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat appplication");

        tabLayout.addTab(tabLayout.newTab().setText("CAMERA"));
        tabLayout.addTab(tabLayout.newTab().setText("REQUESTS"));
        tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
        tabLayout.addTab(tabLayout.newTab().setText("FRIENDS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

       // tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }







    private void returnToStartActivity(){
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    //----------------------MENU---------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
     }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLogout:
                FirebaseAuth.getInstance().signOut();
                returnToStartActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
