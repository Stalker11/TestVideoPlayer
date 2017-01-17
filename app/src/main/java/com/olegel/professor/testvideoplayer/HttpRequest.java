package com.olegel.professor.testvideoplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest implements Runnable{
    private HttpURLConnection connection;
    private RequestCallBack callBack;
    private Handler hand;
    private static final String TAG = HttpRequest.class.getSimpleName();
    public void Request(RequestCallBack callBack){
        this.callBack = callBack;

    }

    public HttpRequest(RequestCallBack callBack, Handler hand) {
        this.callBack = callBack;
        this.hand = hand;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("http://google.com");
            connection = (HttpURLConnection) url.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Log.d(TAG, "run: "+callBack+" "+connection.getContent());
                Bundle bu = new Bundle();
                bu.putString("dd",connection.getContent().toString());
                Message mes = new Message();
                mes.setData(bu);
                Log.d(TAG, "onSucsess: "+bu.get("dd"));

                callBack.onSucsess(connection.getContent().toString());
            }else {
                callBack.onError(connection.getResponseMessage());
            }
        }catch (MalformedURLException e){
            callBack.onError(e.getMessage());
        }catch (IOException e){
            callBack.onError(e.getMessage());
        }finally {
            connection.disconnect();
        }
    }
}
