package be.hvwebsites.healthmeasurements.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(tableName = "belly_radius")
public class Belly {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private String date;
    @NonNull
    @ColumnInfo(name = "bellyradius")
    private float bellyRadius;
    @ColumnInfo(name = "remark")
    private String remark; // initieel niet gebruikt

    public Belly(String date, float bellyRadius) {
        this.date = date;
        this.bellyRadius = bellyRadius;
    }

    public String getDate() {
        return date;
    }

    public float getBellyRadius() {
        return bellyRadius;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setBellyRadius(float bellyRadius) {
        this.bellyRadius = bellyRadius;
    }

    @Override
    public String toString() {
        return "Belly{" +
                "date='" + date + '\'' +
                ", bellyRadius=" + bellyRadius +
                ", remark='" + remark + '\'' +
                '}';
    }
}
