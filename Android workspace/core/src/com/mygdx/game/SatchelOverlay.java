package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Karl on 13/04/2016.
 */
public class SatchelOverlay extends Group {
    private Texture tBackground;
    private Food foodDisplay;
    private BitmapFont font;

    public SatchelOverlay(Food food) {
        this.tBackground = new Texture(Gdx.files.internal("ui\\satchelpanel.png"));
        this.foodDisplay = food;

        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);

        this.addActor(this.foodDisplay);
        this.setWidth(155f);
        this.setHeight(145f);
    }

    public Food getFoodDisplay() {
        return foodDisplay;
    }

    public void setFoodDisplay(Food foodDisplay) {
        this.foodDisplay = foodDisplay;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.tBackground,this.getX(),this.getY());
        super.draw(batch, parentAlpha);
    }
}
