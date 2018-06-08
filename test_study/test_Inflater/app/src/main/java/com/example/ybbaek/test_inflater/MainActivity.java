package com.example.ybbaek.test_inflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    FrameLayout frame;
    View view;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame.findViewById(R.id.frame);
        //xml 파일을 객체화 시킬 준비.
        LayoutInflater in = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        view = in.inflate(R.layout.inner_layout, null);
        frame.addView(view);

        btn_ok = (Button)findViewById(R.id.btn_ok);

    }
}
