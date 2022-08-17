package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpritesAnimation {
  private Texture img;

  private Animation<TextureRegion> animation;

  private float time;

  public SpritesAnimation (String image, int column, int row, Animation.PlayMode playMode) {
    img = new Texture(image);

    TextureRegion allRegions = new TextureRegion(img);
    int xCnt = allRegions.getRegionWidth() / column;
    int yCnt = allRegions.getRegionHeight() / row;
    TextureRegion[][] regionMatrix = allRegions.split(xCnt, yCnt);
    TextureRegion[] regions = new TextureRegion[regionMatrix.length * regionMatrix[0].length];

    int count = 0;
    for (int i = 0; i < regionMatrix.length; i++) {
      for (int j = 0; j < regionMatrix[0].length; j++) {
        regions[count++] = regionMatrix[i][j];
      }
    }

    animation = new Animation<>(1 / 20f, regions);
    animation.setPlayMode(playMode);

    time += Gdx.graphics.getDeltaTime();
  }

  public TextureRegion getFrame () {
    return animation.getKeyFrame(time);
  }

  public void setTime (float time) {
    this.time += time;
  }

  public boolean isAnimationOver () {
    return animation.isAnimationFinished(time);
  }

  public void zeroTime () {
    this.time = 0;
  }

  public void setPlayMode (Animation.PlayMode playMode) {
    animation.setPlayMode(playMode);
  }

  public void dispose () {
    img.dispose();
  }

  public void flip (String direction) {
    if (direction == "left" && getFrame().isFlipX()) {
      getFrame().flip(true, false);

      return;
    }

    if (direction == "right" && !getFrame().isFlipX()) {
      getFrame().flip(true, false);
    }
  }
}
