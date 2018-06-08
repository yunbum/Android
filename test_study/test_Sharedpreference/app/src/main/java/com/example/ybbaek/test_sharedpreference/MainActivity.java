package com.example.ybbaek.test_sharedpreference;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    CheckBox my_check;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_check = (CheckBox)findViewById(R.id.my_check);
        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        boolean ch = pref.getBoolean("c", false);

        my_check.setChecked(ch);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("c", my_check.isChecked());

        edit.commit();

    }
}
