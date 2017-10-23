package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Karl on 14/04/2016.
 */
public class Decoration extends Item {
    private boolean isInteractable;
    private int iIndex;

    public Decoration(String name,boolean isInteractable,int buyValue,int sellValue) {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("decoration\\" + name.toLowerCase() + ".png")));
        this.isInteractable = isInteractable;
        this.iBuyValue = buyValue;
        this.iSellValue = sellValue;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public int getIndex() {
        return iIndex;
    }

    public void setIndex(int iIndex) {
        this.iIndex = iIndex;
    }
}
