package com.example.callrecorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    Button btnStart,btnStop,btnPlayRecorded,btnStopRecorded;
    File file;
    String fileExtension =".3gp",pathSave;
    private static final String TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 100;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 200;
    private static final int REQUEST_WRIT_EXTERNAL_STORAGE_PERMISSION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForPermissions();
        setId();
        setListner();
        btnStop.setEnabled(false);
        btnPlayRecorded.setEnabled(false);
        btnStopRecorded.setEnabled(false);
    }

    public void setId(){
        btnStart=findViewById(R.id.startRecording);
        btnStop=findViewById(R.id.stopRecording);
        btnPlayRecorded=findViewById(R.id.playRecordedAudio);
        btnStopRecorded=findViewById(R.id.stopRecordedAudio);
    }

    public void setListner(){
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPlayRecorded.setOnClickListener(this);
        btnStopRecorded.setOnClickListener(this);
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
            case R.id.playRecordedAudio:
                playRecordedAudio();
                break;
            case R.id.stopRecordedAudio:
                stopRecordedAudio();
                break;
        }
    }
    public void startRecording(){
        getFilePath();
        setMediaRecorder();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "startRecording: failed due to ", e.getCause());
        }catch (IllegalStateException ise){
        }
        btnStart.setEnabled(false);
        btnPlayRecorded.setEnabled(false);
        btnStopRecorded.setEnabled(false);
        btnStop.setEnabled(true);
        Toast.makeText(this, "Recording Started!", Toast.LENGTH_SHORT).show();
    }
    public void stopRecording(){
        mediaRecorder.stop();
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnPlayRecorded.setEnabled(true);
        btnStopRecorded.setEnabled(false);
        Toast.makeText(this, "Recording Stopped!", Toast.LENGTH_SHORT).show();
    }
    public void playRecordedAudio(){
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        btnPlayRecorded.setEnabled(false);
        btnStopRecorded.setEnabled(true);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(pathSave);
            mediaPlayer.prepare();
        }catch (IOException ioe){
        }
        mediaPlayer.start();
        Toast.makeText(this, "Playing the recorded Audio!", Toast.LENGTH_SHORT).show();
    }
    public void stopRecordedAudio(){
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnPlayRecorded.setEnabled(true);
        btnStopRecorded.setEnabled(false);

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            setMediaRecorder();
        }
    }
    public String getFilePath(){
        String filePath = Environment.getExternalStorageDirectory().getPath();
        file = new File(filePath,"CallRecorder");
        if (!file.exists()) {
            file.mkdirs();
        }
        pathSave=file.getAbsolutePath()+"/"+fileExtension;
        return (pathSave);
    }
    private void setMediaRecorder(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(getFilePath());
    }

    public void askForPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_RECORD_AUDIO_PERMISSION);
        }
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
        }
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRIT_EXTERNAL_STORAGE_PERMISSION);
        }
    }
}
