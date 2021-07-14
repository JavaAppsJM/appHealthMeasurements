package be.hvwebsites.healthmeasurements.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.database.BellyDB;
import be.hvwebsites.healthmeasurements.entities.Bellydb;

public class BellyRepositoryDB {
    private BellyDAO bellyDAO;
    private LiveData<List<Bellydb>> bellyList;
    private Bellydb latestBelly;
    private int maxDateInt;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public BellyRepositoryDB(Application application){
        BellyDB db = BellyDB.getDatabase(application);
        bellyDAO = db.bellyDAO();
        bellyList = bellyDAO.getAllBellys();

        maxDateInt = bellyDAO.getMaxDateint();
        latestBelly = bellyDAO.getLatestBelly(maxDateInt);
    }

    public LiveData<List<Bellydb>> getBellyList() {
        return bellyList;
    }

    public Bellydb getLatestBelly(){
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
    public void insertBelly(Bellydb belly){
        executor.submit(new insertBellyAsyncTask(bellyDAO, belly));
    }

    public class insertBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Bellydb belly;

        insertBellyAsyncTask(BellyDAO dao, Bellydb belly){
            mAsyncBellyDao = dao;
            this.belly = belly;
        }

        @Override
        public void run() {
            mAsyncBellyDao.insertBellyMeasurement(belly);
        }
    }

    public void deleteBelly(Bellydb belly){
        executor.submit(new deleteBellyAsyncTask(bellyDAO, belly));
    }

    public class deleteBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Bellydb belly;

        deleteBellyAsyncTask(BellyDAO dao, Bellydb belly){
            mAsyncBellyDao = dao;
            this.belly = belly;
        }

        @Override
        public void run() {
            mAsyncBellyDao.deleteBellyMeasurement(belly);
        }
    }

    public void updateBelly(Bellydb newBelly){
        executor.submit(new updateBellyAsyncTask(bellyDAO, newBelly));
    }

    public class updateBellyAsyncTask implements Runnable{
        private BellyDAO mAsyncBellyDao;
        private Bellydb newBelly;

        updateBellyAsyncTask(BellyDAO dao, Bellydb newBelly){
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
