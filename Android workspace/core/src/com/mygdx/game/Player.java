package com.mygdx.game;

import java.util.ArrayList;

/**
 * Created by Karl on 3/9/2016.
 */
public class Player {
    private String sName;
    private ArrayList<Animal> alAnimals;
    private ArrayList<Habitat> alHabitats;
    private ArrayList<Decoration> alInventory;
    private ArrayList<Food> alRecipe;
    private int iGold;
    public int currHabitatIndex;

    public Player(int gold, ArrayList<Habitat> habitats, String name, ArrayList<Animal> animals, ArrayList<Decoration> inventory) {
        this.iGold = gold;
        this.alHabitats = habitats;
        this.sName = name;
        this.alAnimals = animals;
        this.alInventory = inventory;
        this.alRecipe = new ArrayList<Food>();
        this.currHabitatIndex = 0;
    }

    // for new players
    public Player(int gold, String name) {
        this.iGold = gold;
        this.sName = name;
        this.alHabitats = new ArrayList<Habitat>();
        this.alHabitats.add(new Habitat(this,"Basic",alHabitats.size(),new Animal.Species[]{Animal.Species.CAT,Animal.Species.DOG}));
        this.alAnimals = new ArrayList<Animal>();
        this.alInventory = new ArrayList<Decoration>();
        this.alRecipe = new ArrayList<Food>();
        this.alInventory.add(new Decoration("BlueCushion", true, 500, 250));
        this.getCurrentHabitat().addDecoration(this.alInventory.get(0));
        this.currHabitatIndex = 0;
    }

    public String getName() {
        return sName;
    }

    public void setsName(String name) {
        this.sName = name;
    }

    public ArrayList<Animal> getAnimals() {
        return alAnimals;
    }

    public Animal getAnimal(int index) {
        return alAnimals.get(index);
    }

    public void addAnimal(Animal animal) {
        alAnimals.add(animal);
        alHabitats.get(animal.getHabitatIndex()).addAnimal(animal);
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.alAnimals = animals;
    }

    public void setAnimal(int index, Animal animal) {
        this.alAnimals.set(index, animal);
    }

    public void removeAnimal(int index) {
        this.alAnimals.remove(index);
    }

    public ArrayList<Habitat> getHabitats() {
        return alHabitats;
    }

    public Habitat getHabitat(int index) {
        return alHabitats.get(index);
    }

    public void addHabitat(Habitat habitat) {
        habitat.setIndex(this.alHabitats.size());
        this.alHabitats.add(habitat);
    }

    public void setHabitats(ArrayList<Habitat> habitats) {
        this.alHabitats = habitats;
    }

    public void setHabitat(int index,Habitat habitat) {
        this.alHabitats.set(index, habitat);
    }

    public void removeHabitat(int index) {
        this.alHabitats.remove(index);
    }

    public ArrayList<Decoration> getInventory() {
        return alInventory;
    }

    public void setInventory(ArrayList<Decoration> alInventory) {
        this.alInventory = alInventory;
    }

    public Decoration getDecoration(int index) {
        return this.alInventory.get(index);
    }

    public void addDecoration(Decoration item) {
        this.alInventory.add(item);
    }

    public ArrayList<Food> getRecipes() {
        return alRecipe;
    }

    public void setRecipes(ArrayList<Food> alRecipe) {
        this.alRecipe = alRecipe;
    }

    public Food getRecipe(int index) {
        return this.alRecipe.get(index);
    }

    public void addRecipe(Food food) {
        this.alRecipe.add(food);
    }

    public void buy(Item item) {
        if(item instanceof Food) {
            Food clone = ((Food)item).clone();
            clone.setBuyValue(item.getBuyValue()/10);
            this.addRecipe((Food)clone);
        }
    }

    public int getGold() {
        return iGold;
    }

    public void setGold(int gold) {
        this.iGold = gold;
    }

    public Habitat getCurrentHabitat() {
        return this.alHabitats.get(this.currHabitatIndex);
    }
}
