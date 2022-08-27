package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Physics {

  private final World world;
  private final Box2DDebugRenderer debugRenderer;

  public Physics() {
    world = new World(new Vector2(0, -9.81f), true);
    debugRenderer = new Box2DDebugRenderer();
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

  public Body addObject (RectangleMapObject mapObject, String name) {
    Rectangle rectangle = mapObject.getRectangle();
    String bodyType = (String) mapObject.getProperties().get("BodyType");
    float gravityScale = (float) mapObject.getProperties().get("gravityScale");
    BodyDef def = new BodyDef();
    FixtureDef fDef = new FixtureDef();
    PolygonShape polygonShape = new PolygonShape();

    def.type = getBodyType(bodyType);
    def.position.set(rectangle.x + rectangle.width/2, rectangle.y + rectangle.height/2);
    def.gravityScale = gravityScale;

    polygonShape.setAsBox(rectangle.width/2, rectangle.height/2);

    fDef.shape = polygonShape;
    fDef.friction = 1;
    fDef.density = 1;
    fDef.restitution = 0;

    Body body = world.createBody(def);
    body.createFixture(fDef).setUserData(name);

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

  public void dispose () {
    world.dispose();
    debugRenderer.dispose();
  }
}
