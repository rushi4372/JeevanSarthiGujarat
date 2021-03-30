package com.example.jeevansarthigujarat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BottomTabActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HomeFragment homeFragment;
    private BookNowFragment bookNowFragment;
    private HealthFileFragment healthFileFragment;

    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        header = navigationView.getHeaderView(0);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.main_drawer_profile) {
                    //loadFragment(new galleryFragment());
                    new ToastIntentClass(BottomTabActivity.this,ProfileActivity.class);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        viewPager = findViewById(R.id.tab_viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        homeFragment = new HomeFragment();
        bookNowFragment = new BookNowFragment();
        healthFileFragment = new HealthFileFragment();

        tabLayout.setupWithViewPager(viewPager);



        /*tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });*/

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(homeFragment,"Home");
        viewPagerAdapter.addFragment(bookNowFragment,"Emergency");
        viewPagerAdapter.addFragment(healthFileFragment,"Health Files");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_help);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_medical_folder);



    }

    @Override
    public void onBackPressed() {

        //viewPager.getCurrentItem();
        //String s = String.valueOf(tabLayout.getSelectedTabPosition());
        //new ToastIntentClass(TabDemoActivity.this,s);
        int tabposition=tabLayout.getSelectedTabPosition();
        if(tabposition==2){
            viewPager.setCurrentItem(1);

        }
        else if (tabposition==1){
            viewPager.setCurrentItem(0);
        }
        else{

            super.onBackPressed();

        }


    }



    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>(); //store fragments in this list
        private List<String> fragmentTitle = new ArrayList<>();//titles of fragments in this list

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            fragmentTitle.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}