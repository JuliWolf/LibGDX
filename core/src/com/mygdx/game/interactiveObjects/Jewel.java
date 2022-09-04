package com.mygdx.game.interactiveObjects;

import com.badlogic.gdx.physics.box2d.Body;

public class Jewel {

  private final String type;

  private final Body body;

  public Jewel(String type, Body body) {
    this.type = type;
    this.body = body;
  }
}
