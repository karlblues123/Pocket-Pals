package com.mygdx.game;

/**
 * Created by asus on 4/14/2016.
 */
public class DBAnimal {
    public String species;
    public String name;
    public int hunger;
    public int iHabitatIndex;

    public DBAnimal(String name,String species,int hunger,int habitatIndex) {
        this.species = species;
        this.name=name;
        this.hunger = hunger;
        this.iHabitatIndex = habitatIndex;
    }
}
