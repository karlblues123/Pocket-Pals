package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.*;

/**
 * Created by Karl on 13/04/2016.
 */
public class Coin extends  Item {
    private int value;
    private Sound wavSound;

    public Coin(float x,float y){
        wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\coin.mp3"));

        Random rand = new Random();
        int randomNumber = 10+rand.nextInt(6); // 0-9.
        this.setPosition(x,y);
        this.sprite = new Sprite(new Texture(Gdx.files.internal("ui\\coin.png")));

        this.value=randomNumber;
    }

    public int getValue() {
        return value;
    }

    public void playsound(){

        if (this.wavSound != null) {
            this.wavSound.play();
        }else{
            System.out.println("sound is null");
        }
    }

    public void setValue(int value) {
        this.value = value;
    }
}
