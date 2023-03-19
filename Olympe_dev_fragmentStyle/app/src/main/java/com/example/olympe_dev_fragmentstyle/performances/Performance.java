package com.example.olympe_dev_fragmentstyle.performances;

public class Performance {
    private int idUser;
    private String nomPerf;
    private int datePerf;
    private int valeurPerf;

    public Performance(int id, String nom, int date, int valeur) {
        idUser = id;
        nomPerf = nom;
        datePerf = date;
        valeurPerf = valeur;
    }

    public int getIdUser() {return idUser;}
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
        return "[" + idUser + ", " + nomPerf + ", " + valeurPerf + ", " + datePerf + "]";
    }
}
