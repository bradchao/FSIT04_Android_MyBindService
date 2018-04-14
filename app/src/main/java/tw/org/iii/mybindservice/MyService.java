package tw.org.iii.mybindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private LocalBinder binder = new LocalBinder();

    public MyService() {
        Log.v("brad", "MyService()");
    }

    public class LocalBinder extends Binder {
        MyService getService() {
            Log.v("brad", "getService()");
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("brad", "onBind()");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("brad", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("brad", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("brad", "onDestroy");
    }
}
