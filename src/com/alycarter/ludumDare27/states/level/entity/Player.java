package com.alycarter.ludumDare27.states.level.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.Animation;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.states.level.Level;

public class Player extends Entity {
	
	private int command1 =0;
	private int command2 =0;
	
	private boolean weaponDrawn = false;
	
	private Double shootDirection = new Double(0, 0);
	
	public static final int command_walk = 0;
	public static final int command_shoot =1;
	public static final int command_draw =2;
	public static final int command_dash =3;
	public Player(Game game, Level level, Double location) {
		super(game, level, Entity.player, location, true, 0.5, 1, new Double(1,1), 0);
		TileSheet sheet = new TileSheet(FileLoader.loadImage("/player.png"), 16, 4);
		animations.addAnimation(new Animation(game, "holser", sheet, 1, 0));
		animations.addAnimation(new Animation(game, "drawn", sheet, 1, 1));
		animations.setCurrentAnimation("holser");
	}

	@Override
	public void onUpdate() {
		if(!hasCommand && !dead){
			if(!level.buttonClicked){
				Double d;
				switch (level.selectedButton.command){
				case command_walk:
					if(game.controls.isMouseClicked()){
						d = level.getMouseLocationInGame();
						d.x-=location.x;
						d.y-=location.y;
						setDirection(d);
						command1=command_walk;
						level.setSelectedButtonAsActive();
					}
					break;
				case command_shoot:
					if(game.controls.isMouseClicked()){
						if(weaponDrawn){
							d = level.getMouseLocationInGame();
							d.x-=location.x;
							d.y-=location.y;
							shootDirection=d;
							command2=command_shoot;
							level.setSelectedButtonAsActive();
						}
					}
					break;
				case command_draw:
					if(!weaponDrawn){
						command2=command_draw;
						level.setSelectedButtonAsActive();
					}
					break;
				case command_dash:
					if(command1 ==command_walk){
						command2=command_dash;
						level.setSelectedButtonAsActive();
					}
					break;
				}
			}
			if(game.controls.isKeyReleased(KeyEvent.VK_SPACE)){
				hasCommand=true;
			}
		}
	}

	@Override
	public void OnEndRound() {
		hasCommand=false;
		command1=-1;
		command2=-1;
		velocity=0;
	}

	@Override
	public void OnStartRound() {
		switch (command1){
		case command_walk:
			velocity=1;
			if(command2!=command_shoot){
				lookDirection=getDirection();
			}
			if(command2==command_dash){
				velocity+=1;
			}
			break;
		default: 
			break;
		}
		switch (command2){
		case command_shoot:
			lookDirection=shootDirection;
			level.entities.add(new Bullet(game, level, this, location, shootDirection, 5));
			break;
		case command_draw:
			weaponDrawn=true;
			animations.setCurrentAnimation("drawn");
		default: 
			break;
		}
	}
	
	@Override
	public void onRender(Graphics g) {
		g.drawLine(getLocationOnScreen().x, getLocationOnScreen().y, game.controls.mouseLocation.x, game.controls.mouseLocation.y);
	}

}
