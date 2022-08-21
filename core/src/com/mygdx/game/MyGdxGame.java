package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	SpritesAnimation animation;

	String direction = "left";

	float currentPosition;
	float stepLength = 3f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		animation = new SpritesAnimation("character.png", 6, 4, Animation.PlayMode.LOOP);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		TextureRegion frame = animation.getFrame();
		animation.setTime(Gdx.graphics.getDeltaTime());

		if (direction == "left" && Gdx.graphics.getWidth() > (currentPosition + frame.getRegionWidth())) {
			currentPosition += stepLength;
		} else if (direction == "left" && Gdx.graphics.getWidth() <= (currentPosition + frame.getRegionWidth())) {
			direction = "right";
		} else if (direction == "right" && currentPosition > 0) {
			currentPosition -= stepLength;
		} else if (direction == "right" && currentPosition <= 0) {
			direction = "left";
		}

		animation.flip(direction);

		batch.begin();
		batch.draw(frame, currentPosition, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		animation.dispose();
	}
}
