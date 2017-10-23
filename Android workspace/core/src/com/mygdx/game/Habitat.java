package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import com.badlogic.gdx.scenes.scene2d.InputListener;


import java.util.ArrayList;

/**
 * Created by Karl on 3/8/2016.
 */
public class Habitat extends Group {
    private Animal.Species[] compatibility;
    private Player pReference;
    private Texture tBackground;
    private ArrayList<Animal> alAnimals;
    private ArrayList<Coin> alCoins;
    private ArrayList<Food> alFood;
    private ArrayList<Decoration> alDecoration;
    private String sType;
    private int iCapacity;
    private int iIndex;

    public Habitat(Player player,String type, int index,Animal.Species[] compatibility) {
        this.pReference = player;
        this.sType = type;
        this.tBackground = new Texture("background\\"+Gdx.files.internal(this.sType.toLowerCase() + ".png"));
        this.iIndex = index;
        this.compatibility = compatibility;
        this.init();
    }

    public Habitat(String type,int index,Animal.Species[] compatibility) {
        this.sType = type;
        this.tBackground = new Texture("background\\"+Gdx.files.internal(this.sType.toLowerCase() + ".png"));
        this.iIndex = index;
        this.compatibility = compatibility;
        this.init();
    }

    public void init() {
        this.alAnimals = new ArrayList<Animal>();
        this.alCoins = new ArrayList<Coin>();
        this.alFood = new ArrayList<Food>();
        this.alDecoration = new ArrayList<Decoration>();
    }

    public Texture getBackground() {
        return tBackground;
    }

    public void setBackground(Texture background) {
        this.tBackground = background;
    }

    public Animal getAnimal(int index) {
        return alAnimals.get(index);
    }

    public void addAnimal(Animal animal) {
        alAnimals.add(animal);
        this.addActor(animal);
    }

    public void setAnimal(int index, Animal animal) {
        this.alAnimals.set(index, animal);
    }

    public void removeAnimal(int index) {
        this.alAnimals.remove(index);
    }

    public ArrayList<Animal> getAnimals() {
        return alAnimals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.alAnimals = animals;
        for(int i = 0; i < animals.size(); i++) {
            if(this.iIndex == animals.get(i).getHabitatIndex())
                for(Animal.Species specie : compatibility) {
                    if(specie.toString().equals(this.alAnimals.get(i).getSpecies().toString()))
                        this.addAnimal(animals.get(i));
                }

        }
    }

    public ArrayList<Decoration> getDecorations() {
        return alDecoration;
    }

    public void setDecorations(ArrayList<Decoration> alDecoration) {
        this.alDecoration = alDecoration;
    }

    public void addDecoration(Decoration decoration) {
        decoration.setPosition(200,400);
        this.alDecoration.add(decoration);
        this.addActor(decoration);
    }

    public Player getReference() {
        return pReference;
    }

    public void setReference(Player pReference) {
        this.pReference = pReference;
    }

    public Coin getCoin(int index) {
        return alCoins.get(index);
    }

    public void addCoin(final Coin coin) {
        this.addActor(coin);
        this.alCoins.add(coin);
        coin.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pReference.setGold(pReference.getGold() + coin.getValue());
                coin.playsound();
                alCoins.remove(coin);
                removeActor(coin);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void setCoin(int index, Coin coin) {
        this.alCoins.set(index, coin);
    }

    public void removeCoin(Coin coin) {

        this.removeActor(coin);
    }

    public ArrayList<Coin> getAlCoins() {
        return alCoins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.alCoins = coins;
    }

    public String getType() {
        return sType;
    }

    public void setType(String type) {
        this.sType = type;
    }

    public int getCapacity() {
        return iCapacity;
    }

    public void setCapacity(int capacity) {
        this.iCapacity = capacity;
    }

    public int getIndex() {
        return iIndex;
    }

    public void setIndex(int index) {
        this.iIndex = index;
    }

    public Animal.Species[] getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(Animal.Species[] compatibility) {
        this.compatibility = compatibility;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(Food food : this.alFood) {
            if(food.getY() < 800) {
                for(Animal animal : this.alAnimals) {
                if(animal.fMyFood == null)
                    animal.FoodAlerted(food);
                }
            }
            if(food.getCurrentMagnitude() <= 0) {
                this.removeActor(food);
                this.alFood.remove(food);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tBackground, 0, 0);
        super.draw(batch,parentAlpha);
        this.act(Gdx.graphics.getDeltaTime());
    }

    public void addFood(Food food) {
        this.alFood.add(food);
        super.addActor(food);
        food.addListener(new DragListener(){
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                event.getTarget().moveBy(x-event.getTarget().getHeight()/2,y-event.getTarget().getHeight()/2);
                super.drag(event, x, y, pointer);
            }
        });
    }
}
