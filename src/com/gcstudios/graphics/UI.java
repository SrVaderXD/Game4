package com.gcstudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {

	public static BufferedImage LIFE_ICON = Game.spritesheet.getSprite(32, 16, 16, 16);

	public void render(Graphics g) {
		score(g);
		life(g);
	}

	private void score(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString("Score: " + Player.score, 300, 34);
	}

	public void life(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString("x" + Player.life, 650, 34);
		g.drawImage(LIFE_ICON, 600, -10, 64, 64, null);
	}

}
