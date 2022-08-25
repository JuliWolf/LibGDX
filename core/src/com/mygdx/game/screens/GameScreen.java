package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.SpritesAnimation;

public class GameScreen implements Screen {

  private Main game;

  private SpritesAnimation animation;

  private String direction = "left";
  private float stepLength = 3f;
  private float currentPosition;

  public GameScreen(Main game) {
    this.game = game;

    animation = new SpritesAnimation("atlas/player.atlas", new String[]{"Jump", "Walk"}, Animation.PlayMode.LOOP);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);

    TextureRegion frame = animation.getFrame();

    renderFrame(frame);

    game.batch.begin();
    game.batch.draw(frame, currentPosition, 0);
    game.batch.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
      dispose();
      game.setScreen(new MenuScreen(game));
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    this.animation.dispose();
  }

  private void renderFrame (TextureRegion frame) {
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
  }
}
