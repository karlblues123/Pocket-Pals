package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.Random;

/**
 * Created by Karl on 3/16/2016.
 */
public class Overlay extends Group {
    protected Player pReference;
    private Texture tBar, tMoney;
    private ImageButton ibShop, ibSatchel, ibInventory, ibOptions, ibAlbum, ibBait;
    private InputListener ilSatchel;
    private Satchel satchel;
    private BitmapFont font;
    private AdoptionPopup adoptionPopup;


    public Overlay(Player player) {
        //Get the refernce of the player
        this.pReference = player;

        //Create the UI Elements
        this.tBar = new Texture(Gdx.files.internal("ui\\bar.png"));
        this.tMoney = new Texture(Gdx.files.internal("ui\\money.png"));
        this.ibShop = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\shop.png")))));
        this.ibSatchel = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\foodsatchel.png")))));
        this.ibInventory = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\inventory.png")))));
        this.ibOptions = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\options.png")))));
        this.ibAlbum = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\album.png")))));
        this.ibBait = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\bait.png")))));
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);

        //Set the locations of the buttons on the screen
        this.ibBait.setPosition(1000,0);
        this.ibShop.setPosition(1150, 0);
        this.ibAlbum.setPosition(1300, 0);
        this.ibSatchel.setPosition(1450, 0);
        this.ibInventory.setPosition(1600, 0);
        this.ibOptions.setPosition(1750, 0);

        //Set the input listeners
        this.ibShop.setWidth(130);
        this.ibShop.setHeight(130);
        this.ibShop.setTouchable(Touchable.enabled);
        this.ibShop.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pReference.getCurrentHabitat().setVisible(false);
                disable();
                event.getStage().addActor(new Shop(pReference));
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.ibSatchel.setWidth(130);
        this.ibSatchel.setHeight(130);
        this.ibSatchel.setTouchable(Touchable.enabled);
        this.ilSatchel = new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                satchel.setVisible(!satchel.isVisible());
                if(satchel.isVisible()) {
                    if(pReference.getRecipes().size() > 0) {
                        for(Food food : pReference.getRecipes())
                            satchel.addOverlay(new SatchelOverlay(food));
                    }
                }
                else {
                    satchel.cleanOverlay();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        };
        this.ibSatchel.addListener(this.ilSatchel);

        this.ibBait.setWidth(130);
        this.ibBait.setHeight(130);
        this.ibBait.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(pReference.getGold() >= 100) {
                    Animal.Species[] species = pReference.getCurrentHabitat().getCompatibility();
                    Random rng = new Random();
                    int index = rng.nextInt(species.length);
                    final Animal newAnimal = new Animal("", species[index], 50, pReference.getCurrentHabitat().getIndex());
                    adoptionPopup.setPotentialPal(newAnimal);
                    adoptionPopup.setVisible(true);
                    pReference.setGold(pReference.getGold() - 100);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.satchel = new Satchel(this.pReference);
        this.satchel.setPosition(0,200);
        this.satchel.setVisible(false);

        this.adoptionPopup = new AdoptionPopup(this.pReference);
        this.adoptionPopup.setPosition(600,500);
        this.adoptionPopup.setVisible(false);


        //Add the Buttons to the group
        this.addActor(ibBait);
        this.addActor(ibShop);
        this.addActor(ibSatchel);
        this.addActor(ibInventory);
        this.addActor(ibOptions);
        this.addActor(ibAlbum);
        this.addActor(this.satchel);
        this.addActor(this.adoptionPopup);
    }

    public Player getReference() {
        return pReference;
    }

    public void setReference(Player pReference) {
        this.pReference = pReference;
    }

    public Texture getBar() {
        return tBar;
    }

    public void setBar(Texture tBar) {
        this.tBar = tBar;
    }

    public Texture getMoney() {
        return tMoney;
    }

    public void setMoney(Texture tMoney) {
        this.tMoney = tMoney;
    }

    public ImageButton getShopButton() {
        return ibShop;
    }

    public void setShopButton(ImageButton ibShop) {
        this.ibShop = ibShop;
    }

    public ImageButton getSatchelButton() {
        return ibSatchel;
    }

    public void setSatchelButton(ImageButton ibSatchel) {
        this.ibSatchel = ibSatchel;
    }

    public ImageButton getInventoryButton() {
        return ibInventory;
    }

    public void setInventoryButton(ImageButton ibInventory) {
        this.ibInventory = ibInventory;
    }

    public ImageButton getOptionsButton() {
        return ibOptions;
    }

    public void setOptionsButton(ImageButton ibOptions) {
        this.ibOptions = ibOptions;
    }

    public ImageButton getAlbumButton() {
        return ibAlbum;
    }

    public void setAlbumButton(ImageButton ibAlbum) {
        this.ibAlbum = ibAlbum;
    }

    public void dispose() {
        this.tBar.dispose();
        this.tMoney.dispose();
    }

    protected void disable() {
        this.setVisible(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch,float parentAlpha) {
        batch.draw(tBar, 0, 0);
        batch.draw(tMoney, 100, 50);
        super.draw(batch, parentAlpha);
        font.draw(batch,this.pReference.getGold()+"",200,105);
    }
}
