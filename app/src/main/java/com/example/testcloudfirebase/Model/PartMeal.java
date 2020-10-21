package com.example.testcloudfirebase.Model;

public class PartMeal {
    public double getWaga() {
        return waga;
    }

    public void setWaga(double waga) {
        this.waga = waga;
    }

    public String getDayID() {
        return dayID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public String getProduktID() {
        return produktID;
    }
    public void setProduktID(String produktID) {
        this.produktID = produktID;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public PartMeal(double waga, String produktID, String docID, String dayID) {
        this.waga = waga;
        this.produktID = produktID;
        this.docID = docID;
        this.dayID = dayID;

    }

    private double waga;
    private String produktID;
    private String docID;
    private String dayID;

}
