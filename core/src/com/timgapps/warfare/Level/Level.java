package com.timgapps.warfare.Level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Tools.WorldContactListener;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.Enemy.Zombie;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.Gnome;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Level extends StageGame {

    public static final float WORLD_SCALE = 100; // коэффициент масщтабирования
    private Box2DDebugRenderer debugRender;

    private World world;
    private float accumulator;
    public static final float STEP = 1 / 55f;
    private ArrayList<EnemyUnit> arrayEnemies;

    public Level() {
        setBackGround("level_bg");
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener()); // присваиваем слушатель ContactListener, который регистрирует событие столкновения в игровом мире
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона

        Gnome gnome = new Gnome(this, 100, 400, 10, 10);
        Zombie zombie = new Zombie(this, 800, 300, 20, 10);
        Zombie zombie1 = new Zombie(this, 1200, 450, 20, 10);
        Zombie zombie2 = new Zombie(this, 1400, 250, 20, 10);
        Zombie zombie3 = new Zombie(this, 2000, 350, 20, 10);
        accumulator = 0;
        arrayEnemies = new ArrayList<EnemyUnit>();
        arrayEnemies.add(zombie);
        arrayEnemies.add(zombie1);
        arrayEnemies.add(zombie2);
        arrayEnemies.add(zombie3);
    }

    public ArrayList<EnemyUnit> getArrayEnemies() {
        return arrayEnemies;
    }

    public void removeEnemyUnitFromArray(EnemyUnit unit) {
        int targetIndex = 0;
        for (int i = 0; i < arrayEnemies.size(); i++) {
            if (unit.equals(arrayEnemies.get(i))) {
                arrayEnemies.remove(i);
                System.out.println("remove i= " + i);
                System.out.println("break");
                break;
            }
        }
//        arrayEnemies.remove(i);
    }

    private void setBackGround(String region) {
        clearBackground();
        Image bg = new Image(Warfare.atlas.findRegion(region));
        addBackground(bg, true, false);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.stage.getBatch().setColor(1, 1, 1, 1);     //переустановим альфу для всец сцены
        if (Setting.DEBUG_WORLD) {
            debugRender.render(world, camera.combined.cpy().scl(WORLD_SCALE));
        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);


        /** Timur **/
        accumulator += delta;
        while (accumulator >= STEP) {
//            if (state == PLAY) {
                world.step(STEP, 8, 6);
//            }
            accumulator -= STEP;

//            player1.updatePosition();
//            updateCamera();

        }
    }
}
