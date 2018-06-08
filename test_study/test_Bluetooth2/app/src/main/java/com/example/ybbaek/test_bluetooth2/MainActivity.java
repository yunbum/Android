package com.example.ybbaek.test_bluetooth2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private UUID uuid=UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    Button btn_on, btn_off, btn_discover, btn_list, btn_connect;
    ListView list;

    TextView tv_SelDev, tv_read_data, tv_send_data;

    private static final int REQUEST_ENABLED =0;
    private static final int REQUEST_DISCOVERABLED =0;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice mDevice;
    ConnectedThread mConnectedThread;

    private Handler handler;

    ArrayList<String> devices = new ArrayList<>();
    ArrayList<String> devices_addr = new ArrayList<>();

    String DEV_ADDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_on = findViewById(R.id.btn_on);
        btn_off = findViewById(R.id.btn_off);
        btn_discover = findViewById(R.id.btn_discover);
        btn_list = findViewById(R.id.btn_list);
        btn_connect = findViewById(R.id.btn_connect);

        list = findViewById(R.id.lv_list);
        tv_SelDev = findViewById(R.id.tv_SelDev);
        tv_read_data = findViewById(R.id.tv_read_data);
        tv_send_data = findViewById(R.id.tv_send_data);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null){
            Toast.makeText(this, "Bluetooth not supported!.", Toast.LENGTH_SHORT).show();
            finish();
        }

        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLED);

            }
        });

        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();

            }
        });

        btn_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isDiscovering()){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVERABLED);
                }

            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                for(BluetoothDevice bt : pairedDevices){
                    devices.add(bt.getName());
                    devices_addr.add(bt.getAddress());

                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, devices);

                list.setAdapter(arrayAdapter);

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sel_item = (String) parent.getAdapter().getItem(position);
                DEV_ADDR = (String) devices_addr.get(position);
                tv_SelDev.setText(sel_item + " - " + DEV_ADDR);
            }


        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void connected(BluetoothSocket mSocket) {
        mConnectedThread = new ConnectedThread(mSocket);
        mConnectedThread.start();
    }

    private class ConnectedThread extends Thread{
        private BluetoothSocket mSocket;
        private InputStream mInStream;
        private OutputStream mOutStream;

        public ConnectedThread(BluetoothSocket socket){
            mSocket = socket;
            InputStream tmpIn =null;
            OutputStream tmpOut = null;

            try {
                tmpIn = mSocket.getInputStream();
                tmpOut = mSocket.getOutputStream();
            }catch (IOException e){
                e.printStackTrace();
            }

            mInStream = tmpIn;
            mOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];

            int bytes;

            while (true){
                try{
                    bytes = mInStream.read(buffer);
                    final String incomingMsg = new String(buffer, 0, bytes);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_read_data.setText(incomingMsg);
                        }
                    });

                }catch (IOException e){
                    break;
                }
            }
        }

        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            try{
                mOutStream.write(bytes);
            }catch (IOException e){
                Toast.makeText(getApplicationContext(), "write: err!.", Toast.LENGTH_SHORT).show();

            }
        }

        public void cancel(){
            try{
                mSocket.close();
            }catch (IOException e){}

        }

        public void SendMessage(View v){
            byte[] bytes = tv_send_data.getText().toString().getBytes(Charset.defaultCharset());
            mConnectedThread.write(bytes);
        }
    }

    private class ConnectThread extends Thread{
        private BluetoothSocket mSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            mDevice = device;

        }
    }
}
