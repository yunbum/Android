package com.example.ybbaek.test_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MY", "--onCreate--");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("MY", "--onStart--");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MY", "--onResume--");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MY", "--onRestart--");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MY", "--onPause--");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MY", "--onStop--");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MY", "--onDestroy--");

    }
}
