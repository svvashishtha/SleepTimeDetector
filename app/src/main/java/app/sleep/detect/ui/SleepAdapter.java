package app.sleep.detect.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import app.sleep.detect.R;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepItemHolder> {

    @NonNull
    @Override
    public SleepItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SleepItemHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.sleep_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SleepItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SleepItemHolder extends RecyclerView.ViewHolder{

        public SleepItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
