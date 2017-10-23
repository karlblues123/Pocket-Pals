package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;


/**
 * Created by Karl on 13/04/2016.
 */
public class Satchel extends Group {
    private Texture tBackground;
    private ArrayList<SatchelOverlay> alFood;
    private Player pReference;

    public Satchel(final Player pReference) {
        this.tBackground = new Texture(Gdx.files.internal("ui\\headerbar.png"));
        this.pReference = pReference;

        this.alFood = new ArrayList<SatchelOverlay>();
        this.alFood.add(new SatchelOverlay(new Food("Cracker", 0, 0, Animal.Species.values(), 10)));
        this.alFood.get(0).setTouchable(Touchable.enabled);
        this.alFood.get(0).setPosition(20,650);
        this.alFood.get(0).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Food food = alFood.get(0).getFoodDisplay().clone();
                food.setPosition(event.getStageX(),event.getStageY());
                pReference.getCurrentHabitat().addFood(food);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        this.addActor(this.alFood.get(0));
    }

    public void addOverlay(final SatchelOverlay overlay) {
        overlay.setPosition(20, 650 - (this.alFood.size() * 160));
        overlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Food food = overlay.getFoodDisplay().clone();
                food.setPosition(event.getStageX(), event.getStageY());
                pReference.getCurrentHabitat().addFood(food);
                pReference.setGold(pReference.getGold() - food.getBuyValue());
                return true;
            }
        });
        this.alFood.add(overlay);
        this.addActor(this.alFood.get(this.alFood.size() - 1));
    }

    public void cleanOverlay() {
        if(this.alFood.size() > 1) {
            for (int i = 1; i < this.alFood.size(); i++) {
                this.alFood.remove(i);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.tBackground,this.getX(),this.getY());
        super.draw(batch, parentAlpha);
    }
}
