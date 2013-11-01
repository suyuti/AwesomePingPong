package net.peakgames.mobile.android.devfest.pong.core.view.effects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleActor extends Actor {
	ParticleEffect effect;
	private boolean draw = false;
	private float animatedTime = 0;
	private float duration = 0.6f;

	public ParticleActor(ParticleEffect effect, float duration) {
		this.effect = effect;
		this.duration = duration;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(draw) {			
			effect.draw(batch);
		}
	}

	public void show(float positionX, float positionY, float minAngle, float maxAngle) {
		ParticleEmitter emitter = effect.getEmitters().get(0);
		emitter.getAngle().setHighMin(minAngle);
		emitter.getAngle().setHighMax(maxAngle);
		setPosition(positionX, positionY);
		effect.start(); // need to start the particle spawning
		draw = true;
	}
	
	public void show(float positionX, float positionY) {
		setPosition(positionX, positionY);
		effect.start(); // need to start the particle spawning
		draw = true;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		effect.setPosition(getX(), getY());
		effect.update(delta); // update it
		
		if(draw) {
			animatedTime += delta;
			if(animatedTime >= duration) {
				animatedTime = 0;
				draw = false;
			}
		}
	}
	

	public ParticleEffect getEffect() {
		return effect;
	}
}
