package com.example.nb_sh_001.test_gyroscope;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

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
    String str_xVal, str_yVal, str_zVal, str_pitch, str_roll, str_yaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Toast.makeText(this, "No Gyroscope", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Gyroscope detected", Toast.LENGTH_SHORT).show();
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

                if (dt - timestamp*NS2S!=0){
                    pitch = pitch + gyroY*dt;
                    roll = roll + gyroX*dt;
                    yaw = yaw + gyroZ*dt;

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
}











