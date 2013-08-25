package com.alycarter.ludumDare27.stateMachine;

import java.awt.Color;
import java.awt.Graphics;

import com.alycarter.ludumDare27.Game;

public class Victory implements State {
	private Game game;
	public Victory(Game game) {
		this.game= game;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, game.getWidth(), game.getHeight());
		g.setColor(Color.BLACK);
		g.drawString("thank you for playing my game", 10, 10);
		g.drawString("this game was made for the Ludum Dare 48 hour comp number 27", 10, 40);
		g.drawString("i hope you enjoyed it and that it didnt crash or bug out too bad", 10, 70);
		g.drawString("made by Alex Carter", 10, 100);
	}

}
