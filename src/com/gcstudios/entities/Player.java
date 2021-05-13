package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity {

	public boolean right, left;
	private boolean moved = false;
	private int gravity = 2;
	private int dir;
	
	public int life = 3;

	public boolean jump = false, isJumping = false;
	public int jumpHeight = 40;
	public int jumpFrames = 0;

	private int framesWalk = 0, maxFramesWalk = 15, maxSprite = 2, curSprite = 0;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		depth = 2;

		moved = false;

		if (World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;

			for (int i = 0; i < Game.entities.size(); i++) {

				Entity e = Game.entities.get(i);

				if (e instanceof Enemy) {
					if (Entity.isColidding(this, e)) {

						isJumping = true;
						jumpHeight = 32;
						((Enemy) e).death = true;
						if (((Enemy) e).death) {
							Game.entities.remove(i);
							break;
						}
					}
				}
			}
		}

		if (right && World.isFree((int) (x + speed), (int) y)) {
			x += speed;
			dir = 1;
			moved = true;
		} else if (left && World.isFree((int) (x - speed), (int) y)) {
			x -= speed;
			dir = -1;
			moved = true;
		}

		jump();

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

	}

	public void render(Graphics g) {

		if (moved) {
			framesWalk++;

			if (framesWalk == maxFramesWalk) {
				curSprite++;
				framesWalk = 0;

				if (curSprite == maxSprite) {
					curSprite = 0;
				}
			}
		}

		if (dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		} else if (dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);
	}

	private void jump() {

		if (jump) {

			if (!World.isFree(this.getX(), this.getY() + 1)) {
				isJumping = true;
			} else {
				jump = false;
			}
		}

		if (isJumping) {
			if (World.isFree(this.getX(), this.getY() - 2)) {
				y -= 2;
				jumpFrames += 2;

				if (jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			} else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
	}
}
