package be.hvwebsites.healthmeasurements.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.returnInfo.ReturnInfo;

public class BellyViewModel extends AndroidViewModel {
    private BellyRepository repository;
    private File bellyFile;
    private LiveData<List<Belly>> bellyList;
    private List<Belly> tBellyList = new ArrayList<>();
    private Belly latestBelly;

    public BellyViewModel(Application application){
        super(application);
        repository = new BellyRepository();
    }

    public ReturnInfo initializeBellyViewModel(File bellyFile){
        ReturnInfo bellyToestand = repository.initializeRepository(bellyFile);
        if (bellyToestand.getReturnCode() == 0){
            tBellyList = repository.getTBellyList();
            latestBelly = repository.getLatestBelly();
        } else if (bellyToestand.getReturnCode() == 100){
        }else {
        }
        return bellyToestand;
    }

    public boolean storeBellies(File bellyFile, List<Belly> bellyList){
        if (repository.storeBellies(bellyFile, bellyList)){
            return true;
        }else {
            return false;
        }
    }

    public void setBellyFile(File bellyFile) {
        this.bellyFile = bellyFile;
    }

    public List<Belly> gettBellyList() {
        return tBellyList;
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public boolean insertBelly(Belly belly, File bellyFile){
        if (repository.insertBelly(belly, bellyFile)){
            return true;
        }else {
            return false;
        }
    }

    public void deleteBelly(Belly belly, File bellyFile){
        repository.deleteBelly(belly, bellyFile);
    }

    public void updateBelly(Float oldRadius, Belly belly, File bellyFile){
        repository.updateBelly(oldRadius, belly,bellyFile);
    }

    public Belly getLatestBelly(){
        return latestBelly;
    }
}
