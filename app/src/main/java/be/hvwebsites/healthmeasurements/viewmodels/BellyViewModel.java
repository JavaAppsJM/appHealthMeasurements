package be.hvwebsites.healthmeasurements.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;

public class BellyViewModel extends AndroidViewModel {
    private BellyRepository repository;
    private LiveData<List<Belly>> bellyList;

    public BellyViewModel(Application application){
        super(application);
        repository = new BellyRepository(application);
        bellyList = repository.getBellyList();
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
}
