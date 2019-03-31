package app.sleep.detect.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {SleepObject.class}, version = 1)
public abstract class SleepDatabase extends RoomDatabase {

    public abstract SleepDao sleepDao();
}
