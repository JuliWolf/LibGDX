package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Character;
import com.mygdx.game.Main;
import com.mygdx.game.Physics;

import java.util.ArrayList;

public class GameScreen implements Screen {

  private Main game;

  private TiledMap map;
  private OrthoCachedTiledMapRenderer mapRenderer;

  private OrthographicCamera camera;

  private Character character;

  private final int[] layer;
  private final int[] objectLayer;
  private final int[] background;

  private Physics physics;
  private Body hero;

  public static ArrayList<Body> bodies;

  private final Rectangle heroRectangle;

  public GameScreen(Main game) {
    this.game = game;

    map = new TmxMapLoader().load("map/map.tmx");
    bodies = new ArrayList<>();
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    physics = new Physics();
    character = new Character();
    mapRenderer = new OrthoCachedTiledMapRenderer(map);

    // Init layers
    layer = new int[2];
    layer[0] = map.getLayers().getIndex("Layer-back");
    layer[1] = map.getLayers().getIndex("Layer-front");
    background = new int[2];
    background[0] = map.getLayers().getIndex("Background");
    background[1] = map.getLayers().getIndex("Sea");
    objectLayer = new int[1];
    objectLayer[0] = map.getLayers().getIndex("Objects");

    MapLayer mapCamera = map.getLayers().get("setup");
    MapLayer mapCollisions = map.getLayers().get("collision");
    MapLayer mapInteractives = map.getLayers().get("Interactive-objects");

    // choose object by name
    RectangleMapObject mapHeroObject = (RectangleMapObject) mapCamera.getObjects().get("hero");
    Array<RectangleMapObject> mapCollisionBoxes = mapCollisions.getObjects().getByType(RectangleMapObject.class);
    Array<RectangleMapObject> mapInteractiveObjects = mapInteractives.getObjects().getByType(RectangleMapObject.class);

    hero = physics.addObject(mapHeroObject);
    heroRectangle = mapHeroObject.getRectangle();

    for (int i = 0; i < mapCollisionBoxes.size; i++) {
      physics.addObject(mapCollisionBoxes.get(i));
    }

    for (int i = 0; i < mapInteractiveObjects.size; i++) {
      physics.addObject(mapInteractiveObjects.get(i));
    }
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    renderCamera();

    ScreenUtils.clear(Color.WHITE);

    renderMap();

    physics.step();
    physics.debugDraw(camera);

    onKeyPressed();

    renderCharacter();

    updateBodies();
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
    this.character.dispose();
  }

  private void onKeyPressed () {
    boolean isKeyPressed = false;

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && physics.contactListener.isOnGround()) {
      character.setDirection("left");
      character.setCurrentAnimation("run");

      hero.applyForceToCenter(new Vector2(-0.3f, 0), true);

      isKeyPressed = true;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && physics.contactListener.isOnGround()) {
      character.setDirection("right");
      character.setCurrentAnimation("run");

      hero.applyForceToCenter(new Vector2(0.3f, 0), true);

      isKeyPressed = true;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.UP) && physics.contactListener.isOnGround()) {
      character.setCurrentAnimation("jump");

      hero.applyForceToCenter(new Vector2(0, 3f), true);

      isKeyPressed = true;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      character.setCurrentAnimation("fall");

      isKeyPressed = true;
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

    if (!isKeyPressed && physics.contactListener.isOnGround()) {
      character.setCurrentAnimation("idle");
    }
  }

  private void renderCharacter () {
    Sprite sprite = character.renderCharacter();

    heroRectangle.x = hero.getPosition().x - heroRectangle.width;
    heroRectangle.y = hero.getPosition().y - heroRectangle.height;

    float x = Gdx.graphics.getWidth() / 2 - heroRectangle.getWidth()/2/camera.zoom;
    float y = Gdx.graphics.getHeight() / 2 - heroRectangle.getHeight()/2/camera.zoom;

    game.batch.begin();
    sprite.setPosition(x, y);
    sprite.draw(game.batch);
    game.batch.end();
  }

  private void renderMap () {
    mapRenderer.setView(camera);
    mapRenderer.render(background);
    mapRenderer.render(objectLayer);
    mapRenderer.render(layer);
  }

  private void renderCamera () {
    camera.update();

    camera.zoom = 0.25f;

    camera.position.x = hero.getPosition().x * Physics.PPM;
    camera.position.y = hero.getPosition().y * Physics.PPM;
  }

  private void updateBodies () {
    for (int i = 0; i < bodies.size(); i++) {
      physics.destroyBody(bodies.get(i));
    }

    bodies.clear();
  }
}
