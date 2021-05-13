package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Coin extends Entity {

	private int animationFrames = 0, maxAnimationFrames = 9, spriteIndex = 0, maxSpriteIndex = 1;

	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void render(Graphics g) {

		animationFrames++;

		if (animationFrames == maxAnimationFrames) {
			animationFrames = 0;
			spriteIndex++;

			if (spriteIndex > maxSpriteIndex) {
				spriteIndex = 0;
			}
		}

		sprite = Entity.COIN_SPRITE[spriteIndex];
		super.render(g);
	}
}
