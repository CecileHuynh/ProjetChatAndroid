package com.cpelyon.cechu.projetchatandroid;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageUser extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Ajout des fragments
        adapter.AddFragment(new FragmentContact(),"Contacts");
        adapter.AddFragment(new FragmentConversation(),"Conversations");

        //Configuration de l'Adapter
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }


}

