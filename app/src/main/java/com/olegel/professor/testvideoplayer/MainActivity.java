package com.olegel.professor.testvideoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
private VideoView video;
    private MediaController controller;
    private Uri uri;
    private int currentPosition = -1;
    private Handler handler;
    private TextView text;
    private String testText;
    private Executor exec = Executors.newCachedThreadPool();
    public static final String TAG = MainActivity.class.getSimpleName();
   /* url for work with player https://www.youtube.com/watch?v=chvki68McG0
    https://m.youtube.com/watch?v=T3iJqFVyF20&app=m*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // startActivity(new Intent(MainActivity.this,NextVariant.class));
        setContentView(R.layout.activity_main);
        video = (VideoView)findViewById(R.id.video_view);
        controller = new MediaController(this);
        text = (TextView) findViewById(R.id.text_view);
       // controller.setAnchorView(video);
        controller.setMediaPlayer(video);
        handler = new Handler();
        handler = new Handler(Looper.getMainLooper()){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
               Bundle ff = (Bundle) msg.obj;
                Log.d(TAG, "handleMessage: "+msg);
                text.setText(ff.get("dd").toString());
            }
        };
       Runnable runn = new HttpRequest(new TestRequest(), handler);

       Thread tr = new Thread(runn);
        tr.start();
       // controller.setVisibility(View.VISIBLE);
      //  controller.show();
        uri = Uri.parse("rtsp://r9---sn-5hne6nez.googlevideo.com/Cj0LENy73wIaNAltF3JVqIl4TxMYDSANFC0l7HlYMOCoAUIASARg6tS2geOM85xXigELOHlkc0VWSU9FOVkM/7D671602E27E1EFB177BACA3BEFFF95BC2FA790D.482D56A6A0C63B749D7D75BA8633A0762A3C73DB/yt6/1/video.3gp");
       // uri = Uri.parse("/storage/sdcard0/Pictures/MyCameraApp/VID_20170114_152505.mp4");

        if(savedInstanceState != null){
            currentPosition = savedInstanceState.getInt(ProjectConstants.SAVED_INT);

        }
        video.setVideoURI(uri);
//        Log.d(TAG, "onCreate: "+video.getTag().toString());
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
               mediaPlayer.setLooping(true);
                mediaPlayer.start();
                Log.d(TAG, "onPrepared: 1100"+mediaPlayer.getDuration());
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.d(TAG, "onError: "+" "+i+" "+i1+" "+mediaPlayer.getDuration());
                mediaPlayer.reset();
               // mediaPlayer.start();
                //recreate();
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ProjectConstants.SAVED_INT, currentPosition);       
        super.onSaveInstanceState(outState);
    }
private void setText(String str){
    this.testText = str;
    text.setText(testText);
}
    @Override
    protected void onStart() {
        if (currentPosition != -1) {

        }
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                video.setMediaController(controller);
                video.requestFocus();
                mediaPlayer.seekTo(currentPosition);
                mediaPlayer.start();
                Log.d(TAG, "onPrepared: "+mediaPlayer.getDuration());
            }
        });
        Log.d(TAG, "onPrepared: "+15);

        super.onStart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        currentPosition = video.getCurrentPosition();
        super.onPause();
    }
private class TestRequest implements RequestCallBack{

    @Override
    public void onSucsess(final String request) {
/*
Message mes = new Message();
        Log.d(TAG, "onSucsess: "+request);
        Bundle bu = new Bundle();
        bu.putString("dd",request);
        mes.setData(bu);
        Log.d(TAG, "onSucsess: "+bu.get("dd"));
        handler.handleMessage(mes);
*/
        handler.post(new Runnable() {
            @Override
            public void run() {
               text.setText(request);
            }
        });
    }

    @Override
    public void onError(String error) {

    }
}
}
