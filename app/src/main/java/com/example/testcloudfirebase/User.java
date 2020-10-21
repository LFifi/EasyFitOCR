package com.example.testcloudfirebase;

public class User {
    int aktywnosc,cel,wiek;
    Double waga,wzrost;
    String plec;

    public User(int aktywnosc, int cel, int wiek, Double waga, Double wzrost, String plec) {
        this.aktywnosc = aktywnosc;
        this.cel = cel;
        this.wiek = wiek;
        this.waga = waga;
        this.wzrost = wzrost;
        this.plec = plec;
    }

    public User() {
    }

    public int getAktywnosc() {
        return aktywnosc;
    }

    public void setAktywnosc(int aktywnosc) {
        this.aktywnosc = aktywnosc;
    }

    public int getCel() {
        return cel;
    }

    public void setCel(int cel) {
        this.cel = cel;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public Double getWaga() {
        return waga;
    }

    public void setWaga(Double waga) {
        this.waga = waga;
    }

    public Double getWzrost() {
        return wzrost;
    }

    public void setWzrost(Double wzrost) {
        this.wzrost = wzrost;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }
}
