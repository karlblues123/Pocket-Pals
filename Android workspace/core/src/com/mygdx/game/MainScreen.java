package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainScreen implements ApplicationListener {
	Player player;
	Stage stage;
	Overlay overlay;
	SpriteBatch batch;
	Sound sBackground;

	@Override
	public void create () {

		this.batch = new SpriteBatch();
		this.stage = new Stage();

		this.player = new Player(1500,"Sample");

		this.stage.addActor(this.player.getCurrentHabitat());
		this.sBackground = Gdx.audio.newSound(Gdx.files.internal("sounds\\background.mp3"));
		this.sBackground.play();

		Gdx.input.setInputProcessor(stage);

        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        Gson gson = new Gson();

        if(!prefs.getString("arr").isEmpty()){
            ArrayList<DBAnimal> a = gson.fromJson(prefs.getString("arr"), new TypeToken<ArrayList<DBAnimal>>() { }.getType());
            //ArrayList<Animal> ab = new ArrayList<Animal>();
            for(int i=0; i<a.size(); i++){
                this.player.addAnimal(new Animal(a.get(i).name, Animal.Species.valueOf(a.get(i).species), a.get(i).hunger, a.get(i).iHabitatIndex));
            }
            //this.player.setAnimals(ab);
			this.player.setGold(prefs.getInteger("money"));
        }
        //System.out.println(prefs.getString("arr"));
        //System.out.println(this.player.getAnimals().toString());
		this.overlay = new Overlay(this.player);
		this.stage.addActor(this.overlay);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {

        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        Gson gson = new Gson();

        ArrayList<DBAnimal> a = new ArrayList<DBAnimal>();
        for(int i=0; i<this.player.getAnimals().size(); i++){
            a.add(new DBAnimal(this.player.getAnimal(i).getName(),this.player.getAnimal(i).getSpecies().toString(),this.player.getAnimal(i).getHunger(),this.player.getAnimal(i).getHabitatIndex()));
        }

        String json = gson.toJson(a,  new TypeToken<ArrayList<Animal>>(){}.getType());

        prefs.putString("arr",json);
        prefs.putInteger("money",this.player.getGold());
        prefs.flush();
        System.out.println(prefs.getString("arr"));
        System.out.println(this.player.getAnimals().toString());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
