package be.hvwebsites.healthmeasurements;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.List;

import be.hvwebsites.healthmeasurements.adapters.BellyListAdapter;
import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.viewmodels.BellyViewModel;

public class BellyActivity extends AppCompatActivity {
    private BellyViewModel bellyViewModel;
    public static final int INTENT_REQUEST_CODE = 1;
    public static final String EXTRA_INTENT_KEY_ACTION =
            "be.hvwebsites.healthmeasurements.INTENT_KEY_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belly);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BellyActivity.this,
                        NewBellyMeasurementActivity.class);
                // als je antwoord terug verwacht, het antwoord wordt verwerkt in onActivityResult
                startActivityForResult(intent,INTENT_REQUEST_CODE);
                // omdat forResult niet werkt bij update via viewHolder gaan we met een gewone startActivity werken
                // maar in comment gezet omdat de activity voor een update geen datum wijziging mag hebben dus hiervoor hebben we een aparte activity nodig
                // intent.putExtra(EXTRA_INTENT_KEY_ACTION, "insert");
                // startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BellyListAdapter adapter = new BellyListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a viewmodel from the viewmodelproviders
        bellyViewModel = ViewModelProviders.of(this).get(BellyViewModel.class);

        // Add an observer to observe changes
        bellyViewModel.getBellyList().observe(this, new Observer<List<Belly>>() {
            @Override
            public void onChanged(@Nullable final List<Belly> bellies) {
                // Update the cached copy of the data in the adapter.
                adapter.setBellyList(bellies);
            }
        });

        // om te kunnen swipen in de recyclerview
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Belly sBelly = adapter.getBellyAtPosition(position);
                Toast.makeText(BellyActivity.this,
                        "Deleting belly measurement on " + sBelly.getFormatDate(),
                        Toast.LENGTH_LONG).show();
                // Delete Belly measurement
                bellyViewModel.deleteBelly(sBelly);
            }
        });
        helper.attachToRecyclerView(recyclerView);

        // verwerken replyIntent vn UpdateBelly vr update
        Intent newBellyIntent = getIntent();
        if (newBellyIntent.hasExtra(EXTRA_INTENT_KEY_ACTION)){
            Belly belly = new Belly(
                    newBellyIntent.getStringExtra(NewBellyMeasurementActivity.EXTRA_INTENT_KEY_DATE),
                    newBellyIntent.getFloatExtra(NewBellyMeasurementActivity.EXTRA_INTENT_KEY_RADIUS,
                            0));
            if (newBellyIntent.getStringExtra(EXTRA_INTENT_KEY_ACTION).equals("update")){
                bellyViewModel.updateBelly(belly);
            }
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
            bellyViewModel.insertBelly(belly);
        } else {
            Toast.makeText(getApplicationContext(),
                    "empty reply from New Belly activity", Toast.LENGTH_LONG).show();
        }
    }
}