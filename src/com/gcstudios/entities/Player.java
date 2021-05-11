package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.gcstudios.world.World;

public class Player extends Entity{
	
	public boolean right,left;
	private boolean moved = false;
	private int gravity = 1;
	private int dir;
	
	public boolean jump = false, isJumping = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;

	private int framesWalk = 0, maxFramesWalk = 15, maxSprite = 2, curSprite = 0;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;
		
		moved = false;
		
		if(World.isFree((int)x, (int)(y+gravity)) && isJumping == false) {
			y+=gravity;
		}
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
			moved = true;
		} else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
			moved = true;
		}
		
		jump();
		
	}
	
	public void render(Graphics g) {

		if(moved) {
			framesWalk++;
			
			if(framesWalk == maxFramesWalk) {
				curSprite++;
				framesWalk = 0;
				
				if(curSprite == maxSprite) {
					curSprite = 0;
				}
			}
		}
		
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		} else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);
	}
	
	private void jump() {
		
		if(jump) {
		
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJumping = true;
			} else {
				jump = false;
			}	
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=2;
				jumpFrames+=2;
				
				if(jumpFrames == jumpHeight) {
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
