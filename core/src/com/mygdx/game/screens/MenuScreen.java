package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class MenuScreen implements Screen {
  private Main game;
  private Texture img;

  private Rectangle startRect;
  private ShapeRenderer shapeRenderer;

  public MenuScreen(Main game) {
    this.game = game;

    this.img = new Texture("start.png");
    this.startRect = new Rectangle(0, 0, img.getWidth(), img.getHeight());
    this.shapeRenderer = new ShapeRenderer();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BROWN);

    game.batch.begin();
    game.batch.draw(img, 0, 0);
    game.batch.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.ROYAL);
    shapeRenderer.rect(startRect.x, startRect.y, startRect.width, startRect.height);
    shapeRenderer.end();

    if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) return;

    int x = Gdx.input.getX();
    int y = Gdx.graphics.getHeight() - Gdx.input.getY();
    Vector2 vect = new Vector2(x, y);

    if (!startRect.contains(vect)) return;

    dispose();
    game.setScreen(new GameScreen(game));
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
    this.img.dispose();

    this.shapeRenderer.dispose();
  }
}
