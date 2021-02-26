package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Warfare;

public class Stone extends Bullet {
    private Vector2 targetPos;
    private final float VELOCITY = -14;
    private float damage;
    private PlayerUnitData data;
    private boolean moveIsEnd = false;
    private boolean isDamaged = false;
    private float health;
    //    private static final float APPEARANCE_TIME = 10;
    private static final float APPEARANCE_TIME = 1;
    //    private static int ENERGY_PRICE = 6;
    private static int energyPrice = 0;
    private ParticleEffect destroyEffect;

    public Stone(LevelScreen levelScreen, Vector2 position, PlayerUnitData data) {
        super(levelScreen, position, 0);
        this.data = data;
        this.health = data.getHealth();
        this.damage = data.getDamage();
//        health = 10;
//        damage = 5;
        energyPrice = data.getEnergyPrice();
        targetPos = new Vector2();
        targetPos.set(position.x, position.y);             // позиция цель
        this.position = position;
        image = new TextureRegion(Warfare.atlas.findRegion("block1"));
        this.position.add(-image.getRegionWidth() / 2, 600);  // начальная позиция камня
//        this.position.add(-21, 600);  // начальная позиция камня
        velocity.set(0, VELOCITY);
        levelScreen.addChild(this);
        levelScreen.arrayActors.add(this);
        System.out.println("Target Pos = " + targetPos);
        System.out.println("Start Pos = " + this.position);
//        isDebug = true;
        setSize(image.getRegionWidth(), image.getRegionHeight());
        debug();
        destroyEffect = new ParticleEffect();
        destroyEffect.load(Gdx.files.internal("effects/destroyRockEffect10.paty"), Gdx.files.internal("effects/")); //file);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isDestroyed) {
            destroyEffect.draw(batch);
        }
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle();
        body.set(position.x, position.y, 42, 24);
        return body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /** изменим позицию нашего актреа **/
//        System.out.println("Position = " + position);
//        System.out.println("TargetPosition = " + targetPos);

        if ((position.y <= targetPos.y)) {
            if (!moveIsEnd) {
                moveIsEnd = true;
//                checkCollisionEnemyUnit();
            }
        }
        if (moveIsEnd) {
            position.y = targetPos.y;
            if (!isDestroyed) {
                checkCollisionEnemyUnit();
                destroyEffect.setPosition(position.x + image.getRegionWidth() / 2, position.y);
                destroy();
            }
        }
        if (isDestroyed) {
            destroyEffect.update(delta);
            if (destroyEffect.isComplete()) {
                destroyEffect.dispose();
                remove();
            }
        }

//        System.out.println("Body Position = " + body.getX() + ", " + body.getY());
//        System.out.println(" Position Actor = " + getX() + ", " + getY());
        /** изменим позицию нашего прямоугольника для определения коллизий **/
    }


    // метод для разрушения камня
    public void destroy() {
        image = null;
        isDestroyed = true;
        destroyEffect.start();
    }

    // метод для получения урона от противника
    public void subHealth(float damage) {
        health -= damage;
    }

    private void checkCollisionEnemyUnit() {
        if (moveIsEnd && !isDamaged) {
            try {
                for (EnemyUnitModel enemy : levelScreen.getArrayEnemies()) {
                    if (Intersector.overlaps(body, enemy.getBody())) {
                        enemy.subHealth(damage);
//                        enemy.subHealth(45);
                    }
                }
            } catch (Exception e) {

            }
            isDamaged = true;
        }
    }

    public float getHealth() {
        return health;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }

    public static int getEnergyPrice() {
        return energyPrice;
    }
}
