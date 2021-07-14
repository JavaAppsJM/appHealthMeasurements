package be.hvwebsites.healthmeasurements.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "belly_radius")
public class Bellydb {
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

    public Bellydb(String date, float bellyRadius) {
        String[] dateStringParts = date.split("/");
        String day = this.leadingZero(dateStringParts[0]);
        String month = this.leadingZero(dateStringParts[1]);
        String year = dateStringParts[2];
        this.date = day + month + year;
        this.bellyRadius = bellyRadius;
        this.dateInt = Integer.parseInt(year + month + day);
    }

    public Bellydb() {
    }

    public String leadingZero(String string){
        if (Integer.parseInt(string) < 10 && string.length() < 2){
            return  "0" + string;
        }else {
            return string;
        }
    }

    public String getFormatDate() {
        String day = date.substring(0,2);
        String month = date.substring(2,4);
        String year = date.substring(4);
        return day + "/" + month + "/" + year;
    }

    public String getDate(){
        return this.date;
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
        this.dateInt = Integer.parseInt(date.substring(4) + date.substring(2,4) + date.substring(0,2));
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

    public void setBelly(Bellydb belly2){
        this.date = belly2.getDate();
        this.bellyRadius = belly2.getBellyRadius();
        this.dateInt = belly2.getDateInt();
        this.remark = belly2.getRemark();
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
