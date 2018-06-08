package com.example.ybbaek.test_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView txt;
    Button btn_start, btn_stop;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_stop = (Button)findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(click);
        btn_stop.setOnClickListener(click);

    }

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch (view.getId()){

                case R.id.btn_start:
                    handler.sendEmptyMessage(0);
                    break;

                case R.id.btn_stop:
                    handler.removeMessages(0);
                    break;

            }


        }
    };

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){

            handler.sendEmptyMessageDelayed(0, 1000);
            count++;
            txt.setText("" + count);

        }

    };

}
