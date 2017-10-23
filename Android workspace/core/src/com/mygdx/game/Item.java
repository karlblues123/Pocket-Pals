package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Karl on 3/16/2016.
 */
public class Item extends Actor {
    protected int iBuyValue;
    protected int iSellValue;

    //Graphics variables
    protected Sprite sprite;

    public Item() {
    }

    public Item(int buyvalue, int sellvalue) {
        this.iBuyValue = buyvalue;
        this.iSellValue = sellvalue;
    }

    public int getBuyValue() {
        return iBuyValue;
    }

    public void setBuyValue(int buyvalue) {
        this.iBuyValue = buyvalue;
    }

    public int getSellValue() {
        return iSellValue;
    }

    public void setSellValue(int sellvalue) {
        this.iSellValue = sellvalue;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.sprite.setPosition(this.getX(),this.getY());
        this.setBounds(super.getX(),super.getY(),this.sprite.getWidth(),this.sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        this.sprite.draw(batch);
    }

    public Item clone() {
        return new Item(this.iBuyValue,this.iSellValue);
    }
}
