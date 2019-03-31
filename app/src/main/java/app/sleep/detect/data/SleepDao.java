package app.sleep.detect.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SleepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSleepObject(SleepObject sleepObject);

    @Query("Select * from SleepObject where date LIKE :date")
    SleepObject getSleepObject(String date);

    @Query("Select * from SleepObject order by date desc")
    List<SleepObject> getAllSleepObjects();
}
