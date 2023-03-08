package com.example.olympe_dev_fragmentstyle.utils;

public class Aliment {
    private String nom;
    private String imagePath;
    private int calories;
    private float proteines;
    private float glucides;
    private float lipides;

    public String getNom() {
        return nom;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Aliment(String nom, String imagePath, int calories, float proteines, float glucides, float lipides) {
        this.nom = nom;
        this.imagePath = imagePath;
        this.calories = calories;
        this.proteines = proteines;
        this.glucides = glucides;
        this.lipides = lipides;
    }

    public int getCalories() {
        return calories;
    }

    public float getProteines() {
        return proteines;
    }

    public float getGlucides() {
        return glucides;
    }

    public float getLipides() {
        return lipides;
    }


}
