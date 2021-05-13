package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity {

	public boolean right, left;
	private boolean moved = false;
	private int dir;

	public static int life = 3, score = 0;
	public static boolean dead;

	private double gravity = 0.4;
	private double vspd = 0;
	private boolean grounded;

	public boolean jump = false, isJumping = false;

	private int framesWalk = 0, maxFramesWalk = 15, maxSprite = 2, curSprite = 0;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		depth = 2;
		moved = false;
		System.out.println(grounded);
		
		if (right && World.isFree((int) (x + speed), (int) y)) {
			x += speed;
			dir = 1;
			moved = true;
		} else if (left && World.isFree((int) (x - speed), (int) y)) {
			x -= speed;
			dir = -1;
			moved = true;
		}

		lifeUP();
		isGrounded();
		jump();
		collisionWith();

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

		vspd += gravity;
		if (!World.isFree((int) x, (int) (y + 1)) && jump) {
			vspd = -6;
			jump = false;
			isJumping = true;
		}

		if (!World.isFree((int) x, (int) (y + vspd))) {

			isJumping = true;
			
			int signVsp = 0;
			if (vspd >= 0) {
				signVsp = 1;
			} else {
				signVsp = -1;
			}
			while (World.isFree((int) x, (int) (y + signVsp))) {
				y += signVsp;
			}
			vspd = 0;
			isJumping = false;
		}

		y += vspd;
	}
	
	private void isGrounded() {
		if(!World.isFree((int) x, (int) (y + 1)) && !isJumping) {
			grounded = true;
		} else if(World.isFree((int) x, (int) (y + 1))){
			grounded = false;
		}
	}

	private void collisionWith() {
		for (int i = 0; i < Game.entities.size(); i++) {

			Entity e = Game.entities.get(i);

			if (e instanceof Enemy) {
				if (isColidding(this, e)) {
					if(grounded)
						dead = true;
					if(!grounded) {
						vspd = -5;
						score+=100;
						Game.entities.remove(i);
					}
				}
			}

			if (e instanceof Coin) {
				if (isColidding(this, e)) {
					score += 50;
					Game.entities.remove(i);
				}
			}
		}
	}

	private void lifeUP() {
		if (score >= 1000) {
			life++;
		}
	}
}
