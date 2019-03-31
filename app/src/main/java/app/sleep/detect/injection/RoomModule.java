package app.sleep.detect.injection;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import app.sleep.detect.data.SleepDao;
import app.sleep.detect.data.SleepDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private SleepDatabase sleepDatabase;

    public RoomModule(Context context) {
        sleepDatabase = Room.databaseBuilder(context.getApplicationContext(),
                SleepDatabase.class, "sleep.db")
                .build();
    }

    @Singleton
    @Provides
    SleepDatabase providesUserDatabase() {
        return sleepDatabase;
    }

    @Singleton
    @Provides
    SleepDao providesSleepDao()
    {
        return sleepDatabase.sleepDao();
    }
}
