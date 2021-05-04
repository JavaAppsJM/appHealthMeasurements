package be.hvwebsites.healthmeasurements.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.database.BellyDB;
import be.hvwebsites.healthmeasurements.entities.Belly;

public class BellyRepository{
    private BellyDAO bellyDAO;
    private LiveData<List<Belly>> bellyList;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public BellyRepository(Application application){
        BellyDB db = BellyDB.getDatabase(application);
        bellyDAO = db.bellyDAO();
        bellyList = bellyDAO.getAllBellys();
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public void insertBelly(Belly belly){
//        bellyDAO.insertBellyMeasurement(belly);
//        new insertBellyAsync(bellyDAO).doInBackground(belly);
        executor.submit(new insertBellyAsyncTask(bellyDAO, belly));
    }

    public void deleteBelly(Belly belly){
        executor.submit(new deleteBellyAsyncTask(bellyDAO, belly));
    }

    public void updateBelly(Belly newBelly){
        bellyDAO.updateBellyMeasurement(newBelly.getDate(), newBelly.getBellyRadius());
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

    /*
private static class insertBellyAsync extends AsyncTask<Belly, Void, Void>{
        private BellyDAO mAsyncBellyDao;

        insertBellyAsync(BellyDAO dao){
            mAsyncBellyDao = dao;
        }

        void doInBackground(Belly belly){
            mAsyncBellyDao.insertBellyMeasurement(belly);
        }

        @Override
        protected Void doInBackground(Belly... bellies) {
            mAsyncBellyDao.insertBellyMeasurement(bellies[0]);
            return null;
        }
    }
*/
}
