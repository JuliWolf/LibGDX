package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class ContListener implements ContactListener {
  @Override
  public void beginContact(Contact contact) {
    Fixture fixtureA = contact.getFixtureA();
    Fixture fixtureB = contact.getFixtureB();

    if (
        fixtureA.getUserData() == null ||
        fixtureB.getUserData() == null
    ) return;

    String nameA = (String) fixtureA.getUserData();
    String nameB = (String) fixtureB.getUserData();

    if (nameA.equals("hero") && nameB.equals("Jewel")) {
      Body bodyB = fixtureB.getBody();
      GameScreen.bodies.add(bodyB);
    }

    if (nameB.equals("hero") && nameA.equals("Jewel")) {
      Body bodyA = fixtureA.getBody();
      GameScreen.bodies.add(bodyA);
    }
  }

  @Override
  public void endContact(Contact contact) {

  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }
}
