package com.example.zzzleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.zzzleep.ui.goodmorning.GoodMorningFragment;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodmorning_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, GoodMorningFragment.newInstance())
                    .commitNow();
        }
    }
}