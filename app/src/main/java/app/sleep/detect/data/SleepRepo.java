package app.sleep.detect.data;


import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import app.sleep.detect.AppExecutors;
import app.sleep.detect.Logger;

public class SleepRepo implements SleepSource.Repo {


    private Logger logger;
    private SleepDao sleepDao;
    private AppExecutors appExecutors;

    @Inject
    public SleepRepo(SleepDao sleepDao, AppExecutors appExecutors, Logger logger) {
        this.sleepDao = sleepDao;
        this.appExecutors = appExecutors;
        this.logger = logger;
    }


    @Override
    public void insertSleepData(final SleepObject sleepObject) {
        appExecutors.backgroundIO().execute(new Runnable() {
            @Override
            public void run() {
                if (sleepObject != null) {
                    sleepDao.insertSleepObject(sleepObject);
                    logger.fLog("Successfully saved sleepObject");
                    logger.fLog(sleepObject.toString());
                }
            }
        });

    }

    @Override
    public void getSleepObject(final Date date, final SleepSource.SleepObjectOperationResult sleepObjectOperationResult) {
//get sleep object for current data if time is between 8pm - 12 am
        //otherwise return sleep object for date.day -1 (12 am to 10am)
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                String dateString = null;
//                if (date.getHour() >= 20 && date.getHour() <= 23) {
                dateString = DateConverter.formatter.format(date);
                 /*}else {
                    OffsetDateTime tempDate = date.minusDays(1);
                    dateString = tempDate.format(DateConverter.formatter);
                }*/


                final SleepObject sleepObject = sleepDao.getSleepObject(dateString);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (sleepObject != null) {
                            logger.fLog("Successfully retrieved sleep object");
                            sleepObjectOperationResult.onDataAvailable(sleepObject);
                        } else {
                            logger.fLog("sleep object not available");
                            sleepObjectOperationResult.onDataNotAvailable();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void getAllSleepObjects(final SleepSource.SleepObjectListOperationResult callback) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<SleepObject> sleepObjects = (ArrayList<SleepObject>) sleepDao.getAllSleepObjects();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (sleepObjects.size() > 0) {
                            callback.onDataAvailable(sleepObjects);

                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });

            }
        });
    }


}
