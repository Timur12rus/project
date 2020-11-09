package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Warfare;

import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.ENEMY_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.STONE_BIT;

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
    private static int ENERGY_PRICE = 1;

    public Stone(Level level, Vector2 position, PlayerUnitData data) {
        super(level, position, 0);
        this.data = data;
//        this.health = data.getHealth();
//        this.damage = data.getDamage();
        health = 10;
        damage = 5;
        targetPos = new Vector2();
        targetPos.set(position.x, position.y);             // позиция цель
        this.position = position;
        image = new TextureRegion(Warfare.atlas.findRegion("block1"));
        this.position.add(-image.getRegionWidth() / 2, 600);  // начальная позиция камня
//        this.position.add(-21, 600);  // начальная позиция камня
        velocity.set(0, VELOCITY);
        level.addChild(this);
        level.arrayActors.add(this);
        System.out.println("Target Pos = " + targetPos);
        System.out.println("Start Pos = " + this.position);
        isDebug = true;
        setSize(image.getRegionWidth(), image.getRegionHeight());
        debug();
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
        System.out.println("Position = " + position);
        System.out.println("TargetPosition = " + targetPos);

        if ((position.y <= targetPos.y)) {
            if (!moveIsEnd) {
                moveIsEnd = true;
//                checkCollisionEnemyUnit();
            }
        }
        if (moveIsEnd) {
            position.y = targetPos.y;
            checkCollisionEnemyUnit();
        }
        /** изменим позицию нашего прямоугольника для определения коллизий **/
    }

    public void setHealth(float damage) {
        health -= damage;
    }

    private void checkCollisionEnemyUnit() {
    }

    public float getHealth() {
        return health;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }

    public static int getEnergyPrice() {
        return ENERGY_PRICE;
    }
}
