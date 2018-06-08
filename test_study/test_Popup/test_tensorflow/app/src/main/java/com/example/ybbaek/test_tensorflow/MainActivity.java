package com.example.ybbaek.test_tensorflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("tensorflow_inference");
    }

    private static final String MODEL_FILE = "file:///android_asset/freeze.pb";
    private static final String INPUT_NODE = "X";
    private static final String OUTPUT_NODE = "op_restore";

    private static final int[] INPUT_SIZE = {1};

    private TensorFlowInferenceInterface inferenceInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMyModel();

        View button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runMyModel();
            }
        });
    }


    private void initMyModel() {
        inferenceInterface = new TensorFlowInferenceInterface();
        inferenceInterface.initializeTensorFlow(getAssets(), MODEL_FILE);
    }


    private void runMyModel() {
        float[] inputFloats = new float[1];
        inputFloats[0] = getValue(R.id.editText);

        inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SIZE, inputFloats);
        inferenceInterface.runInference(new String[] {OUTPUT_NODE});

        float[] res = {1};
        inferenceInterface.readNodeFloat(OUTPUT_NODE, res);

        String msg = "input: " + inputFloats[0];
        msg += "\nResult: " +res[0];

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(msg);
    }

    //private static final String MODEL_FILE = "file:///android_asset/freeze.pb";



    private float getValue(int res) {
        EditText editText = (EditText) findViewById(res);
        return Float.parseFloat(editText.getText().toString());

    }
}
