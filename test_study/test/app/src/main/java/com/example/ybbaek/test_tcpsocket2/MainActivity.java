package com.example.ybbaek.test_tcpsocket2;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2;
    TextView tv_get;
    Button btn_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);

        btn_left = findViewById(R.id.btn_left);
        tv_get = findViewById(R.id.tv_get);

        btn_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        Thread myThread = new Thread(new MyServer());
        myThread.start();
    }


    class MyServer implements Runnable{

        ServerSocket ss;
        Socket mysocket;
        DataInputStream dis;
        String message;
        Handler handler = new Handler();
        //BufferedReader bufferedReader;

        @Override
        public void run() {

            try{
                ss= new ServerSocket(9700);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"waiting for client", Toast.LENGTH_SHORT).show();
                    }
                });

                while (true){
                    mysocket = ss.accept();
                    dis = new DataInputStream(mysocket.getInputStream());
                    message = dis.readUTF();


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(),"client msg: " + message, Toast.LENGTH_SHORT).show();
                            tv_get.setText(message);

                            if (message.equals("left")){
                                //Toast.makeText(getApplicationContext(),"Open camera on other device", Toast.LENGTH_SHORT).show();

                            }else if (message.equals("right")){
                                //Toast.makeText(getApplicationContext(),"Open camera on other device", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void btn_send(View v){
        BackgroundTask b = new BackgroundTask();
        b.execute(e1.getText().toString(),e2.getText().toString());
    }

    public void btn_up(View v) {
        BackgroundTask b = new BackgroundTask();
        b.execute(e1.getText().toString(),"up".toString());

    }


    class BackgroundTask extends AsyncTask<String, Void, String> {

        Socket s;
        DataOutputStream dos;
        String ip, message;

        @Override
        protected String doInBackground(String... params) {

            ip = params[0];
            message = params[1];

            try{
                s = new Socket(ip, 9700);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(message);

                dos.close();

                s.close();

            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
