package com.niujunjie.www.framapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.niujunjie.www.framapp.fragment.HomeFragment;


public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, new HomeFragment()).commit();
    }
}
