package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.DamageLabel;
import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Effects.Fire;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Arrow;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

public class SiegeTower extends Group {
    private Image tower, frontWheel, backWheel;
    private World world;
    private LevelScreen levelScreen;
    private float health;
    private Rectangle body;
    private boolean isDestroyed = false;
    private Texture healthTexture;
    private Texture backTexture;
    private float fullHealth;
    private int healthBarWidth;
    private int healthBarHeight;
    private boolean isDrawHealthBar = false;
    private Fire fire;
    private ParticleEffect smoke;
    private Explosion explosion1;
    private Explosion explosion2;
    private Vector2 bodyPosition;
    private final float WIDTH = 32;
    private final float HEIGHT = 220;
    private ShapeRenderer shapeRenderer;
    private boolean isMove;                 // флаг, движется ли башня
    private float angleFWheel = 0;
    private float velocity;
    private final float MAX_VELOCITY = 6;
    private float deltaAngle;
    private boolean isStart, isStop;
    private boolean isAttack;
    private boolean isHaveTarget;
    private EnemyUnitModel targetEnemy;
    private final float ATTACK_DISTANCE = 140;
    private final float ATTACK_PAUSE_TIME = 180;
    private float distanceToEnemy;
    private float attackPauseTime;

    public SiegeTower(LevelScreen levelScreen, float x, float y, float health, float damage) {
        this.levelScreen = levelScreen;
        bodyPosition = new Vector2();
        tower = new Image(Warfare.atlas.findRegion("tower"));
        frontWheel = new Image(Warfare.atlas.findRegion("wheel"));
        backWheel = new Image(Warfare.atlas.findRegion("wheel"));
        this.health = health;
        isStop = true;
        isMove = true;
        deltaAngle = 4;
        velocity = 2.6f;

        frontWheel.setOrigin(Align.center);
        frontWheel.setRotation(angleFWheel + 90);
        frontWheel.setPosition(135, 0);

        backWheel.setPosition(24, 0);
        backWheel.setOrigin(Align.center);
        backWheel.setRotation(angleFWheel);

        addActor(tower);
        addActor(frontWheel);
        addActor(backWheel);

        bodyPosition.set(x + tower.getWidth() - WIDTH - 16, y - 128);  // позиция тела
//        bodyPosition.set(x + tower.getWidth() - WIDTH / 3, y - 128);  // позиция тела
        body = createBody();
        smoke = new ParticleEffect();
        smoke.load(Gdx.files.internal("effects/smoke.paty"), Gdx.files.internal("effects/")); //file);

        /** создадим HealthBar **/
        healthBarWidth = 108;        // ширина HealthBar
        healthBarHeight = 10;       // высота HealthBar
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight);

        /** создадим анимацию взрыва **/
        explosion1 = new Explosion();
        explosion2 = new Explosion();
        explosion1.setPosition(tower.getWidth() / 2, 64);
        explosion2.setPosition(0, 96);
        addActor(explosion1);
        addActor(explosion2);

        /** создадим ОГОНЬ  и разместим его в координатах**/
        fire = new Fire(levelScreen);
        fire.setPosition(getX() + tower.getWidth() - 64, 48);
        smoke.setPosition(getX() + tower.getWidth() - 32, 364);
        addActor(fire);
        levelScreen.addChild(this, x, y);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            checkToDestroy();       // проверяем, нужно ли уничтожить актера
            smoke.update(delta);
            if (explosion1.isEnd()) {
                explosion2.start();
            }
            if (health > 0) {       // если башня не уничтожена
                if (isMove) {
                    smoke.setPosition(getX() + tower.getWidth() - 32, 364);
                    rotateWheels();
                }
                if (targetEnemy == null && !isHaveTarget) {          // если не имеет врага
                    targetEnemy = findTarget();
                }
                if (targetEnemy == null && isHaveTarget) {
                    isHaveTarget = false;
                    isAttack = false;
                }
                if (targetEnemy != null) {
                    System.out.println("TARGET TOWER ENEMY = " + targetEnemy.getName());
                    if (targetEnemy.isDestroyed()) {
                        targetEnemy = null;
                        isHaveTarget = false;
                        isAttack = false;
                    }
                }
                if (targetEnemy != null && !isAttack) {
                    attack();
                }
                if (isAttack) {
                    if (attackPauseTime < 0) {
                        attackPauseTime = ATTACK_PAUSE_TIME;
                        throwArrow();
                    }
                    attackPauseTime--;
                }
            }
        }
        // проверим, имеет ли башня цель-врага
    }

    private void attack() {
        isAttack = true;
        throwArrow();
    }

    private void throwArrow() {
        System.out.println("Throw Arrow!");
        Vector2 velocity = new Vector2(8, 0);
        Vector2 startPosition = new Vector2(getX() + tower.getWidth() - 96, getY() + 140);
        Vector2 endPosition = new Vector2(targetEnemy.getX(), targetEnemy.getY());
        System.out.println("StartPosition = " + startPosition);
        System.out.println("EndPosition = " + endPosition);
        float angle = endPosition.sub(startPosition).angle();
        velocity.setAngle(angle);
        System.out.println("Angle = " + angle);
        System.out.println("Velosity = " + velocity);
        new Arrow(levelScreen, startPosition, 10, velocity).setRotation(angle);
//        new Arrow(level, startPosition, 10, velocity.setAngle(0));
    }

    // метод устанавливает врага по которому будем стрелять
    public void setTargetEnemy(EnemyUnitModel enemyUnitModel) {
        targetEnemy = enemyUnitModel;
    }

    private EnemyUnitModel findTarget() {
        if (levelScreen.getArrayEnemies().size() > 0) {
            EnemyUnitModel enemyUnit = levelScreen.getArrayEnemies().get(0);
            float minDistance = enemyUnit.getX() - bodyPosition.x;
            targetEnemy = enemyUnit;
//            System.out.println("Target ENNNNEMMMYYY = " + targetEnemy);
            for (EnemyUnitModel enemyUnitModel : levelScreen.getArrayEnemies()) {
                distanceToEnemy = enemyUnitModel.getX() - bodyPosition.x;
                if (distanceToEnemy < ATTACK_DISTANCE) {
                    if (distanceToEnemy < minDistance) {
                        minDistance = distanceToEnemy;
                        targetEnemy = enemyUnitModel;
                        return targetEnemy;
                    }
                }
            }
            if ((targetEnemy.getX() - bodyPosition.x) < ATTACK_DISTANCE) {
                return targetEnemy;
            }
        }
        return null;        // возвращаем, что врага нет
    }

    // метод для проверки, может ли башня выстрелить по варжескому юниту

    // метод для движения
    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }

    public boolean isMove() {
        return isMove;
    }

    public Rectangle getBody() {
        return body;
    }

    // метод создает прямоугольное тело
    private Rectangle createBody() {
        Rectangle body = new Rectangle(bodyPosition.x, bodyPosition.y, WIDTH, HEIGHT);
        return body;
    }

    protected void addDamageLabel(float x, float y, float value) {
        new DamageLabel(levelScreen, x, y, (int) value);
    }

    /**
     * метод для рисовния healBar
     *
     * @param x - координата Х
     * @param y -
     **/
    public void drawHealthBar(Batch batch, float x, float y) {
        batch.draw(backTexture, getX() + x, getY() + y);
        batch.draw(healthTexture, getX() + x + 1, getY() + y + 1, health * (healthBarWidth - 2) / fullHealth, healthBarHeight - 2);
    }

    /**
     * метод для создания HealthBar
     *
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

    public void setToDestroy() {
        isDestroyed = true;
    }

    public void checkToDestroy() {
        if (isDestroyed) {
            if (explosion2.isEnd()) {
                this.remove();
                levelScreen.levelFailed();
//                levelScreen.gameOver();
            }
        }
    }

    /**
     * метод вычитает кол-во здоровья
     **/
    public void subHealth(float damage) {
        isDrawHealthBar = true;
        addDamageLabel(getX() + (tower.getWidth()) / 2 + 16, getY() + tower.getHeight() + 14, damage);
        health -= damage;
        if (health <= 0) {
            health = 0;
            explosion1.start();      // запускаем анимацию взрыва
            setToDestroy();         // делаеме тело не активным
        }

        if (health <= (fullHealth * 0.85)) {
            startFire();
        }
    }

    /**
     * метод запускает анимацию пламени
     **/
    private void startFire() {
        fire.startFire();
        smoke.start();
    }

    public float getHealth() {
        return health;
    }

    public float getFullHealth() {
        return fullHealth;
    }

    public void startMove() {
        isStart = true;
        isMove = true;
    }

    // метод для вращения колес
    private void rotateWheels() {
        // если машина должна остановиться
        if (isStart) {
            if (velocity < MAX_VELOCITY) {
                velocity += 0.05f;
            }
            if (deltaAngle < 4) {
                deltaAngle += 0.8f;
            }
            angleFWheel -= deltaAngle;
            if (angleFWheel < -360) {
                angleFWheel = 0;
            }
        } else if (isStop) {        // если машина должна остановиться
            if (velocity > 0) {
                velocity -= 0.05f;      // уменьшаем скорость
//                System.out.println("velocityTower = " + velocity);
            } else {
                velocity = 0;
                isMove = false;
                isStop = false;
            }
//            System.out.println("deltaAngle = " + deltaAngle);
            if (deltaAngle > 0) {
                deltaAngle -= 0.05f;
            } else {
                deltaAngle = 0;
            }
//            System.out.println("deltaAngle = " + deltaAngle);
        }
        if (isMove) {
            angleFWheel -= deltaAngle;
        }
        if (angleFWheel < -360) {
            angleFWheel = 0;
        }

        frontWheel.setRotation(angleFWheel + 90);
        backWheel.setRotation(angleFWheel);
        bodyPosition.add(velocity, 0);       // изменим позицию тела (прямоугольника)
        body.setX(bodyPosition.x);
        body.setY(bodyPosition.y);

        setPosition(getX() + velocity, getY());      // изменим позицию актера-башни
//        frontWheel.setPosition();
//        backWheel.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();
        if (Setting.DEBUG_GAME) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(bodyPosition.x, bodyPosition.y, WIDTH, HEIGHT);
            shapeRenderer.end();
        }
        batch.begin();

        if (fire.IsStarted())
            smoke.draw(batch);

        if (isDrawHealthBar) {
            drawHealthBar(batch, (tower.getWidth() - healthBarWidth) / 2 + 16, tower.getHeight());
        }
    }
}
