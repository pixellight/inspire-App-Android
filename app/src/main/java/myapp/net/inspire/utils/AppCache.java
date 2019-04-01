package myapp.net.inspire.utils;

/**
 * Created by QPay on 3/17/2019.
 */

public class AppCache {
    public static String wakeUpTime = "7:00 AM";
    public static int doseTwoWakeUp = 18;
    public static int timeBetweenDoses = 18;
    public static String napOne = "-1";
    public static String napTwo = "-1";
    public static String napThree = "-1";
    public static boolean eatBy = true;
    public static boolean doseOne = true;
    public static boolean napReminder = true;
    public static int dose1 = 0;
    public static int dose2 = 0;
    public static int wake = 0;
    public static int napStart = 0;
    public static int napEnd = 0;

    public static int getDose1() {
        return dose1;
    }

    public static void setDose1(int dose1) {
        AppCache.dose1 = dose1;
    }

    public static int getDose2() {
        return dose2;
    }

    public static void setDose2(int dose2) {
        AppCache.dose2 = dose2;
    }

    public static int getWake() {
        return wake;
    }

    public static void setWake(int wake) {
        AppCache.wake = wake;
    }

    public static int getNapStart() {
        return napStart;
    }

    public static void setNapStart(int napStart) {
        AppCache.napStart = napStart;
    }

    public static int getNapEnd() {
        return napEnd;
    }

    public static void setNapEnd(int napEnd) {
        AppCache.napEnd = napEnd;
    }

    public static String getWakeUpTime() {
        return wakeUpTime;
    }

    public static void setWakeUpTime(String wakeUpTime) {
        AppCache.wakeUpTime = wakeUpTime;
    }

    public static int getDoseTwoWakeUp() {
        return doseTwoWakeUp;
    }

    public static void setDoseTwoWakeUp(int doseTwoWakeUp) {
        AppCache.doseTwoWakeUp = doseTwoWakeUp;
    }

    public static int getTimeBetweenDoses() {
        return timeBetweenDoses;
    }

    public static void setTimeBetweenDoses(int timeBetweenDoses) {
        AppCache.timeBetweenDoses = timeBetweenDoses;
    }

    public static String getNapOne() {
        return napOne;
    }

    public static void setNapOne(String napOne) {
        AppCache.napOne = napOne;
    }

    public static String getNapTwo() {
        return napTwo;
    }

    public static void setNapTwo(String napTwo) {
        AppCache.napTwo = napTwo;
    }

    public static String getNapThree() {
        return napThree;
    }

    public static void setNapThree(String napThree) {
        AppCache.napThree = napThree;
    }

    public static boolean isEatBy() {
        return eatBy;
    }

    public static void setEatBy(boolean eatBy) {
        AppCache.eatBy = eatBy;
    }

    public static boolean isDoseOne() {
        return doseOne;
    }

    public static void setDoseOne(boolean doseOne) {
        AppCache.doseOne = doseOne;
    }

    public static boolean isNapReminder() {
        return napReminder;
    }

    public static void setNapReminder(boolean napReminder) {
        AppCache.napReminder = napReminder;
    }
}
