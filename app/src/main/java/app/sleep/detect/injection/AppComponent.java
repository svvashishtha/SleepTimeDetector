package app.sleep.detect.injection;

import android.content.Context;

import javax.inject.Singleton;

import app.sleep.detect.sleepManager.SleepManagerService;
import app.sleep.detect.ui.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class,
        ContextModule.class,
        UtilsModule.class,
ViewModelModule.class})
public interface AppComponent {


    void inject(SleepManagerService sleepManagerService);

    void inject(MainActivity mainActivity);

    final class Initializer {
        public static AppComponent init(Context context) {
            return DaggerAppComponent.builder().
                    contextModule(new ContextModule(context)).
                    roomModule(new RoomModule(context)).
                    build();
        }
    }
}
