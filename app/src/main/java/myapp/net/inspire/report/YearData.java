package myapp.net.inspire.report;

/**
 * Created by QPay on 3/14/2019.
 */

public class YearData {
    private int sunday = -1;
    private int monday = -1;
    private int tuesday = -1;
    private int wednesday = -1;
    private int thursday = -1;
    private int friday = -1;
    private int saturday = -1;

    private int sundayC = -1;
    private int mondayC = -1;
    private int tuesdayC = -1;
    private int wednesdayC = -1;
    private int thursdayC = -1;
    private int fridayC = -1;
    private int saturdayC= -1;
    private int featuredFlag=0;
    private String month="";
    private String weekTitle="";



    public YearData() {
    }

    public YearData(int sunday, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public String getWeekTitle() {
        return weekTitle;
    }

    public void setWeekTitle(String weekTitle) {
        this.weekTitle = weekTitle;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getFeaturedFlag() {
        return featuredFlag;
    }

    public void setFeaturedFlag(int featuredFlag) {
        this.featuredFlag = featuredFlag;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSundayC() {
        return sundayC;
    }

    public void setSundayC(int sundayC) {
        this.sundayC = sundayC;
    }

    public int getMondayC() {
        return mondayC;
    }

    public void setMondayC(int mondayC) {
        this.mondayC = mondayC;
    }

    public int getTuesdayC() {
        return tuesdayC;
    }

    public void setTuesdayC(int tuesdayC) {
        this.tuesdayC = tuesdayC;
    }

    public int getWednesdayC() {
        return wednesdayC;
    }

    public void setWednesdayC(int wednesdayC) {
        this.wednesdayC = wednesdayC;
    }

    public int getThursdayC() {
        return thursdayC;
    }

    public void setThursdayC(int thursdayC) {
        this.thursdayC = thursdayC;
    }

    public int getFridayC() {
        return fridayC;
    }

    public void setFridayC(int fridayC) {
        this.fridayC = fridayC;
    }

    public int getSaturdayC() {
        return saturdayC;
    }

    public void setSaturdayC(int saturdayC) {
        this.saturdayC = saturdayC;
    }

    @Override
    public String toString() {
        return "YearData{" +
                "sunday=" + sunday +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sundayC=" + sundayC +
                ", mondayC=" + mondayC +
                ", tuesdayC=" + tuesdayC +
                ", wednesdayC=" + wednesdayC +
                ", thursdayC=" + thursdayC +
                ", fridayC=" + fridayC +
                ", saturdayC=" + saturdayC +
                ", featuredFlag=" + featuredFlag +
                ", month='" + month + '\'' +
                ", weekTitle='" + weekTitle + '\'' +
                '}';
    }
}
