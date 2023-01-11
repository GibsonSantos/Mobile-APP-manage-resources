package com.example.manageresourceshome;

public class DaySpending {
    private String date;
    private float spedingWater;
    private float spedingGas;
    private float spedingEnergy;

    public DaySpending(String date, float spedingWater, float spedingGas, float spedingEnergy) {
        this.date = date;
        this.spedingWater = spedingWater;
        this.spedingGas = spedingGas;
        this.spedingEnergy = spedingEnergy;
    }

    public String getDate() {
        return date;
    }

    public float getSpedingWater() {
        return spedingWater;
    }

    public float getSpedingGas() {
        return spedingGas;
    }

    public float getSpedingEnergy() {
        return spedingEnergy;
    }
}
