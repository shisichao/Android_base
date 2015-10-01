package com.example.administrator.checkpermissionincode;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/10/1.
 */
public class Hello {

    public static final String PERMISSION_SAY_HELLO = "com.example.administrator.checkpermissionincode.permission.SAY_HELLO";
    public static  void sayHello(Context context){

        int checkResult = context.checkCallingOrSelfPermission(PERMISSION_SAY_HELLO);//判断是否具有该权限
        if (checkResult!= PackageManager.PERMISSION_GRANTED){
            throw  new SecurityException("执行sayHELLO方法需要com.example.administrator.checkpermissionincode.permission.SAY_HELLO权限");

        }
        System.out.println("hello 即可学院");
    }
}
