package com.anusha_peddina.privacyapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.anusha_peddina.privacyapp.R;
import com.anusha_peddina.privacyapp.card.AddNewCardActivity;
import com.anusha_peddina.privacyapp.password.AddPasswordActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    MyViewPagerAdapter viewPagerAdapter;
    private final int PASSWORDS_TAB = 0;
    private final int CARDS_TAB = 1;
    private int selectedTab = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddScreens();
            }
        });

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Passwords"));
        tabLayout.addTab(tabLayout.newTab().setText("Cards"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == PASSWORDS_TAB) {
                    selectedTab = PASSWORDS_TAB;
                } else  if(tab.getPosition() == CARDS_TAB) {
                    selectedTab = CARDS_TAB;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void launchAddScreens() {
        if(selectedTab == 0) {
            launchAddPasswordScreen();
        } else if(selectedTab == 1) {
            launchAddCardScreen();
        }
    }

    private void launchAddCardScreen() {
        Intent intent = new Intent(HomeActivity.this, AddNewCardActivity.class);
        startActivity(intent);
    }

    private void launchAddPasswordScreen() {
        Intent intent = new Intent(HomeActivity.this, AddPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            finish();
            return true;
        }

        if (id == R.id.action_add_password) {
            launchAddPasswordScreen();
            return true;
        }

        if (id == R.id.action_add_card) {
            launchAddCardScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}