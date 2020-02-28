package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Barricade {
    //public class Barricade extends Group {
    public static final int ROCKS = 1;
    public static final int TREES = 2;
    private int typeOfBarricade;
    private Level level;

    private Image rockBig, rockMiddle, rockSmall;

    private Body body;

    private float health = 5;

    private Texture healthTexture;
    private Texture backTexture;
    private float fullHealth;
    private int healthBarWidth;
    private int healthBarHeight;
    private boolean isDrawHealthBar = false;


    public Barricade(Level level, int typeOfBarricade) {
        this.level = level;
        this.typeOfBarricade = typeOfBarricade;
        createBarricade(typeOfBarricade);

        /** создадим HealthBar **/
        healthBarWidth = 54;        // ширина HealthBar
        healthBarHeight = 10;       // высота HealthBar
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight);
    }

    public void drawHealthBar(Batch batch, float x, float y) {
        batch.draw(backTexture, getX() + x, getY() + y);
        batch.draw(healthTexture, getX() + x + 1, getY() + y + 1, health * (healthBarWidth - 2) / fullHealth, healthBarHeight - 2);
    }

    /**
     * метод для сздания HealthBar
     * @param
     **/
    private void createHealthBar(int healthBarWidth, int healthBarHeight) {
        Pixmap healthPixmap = createProceduralPixmap(healthBarWidth - 2, healthBarHeight - 2, 1, 0, 0);
        Pixmap backPixmap = createProceduralPixmap(healthBarWidth, healthBarHeight, 0, 0, 0);
        healthTexture = new Texture(healthPixmap);
        backTexture = new Texture(backPixmap);
    }

    private Pixmap createProceduralPixmap(int width, int height, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }


    /**
     * метод для создания БАРРИКАДЫ
     **/
    private void createBarricade(int typeOfBarricade) {
        switch (typeOfBarricade) {
            case ROCKS:
                rockBig = new Image(Warfare.atlas.findRegion("rock_big"));
                rockMiddle = new Image(Warfare.atlas.findRegion("rock_middle"));
                rockSmall = new Image(Warfare.atlas.findRegion("rock_small"));

                rockSmall.setPosition(1100, 300);
                rockMiddle.setPosition(1120, 240);
                rockBig.setPosition(1090, 150);

                level.arrayActors.add(rockSmall);
                level.arrayActors.add(rockMiddle);
                level.arrayActors.add(rockBig);

                level.addChild(rockSmall);
                level.addChild(rockMiddle);
                level.addChild(rockBig);

                body = createBody(rockBig.getX() + rockBig.getWidth() / 2, rockBig.getY());
        }
    }

    /**
     * метод для получения координаты X БАРРИКАДЫ
     **/
    public float getX() {
        return rockBig.getX();
    }

    /**
     * метод для получения координаты X БАРРИКАДЫ
     **/
    public float getY() {
        return rockSmall.getY() + rockSmall.getHeight() + 8;
    }

    public void setHealth(float damage) {
        health -= damage;
        if (health <= 0) {
            body.setActive(false);
        }
    }

    public float getHealth() {
        return health;
    }

    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
//        def.type = BodyDef.BodyType.DynamicBody;
        Body body = level.getWorld().createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24 / Level.WORLD_SCALE, 100 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 10;
        fDef.filter.categoryBits = GameUnit.BARRICADE_BIT;
        fDef.filter.maskBits = GameUnit.PLAYER_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, (y + 100) / Level.WORLD_SCALE, 0);

        return body;
    }
}
