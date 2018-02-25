package com.example.garrettwong99.bc;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private CameraSource source;
    private SurfaceView mPreview;


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of Camera
        //mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new SurfaceView(this);
        mPreview.getHolder().addCallback(new SurfaceCallback());
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);



    }
    private class SurfaceCallback implements SurfaceHolder.Callback{
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            BarcodeDetector detector = new BarcodeDetector.Builder(MainActivity.this).build();
            detector.setProcessor(new Processor(MainActivity.this));
            source = new CameraSource.Builder(MainActivity.this,detector)
                    .setAutoFocusEnabled(true).build();
            if ( ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( MainActivity.this, new String[] {  Manifest.permission.CAMERA  },
                        5354 );
            }
            try{
                source.start(mPreview.getHolder());
            } catch(SecurityException e){
                Toast.makeText(MainActivity.this,"security excpetion",Toast.LENGTH_LONG);
            } catch(IOException e){
                Toast.makeText(MainActivity.this,"io exception",Toast.LENGTH_LONG);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if(source!=null){
                //mCamera.stopPreview();
                // mCamera.setPreviewCallback(null);

                source.release();
                source = null;
            }
        }
    }
}
