package com.example.testcloudfirebase.Model;

public class Product {
    private String nazwa;
    private String idProduct;
    private double kcal;
    private double wartoscEnergetyczna;
    private double tluszcz;
    private double tluszczNasycony;
    private double weglowodany;
    private double weglowodanyWTymCukry;
    private double bialko;
    private double sol;

    public Product(String nazwa,String idProduct, double kcal, double wartoscEnergetyczna, double tluszcz, double tluszczNasycony, double weglowodany, double weglowodanyWTymCukry, double bialko, double sol) {
        this.nazwa = nazwa;
        this.idProduct = idProduct;
        this.kcal = kcal;
        this.wartoscEnergetyczna = wartoscEnergetyczna;
        this.tluszcz = tluszcz;
        this.tluszczNasycony = tluszczNasycony;
        this.weglowodany = weglowodany;
        this.weglowodanyWTymCukry = weglowodanyWTymCukry;
        this.bialko = bialko;
        this.sol = sol;
    }

    public Product()
    {
    }
    public Product(String nazwa) {
        this.nazwa = nazwa;
        this.kcal = kcal;
        this.wartoscEnergetyczna = wartoscEnergetyczna;
        this.tluszcz = tluszcz;
        this.tluszczNasycony = tluszczNasycony;
        this.weglowodany = weglowodany;
        this.weglowodanyWTymCukry = weglowodanyWTymCukry;
        this.bialko = bialko;
        this.sol = sol;
    }


    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }


    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getWartoscEnergetyczna() {
        return wartoscEnergetyczna;
    }

    public void setWartoscEnergetyczna(double wartoscEnergetyczna) {
        this.wartoscEnergetyczna = wartoscEnergetyczna;
    }

    public double getTluszcz() {
        return tluszcz;
    }

    public void setTluszcz(double tluszcz) {
        this.tluszcz = tluszcz;
    }

    public double getTluszczNasycony() {
        return tluszczNasycony;
    }

    public void setTluszczNasycony(double tluszczNasycony) {
        this.tluszczNasycony = tluszczNasycony;
    }

    public double getWeglowodany() {
        return weglowodany;
    }

    public void setWeglowodany(double weglowodany) {
        this.weglowodany = weglowodany;
    }

    public double getWeglowodanyWTymCukry() {
        return weglowodanyWTymCukry;
    }

    public void setWeglowodanyWTymCukry(double weglowodanyWTymCukry) {
        this.weglowodanyWTymCukry = weglowodanyWTymCukry;
    }

    public double getBialko() {
        return bialko;
    }

    public void setBialko(double bialko) {
        this.bialko = bialko;
    }

    public double getSol() {
        return sol;
    }

    public void setSol(double sol) {
        this.sol = sol;
    }

}
