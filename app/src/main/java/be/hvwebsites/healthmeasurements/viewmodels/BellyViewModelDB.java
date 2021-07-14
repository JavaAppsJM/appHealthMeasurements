package be.hvwebsites.healthmeasurements.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.entities.Bellydb;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.repositories.BellyRepositoryDB;

public class BellyViewModelDB extends AndroidViewModel {
    private BellyRepositoryDB repository;
    private LiveData<List<Bellydb>> bellyList;
    private Bellydb latestBelly;

    public BellyViewModelDB(Application application){
        super(application);
        repository = new BellyRepositoryDB(application);
        bellyList = repository.getBellyList();
        latestBelly = repository.getLatestBelly();
    }

    public LiveData<List<Bellydb>> getBellyList() {
        return bellyList;
    }

    public void insertBelly(Bellydb belly){
        repository.insertBelly(belly);
    }

    public void deleteBelly(Bellydb belly){
        repository.deleteBelly(belly);
    }

    public void updateBelly(Bellydb belly){
        repository.updateBelly(belly);
    }

    public Bellydb getLatestBelly(){
        return latestBelly;
    }
}
