package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.SpritesAnimation;

import java.util.Objects;

public class GameScreen implements Screen {

  private Main game;

  private TiledMap map;
  private Rectangle mapSize;
  private OrthoCachedTiledMapRenderer mapRenderer;

  private OrthographicCamera camera;

  private SpritesAnimation animation;


  private float STEP = 6f;
  private String direction = "left";
  private float stepLength = 3f;
  private float currentPosition;

  public GameScreen(Main game) {
    this.game = game;

    map = new TmxMapLoader().load("map/map.tmx");
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    animation = new SpritesAnimation("atlas/player.atlas", new String[]{"Jump", "Walk"}, Animation.PlayMode.LOOP);
    mapRenderer = new OrthoCachedTiledMapRenderer(map);

    // choose objects by type
//    map.getLayers().get("camera-objects").getObjects().getByType(RectangleMapObject.class);
    
    MapLayer mapLayer = map.getLayers().get("camera-objects");

    // choose object by name
    RectangleMapObject mapBounds = (RectangleMapObject) mapLayer.getObjects().get("bounds");
    RectangleMapObject mapCameraObject = (RectangleMapObject) mapLayer.getObjects().get("camera");

    camera.position.x = mapCameraObject.getRectangle().x;
    camera.position.y = mapCameraObject.getRectangle().y;

    mapSize = mapBounds.getRectangle();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    renderCamera();

    ScreenUtils.clear(Color.WHITE);

//    renderCharacter();

    renderMap();

    onKeyPressed();
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width;
    camera.viewportHeight = height;

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

    if (Objects.equals(direction, "left") && Gdx.graphics.getWidth() > (currentPosition + frame.getRegionWidth())) {
      currentPosition += stepLength;
    } else if (Objects.equals(direction, "left") && Gdx.graphics.getWidth() <= (currentPosition + frame.getRegionWidth())) {
      direction = "right";
    } else if (Objects.equals(direction, "right") && currentPosition > 0) {
      currentPosition -= stepLength;
    } else if (Objects.equals(direction, "right") && currentPosition <= 0) {
      direction = "left";
    }

    animation.flip(direction);
  }

  private void onKeyPressed () {
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      camera.position.x -= STEP;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      camera.position.x += STEP;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      camera.position.y += STEP;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      camera.position.y -= STEP;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.P)) {
      camera.zoom -= 0.01f;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0) {
      camera.zoom += 0.01f;
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      dispose();
      game.setScreen(new MenuScreen(game));
    }
  }

  private void renderCharacter () {
    TextureRegion frame = animation.getFrame();

    renderFrame(frame);

    game.batch.begin();
    game.batch.draw(frame, currentPosition, 0);
    game.batch.end();
  }

  private void renderMap () {
    mapRenderer.setView(camera);
    mapRenderer.render();
  }

  private void renderCamera () {
    camera.update();

    game.batch.setProjectionMatrix(camera.combined);
  }
}
