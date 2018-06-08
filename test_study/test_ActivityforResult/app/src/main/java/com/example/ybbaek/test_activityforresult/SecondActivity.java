package com.example.ybbaek.test_activityforresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mMsgTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");

        mMsgTxtView = findViewById(R.id.edtxt_msg);
        mMsgTxtView.setText(age + "살" + name);

        findViewById(R.id.btn_result).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", mMsgTxtView.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }
}
