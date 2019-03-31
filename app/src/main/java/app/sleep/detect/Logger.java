package app.sleep.detect;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

public class Logger {

    Context context;

    @Inject
    public Logger(Context context) {
        this.context = context;
    }

    public void fLog(String message) {
        writeStringAsFile(message);


    }

    void writeStringAsFile(final String fileContents) {


        try {
            File outputFIle = new File(context.getFilesDir(), "SyncWorkerRuntimeLog.txt");
            if (!outputFIle.exists()) {
                outputFIle.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFIle, true));
            out.write(fileContents + " \n======\n");
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e("EXCEP_LOG", e.getMessage());
        }
    }
}
