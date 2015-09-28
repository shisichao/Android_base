package com.example.shi.connectservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * 1.如何启动一个 Service 并且向该 Service 传递数据
 * 2.如何与被绑定的 Service 进行通信
 * 3.如何侦听被绑定的 Service 的内部状态,通过Java中回调的概念
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private EditText etData;
    private MyService.Binder binder;
    private TextView tvout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etData = (EditText) findViewById(R.id.etData);
        tvout = (TextView) findViewById(R.id.tvout);

        findViewById(R.id.btnstartservice).setOnClickListener(this);
        findViewById(R.id.btnstopservice).setOnClickListener(this);
        findViewById(R.id.btnbindservice).setOnClickListener(this);
        findViewById(R.id.btnunbindservice).setOnClickListener(this);
        findViewById(R.id.btnsyncData).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnstartservice:
                Intent i = new Intent(this,MyService.class);
                i.putExtra("data",etData.getText().toString());//传递数据
                startService(i);
                break;
            case R.id.btnstopservice:

                stopService(new Intent(this,MyService.class));

                break;
            case R.id.btnbindservice:
                bindService(new Intent(this,MyService.class),this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnunbindservice:
                unbindService(this);
                break;
            case R.id.btnsyncData:

                if (binder!=null){
                    binder.setData(etData.getText().toString());//通过Binder进行数据通信，比通过Myservice的oncreate函数更便捷，因为这是直接的方法调用不用跟随Intent进行数据传输
                }
                break;
        }
    }


    @Override//bindservic执行之后会执行onServiceConnected
    public void onServiceConnected(ComponentName name, IBinder service) {//IBinder 会访问到Myservice里的onBinder的返回值即一个Binder实例

        binder = (MyService.Binder) service;
        binder.getservice().setCallback(new MyService.Callback() {
            @Override
            public void onDataChange(String data) {

                Message msg = new Message();
                Bundle b= new Bundle();
                b.putString("data",data);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvout.setText(msg.getData().getString("data"));
        }
    };
}
