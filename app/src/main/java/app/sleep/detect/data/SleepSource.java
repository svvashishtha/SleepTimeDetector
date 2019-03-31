package app.sleep.detect.data;

import java.util.Date;

public interface SleepSource {

    interface Repo {
        void insertSleepData(SleepObject sleepObject);

        void getSleepObject(Date date, SleepObjectOperationResult sleepObjectOperationResult);
    }

    interface SleepObjectOperationResult {
        void onDataAvailable(SleepObject sleepObject);

        void onDataNotAvailable();
    }
}
