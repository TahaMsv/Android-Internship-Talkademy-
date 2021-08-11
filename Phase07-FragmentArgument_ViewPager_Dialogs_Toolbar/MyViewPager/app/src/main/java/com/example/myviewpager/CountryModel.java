package com.example.myviewpager;

enum Continent {
    Asia, Africa, Europe, America, Australia
}

public class CountryModel {
    private String name;
    private String id;
    private Continent continent;

    public CountryModel(String name, String id, Continent continent) {
        this.name = name;
        this.id = id;
        this.continent = continent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
