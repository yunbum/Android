package com.example.ybbaek.test_thread_bad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Thread mThread;

    private int mCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startThread(View view) {
        if (mThread == null){
            mThread = new Thread("my thread"){
              public void run(){
                  for (int i =0; i<100; i++){
                      try {
                          mCount++;
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          break;
                      }

                      Log.d("my thread", "thread on" + mCount);
                      //Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                      //Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();

                  }
              }
            };
            mThread.start();
        }
    }

    public void stopThread(View view) {
        if (mThread != null){
            mThread.interrupt();
            mThread = null;
            mCount = 0;
        }
    }
}
