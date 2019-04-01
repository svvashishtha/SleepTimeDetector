package app.sleep.detect.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.sleep.detect.R;
import app.sleep.detect.SleepApplication;
import app.sleep.detect.sleepManager.SleepManagerService;
import app.sleep.detect.data.SleepObject;
import app.sleep.detect.injection.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    AlarmManager alarmManager;

    RecyclerView sleepRecycler;

    @Inject
    ViewModelFactory viewModelFactory;

    MainViewModel mainViewModel;
    SleepAdapter sleepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SleepApplication.getApplication().getAppComponent().inject(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        sleepRecycler = findViewById(R.id.sleep_recycler);
        setUpRecycler();
        setupViewModel();
        setOnClickListeners();
    }

    private void setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).
                get(MainViewModel.class);
        mainViewModel.getSleepObjects().observe(this, new Observer<ArrayList<SleepObject>>() {
            @Override
            public void onChanged(ArrayList<SleepObject> sleepObjects) {
                sleepAdapter.setList(sleepObjects);
            }
        });
        mainViewModel.fetchSleepData();
    }

    private void setUpRecycler() {
        sleepRecycler.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        sleepAdapter = new SleepAdapter();
        sleepRecycler.setAdapter(sleepAdapter);

    }

    private void setOnClickListeners() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, 20);

//                schedule an alarm to wake up service at 8 pm
                Intent intent = new Intent(MainActivity.this, SleepManagerService.class);
                intent.putExtra(SleepManagerService.SLEEP_TRACKER, true);
                PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 0, pintent);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SleepManagerService.class);
                intent.putExtra(SleepManagerService.SLEEP_TRACKER, false);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
            }
        });
    }
}
