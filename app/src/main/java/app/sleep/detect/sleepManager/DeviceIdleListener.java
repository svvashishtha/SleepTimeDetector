package app.sleep.detect.sleepManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import javax.inject.Inject;

import static android.content.Intent.ACTION_DREAMING_STARTED;
import static android.content.Intent.ACTION_DREAMING_STOPPED;
import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_SCREEN_ON;

public class DeviceIdleListener extends BroadcastReceiver {
    String TAG = "DeviceIdleListener";
    Context context;
    OnDeviceAccessedListener onDeviceAccessedListener;

    @Inject
    public DeviceIdleListener(Context context) {
        this.context = context;
    }

    public void register(OnDeviceAccessedListener onDeviceAccessedListener) {
        this.onDeviceAccessedListener = onDeviceAccessedListener;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SCREEN_OFF);
        intentFilter.addAction(ACTION_SCREEN_ON);

        // Dream actions.
        intentFilter.addAction(Intent.ACTION_DREAMING_STARTED);
        intentFilter.addAction(Intent.ACTION_DREAMING_STOPPED);


        context.registerReceiver(this, intentFilter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_SCREEN_ON:
            case ACTION_DREAMING_STOPPED:
                if (onDeviceAccessedListener != null) {
                    onDeviceAccessedListener.deviceAccessed();
                }
                Log.d(TAG, "Screen on");
                break;
            case ACTION_SCREEN_OFF:
            case ACTION_DREAMING_STARTED:
                Log.d(TAG, "Screen off");
                if (onDeviceAccessedListener != null) {
                    onDeviceAccessedListener.deviceIdle();
                }
                break;

        }
    }

    public void unregister() {
        context.unregisterReceiver(this);
    }

    public interface OnDeviceAccessedListener {
        void deviceAccessed();

        void deviceIdle();
    }
}
