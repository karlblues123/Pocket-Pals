package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;


/**
 * Created by Karl on 05/04/2016.
 */
public class ShopOverlay extends Group {
    private Texture tBackground, tCoin;
    private Item itemDisplay;
    private BitmapFont font;

    public ShopOverlay(Item item) {
        this.itemDisplay = item;
        this.itemDisplay.setPosition(125,150);

        this.tCoin = new Texture(Gdx.files.internal("ui\\coin.png"));
        this.tBackground = new Texture(Gdx.files.internal("ui\\panel.png"));
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);

        this.addActor(itemDisplay);
    }

    public Item getItemDisplay() {
        return itemDisplay;
    }

    public void setItemDisplay(Item itemDisplay) {
        this.itemDisplay = itemDisplay;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tBackground,this.getX(),this.getY());
        batch.draw(tCoin,this.getX() + 16,this.getY() + 30);
        super.draw(batch, parentAlpha);
        font.draw(batch, this.itemDisplay.getBuyValue() + "", this.getX() + 100, this.getY() + 100);
        font.draw(batch,this.itemDisplay.getName(),this.getX() + 20,this.getY() + 375);
    }
}
