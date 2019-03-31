package app.sleep.detect.injection;

import android.content.Context;

import javax.inject.Singleton;

import app.sleep.detect.SleepManagerService;
import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class, ContextModule.class, UtilsModule.class})
public interface AppComponent {


    void inject(SleepManagerService sleepManagerService);

    final class Initializer {
        public static AppComponent init(Context context) {
            return DaggerAppComponent.builder().
                    contextModule(new ContextModule(context)).
                    roomModule(new RoomModule(context)).
                    build();
        }
    }
}
