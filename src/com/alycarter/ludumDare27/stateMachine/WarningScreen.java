package com.alycarter.ludumDare27.stateMachine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;

public class WarningScreen implements State {
	private Game game;
	private double delay = 2;
	private String type;
	
	public static final String run = "run";
	public static final String shoot = "shoot";
	public static final String reload = "reload";
	
	public WarningScreen(Game game, String type) {
		this.game=game;
		this.type=type;
	}

	@Override
	public void update() {
		delay-=game.deltaTime;
		if(delay<0){
			game.stateMachine.pop();
		}
	}

	@Override
	public void render(Graphics g) {
		game.level.render(g);
		BufferedImage img = FileLoader.loadImage("/"+type+".png");
		g.drawImage(img, (game.getWidth()/2)-(4*img.getWidth()/2), (game.getHeight()/2)-(4*img.getHeight()/2), 4*img.getWidth(), 4*img.getHeight(), null);
	}

}
