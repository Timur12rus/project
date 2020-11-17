package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.utils.Array;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

public class FlashEffect extends Actor {
    private Vector2 position;
    private Animation animation;
    protected float stateTime;
    private boolean isStarted, isEnd;
    private StageGame stageGame;
    private SequenceAction sa;

    public FlashEffect(StageGame stageGame, Vector2 position) {
        this.stageGame = stageGame;
        this.position = position;
        setPosition(position.x, position.y);
        stageGame.addChild(this);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("flash" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("flash2")));
        animation = new Animation(0.2f, frames);
        setSize(frames.get(3).getRegionWidth(), frames.get(3).getRegionHeight());
//        frames.clear();

//        SizeToAction sizeToAction = new SizeToAction();
////        SizeByAction sizeByAction   = new SizeByAction();
////        sizeByAction.setDuration(10);
//        sizeToAction.setSize(800, 480);
//        sizeToAction.setDuration(7);
////        sizeByAction.setAmount(800, 480);
        ScaleToAction scaleToAction = new ScaleToAction();
        scaleToAction.setScale(3);
        sa = new SequenceAction(scaleToAction);
        System.out.println("GetWidth  = " + getWidth());
//        this.addAction(sa);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animation.isAnimationFinished(stateTime) && !isEnd ) {
            isEnd = true;
            this.addAction(sa);
            System.out.println("isEnd  = " + isEnd);
        }
    }

    public void start() {
//        if (!isStarted) {
            isStarted = true;
//        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isStarted) {
            stateTime += Gdx.graphics.getDeltaTime();
//            batch.draw((TextureRegion) animation.getKeyFrame(stateTime, false), position.x, position.y);
            batch.draw((TextureRegion) animation.getKeyFrame(stateTime, false), position.x, position.y,
                    getWidth() / 2, getHeight() / 2,
            getWidth(), getHeight(), 3, 3, 0);
            System.out.println("Draw  " +  position.x + ", " + position.y);
        }
    }
}
