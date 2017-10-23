package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Karl on 14/04/2016.
 */
public class AdoptionPopup extends Group {
    private Texture tBackground;
    private TextButton tbAccept, tbDecline;
    private Animal potentialPal;
    private Player pReference;
    private TextButton.TextButtonStyle style;
    private BitmapFont font;

    public AdoptionPopup(Player player) {
        this.pReference = player;

        this.tBackground = new Texture(Gdx.files.internal("ui\\panel.png"));

        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);

        this.style = new TextButton.TextButtonStyle();
        this.style.font = this.font;
        this.tbAccept = new TextButton("Accept",this.style);
        this.tbAccept.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        potentialPal.setName(text);
                        pReference.addAnimal(potentialPal);
                        removePotentialPal(potentialPal);
                        disable();
                    }

                    @Override
                    public void canceled() {
                        System.out.print("Canceled");
                    }
                }, "Enter name of new Pal", "", "Joe");
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.tbDecline = new TextButton("Decline",this.style);
        this.tbDecline.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                removePotentialPal(potentialPal);
                disable();
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.tbAccept.setPosition(50,50);
        this.tbDecline.setPosition(200,50);

        this.addActor(this.tbAccept);
        this.addActor(this.tbDecline);
    }

    public void removePotentialPal(Animal pal) {
        this.removeActor(pal);
        this.potentialPal = null;
    }

    public void setPotentialPal(Animal pal) {
        this.potentialPal = pal;
        this.addActor(this.potentialPal);
        this.potentialPal.setPosition(125, 150);
    }

    public void disable() {
        this.setVisible(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tBackground,this.getX(),this.getY());
        font.draw(batch,this.potentialPal.getSpecies().toString(),this.getX() + 20,this.getY() + 375);
        super.draw(batch, parentAlpha);
    }
}
