package be.hvwebsites.healthmeasurements;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.viewmodels.BellyViewModel;

public class MainActivity extends AppCompatActivity {
//    private BellyViewModel bellyViewModel;
    private TextView latestMeasurementsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestMeasurementsView = findViewById(R.id.resumeinfo);

        // Test a file
//        String baseDir = Environment.getDataDirectory().getAbsolutePath();
        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File testFile = new File(baseDir,"test.txt");
        String[] fileLines = new String[1000];
        if (testFile.exists()){
            try {
                Scanner inFile = new Scanner(testFile);
                int i = 0;
                while (inFile.hasNext()){
                    fileLines[i] = inFile.nextLine();
                    i++;
                }
                inFile.close();
                latestMeasurementsView.setText("eerste lijn: " + fileLines[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try {
                PrintWriter outFile = new PrintWriter(testFile);
                outFile.println("<date><24052021><bellyRadius><111>");
                outFile.println("<date><25052021><bellyRadius><121>");
                outFile.println("<date><26052021><bellyRadius><131>");
                outFile.close();
                latestMeasurementsView.setText("lege file er zijn 3 lijnen weggeschreven");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Get a viewmodel from the viewmodelproviders
/*
        bellyViewModel = ViewModelProviders.of(this).get(BellyViewModel.class);
        // Lees de latest values
        Belly latestBelly = bellyViewModel.getLatestBelly();
        String latestInfo = "Belly: "
                + String.valueOf(latestBelly.getBellyRadius());
        latestMeasurementsView.setText(latestInfo);
*/
    }

    // Als op knop Belly wordt gedrukt
    public void startBellyAct(View view) {
//        Intent intent = new Intent(this, BellyActivity.class);
        Intent intent = new Intent(this, BellyActivityF.class);
        startActivity(intent);
    }
}