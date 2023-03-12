package com.example.olympe_dev_fragmentstyle.performances;

public class Performance {
    private String nomPerf;
    private int datePerf;
    private int valeurPerf;

    public Performance(String nom, int date, int valeur) {
        nomPerf = nom;
        datePerf = date;
        valeurPerf = valeur;
    }

    public String getNomPerf() {
        return nomPerf;
    }

    public int getDatePerf() {
        return datePerf;
    }

    public int getValeurPerf() {
        return valeurPerf;
    }

    public String toString() {
        return "[" + nomPerf + ", " + valeurPerf + ", " + datePerf + "]";
    }
}
