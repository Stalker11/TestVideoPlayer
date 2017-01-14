package com.olegel.professor.testvideoplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class NextVariant extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    public static final String TAG = NextVariant.class.getSimpleName();
    private SurfaceView surface;
    private MediaPlayer player;
    private SurfaceHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);
        surface = (SurfaceView) findViewById(R.id.surface);
        holder = surface.getHolder();
        holder.addCallback(this);
        String vidAddress = "https://m.youtube.com/watch?v=Bwe3QxoaQ48&itct=CA8QpDAYACITCOu_ppymwdECFcUEDQodNUIB8jIHcmVsYXRlZEjtrsirhbWivE8%3D&client=mv-google&gl=UA&hl=ru";
        try {
            player = new MediaPlayer();
            player.setDisplay(holder);
            player.setDataSource(vidAddress);
            player.prepare();
            player.setOnPreparedListener(this);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        player.start();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
