package com.example.ybbaek.test_rc;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;

    private double pitch;
    private double roll;
    private double yaw;

    //timestamp and dt
    private double timestamp;
    private double dt;

    // for radian -> dgree
    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    TextView xVal, yVal, zVal, tv_pitch, tv_roll, tv_yaw;
    TextView tv_get;

    EditText edtxt_ip, edtxt_cmd;

    String str_xVal, str_yVal, str_zVal, str_pitch, str_roll, str_yaw;

    Button btn_send, btn_go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxt_ip = findViewById(R.id.edtxt_IP);
        edtxt_cmd = findViewById(R.id.edtxt_cmd);

        Thread myThread = new Thread(new MyServer());
        myThread.start();

        tv_get = findViewById(R.id.tv_get);

        btn_go = findViewById(R.id.btn_go);
        btn_send = findViewById(R.id.btn_send);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        xVal = findViewById(R.id.tv_x);
        yVal = findViewById(R.id.tv_y);
        zVal = findViewById(R.id.tv_z);

        tv_pitch = findViewById(R.id.tv_pitch);
        tv_roll = findViewById(R.id.tv_roll);
        tv_yaw = findViewById(R.id.tv_yaw);

        //Toast.makeText(MainActivity.this, "gyro start: " , Toast.LENGTH_SHORT).show();

        if (gyroscopeSensor == null){
            Toast.makeText(this, "The device has no Gyroscope", Toast.LENGTH_SHORT).show();
            finish();
        }
        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                str_xVal = String.format("%1.03f", sensorEvent.values[0]);
                str_yVal = String.format("%1.03f", sensorEvent.values[1]);
                str_zVal = String.format("%1.03f", sensorEvent.values[2]);

                xVal.setText("xVal: " + str_xVal);
                yVal.setText("yVal: " + str_yVal);
                zVal.setText("zVal: " + str_zVal);

                float gyroX = sensorEvent.values[0];
                float gyroY = sensorEvent.values[1];
                float gyroZ = sensorEvent.values[2];

                dt = (sensorEvent.timestamp - timestamp) * NS2S;
                timestamp = sensorEvent.timestamp;

                if (dt - timestamp * NS2S != 0) {
                    pitch = pitch + gyroY * dt;
                    roll = roll + gyroX * dt;
                    yaw = yaw + gyroZ * dt;

                    str_pitch = String.format("%1.03f", pitch);
                    str_roll = String.format("%1.03f", roll);
                    str_yaw = String.format("%1.03f", yaw);

                    tv_pitch.setText("Pitch: " + str_pitch);
                    tv_roll.setText("Roll: " + str_roll);
                    tv_yaw.setText("Yaw: " + str_yaw);

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
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
        b.execute(edtxt_ip.getText().toString(),edtxt_cmd.getText().toString());
    }

    public void btn_go(View v) {
        BackgroundTask b = new BackgroundTask();
        b.execute(edtxt_ip.getText().toString(),"go".toString());
        //Toast.makeText(this,"up",Toast.LENGTH_SHORT).show();

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
