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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.repositories.BellyRepository;
import be.hvwebsites.healthmeasurements.viewmodels.BellyViewModel;

public class MainActivity extends AppCompatActivity {
    public static final int INTENT_REQUEST_CODE = 1;
    public static final String BELLY_FILE = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView latestMeasurementsView = findViewById(R.id.resumeinfo);

        // Ophalen belly measurements
        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File bellyFile = new File(baseDir, BELLY_FILE);
        // TODO: Kan hiervoor geen repository gebruikt worden ?
        String[] fileLines = new String[1000];
        if (bellyFile.exists()){
            try {
                Scanner inFile = new Scanner(bellyFile);
                int i = 0;
                if (inFile.hasNext()){
                    fileLines[i] = inFile.nextLine();
                }
                inFile.close();
                latestMeasurementsView.setText("laatste meting: " + fileLines[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            latestMeasurementsView.setText("er zijn nog geen Belly Measurements !");
        }
    }

    // Als op knop Belly wordt gedrukt
    public void startBellyAct(View view) {
            // Ongeacht of er bellies zijn, ga naar BellyActivity. Indien er bellies zijn worden ze getoond anders wordt er naar NewBellyActivity gegaan
            Intent intent = new Intent(this, BellyActivity.class);
            startActivity(intent);
    }
}