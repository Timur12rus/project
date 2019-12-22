package com.timgapps.warfare.Level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.UnitButton;
import com.timgapps.warfare.Tools.WorldContactListener;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.Enemy.Zombie1;
import com.timgapps.warfare.Units.Player.Archer1;
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


        /** Добавим вражеских юнитов **/
//        Random random = new Random();
        Zombie1 zombie = new Zombie1(this, 800, 250, 20, 10);
        Zombie1 zombie1 = new Zombie1(this, 1300, 300, 20, 10);
        Zombie1 zombie2 = new Zombie1(this, 1100, 350, 20, 10);
        Zombie1 zombie3 = new Zombie1(this, 900, 200, 20, 10);
//        Zombie1 zombie3 = new Zombie1(this, 900, 120 + (random.nextFloat() * 150) + 30, 20, 10);
//        Zombie1 zombie4 = new Zombie1(this, 870, 120 + (random.nextFloat() * 150), 50, 10);
//        Zombie zombie5 = new Zombie(this, 930, 150 + (random.nextFloat() * 150) + 20, 50, 10);
        accumulator = 0;
        arrayEnemies = new ArrayList<EnemyUnit>();
        arrayEnemies.add(zombie);
        arrayEnemies.add(zombie1);
        arrayEnemies.add(zombie2);
        arrayEnemies.add(zombie3);
//        arrayEnemies.add(zombie1);
//        arrayEnemies.add(zombie2);
//        arrayEnemies.add(zombie3);
//        arrayEnemies.add(zombie4);
//        arrayEnemies.add(zombie5);

//        addChild(new Archer1(this, 200, 200, 30, 20));


        /** Добавим кнопки для вызова игровых юнитов **/
        addChild(new UnitButton(this, new Image(Warfare.atlas.findRegion("gnomeActive")),
                        new Image(Warfare.atlas.findRegion("gnomeInactive")), UnitButton.TypeOfUnit.GNOME),
                500, 16);

        addChild(new UnitButton(this, new Image(Warfare.atlas.findRegion("archer1Active")),
                        new Image(Warfare.atlas.findRegion("archer1Inactive")), UnitButton.TypeOfUnit.ARCHER1),
                640, 16);
    }


    public ArrayList<EnemyUnit> getArrayEnemies() {
        return arrayEnemies;
    }

    public void removeEnemyUnitFromArray(EnemyUnit unit) {
        int targetIndex = 0;
//        for (int i = 0; i < arrayEnemies.size(); i++) {
//            if (unit.equals(arrayEnemies.get(i))) {
//                arrayEnemies.remove(i);
//                System.out.println("remove i= " + i);
//                System.out.println("break");
//                break;
//            }
//        }

        arrayEnemies.remove(unit);
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
//        this.stage.getBatch().setColor(1, 1, 1, 1);     //переустановим альфу для всец сцены
        if (Setting.DEBUG_WORLD) {
            debugRender.render(world, camera.combined.cpy().scl(WORLD_SCALE));
        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        compareActorsYPos();

        /** Timur **/
        accumulator += delta;
        while (accumulator >= STEP) {
            world.step(STEP, 8, 6);
            accumulator -= STEP;
        }
    }

    public void addGnome() {
        new Gnome(this, 100, 160, 20, 10);
    }

    public void addArcher1() {
        new Archer1(this, 100, 160, 20, 10);
    }

    public void compareActorsYPos() {

        try {
            ArrayList<EnemyUnit> gameActors = arrayEnemies;
            boolean needIteration = true;
            while (needIteration) {
                needIteration = false;
                for (int i = 1; i < gameActors.size(); i++) {
//            gameActors.get(i);

                    if (gameActors.get(i).getY() > gameActors.get(i - 1).getY() &&
                            (gameActors.get(i).getZIndex() > gameActors.get(i - 1).getZIndex())) {
                        int buf = gameActors.get(i).getZIndex();
                        gameActors.get(i).setZIndex(gameActors.get(i - 1).getZIndex());
                        gameActors.get(i).setDraw(false);
                        gameActors.get(i - 1).setZIndex(buf);
                        gameActors.get(i - 1).setDraw(false);
                        needIteration = true;
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }
}
