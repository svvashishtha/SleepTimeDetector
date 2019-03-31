package app.sleep.detect.data;

import com.google.gson.Gson;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class SleepObject {
    @TypeConverters(DateConverter.class)
    @PrimaryKey
    @NonNull
    Date date;
    private long gotoSleepTime = 0;//last time the device accessed in night
    private long wakeUpTime = 0;// first time the device is accessed in morning

    public SleepObject() {
        date = new Date();
    }

    public long getGotoSleepTime() {
        return gotoSleepTime;
    }

    public void setGotoSleepTime(long lastAccessedTime) {
        this.gotoSleepTime = lastAccessedTime;
    }

    public long getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(long firstAccessedTime) {
        this.wakeUpTime = firstAccessedTime;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
