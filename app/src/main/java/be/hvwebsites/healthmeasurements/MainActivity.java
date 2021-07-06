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
//    private BellyViewModel bellyViewModel;
    private TextView latestMeasurementsView;
    private boolean bellies = false;
    public static final int INTENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestMeasurementsView = findViewById(R.id.resumeinfo);


        // Test a file
//        String baseDir = Environment.getDataDirectory().getAbsolutePath();
        String baseDir = getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File bellyFile = new File(baseDir,"test.txt");
        String[] fileLines = new String[1000];
        if (bellyFile.exists()){
            try {
                Scanner inFile = new Scanner(bellyFile);
                int i = 0;
                while (inFile.hasNext()){
                    fileLines[i] = inFile.nextLine();
                    i++;
                }
                if (i>0){
                    bellies = true;
                }
                inFile.close();
                latestMeasurementsView.setText("eerste lijn: " + fileLines[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
/*
            try {
                PrintWriter outFile = new PrintWriter(testFile);
                outFile.println("<date><24052021><bellyRadius><211>");
                outFile.println("<date><25052021><bellyRadius><221>");
                outFile.println("<date><26052021><bellyRadius><231>");
                outFile.println("<date><27052021><bellyRadius><241>");
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
*/
            latestMeasurementsView.setText("er zijn nog geen Belly Measurements !");
        }
    }

    // Als op knop Belly wordt gedrukt
    public void startBellyAct(View view) {
        if (bellies){
            // Als er bellies zijn, toon bellies in BellyActivity
            Intent intent = new Intent(this, BellyActivity.class);
            startActivity(intent);
        }else {
            // Als er geen bellies zijn, ga naar NewBellyMeasurement
            Intent intent = new Intent(this,
                    NewBellyMeasurementActivity.class);
            // als je antwoord terug verwacht, het antwoord wordt verwerkt in onActivityResult
            startActivityForResult(intent,INTENT_REQUEST_CODE);

        }
    }

    // verwerken vn replyIntent vn NewBelly vr insert
    public void onActivityResult(int requestcode, int resultcode, Intent bellyIntent) {

        super.onActivityResult(requestcode, resultcode, bellyIntent);

        if (requestcode == INTENT_REQUEST_CODE && resultcode == RESULT_OK){
            Belly belly = new Belly(
                    bellyIntent.getStringExtra(NewBellyMeasurementActivity.EXTRA_INTENT_KEY_DATE),
                    bellyIntent.getFloatExtra(NewBellyMeasurementActivity.EXTRA_INTENT_KEY_RADIUS,
                            0));
            //bellyViewModel.insertBelly(belly);
        } else {
            Toast.makeText(getApplicationContext(),
                    "empty reply from New Belly activity or cancel", Toast.LENGTH_LONG).show();
        }
    }

}