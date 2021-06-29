package be.hvwebsites.healthmeasurements.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.repositories.BellyRepositoryDB;

public class BellyViewModelDB extends AndroidViewModel {
    private BellyRepositoryDB repository;
    private LiveData<List<Belly>> bellyList;
    private Belly latestBelly;

    public BellyViewModelDB(Application application){
        super(application);
        repository = new BellyRepositoryDB(application);
        bellyList = repository.getBellyList();
        latestBelly = repository.getLatestBelly();
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public void insertBelly(Belly belly){
        repository.insertBelly(belly);
    }

    public void deleteBelly(Belly belly){
        repository.deleteBelly(belly);
    }

    public void updateBelly(Belly belly){
        repository.updateBelly(belly);
    }

    public Belly getLatestBelly(){
        return latestBelly;
    }
}
