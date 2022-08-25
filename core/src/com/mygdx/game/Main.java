package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;

public class Main extends Game {
  public SpriteBatch batch;

  @Override
  public void create() {
    batch = new SpriteBatch();

    setScreen(new MenuScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    super.dispose();

    batch.dispose();
  }

  @Override
  public void setScreen(Screen screen) {
    super.setScreen(screen);
  }
}
