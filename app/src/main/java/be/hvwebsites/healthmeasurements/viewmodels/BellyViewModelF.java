package be.hvwebsites.healthmeasurements.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.FileNotFoundException;
import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.files.BellyFile;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.repositories.BellyRepositoryF;

public class BellyViewModelF extends AndroidViewModel {
    private BellyRepositoryF repository;
    private LiveData<List<Belly>> bellyList;
    private Belly latestBelly;

    public BellyViewModelF(Application application){
        super(application);
        repository = new BellyRepositoryF(application);
        bellyList = repository.getBellyList();
        latestBelly = repository.getLatestBelly();
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public void insertBelly(Belly belly){
        try {
            repository.insertBelly(belly);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
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
