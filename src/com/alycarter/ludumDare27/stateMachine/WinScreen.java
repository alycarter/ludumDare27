package com.alycarter.ludumDare27.stateMachine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;

public class WinScreen implements State {
	private Game game;
	public WinScreen(Game game) {
		this.game=game;
	}

	@Override
	public void update() {
		if(game.controls.isKeyPressed(KeyEvent.VK_R)){
			game.stateMachine.pop();
			game.level.levelNumber++;
			game.level.loadLevel();
		}
	}

	@Override
	public void render(Graphics g) {
		game.level.render(g);
		BufferedImage img = FileLoader.loadImage("/win.png");
		g.drawImage(img, (game.getWidth()/2)-(4*img.getWidth()/2), (game.getHeight()/2)-(4*img.getHeight()/2), 4*img.getWidth(), 4*img.getHeight(), null);
	}
}
