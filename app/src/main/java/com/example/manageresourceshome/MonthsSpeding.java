package com.example.manageresourceshome;

public class MonthsSpeding {
    private String month;
    private float waterSpending;
    private float waterSpendingGoal;
    private float gasSpending;
    private float gasSpendingGoal;
    private float energySpending;
    private float energySpendingGoal;

    //este construtor é utilizado apenas qunado utilizador deseja inserir/motifigar as metas de gastos
    public MonthsSpeding(String month, float waterSpendingGoal, float gasSpendingGoal,float energySpendingGoal) {
        this.month = month;
        this.waterSpendingGoal = waterSpendingGoal;
        this.gasSpendingGoal = gasSpendingGoal;
        this.energySpendingGoal = energySpendingGoal;
    }
    //este construtor é utilizado para ler os todos os dados presentes acerca do total de gasto de um mes assim como as metas desse mes
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
