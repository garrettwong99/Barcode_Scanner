package com.example.garrettwong99.bc;

import android.content.Context;
import android.content.Loader;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;

/**
 * Created by garrettwong99 on 2/24/2018.
 */
public class Processor implements Detector.Processor {
    private Context context;
    public Processor(Context context){
        this.context = context;
    }
    @Override
    public void release() {
    }
    @Override
    public void receiveDetections(Detector.Detections detections) {
        
    }
}
