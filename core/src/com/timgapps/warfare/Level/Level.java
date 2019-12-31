package com.timgapps.warfare.Level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.StoneButton;
import com.timgapps.warfare.Level.GUI.UnitButton;
import com.timgapps.warfare.Tools.WorldContactListener;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1;
import com.timgapps.warfare.Units.GameUnits.Player.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;
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
    public ArrayList<Actor> arrayActors;

    private float timeCount = 0;

    public Level() {
        setBackGround("level_bg");
        arrayEnemies = new ArrayList<EnemyUnit>();
        arrayActors = new ArrayList<Actor>();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener()); // присваиваем слушатель ContactListener, который регистрирует событие столкновения в игровом мире
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона


        /** Добавим вражеских юнитов **/
//        Random random = new Random();
        Zombie1 zombie = new Zombie1(this, 800, 250, 20, 10);
        Zombie1 zombie1 = new Zombie1(this, 1300, 230, 20, 10);
        Zombie1 zombie2 = new Zombie1(this, 1100, 170, 20, 10);
        Zombie1 zombie3 = new Zombie1(this, 900, 200, 20, 10);
//        Zombie1 zombie3 = new Zombie1(this, 900, 120 + (random.nextFloat() * 150) + 30, 20, 10);
//        Zombie1 zombie4 = new Zombie1(this, 870, 120 + (random.nextFloat() * 150), 50, 10);
//        Zombie zombie5 = new Zombie(this, 930, 150 + (random.nextFloat() * 150) + 20, 50, 10);
        accumulator = 0;

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

        addChild(new StoneButton(this, new Image(Warfare.atlas.findRegion("stoneButtonActive")),
                        new Image(Warfare.atlas.findRegion("stoneButtonInactive")), UnitButton.TypeOfUnit.STONE),
                780, 16);
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

        timeCount += delta;

        /** Timur **/
        accumulator += delta;
        while (accumulator >= STEP) {
            world.step(STEP, 8, 6);
            accumulator -= STEP;
        }

        compareActorsYPos();
    }

    public void addGnome() {
        new Gnome(this, 100, 160, 20, 10);
    }

    public void addArcher1() {
        new Archer1(this, 100, 160, 20, 10);
    }

    public void compareActorsYPos() {


        /** Полностью рабочий код, но с ошиблкой Zindex cannot be < 0 **/
//        if (arrayActors.size() > 1) {
//            ArrayList<Actor> gameActors = arrayActors;
////            ArrayList<Actor> gameActors = arrayActors;
//            boolean sorted = false;
//            int tempZIndex;
//            Actor tempActor;
//            while (!sorted) {
//                sorted = true;
//                for (int i = 0; i < gameActors.size() - 1; i++) {
//                    if (gameActors.get(i).getZIndex() < gameActors.get(i + 1).getZIndex()) {
//                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
//                            tempZIndex = gameActors.get(i).getZIndex();
//                            gameActors.get(i).setZIndex(gameActors.get(i + 1).getZIndex());
//                            gameActors.get(i + 1).setZIndex(tempZIndex);
//
//                            tempActor = gameActors.get(i);
//
//                            gameActors.set(i, gameActors.get(i + 1));
//                            gameActors.set(i + 1, tempActor);
//                            sorted = false;
//                        }
//                    }
//                }
//            }
//        }

        if (arrayActors.size() > 1) {
            Array<Actor> gameActors = stage.getActors();
//            ArrayList<Actor> gameActors = arrayActors;
            boolean sorted = false;
            int tempZIndex;
            Actor tempActor;
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < gameActors.size - 1; i++) {
                    if (gameActors.get(i).getZIndex() < gameActors.get(i + 1).getZIndex()) {
                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
                            tempZIndex = gameActors.get(i).getZIndex();
                            gameActors.get(i).setZIndex(gameActors.get(i + 1).getZIndex());
                            gameActors.get(i + 1).setZIndex(tempZIndex);

                            tempActor = gameActors.get(i);

                            gameActors.set(i, gameActors.get(i + 1));
                            gameActors.set(i + 1, tempActor);
                            sorted = false;
                        }
                    }
                }
            }
        }


//        } catch (Exception e) {
//            return;
//        }


//        try {
//
////            ArrayList<EnemyUnit> gameActors = arrayEnemies;
//            boolean needIteration = true;
//            while (needIteration) {
//                needIteration = false;
//                for (int i = 1; i < gameActors.size(); i++) {
//
//                    System.out.println(gameActors.toString());
//                    if ((gameActors.get(i).getY() > gameActors.get(i - 1).getY())
//                            && (gameActors.get(i).getZIndex() > gameActors.get(i - 1).getZIndex())) {
//                        int buf = gameActors.get(i).getZIndex();
//                        gameActors.get(i).setZIndex(gameActors.get(i - 1).getZIndex());
//                        gameActors.get(i - 1).setZIndex(buf);
//                        needIteration = true;
//                    }
//
//
//                }
//            }
//        } catch (Exception e) {
//            return;
//        }
    }
}
