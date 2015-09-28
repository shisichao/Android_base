package com.example.shi.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.AndroidCharacter;

public class MyService extends Service {
    private boolean running= false;
    private String data= "这是默认信息";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();

    }

    public class Binder extends android.os.Binder{

        public  void setData(String Data){
            MyService.this.data = Data;
        }
        public MyService getservice(){
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");//接收activity的传出的参数
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建一个线程
        new Thread(){
            @Override
            public void run() {
                super.run();

                running = true;
                int i= 0;
                while(running){
                    //执行的代码
                    i++;
                    String str = i+":"+data;
                    System.out.println(str);
                    if (callback!= null){
                        callback.onDataChange(str);
                    }
                    //每次执行后休眠1秒
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    private  Callback callback= null ;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback() {
        return callback;
    }

    public static interface  Callback{
        void onDataChange(String data);
    }
}
