package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.gcstudios.world.World;

public class Player extends Entity{
	
	public boolean right,left;
	private int gravity = 1;
	private int dir;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;
		
		if(World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
		}
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
		} else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
		}
		
	}
	
	public void render(Graphics g) {
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT;
		} else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT;
		}
		super.render(g);
	}
}
