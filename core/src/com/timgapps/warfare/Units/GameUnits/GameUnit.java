package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Interfaces.IBody;

public abstract class GameUnit extends Actor {
    protected Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected Rectangle bodyRectangle;
    protected Vector2 bodyPosition;
    protected World world;
    protected float health;
    protected float damage;
    protected Level level;
    protected boolean setToDestroyBody = false;
    protected boolean isDraw;        //  переменная указывает рисовать данного актера или нет
    protected float velocity;
//    protected Vector2 velocity;

    protected ShapeRenderer shapeRenderer;
    /**
     * переменная отвечает за то, отрисовывать ли прямоугольник для определения коллизий с камнем
     **/
    protected boolean isDebug = true;
    public static final short PLAYER_BIT = 1;
    public static final short ENEMY_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short STONE_BIT = 8;
    public static final short BARRICADE_BIT = 16;
    public static final short TOWER_BIT = 32;
    private boolean isDestroyed = false;
    private boolean isBodyInactive = false;
    protected float stateTime;
    protected Body body;
    protected int healthBarWidth;
    protected int healthBarHeight;
    protected Texture healthTexture;
    protected Texture backTexture;
    protected boolean isDrawHealthBar = false;
//    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    public GameUnitView.State currentState;
    protected float xPosDamageLabel, yPosDamageLabel;   // смещение по Х и У надписи значения получаемого урона при получении урона
    protected float fullHealth;

    /**
     * конструктор
     **/
    public GameUnit(Level level, float x, float y, float health, float damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.world = level.getWorld();
        isDraw = true;              // isDraw = true - значит отрисовывается актёр (его "рамка")
        currentState = GameUnitView.State.STAY;
        bodyRectangle = new Rectangle();
        bodyPosition = new Vector2(x, y);          // позиция прямоугольника "тела"
        if (isDebug) {                                      // проверим, если true отрисовываем прямоугольник "тело"
            shapeRenderer = new ShapeRenderer();
        }

        /** создадим HealthBar **/
        healthBarWidth = 54;        // ширина HealthBar
        healthBarHeight = 10;       // высота HealthBar
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight);
    }

    /**
     * метод для сздания HealthBar
     *
     * @param
     **/
    private void createHealthBar(int healthBarWidth, int healthBarHeight) {
        Pixmap healthPixmap = createProceduralPixmap(healthBarWidth - 2, healthBarHeight - 2, 1, 0, 0);
        Pixmap backPixmap = createProceduralPixmap(healthBarWidth, healthBarHeight, 0, 0, 0);
        healthTexture = new Texture(healthPixmap);
        backTexture = new Texture(backPixmap);
    }

    /**
     * метод для создания Pixmap для текстур healthBar'a
     *
     * @param width  - ширина Pixmap
     * @param height - высота Pixmap
     **/
    private Pixmap createProceduralPixmap(int width, int height, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    /**
     * метод для рисовния healthBar'a
     *
     * @param batch - spriteBatch
     * @param x     - смещение по Х
     * @param y     - смещение по У
     */
    protected void drawHealthBar(Batch batch, float x, float y) {
        batch.draw(backTexture, getX() + x, getY() + y);
        batch.draw(healthTexture, getX() + x + 1, getY() + y + 1, health * (healthBarWidth - 2) / fullHealth, healthBarHeight - 2);
    }

    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    /**
     * метод для установки занчения "здоровья" у персонажа, также используется при получении урона от персонажей игрока
     *
     * @param value - урон
     **/
    public void subHealth(float value) {
        if (!isDrawHealthBar) {
            if (health > 0) isDrawHealthBar = true;
        }
        health -= value;
        if (health <= 0) {
            health = 0;
            isDrawHealthBar = false;
            if (!isBodyInactive) {
                stateTime = 0;
                currentState = GameUnitView.State.DIE;
                setInactiveBody();
            }
        }
    }

    protected void setInactiveBody() {
        isBodyInactive = true;
    }

    protected void addDamageLabel(float x, float y, float value) {
//        if (health > 0)
        new DamageLabel(level, x, y, (int) value);
    }

    /**
     * метод устанавливает отрисовывать ли актера
     *
     * @param draw
     **/
    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    /**
     * метод для атаки
     **/
    public void attack() {
    }

    /**
     * Метод для получения текущего состояния
     **/
    public GameUnitView.State getCurrentState() {
        return currentState;
    }

    /**
     * Метод для установки текущего состояния
     **/
    public void setCurrentState(GameUnitView.State currentState) {
        this.currentState = currentState;
    }


    /**
     * метод для нанесения урона юниту противника
     *
     * @param unit  - юнит противника
     * @param value - урон, наносимый противнику
     **/
    public void inflictDamage(GameUnit unit, float value) {
        if (unit != null) {
            unit.subHealth(value);
        }
    }

    /**
     * метод для получения значения здоровья
     **/
    public float getHealth() {
        return health;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        stateTime += Gdx.graphics.getDeltaTime();
        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(bodyRectangle.getX(), bodyRectangle.getY(), bodyRectangle.getWidth(), bodyRectangle.getHeight());
            shapeRenderer.end();
            batch.begin();
        }
    }

    public float getDamage() {
        return damage;
    }

    public void setToDestroy() {
        isDestroyed = true;
    }

    public void checkToDestroy() {
//        if (!body.isActive()) {
        if (!body.isActive() && isDestroyed) {
            world.destroyBody(body);
            this.remove();
        }

//        if (isDestroyed) {
//            body.setActive(false);
//        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (level.getState() != Level.PAUSED) {
//        if (level.getState() == Level.PLAY) {
            stateTime += Gdx.graphics.getDeltaTime();
            checkToDestroy();
            if (isBodyInactive) {
                body.setActive(false);
            }
        }
    }

}


