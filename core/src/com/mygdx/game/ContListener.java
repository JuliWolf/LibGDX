package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class ContListener implements ContactListener {

  private int counter;

  @Override
  public void beginContact(Contact contact) {
    if (!hasBodyName(contact)) return;

    Fixture fixtureA = contact.getFixtureA();
    Fixture fixtureB = contact.getFixtureB();

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

    if (
        nameA.equals("legs") && nameB.equals("Floor") ||
        nameB.equals("legs") && nameA.equals("Floor") ||
        nameA.equals("legs") && nameB.equals("Box") ||
        nameB.equals("legs") && nameA.equals("Box")
    ) {
      counter++;
    }
  }

  @Override
  public void endContact(Contact contact) {
    if (!hasBodyName(contact)) return;

    Fixture fixtureA = contact.getFixtureA();
    Fixture fixtureB = contact.getFixtureB();

    String nameA = (String) fixtureA.getUserData();
    String nameB = (String) fixtureB.getUserData();

    if (
        nameA.equals("legs") && nameB.equals("Floor") ||
        nameB.equals("legs") && nameA.equals("Floor") ||
        nameA.equals("legs") && nameB.equals("Box") ||
        nameB.equals("legs") && nameA.equals("Box")
    ) {
      counter--;
    }
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }

  private boolean hasBodyName (Contact contact) {
    Fixture fixtureA = contact.getFixtureA();
    Fixture fixtureB = contact.getFixtureB();

    if (
        fixtureA.getUserData() == null ||
        fixtureB.getUserData() == null
    ) return false;

    return true;
  }

  public boolean isOnGround () {
    return counter > 0;
  }
}
