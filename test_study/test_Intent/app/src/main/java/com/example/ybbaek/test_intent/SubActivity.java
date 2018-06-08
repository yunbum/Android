package com.example.ybbaek.test_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    Button btn_prev;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txt = (TextView)findViewById(R.id.txt);

        Intent intent = getIntent();

        //Bundle
        Bundle bundle = intent.getExtras();

        String str = bundle.getString("t2");

        //String str = intent.getStringExtra("t");

        txt.setText(str);

        btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(SubActivity.this, MainActivity.class);

                startActivity(intent);
                */
                finish();
            }
        });
    }
}
