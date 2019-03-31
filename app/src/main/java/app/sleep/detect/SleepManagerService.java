package app.sleep.detect;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import java.util.Date;

import javax.inject.Inject;

import app.sleep.detect.data.SleepObject;
import app.sleep.detect.data.SleepRepo;
import app.sleep.detect.data.SleepSource;

public class SleepManagerService extends Service implements DeviceIdleListener.OnDeviceAccessedListener {
    public static final String SLEEP_TRACKER = "SLEEP_TRACKER";
    private static final String TRACKING = "TRACKING";
    @Inject
    DeviceIdleListener deviceIdleListener;
    @Inject
    SharedPreferencesProvider sharedPreferencesProvider;
    @Inject
    Logger logger;
    @Inject
    SleepRepo sleepRepo;
    boolean trackingSleep;
    SleepObject sleepObject;
    long currentTIme = 0;
    long deviceIdleTime = 0;
    Handler mHandler;
    private int deviceAccessedWhileAsleepCounter = 0;
    private Runnable resetCounterRunnable = new Runnable() {
        @Override
        public void run() {
            deviceAccessedWhileAsleepCounter = 0;
        }
    };

    public SleepManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SleepApplication.getApplication().getAppComponent().inject(this);
        logger.fLog("Creating service at " + System.currentTimeMillis());
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null &&
                intent.getExtras() != null &&
                intent.getExtras().containsKey(SLEEP_TRACKER)) {
            if (intent.getBooleanExtra(SLEEP_TRACKER, true)) {
                startTracker();
            } else {
                stopTracker();
            }
            sharedPreferencesProvider.putData(TRACKING, trackingSleep);
        } else {
            if (sharedPreferencesProvider.getBooleanData(SLEEP_TRACKER, false)) {
                startTracker();
            } else {
                stopTracker();
            }
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        logger.fLog("destroying service at " + System.currentTimeMillis());
        sleepRepo.insertSleepData(sleepObject);
    }

    private void stopTracker() {

        if (trackingSleep) {
            logger.fLog("Stopping tracker at " + System.currentTimeMillis());
            trackingSleep = false;
            deviceIdleListener.unregister();
            stopSelf();
        }
    }

    private void startTracker() {
        if (!trackingSleep) {
            logger.fLog("Starting tracker at " + System.currentTimeMillis());
            trackingSleep = true;
            sleepRepo.getSleepObject(new Date(), new SleepSource.SleepObjectOperationResult() {
                @Override
                public void onDataAvailable(SleepObject sleepObj) {
                    sleepObject = sleepObj;
                }

                @Override
                public void onDataNotAvailable() {
                    sleepObject = new SleepObject();
                }
            });
            deviceIdleListener.register(this);

        }
    }

    @Override
    public void deviceAccessed() {
        currentTIme = System.currentTimeMillis();
        if (sleepObject.getGotoSleepTime() > 0) {
            // when waking up
            // if device is accessed after 2 hours of going to sleep, then this is wakeup time.
            if (sleepObject.getWakeUpTime() == 0) {
                logger.fLog("setting from 3 first accessed time in sleepObject " + currentTIme);
                sleepObject.setWakeUpTime(currentTIme);
            }
            // if device is not accessed for more than 1 hour after wake up time is set, user is not
            // awake. set wake up time when the device is accessed next.
            else if (currentTIme - sleepObject.getWakeUpTime() > 3600000) {
                logger.fLog("setting from 4 first accessed time in sleepObject " + currentTIme);
                sleepObject.setWakeUpTime(currentTIme);
            }
            // if sleep time is set and there is a value in wake up time variable then,
            // if user is accessing their phone within 1 hour
            else if (sleepObject.getWakeUpTime() != 0 &&
                    currentTIme - sleepObject.getWakeUpTime() < 3600000) {
                deviceAccessedWhileAsleepCounter++;
                // set a handler that will reset deviceAccessedWhileAsleepCounter if it is not
                // updated within 30 minutes
                mHandler.removeCallbacks(resetCounterRunnable);
                mHandler.postDelayed(resetCounterRunnable, 30 * 60 * 1000);
                // if device is accessed 3 times within 30 minutes, user is awake
                if (deviceAccessedWhileAsleepCounter >= 3) {
                    stopTracker();
                }
            }
        }
    }

    @Override
    public void deviceIdle() {
        //if last accessed time is 0 or less than 2 hours ago set last accessed time as now
        deviceIdleTime = System.currentTimeMillis();
        if (sleepObject.getGotoSleepTime() == 0) {
            logger.fLog(" setting form 1 last accessed time in sleepObject " + deviceIdleTime);
            sleepObject.setGotoSleepTime(deviceIdleTime);

        } else if (deviceIdleTime - sleepObject.getGotoSleepTime() < 7200000) {
            logger.fLog("setting from 2 last accessed time in sleepObject " + deviceIdleTime);
            sleepObject.setGotoSleepTime(deviceIdleTime);
        }
    }
}
