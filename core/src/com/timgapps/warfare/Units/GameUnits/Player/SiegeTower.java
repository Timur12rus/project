package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.DamageLabel;
import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Effects.Fire;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;

import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.ENEMY_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.TOWER_BIT;

public class SiegeTower extends Group {
    private Image tower, frontWheel, backWheel;
    private World world;
    private Level level;
    private float health;

    private Body body;

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


    public SiegeTower(Level level, float x, float y, float health, float damage) {
        this.level = level;
        world = level.getWorld();
        tower = new Image(Warfare.atlas.findRegion("tower"));
        frontWheel = new Image(Warfare.atlas.findRegion("wheel"));
        backWheel = new Image(Warfare.atlas.findRegion("wheel"));

        this.health = health;

        frontWheel.setOrigin(Align.center);
        frontWheel.setRotation(90);
        frontWheel.setPosition(135, 0);
        backWheel.setPosition(24, 0);

        addActor(tower);
        addActor(frontWheel);
        addActor(backWheel);

        body = createBody(x + tower.getWidth(), (y));

        smoke = new ParticleEffect();
        smoke.load(Gdx.files.internal("effects/smoke.paty"), Gdx.files.internal("effects/")); //file);     /
//        smoke.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);


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
        fire = new Fire(level);
//        fire.setPosition(0, 0);
        fire.setPosition(x + tower.getWidth() - 64, 48);
        smoke.setPosition(x + tower.getWidth() - 32, 364);

//        explosion.start();

//        smoke.setPosition(fire.getX(), fire.getY() + 16);

        addActor(fire);
//        fire.startFire();

        level.addChild(this, x, y);
    }

    protected void addDamageLabel(float x, float y, float value) {
        new DamageLabel(level, x, y, (int) value);
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
        body.setActive(false);
    }

    public void checkToDestroy() {
        if (level.getState() != Level.PAUSED) {
            if (!body.isActive() && isDestroyed && explosion2.isEnd()) {
//        if (!body.isActive() && isDestroyed && explosion.isEnd()) {
                level.gameOver();
                world.destroyBody(body);
                this.remove();
                smoke.dispose();
            }

            if (isDestroyed) {
                body.setActive(false);
            }
        }
    }

    /**
     * метод устанавливает значение здоровья
     **/
    public void setHealth(float damage) {
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

    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
//        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24 / Level.WORLD_SCALE, 84 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = TOWER_BIT;
        fDef.filter.maskBits = ENEMY_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
        return body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (level.getState() != Level.PAUSED) {
            checkToDestroy();       // проверяем, нужно ли уничтожить актера
            smoke.update(delta);

            if (explosion1.isEnd()) {
                explosion2.start();
            }
        }

//        if (explosion2.isEnd()) {
//
//        }

//        if(explosion.isEnd()) {     // когда анимация взрыва завершится, уничтожаем актера (remove())
//        }
//

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (fire.IsStarted())
            smoke.draw(batch);

        if (isDrawHealthBar) {
            drawHealthBar(batch, (tower.getWidth() - healthBarWidth) / 2 + 16, tower.getHeight());
        }
    }
}
