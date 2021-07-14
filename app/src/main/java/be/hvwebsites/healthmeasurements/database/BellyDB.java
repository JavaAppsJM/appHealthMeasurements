package be.hvwebsites.healthmeasurements.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import be.hvwebsites.healthmeasurements.dao.BellyDAO;
import be.hvwebsites.healthmeasurements.entities.Belly;
import be.hvwebsites.healthmeasurements.entities.Bellydb;
import be.hvwebsites.healthmeasurements.typeconverters.DBTypeConverters;

@Database(entities = {Bellydb.class}, version = 2, exportSchema = false)
@TypeConverters({DBTypeConverters.class})
public abstract class BellyDB extends RoomDatabase {
    public abstract BellyDAO bellyDAO();

    private static BellyDB INSTANCE;

    // Migration strategy to go from version 1 to 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE belly_radius "
                    + " ADD COLUMN dateint INTEGER");
        }
    };

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
                } else {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BellyDB.class,
                            "Health_db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        } else {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BellyDB.class,
                    "Health_db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
}
