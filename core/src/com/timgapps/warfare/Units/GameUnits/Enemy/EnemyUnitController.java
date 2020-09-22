package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;

public class EnemyUnitController extends GameUnitController {
    //    private ParticleEffect bloodSpray;      // эффект брызги
    private EnemyUnitModel model;

    public EnemyUnitController(EnemyUnitModel model) {
        super(model);
        this.model = model;
//        bloodSpray = new ParticleEffect();
//        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (model.isDamaged()) {
            model.getBloodSpray().setPosition(model.getX(), model.getY());
            model.getBloodSpray().update(delta);
        }
        if (model.isDamaged() && model.getBloodSpray().isComplete()) {
            model.setIsDamaged(false);
        }
        if (model.getHealth() <= 0) {
            
        }
    }

}
