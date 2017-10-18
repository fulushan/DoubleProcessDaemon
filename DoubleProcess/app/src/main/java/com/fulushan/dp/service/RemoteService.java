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

public class RemoteService extends Service {
    private MyBinder binder;
    private MyConn myConn;
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class), myConn, Context.BIND_IMPORTANT);

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
            return "RemoteService";
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Info","连接本地服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(RemoteService.this,"本地服务被杀死",Toast.LENGTH_SHORT).show();
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class), myConn, Context.BIND_IMPORTANT);

        }
    }
}
