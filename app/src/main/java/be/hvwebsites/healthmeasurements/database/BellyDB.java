package be.hvwebsites.healthmeasurements.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.typeconverters.DBTypeConverters;

@Database(entities = {Belly.class}, version = 1, exportSchema = false)
@TypeConverters({DBTypeConverters.class})
public abstract class BellyDB extends RoomDatabase {
    public abstract BellyDAO bellyDAO();

    private static BellyDB INSTANCE;

    public static BellyDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (BellyDB.class){
                if (INSTANCE == null){
                    // Create DB
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BellyDB.class,
                            "Health_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
