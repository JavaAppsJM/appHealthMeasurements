package be.hvwebsites.healthmeasurements.repositories;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.LiveData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.database.BellyDB;
import be.hvwebsites.healthmeasurements.entities.Belly;

public class BellyRepositoryDB {
    private BellyDAO bellyDAO;
    private LiveData<List<Belly>> bellyList;
    private Belly latestBelly;
    private int maxDateInt;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public BellyRepositoryDB(Application application){
        BellyDB db = BellyDB.getDatabase(application);
        bellyDAO = db.bellyDAO();
        bellyList = bellyDAO.getAllBellys();

        maxDateInt = bellyDAO.getMaxDateint();
        latestBelly = bellyDAO.getLatestBelly(maxDateInt);
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public Belly getLatestBelly(){
        return latestBelly;


/*
        getBellyAsyncTask bellyAsyncTask = new getBellyAsyncTask(bellyDAO);
        executor.submit(bellyAsyncTask);
        return bellyAsyncTask.getBelly();
*/
    }

/*
    public class getBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Belly belly;

        getBellyAsyncTask(BellyDAO dao){
            mAsyncBellyDao = dao;
        }

        @Override
        public void run() {
            int maxDateint = mAsyncBellyDao.getMaxDateint();
            belly = mAsyncBellyDao.getLatestBelly(maxDateint);
        }

        public Belly getBelly() {
            return belly;
        }
    }
*/

    // insert nr DB
    public void insertBelly(Belly belly){
        executor.submit(new insertBellyAsyncTask(bellyDAO, belly));
    }

    public class insertBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Belly belly;

        insertBellyAsyncTask(BellyDAO dao, Belly belly){
            mAsyncBellyDao = dao;
            this.belly = belly;
        }

        @Override
        public void run() {
            mAsyncBellyDao.insertBellyMeasurement(belly);
        }
    }

    public void deleteBelly(Belly belly){
        executor.submit(new deleteBellyAsyncTask(bellyDAO, belly));
    }

    public class deleteBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Belly belly;

        deleteBellyAsyncTask(BellyDAO dao, Belly belly){
            mAsyncBellyDao = dao;
            this.belly = belly;
        }

        @Override
        public void run() {
            mAsyncBellyDao.deleteBellyMeasurement(belly);
        }
    }

    public void updateBelly(Belly newBelly){
        executor.submit(new updateBellyAsyncTask(bellyDAO, newBelly));
    }

    public class updateBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Belly newBelly;

        updateBellyAsyncTask(BellyDAO dao, Belly newBelly){
            mAsyncBellyDao = dao;
            this.newBelly = newBelly;
        }

        @Override
        public void run() {
            mAsyncBellyDao.updateBellyMeasurement(
                            newBelly.getDateInt(),
                            newBelly.getBellyRadius());
        }
    }
}
