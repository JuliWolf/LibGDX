package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Physics {

  public static final float PPM = 100;
  private final World world;
  private final Box2DDebugRenderer debugRenderer;
  public ContListener contactListener;

  public Physics() {
    world = new World(new Vector2(0, -9.81f), true);
    debugRenderer = new Box2DDebugRenderer();
    contactListener = new ContListener();

    world.setContactListener(contactListener);
  }

  public void setGravity (Vector2 gravity) {
    world.setGravity(gravity);
  }

  public void step () {
    world.step(1/60f, 3, 3);
  }

  public void debugDraw (OrthographicCamera camera) {
    debugRenderer.render(world, camera.combined);
  }

  public Body addObject (RectangleMapObject mapObject) {
    Rectangle rectangle = mapObject.getRectangle();
    String name = mapObject.getName();
    String bodyType = (String) mapObject.getProperties().get("BodyType");
    float gravityScale = (float) mapObject.getProperties().get("gravityScale");
    float restitution = (float) mapObject.getProperties().get("restitution");
    BodyDef def = new BodyDef();
    FixtureDef fDef = new FixtureDef();
    PolygonShape polygonShape = new PolygonShape();

    def.type = getBodyType(bodyType);
    def.position.set((rectangle.x + rectangle.width/2) / PPM, (rectangle.y + rectangle.height/2) / PPM);
    def.gravityScale = gravityScale;

    polygonShape.setAsBox(rectangle.width/2 / PPM, rectangle.height/2 / PPM);

    fDef.shape = polygonShape;
    fDef.friction = 1f;
    fDef.density = 1;
    fDef.restitution = restitution;

    Body body = world.createBody(def);
    body.createFixture(fDef).setUserData(name);

    if (name != null && name.equals("hero")) {
      polygonShape.setAsBox(rectangle.width /3 / PPM, rectangle.height / 12 / PPM, new Vector2(0, -rectangle.width/2 / PPM), 0);
      body.createFixture(fDef).setUserData("legs");
      body.setFixedRotation(true);
    }

    polygonShape.dispose();

    return body;
  }

  private BodyDef.BodyType getBodyType (String type) {
    if (type == null) {
      return BodyDef.BodyType.StaticBody;
    }

    switch (type) {
      case "DynamicBody": return BodyDef.BodyType.DynamicBody;
      case "KinematicBody": return BodyDef.BodyType.KinematicBody;
      default: return BodyDef.BodyType.StaticBody;
    }
  }

  public void destroyBody (Body body) {
    world.destroyBody(body);
  }

  public void dispose () {
    world.dispose();
    debugRenderer.dispose();
  }
}
