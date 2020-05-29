package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Effects.BarricadeExplosion;
import com.timgapps.warfare.Units.GameUnits.Effects.Fire;
import com.timgapps.warfare.Units.GameUnits.Player.HealthBar;
import com.timgapps.warfare.Warfare;

public class Barricade {
    //public class Barricade extends Group {
    public static final int ROCKS = 1;
    public static final int TREES = 2;
    private int typeOfBarricade;
    private Level level;
    private Image rockBig, rockMiddle, rockSmall;
    private Body body;
    private float health = 100;
//    private float health = 24;

    private Texture healthTexture;
    private Texture backTexture;
    private float fullHealth;
    private int healthBarWidth;
    private int healthBarHeight;
    private boolean isDrawHealthBar = false;
    private HealthBar healthBar;

    private BarricadeExplosion barricadeExplosion1;
    private BarricadeExplosion barricadeExplosion2;
    private BarricadeExplosion barricadeExplosion3;
    private boolean isDestroyed = false;

    private int numOfExplosions = 0;
    private float posX, posY;
    private float width;

    public Barricade(Level level, int typeOfBarricade) {
        this.level = level;
        this.typeOfBarricade = typeOfBarricade;

        barricadeExplosion1 = new BarricadeExplosion(this);
        barricadeExplosion2 = new BarricadeExplosion(this);
        barricadeExplosion3 = new BarricadeExplosion(this);

        createBarricade(typeOfBarricade);

        barricadeExplosion1.setPosition(posX - 20, posY + 140);
        barricadeExplosion3.setPosition(posX - 10, posY + 80);
        barricadeExplosion2.setPosition(posX - 30, posY - 10);

        level.addChild(barricadeExplosion1);
        level.addChild(barricadeExplosion3);
        level.addChild(barricadeExplosion2);

        /** создадим HealthBar **/
        healthBarWidth = 108;        // ширина HealthBar
        healthBarHeight = 10;       // высота HealthBar
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight, health);

//        barricadeExplosion1.start();
    }

    /**
     * метод для создания HealthBar
     *
     * @param
     **/
    private void createHealthBar(int healthBarWidth, int healthBarHeight, float health) {
        healthBar = new HealthBar(healthBarWidth, healthBarHeight, health);
        level.addChild(healthBar, getX(), rockSmall.getY() + rockSmall.getHeight() + 16);
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

//                rockSmall.setPosition(1100, 300);
//                rockMiddle.setPosition(1120, 240);
//                rockBig.setPosition(1090, 150);

                // ширина баррикады
                width = rockBig.getWidth();

                // координата X
                posX = level.getWidth() - width - 100;
                posY = 150;

                rockSmall.setPosition(posX + 10, posY + 150);
                rockMiddle.setPosition(posX + 30, posY + 90);
                rockBig.setPosition(posX, posY);

                level.arrayActors.add(rockSmall);
                level.arrayActors.add(rockMiddle);
                level.arrayActors.add(rockBig);


                level.addChild(rockSmall);
                level.addChild(rockMiddle);
                level.addChild(rockBig);


                // тело баррикады
                body = createBody(posX + width / 2, posY);
//                body = createBody(rockBig.getX() + rockBig.getWidth() / 2, rockBig.getY());
                break;
        }
    }

    protected void addDamageLabel(float x, float y, float value) {
        new DamageLabel(level, x, y, (int) value);
    }

    /**
     * метод для получения координаты X БАРРИКАДЫ
     **/
    public float getX() {
        return rockBig.getX();
    }

    /**
     * метод для получения координаты Y БАРРИКАДЫ
     **/
    public float getY() {
        return rockSmall.getY() + rockSmall.getHeight() + 8;
    }

    public void setHealth(float damage) {
        health -= damage;

        if (health <= 0) {
            health = 0;
            setToDestroy();
            healthBar.remove();
//            body.setActive(false);
            barricadeExplosion1.start();
        }

        /** добавим цифры получаемого урона **/
        addDamageLabel(rockSmall.getX() + rockSmall.getWidth() / 2, rockSmall.getY() + rockSmall.getHeight() + 16, damage);

        /** установим количество здоровья у БАРРИКАДЫ **/
        healthBar.setHealth(health);

        /** установим флаг isDrawHealthBar = true, чтобы отрисовать HealthBar **/
        healthBar.setIsDrawHealthBar(true);
    }

    public void setToDestroy() {
//        isDestroyed = true;
        body.setActive(false);
    }

    public float getHealth() {
        return health;
    }

    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
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

    public void destroy() {
        numOfExplosions++;
        if (numOfExplosions < 4) {
            if (barricadeExplosion1.isEnd() && !barricadeExplosion2.isStarted()) {
                barricadeExplosion2.start();
                barricadeExplosion1.remove();
                barricadeExplosion1.setVisible(false);
            }

            if (barricadeExplosion2.isEnd() && !barricadeExplosion3.isStarted()) {
                barricadeExplosion3.start();
                barricadeExplosion2.remove();
                barricadeExplosion2.setVisible(false);
            }

            if (barricadeExplosion3.isEnd() && !isDestroyed) {
                barricadeExplosion3.remove();
                barricadeExplosion3.setVisible(false);

                switch (typeOfBarricade) {
                    case ROCKS:
                        rockSmall.remove();
                        rockMiddle.remove();
                        rockBig.remove();
                        break;
                }
                isDestroyed = true;

                /** если баррикада разрушена вызываем метод завершения уровня (победа) **/
//                level.levelCompleted();
            }
        }
    }

    /**
     * метод проверяет разрушен ли баррикада
     **/
    public boolean isBarricadeDestroyed() {
        return isDestroyed;
    }

}
