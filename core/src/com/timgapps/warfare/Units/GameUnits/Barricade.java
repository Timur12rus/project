package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Effects.BarricadeExplosion;
import com.timgapps.warfare.Units.GameUnits.Player.BarricadeHealthBar;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

//public class Barricade {
public class Barricade extends Group {
    public static final int ROCKS = 1;
    public static final int TREES = 2;
    private int typeOfBarricade;
    private Level level;
    private Image rockBig, rockMiddle, rockSmall;
    //    private Body body;
    private float health = 130;
    private Rectangle body;
    private Texture healthTexture;
    private Texture backTexture;
    private float fullHealth;
    private int healthBarWidth;
    private int healthBarHeight;
    private boolean isDrawHealthBar = false;
    private BarricadeHealthBar barricadeHealthBar;
    private BarricadeExplosion barricadeExplosion1;
    private BarricadeExplosion barricadeExplosion2;
    private BarricadeExplosion barricadeExplosion3;
    private boolean isDestroyed = false;
    private int numOfExplosions = 0;
    private float posX, posY;
    private float bodyWidth, bodyHeight;
    private ShapeRenderer shapeRenderer;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        if (Setting.DEBUG_GAME) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(posX, posY, bodyWidth, bodyHeight);
            shapeRenderer.end();
        }
        batch.begin();
    }

    public Barricade(Level level, int typeOfBarricade) {
        this.level = level;
        this.typeOfBarricade = typeOfBarricade;
        bodyWidth = 32;
        bodyHeight = 200;
        shapeRenderer = new ShapeRenderer();
        barricadeExplosion1 = new BarricadeExplosion(this);
        barricadeExplosion2 = new BarricadeExplosion(this);
        barricadeExplosion3 = new BarricadeExplosion(this);

        createBarricade(typeOfBarricade);

        barricadeExplosion1.setPosition(posX - 20, posY + 140);
        barricadeExplosion3.setPosition(posX - 10, posY + 80);
        barricadeExplosion2.setPosition(posX - 30, posY - 10);

        addActor(barricadeExplosion1);
        addActor(barricadeExplosion3);
        addActor(barricadeExplosion2);

        /** создадим HealthBar **/
        healthBarWidth = 108;        // ширина HealthBar
        healthBarHeight = 10;       // высота HealthBar
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight, health);
        level.addChild(this);
//        barricadeExplosion1.start();
    }

    /**
     * метод для создания HealthBar
     *
     * @param
     **/
    private void createHealthBar(int healthBarWidth, int healthBarHeight, float health) {
        barricadeHealthBar = new BarricadeHealthBar(healthBarWidth, healthBarHeight, health);
        addActor(barricadeHealthBar);
        barricadeHealthBar.setPosition(getX(), rockSmall.getY() + rockSmall.getHeight() + 16);
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
                bodyWidth = rockBig.getWidth();
                // координата X
                posX = level.getWidth() - bodyWidth - 100;
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
                body = createBody();
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
            barricadeHealthBar.remove();
            barricadeExplosion1.start();
        }

        /** добавим цифры получаемого урона **/
        addDamageLabel(rockSmall.getX() + rockSmall.getWidth() / 2, rockSmall.getY() + rockSmall.getHeight() + 16, damage);

        /** установим количество здоровья у БАРРИКАДЫ **/
        barricadeHealthBar.setHealth(health);

        /** установим флаг isDrawHealthBar = true, чтобы отрисовать HealthBar **/
        barricadeHealthBar.setIsDrawHealthBar(true);
    }

    public void setToDestroy() {
//        isDestroyed = true;
//        body.setActive(false);
    }

    public float getHealth() {
        return health;
    }

    private Rectangle createBody() {
        Rectangle body = new Rectangle(posX, posY, bodyWidth, bodyHeight);
        return body;
    }

    public Rectangle getBody() {
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
