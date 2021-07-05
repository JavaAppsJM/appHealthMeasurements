package be.hvwebsites.healthmeasurements.repositories;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.LiveData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.database.BellyDB;
import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.files.BellyFile;

public class BellyRepository{
    private LiveData<List<Belly>> bellyList;
    private Belly latestBelly;
    private Belly readBelly;
    private List<Belly> tmpBellyList;
    private int maxDateInt;

    public BellyRepository(Application application){
//        String baseDir = Environment.getDataDirectory().getAbsolutePath();
//        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
//        File testFile = new File(baseDir,"test.txt");
        tmpBellyList = new ArrayList<>();
//        bellyList = (LiveData<List<Belly>>) tmpBellyList;
    }

    public boolean initializeRepository(File bellyFile){
        if (fileNrBellyList(bellyFile)){
            // Bellyfile lezen is gelukt en bellies zitten in bellyList
            // Bepaal latestbelly
            latestBelly = readBelly;
            for (int j = 0; j < tmpBellyList.size() ; j++) {
                if (latestBelly.getDateInt() < tmpBellyList.get(j).getDateInt()){
                    latestBelly = tmpBellyList.get(j);
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean fileNrBellyList(File bellyFile){
        // Belly File lezen
        if (bellyFile.exists()){
            try {
                Scanner inFile = new Scanner(bellyFile);
                int i = 0;
                while (inFile.hasNext()){
                    readBelly = getBellyFromFileLine(inFile.nextLine());
                    tmpBellyList.add(readBelly);
                    i++;
                }
                inFile.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public List<Belly> getTBellyList() {
        return tmpBellyList;
    }

    public Belly getBellyFromFileLine(String fileLine){
        Belly belly = new Belly();
        // fileLine splitsen in belly argumenten
        String[] fileLineContent = fileLine.split("<");
        for (int i = 0; i < fileLineContent.length; i++) {
            if (fileLineContent[i].matches("date.*")){
                belly.setDate(fileLineContent[i+1].replace(">",""));
            }
            if (fileLineContent[i].matches("bellyRadius.*")){
                belly.setBellyRadius(Float.valueOf(fileLineContent[i+1].replace(">","")));
            }
        }
        return belly;
    }

    public LiveData<List<Belly>> getBellyList() {
        return bellyList;
    }

    public Belly getLatestBelly(){
        return latestBelly;
    }

    public boolean insertBelly(Belly belly, File bellyFile){
        // Toevoegen aan bellylist
        tmpBellyList.add(belly);
//        bellyList = (LiveData<List<Belly>>) tmpBellyList;

        // Wegschrijven nr file
        if (bellyListNrFile(bellyFile)){
            // Wegschrijven gelukt
            return true;
        }else {
            // Wegschrijven mislukt
            return false;
        }
    }

    public boolean bellyListNrFile(File bellyFile){
        // Wegschrijven nr file
        if (bellyFile.exists()){
            try {
                PrintWriter outFile = new PrintWriter(bellyFile);
                for (int i = 0; i < tmpBellyList.size(); i++) {
                    outFile.println(makeFileLine(tmpBellyList.get(i)));
                }
                outFile.close();
                return true;
            } catch (FileNotFoundException e){
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public String makeFileLine(Belly belly){
        String fileLine = "<date><" + belly.getDateInt()
                + "><bellyRadius><" + String.valueOf(belly.getBellyRadius()) + ">";
        return fileLine;
    }

    public boolean deleteBelly(Belly belly, File bellyFile){
        // Zoek belly in bellylist
        if (tmpBellyList.remove(belly)){
            // belly is uit de belly list
            // Wegschrijven nr file
            if (bellyListNrFile(bellyFile)){
                // Wegschrijven gelukt
                return true;
            }else {
                // Wegschrijven mislukt
                return false;
            }
        }else {
            // belly niet in bellylist
            return true;
        }
    }

    public void updateBelly(Belly newBelly){
//        executor.submit(new updateBellyAsyncTask(bellyDAO, newBelly));
    }

}
