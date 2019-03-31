package app.sleep.detect.data;

import java.util.ArrayList;
import java.util.Date;

public interface SleepSource {

    interface Repo {
        void insertSleepData(SleepObject sleepObject);

        void getSleepObject(Date date, SleepObjectOperationResult sleepObjectOperationResult);

        void getAllSleepObjects(SleepObjectListOperationResult callback);
    }

    interface SleepObjectOperationResult {
        void onDataAvailable(SleepObject sleepObject);

        void onDataNotAvailable();
    }

    interface SleepObjectListOperationResult {
        void onDataAvailable(ArrayList<SleepObject> sleepObject);

        void onDataNotAvailable();
    }
}
