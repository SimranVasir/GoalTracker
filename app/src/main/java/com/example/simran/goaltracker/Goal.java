package com.example.simran.goaltracker;

/**
 * Created by simra on 12/14/2015.
 */
public class Goal {
    private String goalName;
    private int estHours;
    private boolean isCompleted;
    private int year, month, day, hourOfDay, minute;
    private long goalId;

    public Goal(){}

    public Goal(long goalId, String goalName, int hoursToComplete, boolean isCompleted, int year, int month, int day, int hourOfDay, int minute ) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.isCompleted = isCompleted;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.estHours = hoursToComplete;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public int getEstHours() {
        return estHours;
    }

    public void setEstHours(int estHours) {
        this.estHours = estHours;
    }
}
