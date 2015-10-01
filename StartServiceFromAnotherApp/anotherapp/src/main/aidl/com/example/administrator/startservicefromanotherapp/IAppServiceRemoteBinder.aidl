// IAppServiceRemoteBinder.aidl
package com.example.administrator.startservicefromanotherapp;

// Declare any non-default types here with import statements
//新建aidl文件夹，复制app下同样的aidl下的包和文件
interface IAppServiceRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setData(String data);
}
