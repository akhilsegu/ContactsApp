package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        replaceFragment(new ContactsFragment());
    }

    public void replaceFragment(Fragment fragment) {
        androidx.fragment.app
                .FragmentManager fragmentManager
                = getSupportFragmentManager();
        androidx.fragment.app
                .FragmentTransaction fragmentTransaction
                = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_dummy1, fragment);
        fragmentTransaction.commit();
    }
}


