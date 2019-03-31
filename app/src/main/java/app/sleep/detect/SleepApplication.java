package app.sleep.detect;

import android.app.Application;

import app.sleep.detect.injection.AppComponent;

public class SleepApplication extends Application {

    static SleepApplication sInstance;
    AppComponent appComponent;

    public static SleepApplication getApplication() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        appComponent = AppComponent.Initializer.init(getApplicationContext());
    }

    AppComponent getAppComponent() {
        return appComponent;
    }
}
