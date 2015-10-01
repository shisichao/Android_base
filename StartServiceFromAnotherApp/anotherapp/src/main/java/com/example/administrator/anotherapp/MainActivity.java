package com.example.administrator.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.startservicefromanotherapp.IAppServiceRemoteBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Intent serviceIntent;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = (EditText) findViewById(R.id.etInput);

        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName(
                "com.example.administrator.startservicefromanotherapp", "com.example.administrator.startservicefromanotherapp.AppService"));//包名（需要启动的App),启动的服务的类名可以写全路劲。

        findViewById(R.id.btnstartAppService).setOnClickListener(this);
        findViewById(R.id.btnstopAppAervice).setOnClickListener(this);
        findViewById(R.id.btnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnunbindAppAervice).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);




    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnstartAppService:

                startService(serviceIntent);

                break;
            case R.id.btnstopAppAervice:

                stopService(serviceIntent);
                break;
            case R.id.btnbindAppService:
                bindService(serviceIntent,this, Context.BIND_AUTO_CREATE);

                break;
            case R.id.btnunbindAppAervice:

                unbindService(this);
                binder=null;
                break;
            case R.id.btnSync:

                if(binder!=null){

                    try {
                        binder.setData(etInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        System.out.println("绑定Service");
        System.out.println(service);

        binder = IAppServiceRemoteBinder.Stub.asInterface(service);//不能直接进行强制类型转换如binder = (IAppServiceRemoteBinder)service
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
    private IAppServiceRemoteBinder binder= null;
}
