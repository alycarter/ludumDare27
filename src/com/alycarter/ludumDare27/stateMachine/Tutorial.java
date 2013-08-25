package com.alycarter.ludumDare27.stateMachine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;

public class Tutorial implements State {
	private int tutNum=0;
	private Game game;
	private boolean draw=true;
	private double flash=1;
	public Tutorial(Game game) {
		this.game=game;
	}

	@Override
	public void update() {
		if (game.controls.isKeyReleased(KeyEvent.VK_ENTER)) {
			tutNum++;
		}
		if(tutNum>2){
			game.stateMachine.pop();
		}
		flash-=game.deltaTime;
		if(flash<0){
			flash=1;
			draw=!draw;
		}
	}

	@Override
	public void render(Graphics g) {
		BufferedImage img = FileLoader.loadImage("/tut"+tutNum+".png");
		g.drawImage(img, 0, 0, null);
		if(draw){
			g.setColor(Color.WHITE);
			g.fillRect(10, 10, 150, 32);
			g.setColor(Color.BLACK);
			g.drawString("press enter to continue", 15, 30);
		}
	}

}
