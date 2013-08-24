package com.alycarter.ludumDare27.states.level.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.states.level.Level;

public class Player extends Entity {
	
	private int command1 =0;
	private int command2 =0;
	
	private Double shootDirection = new Double(0, 0);
	
	private static final int command_walk = 1;
	private static final int command_shoot =2;
	public Player(Game game, Level level, Double location) {
		super(game, level, Entity.player, location, true, 1, 1, new Double(0,-1), 0);
	}

	@Override
	public void onUpdate() {
		if(!hasCommand && !dead){
			if(game.controls.isMouseClicked()){
				Double d = level.getMouseLocationInGame();
				d.x-=location.x;
				d.y-=location.y;
				setDirection(d);
				command1=command_walk;
			}
			if(game.controls.isKeyPressed(KeyEvent.VK_S)){
				Double d = level.getMouseLocationInGame();
				d.x-=location.x;
				d.y-=location.y;
				shootDirection=d;
				command2=command_shoot;
			}
			if(game.controls.isKeyReleased(KeyEvent.VK_SPACE)){
				hasCommand=true;
			}
		}
	}

	@Override
	public void OnEndRound() {
		System.out.println("round end");
		hasCommand=false;
		command1=0;
		command2=0;
		velocity=0;
	}

	@Override
	public void OnStartRound() {
		System.out.println("round start");
		switch (command1){
		case command_walk:
			velocity=1;
			break;
		default: 
			break;
		}
		switch (command2){
		case command_shoot:
			level.entities.add(new Bullet(game, level, this, location, shootDirection, 5));
			break;
		default: 
			break;
		}
	}
	
	@Override
	public void onRender(Graphics g) {
		g.drawLine(getLocationOnScreen().x, getLocationOnScreen().y, game.controls.mouseLocation.x, game.controls.mouseLocation.y);
	}

}
