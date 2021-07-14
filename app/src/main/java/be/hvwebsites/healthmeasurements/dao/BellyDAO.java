package be.hvwebsites.healthmeasurements.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Bellydb;

@Dao
public interface BellyDAO {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertBellyMeasurement(Bellydb belly);

    @Query("UPDATE belly_radius SET bellyradius = :newRadius WHERE dateint = :udate")
    void updateBellyMeasurement(int udate, float newRadius);

    @Delete
    void deleteBellyMeasurement(Bellydb belly);

    @Query("SELECT * from belly_radius ORDER BY dateint DESC")
    LiveData<List<Bellydb>> getAllBellys();

    @Query("SELECT * FROM belly_radius WHERE dateint = :maxDateInt")
    Bellydb getLatestBelly(int maxDateInt);

    @Query("SELECT MAX(dateint) FROM belly_radius")
    int getMaxDateint();
}
