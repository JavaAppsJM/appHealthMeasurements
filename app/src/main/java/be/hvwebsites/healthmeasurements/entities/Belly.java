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
    @ColumnInfo(name = "dateint")
    private int dateInt;

    public Belly(String date, float bellyRadius) {
        String[] dateStringParts = date.split("/");
        String day = this.leadingZero(dateStringParts[0]);
        String month = this.leadingZero(dateStringParts[1]);
        String year = dateStringParts[2];
        this.date = day + month + year;
        this.bellyRadius = bellyRadius;
        this.dateInt = Integer.parseInt(year + month + day);
    }

    public String leadingZero(String string){
        if (Integer.parseInt(string) < 10 && string.length() < 2){
            return  "0" + string;
        }else {
            return string;
        }
    }

    public String getDate() {
        String day = date.substring(0,2);
        String month = date.substring(2,4);
        String year = date.substring(4);
        return day + "/" + month + "/" + year;
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

    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

    @Override
    public String toString() {
        return "Belly{" +
                "date='" + date + '\'' +
                ", bellyRadius=" + bellyRadius +
                ", remark='" + remark + '\'' +
                ", dateInt=" + dateInt +
                '}';
    }
}
