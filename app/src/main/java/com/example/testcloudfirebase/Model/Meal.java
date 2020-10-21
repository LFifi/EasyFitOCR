package com.example.testcloudfirebase.Model;

import java.util.List;

public class Meal {
    private String name;
    private int id;

    public Meal(String name, int id, List<PartMeal> lista) {
        this.name = name;
        this.id = id;
        this.lista = lista;
    }

    private List<PartMeal> lista;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PartMeal> getLista() {
        return lista;
    }

    public void setLista(List<PartMeal> lista) {
        this.lista = lista;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
