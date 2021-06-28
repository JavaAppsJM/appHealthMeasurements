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
import be.hvwebsites.healthmeasurements.entities.Belly;

public class BellyRepositoryF {
    private BellyDAO bellyDAO;
    private LiveData<List<Belly>> bellyList;
    private Belly latestBelly;
    private Belly readBelly;
    private List<Belly> tmpBellyList;
    private int maxDateInt;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public BellyRepositoryF(Application application){
        String baseDir = Environment.getDataDirectory().getAbsolutePath();
//        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File testFile = new File(baseDir,"test.txt");
        String[] fileLines = new String[1000];
        if (testFile.exists()){
            try {
                Scanner inFile = new Scanner(testFile);
                int i = 0;
                while (inFile.hasNext()){
                    fileLines[i] = inFile.nextLine();
                    readBelly = getBellyFromFileLine(fileLines[i]);
                    tmpBellyList.add(readBelly);
                    i++;
                }
                inFile.close();
                bellyList = (LiveData<List<Belly>>) tmpBellyList;

                // Bepaal latestbelly
                latestBelly = readBelly;
                for (int j = 0; j < tmpBellyList.size() ; j++) {
                    if (latestBelly.getDateInt() < tmpBellyList.get(j).getDateInt()){
                        latestBelly = tmpBellyList.get(j);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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

    public void insertBelly(Belly belly) throws FileNotFoundException {
//        executor.submit(new insertBellyAsyncTask(bellyDAO, belly));
        // Toevoegen aan bellylist
        tmpBellyList.add(belly);
        bellyList = (LiveData<List<Belly>>) tmpBellyList;

        // Wegschrijven nr file
        String baseDir = Environment.getDataDirectory().getAbsolutePath();
//        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File testFile = new File(baseDir,"test.txt");
        if (testFile.exists()){
            try {
                PrintWriter outFile = new PrintWriter(testFile);
                for (int i = 0; i < tmpBellyList.size(); i++) {
                    outFile.println(makeFileLine(tmpBellyList.get(i)));
                }
                outFile.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    public String makeFileLine(Belly belly){
        String fileLine = "<date><" + belly.getDateInt()
                + "><bellyRadius><" + String.valueOf(belly.getBellyRadius()) + ">";
        return fileLine;
    }

    public void deleteBelly(Belly belly){
//        executor.submit(new deleteBellyAsyncTask(bellyDAO, belly));
    }

    public void updateBelly(Belly newBelly){
//        executor.submit(new updateBellyAsyncTask(bellyDAO, newBelly));
    }
}
