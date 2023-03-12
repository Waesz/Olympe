package com.example.olympe_dev_fragmentstyle.aliments;

public class Aliment {
    private String nom;
    private int image;
    private int calories;
    private float proteines;
    private float glucides;
    private float lipides;

    public String getNom() {
        return nom;
    }

    public int getImage() {
        return image;
    }

    public Aliment(String nom, int image, int calories, float proteines, float glucides, float lipides) {
        this.nom = nom;
        this.image = image;
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
