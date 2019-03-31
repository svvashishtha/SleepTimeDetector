package app.sleep.detect.ui;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import app.sleep.detect.data.SleepObject;
import app.sleep.detect.data.SleepRepo;
import app.sleep.detect.data.SleepSource;

public class MainViewModel extends ViewModel {

    private SleepSource.Repo sleepRepo;
    private MutableLiveData<ArrayList<SleepObject>> sleepObjects = new MutableLiveData<>();

    @Inject
    public MainViewModel(SleepRepo sleepRepo) {
        this.sleepRepo = sleepRepo;
    }


    public LiveData<ArrayList<SleepObject>> getSleepObjects() {
        return sleepObjects;
    }

    public void fetchSleepData() {
        sleepRepo.getAllSleepObjects(new SleepSource.SleepObjectListOperationResult() {
            @Override
            public void onDataAvailable(ArrayList<SleepObject> sleepObject) {
                sleepObjects.postValue(sleepObject);
            }

            @Override
            public void onDataNotAvailable() {
                sleepObjects.postValue(new ArrayList<SleepObject>());
            }
        });
    }
}
