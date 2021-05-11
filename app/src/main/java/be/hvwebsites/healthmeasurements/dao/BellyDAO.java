package be.hvwebsites.healthmeasurements.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import be.hvwebsites.healthmeasurements.entities.Belly;

@Dao
public interface BellyDAO {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertBellyMeasurement(Belly belly);

    @Query("UPDATE belly_radius SET bellyradius = :newRadius WHERE dateint = :udate")
    void updateBellyMeasurement(int udate, float newRadius);

    @Delete
    void deleteBellyMeasurement(Belly belly);

    @Query("SELECT * from belly_radius ORDER BY dateint DESC")
    LiveData<List<Belly>> getAllBellys();
}
