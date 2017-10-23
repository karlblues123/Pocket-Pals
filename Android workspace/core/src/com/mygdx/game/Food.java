package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Created by Karl on 3/16/2016.
 */
public class Food extends Item{
    private Animal.Species[] eCompatibility;
    private int iCurrentMagnitude, iMaxMagnitude;
    private Rectangle rBounds;

    public Food(){};

    public Food(String name,int buyvalue, int sellvalue,Animal.Species[] compatibility,int magnitude){
        super.setName(name);
        this.iBuyValue = buyvalue;
        this.iSellValue = sellvalue;
        this.eCompatibility = compatibility;
        this.iMaxMagnitude = magnitude;
        this.iCurrentMagnitude = this.iMaxMagnitude;

        this.sprite = new Sprite(new Texture(Gdx.files.internal("food\\"+super.getName().toLowerCase() + ".png")));
        this.setWidth(this.sprite.getWidth());
        this.setHeight(this.sprite.getHeight());

        this.rBounds = new Rectangle(super.getX(),super.getY(),this.sprite.getWidth(),this.sprite.getHeight());
    }

    public Animal.Species[] getCompatibility() {
        return eCompatibility;
    }

    public void setCompatibility(Animal.Species[] compatibility) {
        this.eCompatibility = compatibility;
    }

    public int getMaxMagnitude() {
        return iMaxMagnitude;
    }

    public void setMaxMagnitude(int magnitude) {
        this.iMaxMagnitude = magnitude;
    }

    public int getCurrentMagnitude() {return this.iCurrentMagnitude;}

    public void setCurrentMagnitude(int magnitude) {
        this.iCurrentMagnitude = magnitude;
    }

    public Rectangle getBounds() {
        return rBounds;
    }

    public void setBounds(Rectangle bounds) {
        this.rBounds = bounds;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setWidth(this.getWidth()*((float)this.getCurrentMagnitude()/this.getMaxMagnitude()));
        this.setHeight(this.getHeight()*((float)this.getCurrentMagnitude()/this.getMaxMagnitude()));
        this.rBounds.setPosition(super.getX(), super.getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(sprite, super.getX(),super.getY(),this.getWidth(),this.getHeight());
    }

    @Override
    public Food clone() {
        return new Food(this.getName(),this.iBuyValue,this.iSellValue,this.eCompatibility,this.iMaxMagnitude);
    }
}
