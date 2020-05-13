package com.example.grabarvoz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder;
    Button btnSave, btnPlay;
    public static final int RECORD_AUDIO = 0;
    private File audiofile = null;
    static final String TAG= "MediaRecording";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            verifyPermission();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btn_grabar);
        btnSave.setBackgroundColor(Color.GREEN);
        btnPlay = findViewById(R.id.btn_reproducir);

        btnSave.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                btnSaveAction(event, v);
                return true;
            }
        });


    }


    public void btnSaveAction(MotionEvent event, View v){
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    grabar(v);
                    break;
                case MotionEvent.ACTION_UP:
                    stop(v);
                    break;
            }
    }

    public void grabar(View v) {

    File dir = Environment.getExternalStorageDirectory();

        try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();

        btnSave.setBackgroundColor(Color.RED);
        btnSave.setText("Grabando...");

    }



    public void stop(View v){

        try{
            recorder.stop();
            recorder.reset(); // You can reuse the object by going back to setAudioSource() step
            recorder.release(); // Now the object cannot be reused
            //addRecordingToMediaLibrary();
        }catch (Exception e){
            Toast.makeText(this, "Error al parar Grabacion", Toast.LENGTH_LONG).show();
        }

        btnSave.setBackgroundColor(Color.GREEN);
        btnSave.setText("REC");
    }

    private void verifyPermission() {
        int permsRequestCode = 100;
        String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int audioPermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
        int storagePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (audioPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
        } else {
            requestPermissions(perms, permsRequestCode);
        }
    }


}
