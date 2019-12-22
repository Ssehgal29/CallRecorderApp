package com.example.callrecorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart,btnStop;
    File file;
    String fileExtension =".3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setId();
        setListner();
    }

    public void setId(){
        btnStart=findViewById(R.id.startRecording);
        btnStop=findViewById(R.id.stopRecording);
    }

    public void setListner(){
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startRecording:
                startRecording();
                break;
            case R.id.stopRecording:
                stopRecording();
                break;
        }
    }
    public void startRecording(){
        Toast.makeText(this, "Recording Started!", Toast.LENGTH_SHORT).show();
    }
    public void stopRecording(){
        Toast.makeText(this, "Recording Stopped!", Toast.LENGTH_SHORT).show();
    }
    public String getFilePath(){
        String filePath = Environment.getExternalStorageDirectory().getPath();
        file = new File(filePath,"CallRecorder");
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath()+"/"+fileExtension);
    }
}
