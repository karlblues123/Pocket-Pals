package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by Karl on 3/31/2016.
 */
public class Shop extends WidgetGroup {
    private Player pReference;
    private ImageButton ibBuy, ibSell, ibExit;
    private ShopOverlay shopitemTuna, shopitemStrawberry;
    private BitmapFont font;

    public Shop(Player player) {
        this.pReference = player;

        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);

        this.shopitemTuna = new ShopOverlay(new Food("Tuna",1000,0,Animal.Species.values(),25));
        this.shopitemTuna.setBounds(100,500,413,415);
        this.shopitemTuna.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buy(shopitemTuna.getItemDisplay());
                super.touchUp(event, x, y, pointer, button);
            }
        });
        this.addActor(shopitemTuna);

        this.shopitemStrawberry = new ShopOverlay(new Food("Strawberry",1000,0,Animal.Species.values(),40));
        this.shopitemStrawberry.setBounds(600,500,413,415);
        this.shopitemStrawberry.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buy(shopitemStrawberry.getItemDisplay());
                super.touchUp(event, x, y, pointer, button);
            }
        });
        this.addActor(shopitemStrawberry);

        this.ibExit = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui\\left.png")))));
        this.ibExit.setPosition(10,Gdx.graphics.getHeight() - this.ibExit.getHeight());
        this.ibExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                closeShop();
                pReference.getCurrentHabitat().setVisible(true);
                event.getStage().getActors().get(1).setVisible(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        this.addActor(ibExit);
    }

    protected void closeShop() {
        this.setVisible(false);
    }

    protected void buy(Item item) {
        this.pReference.buy(item);
        this.pReference.setGold(this.pReference.getGold() - item.getBuyValue());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glClearColor(249 / 255f, 193 / 255f, 65 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw(batch, parentAlpha);
        font.draw(batch, this.pReference.getGold() + "", 200, 105);
        font.draw(batch, "Shop", 150, Gdx.graphics.getHeight() - 50);
    }
}
