package tw.org.iii.mybindservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyService myService;
    private boolean isBound = false;
    private TextView mesg;
    private MyHandler handler;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.v("brad", "onServiceConnected");

            MyService.LocalBinder binder = (MyService.LocalBinder)iBinder;
            myService = binder.getService();
            myService.setHandler(handler);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v("brad", "onServiceDisconnected");
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);
        handler = new MyHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }


    public void test1(View view) {
        myService.doSomething();
    }

    public void test2(View view) {
        myService.showTextView();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("brad", "MainActivity:MyHandler:handlerMessage()");
            mesg.setText("Lottery: " + msg.arg1);
        }
    }

}
