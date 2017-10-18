package com.fulushan.dp.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.fulushan.dp.IProcessServiceAidlInterface;

public class LocalService extends Service {
    private MyBinder binder;
    private MyConn myConn;
    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class), myConn,Context.BIND_IMPORTANT);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if(null==myConn){
            myConn = new MyConn();
        }
    }

    class MyBinder extends IProcessServiceAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getServiceName() throws RemoteException {
            return "LocalService";
        }
    }

    class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Info","连接远程服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(LocalService.this,"远程服务被杀死",Toast.LENGTH_SHORT).show();
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class), myConn,Context.BIND_IMPORTANT);

        }
    }
}
