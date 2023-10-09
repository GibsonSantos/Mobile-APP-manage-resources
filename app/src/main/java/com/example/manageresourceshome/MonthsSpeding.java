package com.example.manageresourceshome;

public class MonthsSpeding {
    private String month;
    private float waterSpending;
    private float waterSpendingGoal;
    private float gasSpending;
    private float gasSpendingGoal;
    private float energySpending;
    private float energySpendingGoal;

    //this constructor is only used when the user wants to insert/motivate spending goals
    public MonthsSpeding(String month, float waterSpendingGoal, float gasSpendingGoal,float energySpendingGoal) {
        this.month = month;
        this.waterSpendingGoal = waterSpendingGoal;
        this.gasSpendingGoal = gasSpendingGoal;
        this.energySpendingGoal = energySpendingGoal;
    }
    //this constructor is used to read all the data present about the total expenditure for a month as well as the goals for that month
    public MonthsSpeding(String month, float waterSpending, float waterSpendingGoal, float gasSpending, float gasSpendingGoal, float energySpending, float energySpendingGoal) {
        this.month = month;
        this.waterSpending = waterSpending;
        this.waterSpendingGoal = waterSpendingGoal;
        this.gasSpending = gasSpending;
        this.gasSpendingGoal = gasSpendingGoal;
        this.energySpending = energySpending;
        this.energySpendingGoal = energySpendingGoal;
    }

    public String getMonth() {
        return month;
    }

    public float getWaterSpending() {
        return waterSpending;
    }

    public float getWaterSpendingGoal() {
        return waterSpendingGoal;
    }

    public float getGasSpending() {
        return gasSpending;
    }

    public float getGasSpendingGoal() {
        return gasSpendingGoal;
    }

    public float getEnergySpending() {
        return energySpending;
    }

    public float getEnergySpendingGoal() {
        return energySpendingGoal;
    }
}
