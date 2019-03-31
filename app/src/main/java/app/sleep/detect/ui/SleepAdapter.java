package app.sleep.detect.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import app.sleep.detect.R;
import app.sleep.detect.data.DateConverter;
import app.sleep.detect.data.SleepObject;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepItemHolder> {

    ArrayList<SleepObject> sleepObjects = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    DecimalFormat df = new DecimalFormat("#.#");
    @NonNull
    @Override
    public SleepItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SleepItemHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.sleep_row_layout, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull SleepItemHolder holder, int position) {
        SleepObject sleepObject = sleepObjects.get(holder.getAdapterPosition());
        holder.date.setText(DateConverter.dateToTimeStamp(sleepObject.getDate()));
        cal.setTime(new Date(sleepObject.getGotoSleepTime()));
        holder.startTime.setText(String.format("%d:%d %d", cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE), cal.get(Calendar.AM_PM)));
        cal.setTime(new Date(sleepObject.getWakeUpTime()));
        holder.endTime.setText(String.format("%d:%d %d", cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE), cal.get(Calendar.AM_PM)));
        float duration = sleepObject.getWakeUpTime() - sleepObject.getGotoSleepTime();
        duration = duration / (1000 * 60 * 60);
        holder.duration.setText(String.format("%s hours", df.format(duration)));
    }

    @Override
    public int getItemCount() {
        return sleepObjects.size();
    }

    public void setList(ArrayList<SleepObject> sleepObjects) {
        this.sleepObjects = sleepObjects;
    }

    public class SleepItemHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView startTime;
        TextView endTime;
        TextView duration;

        public SleepItemHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.header);
            startTime = itemView.findViewById(R.id.goto_sleep_time);
            endTime = itemView.findViewById(R.id.wake_up_time);
            duration = itemView.findViewById(R.id.duration_value);
        }
    }
}
