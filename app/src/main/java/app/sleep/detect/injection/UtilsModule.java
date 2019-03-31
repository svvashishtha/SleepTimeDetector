package app.sleep.detect.injection;

import app.sleep.detect.AppExecutors;
import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {
    @Provides
    AppExecutors providesAppExecutors()
    {
        return new AppExecutors();
    }
}
