package com.example.shi.learnbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION ="com.example.shi.learnbroadcastreceiver.intent.receiver";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接受到了消息,消息的内容是："+intent.getStringExtra("data"));
        abortBroadcast();//中断广播，优先级低的接收器就不会接受到消息了
    }
}
