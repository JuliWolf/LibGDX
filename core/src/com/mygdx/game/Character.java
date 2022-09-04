package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {
  private SpritesAnimation runAnimation;
  private SpritesAnimation fallAnimation;
  private SpritesAnimation jumpAnimation;
  private SpritesAnimation idleAnimation;
  private SpritesAnimation deadAnimation;
  private SpritesAnimation attackAnimation;

  private SpritesAnimation currentAnimation;

  private String direction;

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public Character() {
    runAnimation = new SpritesAnimation("character/run.png", 8, 1, Animation.PlayMode.LOOP);
    idleAnimation = new SpritesAnimation("character/idle.png", 11, 1, Animation.PlayMode.LOOP);
    fallAnimation = new SpritesAnimation("character/fall.png", 1, 1, Animation.PlayMode.LOOP);
    deadAnimation = new SpritesAnimation("character/dead.png", 4, 1, Animation.PlayMode.LOOP);
    jumpAnimation = new SpritesAnimation("character/jump.png", 1, 1, Animation.PlayMode.LOOP);
    attackAnimation = new SpritesAnimation("character/attack.png", 3, 1, Animation.PlayMode.LOOP);

    currentAnimation = idleAnimation;
  }

  public void setCurrentAnimation (String action) {
    switch (action) {
      case "run": this.currentAnimation = runAnimation; break;
      case "idle": this.currentAnimation = idleAnimation; break;
      case "dead": this.currentAnimation = deadAnimation; break;
      case "jump": this.currentAnimation = jumpAnimation; break;
      case "fall": this.currentAnimation = fallAnimation; break;
      case "attack": this.currentAnimation = attackAnimation; break;
    }
  }

  public void renderCharacter () {
    currentAnimation.setTime(Gdx.graphics.getDeltaTime());

    currentAnimation.flip(direction);
  }

  public TextureRegion getAnimationFrame () {
    return currentAnimation.getFrame();
  }

  public void dispose () {
    this.runAnimation.dispose();
    this.jumpAnimation.dispose();
    this.fallAnimation.dispose();
    this.idleAnimation.dispose();
    this.deadAnimation.dispose();
    this.attackAnimation.dispose();
  }
}
