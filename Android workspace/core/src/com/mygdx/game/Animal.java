package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

/**
 * Created by Karl on 3/8/2016.
 */
public class Animal extends Actor {
    //Enum Species
    public enum Species {
        CAT,
        DOG,
        PENGUIN,
        BEAVER;
    }

    //coin var
    private int coinctr;

    //Animation variables
    private TextureAtlas taSpriteSheet;
    private TextureRegion[] trIdle;
    private Animation aIdle;
    private TextureRegion[] trLeftWalk;
    private TextureRegion[] trRightWalk;
    private Animation aLeftWalk;
    private Animation aRightWalk;
    private Animation currAnimation;

    public Food fMyFood;

    private float fTime;
    private int iHunger;
    private int iHappiness;
    private Species sSpecies;
    private int iHabitatIndex;
    private Rectangle rBounds;

    private Vector2 vDestination;
    private Vector2 vSpeed;

    private StateMachine smBehaviour;

    public boolean hasReached;

    private BitmapFont font;

    public Animal(String name, Species species) {
        coinctr=0;
        this.sSpecies = species;
        super.setName(name);
        this.iHunger = 50;
        this.init();
    }

    public Animal(String name,Species species,int hunger) {
        this.sSpecies = species;
        super.setName(name);
        this.iHunger = hunger;
        this.init();
    }

    public Animal(String name,Species species,int hunger,int habitatIndex) {
        this.sSpecies = species;
        super.setName(name);
        this.iHunger = hunger;
        this.iHabitatIndex = habitatIndex;
        this.init();
    }

    private void init() {
        //Initialize the animations
        this.taSpriteSheet = new TextureAtlas(Gdx.files.internal("animal\\"+sSpecies.toString().toLowerCase()+".atlas"));
        this.trIdle = new TextureRegion[2];
        this.trIdle[0] = (taSpriteSheet.findRegion("i1"));
        this.trIdle[1] = (taSpriteSheet.findRegion("i2"));
        this.trLeftWalk = new TextureRegion[4];
        for(int i = 1; i <= 4; i++) {
            this.trLeftWalk[i-1] = (taSpriteSheet.findRegion("w"+i));
        }
        this.trRightWalk = new TextureRegion[4];
        for(int i = 1; i <= 4; i++) {
            this.trRightWalk[i-1] = (taSpriteSheet.findRegion("wa"+i));
        }
        this.aIdle = new Animation(1/10f,trIdle);
        this.aLeftWalk = new Animation(1/10f, trLeftWalk);
        this.aRightWalk = new Animation(1/10f, trRightWalk);
        this.currAnimation = this.aIdle;
        this.hasReached = false;

        //Initialize the State Machine for the AI
        this.smBehaviour = new DefaultStateMachine<Animal, AnimalState>(this,AnimalState.IDLE);

        //Set the bounds of the animal
        this.setBounds(super.getX(),super.getY(),157,186);

        //Set rectangle bounds
        this.rBounds = new Rectangle(super.getX(),super.getY(),157,186);

        //Set the Vectors
        this.vDestination = new Vector2(0,0);
        this.vSpeed = new Vector2(0,0);

        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(3f);
    }

    public int getHunger() {
        return iHunger;
    }

    public void setHunger(int hunger) {
        this.iHunger = hunger;
    }

    public Species getSpecies() {
        return sSpecies;
    }

    public void setSpecies(Species species) {
        this.sSpecies = species;
    }

    public int getHabitatIndex() {
        return iHabitatIndex;
    }

    public void setHabitatIndex(int habitatIndex) {
        this.iHabitatIndex = habitatIndex;
    }

    public float getTime() {
        return fTime;
    }

    public void setTime(float time) {
        this.fTime = time;
    }

    public Rectangle getBounds() {
        return rBounds;
    }

    public void setBounds(Rectangle bounds) {
        this.rBounds = bounds;
    }

    public Vector2 getDestination() {
        return vDestination;
    }

    public void setDestination(Vector2 destination) {
        this.vDestination = destination;
    }

    public Vector2 getSpeed() {
        return vSpeed;
    }

    public void setSpeed(Vector2 speed) {
        this.vSpeed = speed;
    }

    public StateMachine getBehaviour() {
        return smBehaviour;
    }

    public enum AnimalState implements State<Animal> {

        IDLE() {
            private Random rng = new Random();
            private float fRecordedDuration = 0;

            @Override
            public void enter(Animal animal) {
                animal.ChangeAnimation(this);
                fRecordedDuration = rng.nextInt(10 + 1) + 1;
            }

            @Override
            public void update(Animal animal) {
                fRecordedDuration -= Gdx.graphics.getDeltaTime();
                if(fRecordedDuration <= 0) {
                    animal.getBehaviour().changeState(WALK);
                }
            }

            @Override
            public void exit(Animal animal) {
                fRecordedDuration = 0;
            }
        },
        WALK() {
            private Random rng = new Random();

            @Override
            public void enter(Animal animal) {
                animal.getDestination().set(rng.nextInt(1700 - 250 + 1) + 250 - animal.getWidth(), rng.nextInt(800 - 400 + 1) + 400 - animal.getHeight());
                animal.getSpeed().set(animal.getDestination().x - animal.getX(),animal.getDestination().y - animal.getY());
                animal.getSpeed().nor();
                animal.getSpeed().scl(5);
                animal.ChangeAnimation(this);
                animal.hasReached = false;
            }

            @Override
            public void update(Animal animal) {
                //TO DO update on walk
                if(animal.hasReached) {
                    animal.getBehaviour().changeState(IDLE);
                }
                else {
                    animal.Walk();
                }
            }

            @Override
            public void exit(Animal animal) {
                animal.getDestination().scl(0);
                animal.getSpeed().scl(0);
            }
        },
        WALKTO() {
            @Override
            public void enter(Animal animal) {
                animal.getDestination().set(animal.fMyFood.getX(), animal.fMyFood.getY());
                animal.getSpeed().set(animal.getDestination().x - animal.getX(),animal.getDestination().y - animal.getY());
                animal.getSpeed().nor();
                animal.getSpeed().scl(5);
                animal.ChangeAnimation(this);
                animal.hasReached = false;
            }

            @Override
            public void update(Animal animal) {
                if(animal.hasReached) {
                    animal.getBehaviour().changeState(EAT);
                }
                else {
                    animal.Walk();
                }
            }

            @Override
            public void exit(Animal animal) {
                animal.getDestination().scl(0);
                animal.getSpeed().scl(0);
            }
        },

        EAT() {
            private float counter;
            @Override
            public void enter(Animal animal) {
                animal.ChangeAnimation(this);
                counter = 0f;
                super.enter(animal);
            }

            @Override
            public void update(Animal animal) {
                counter += Gdx.graphics.getDeltaTime();
                if(counter >= 1) {
                    if(animal.fMyFood != null) {
                        animal.fMyFood.setCurrentMagnitude(animal.fMyFood.getCurrentMagnitude() - 1);
                        animal.iHunger++;
                        counter = 0;
                        if (animal.fMyFood.getCurrentMagnitude() <= 0) {
                            animal.fMyFood = null;
                        }
                    }
                }
                if(animal.fMyFood == null) {
                    System.out.println("Finished Eating");
                    animal.fMyFood = null;
                    animal.getBehaviour().changeState(IDLE);
                }
                super.update(animal);
            }

            @Override
            public void exit(Animal entity) {
                counter = 0;
                super.exit(entity);
            }
        };

        @Override
        public void enter(Animal entity) {

        }

        @Override
        public void update(Animal entity) {

        }

        @Override
        public void exit(Animal entity) {

        }

        @Override
        public boolean onMessage(Animal entity, Telegram telegram) {
            return false;
        }
    }

    public void Idle() {
        if(currAnimation != aIdle) {
            this.currAnimation = this.aIdle;
        }
    }

    public void Walk() {
        if(this.vDestination.dst(super.getX(),super.getY()) < 10) {
            System.out.print(this.hasReached);
            this.hasReached = true;
        } else {
            super.setX(super.getX() + this.vSpeed.x);
            super.setY(super.getY() + this.vSpeed.y);
        }
    }

    public void ChangeAnimation(AnimalState state) {
        switch(state) {
            case EAT:
            case IDLE: this.currAnimation = this.aIdle;break;
            case WALKTO:
            case WALK: if(this.vSpeed.x > 0) {
                            this.currAnimation = this.aRightWalk;
                        }
                        else {
                            this.currAnimation = this.aLeftWalk;
                        }break;
        }
    }

    public void coinGen(){
    coinctr++;
        if(coinctr>=500){
            coinctr=0;
            ((Habitat)this.getParent()).addCoin(new Coin(this.getX(),this.getY()));
        }
    }

    @Override
    public void act(float delta) {

        coinGen();
        super.act(delta);
        fTime += delta;
        smBehaviour.update();
        this.setBounds(super.getX(), super.getY(), 157, 186);
        this.rBounds.setPosition(super.getX(), super.getY());
    }

    public void FoodAlerted(Food x){
        if(x.getX() - super.getX() > 10 && x.getY() - super.getY() > 10) {
            System.out.println("FOOD!");
            this.fMyFood = x;
            this.getBehaviour().changeState(Animal.AnimalState.WALKTO);
        }
    }

    @Override
    public void draw(Batch batch,float alpha) {
        font.draw(batch,this.getName(),super.getX(),super.getY() + currAnimation.getKeyFrame(fTime,true).getRegionHeight());
        batch.draw(currAnimation.getKeyFrame(fTime, true), super.getX(), super.getY());
    }
}
