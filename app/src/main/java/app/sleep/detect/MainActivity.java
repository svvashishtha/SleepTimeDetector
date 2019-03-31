package app.sleep.detect;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, 20);

//                schedule an alarm to wake up service at 6 pm
                Intent intent = new Intent(MainActivity.this, SleepManagerService.class);
                intent.putExtra(SleepManagerService.SLEEP_TRACKER, true);
                startService(intent);
//                PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 0, pintent);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SleepManagerService.class);
                intent.putExtra(SleepManagerService.SLEEP_TRACKER, false);
                startService(intent);
                /*PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);*/
            }
        });
    }
}
