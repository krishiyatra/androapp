package com.warmani.gapped;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by demo on 11-May-15.
 */
public class SampleApplication extends Application {
    public void onCreate() {
        Parse.initialize(this, "hWNbe1ddTaEVafTg1bHBWheB6bH2DAp0K7p1tiXY", "0cRLd5qpgFGs81RmyJ9bOw7sUp73g257Oe6TkLu5");


    }
}
