package com.alycarter.ludumDare27.states.level.entity;

import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.Animation;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.states.level.Level;

public class Enemy extends Entity {

	private boolean weaponDrawn = false;
	
	public Enemy(Game game, Level level, Double location, Double direction) {
		super(game, level, Entity.enemy, location, true, 0.5, 1, direction, 0);
		TileSheet sheet = new TileSheet(FileLoader.loadImage("/player.png"), 16, 4);
		animations.addAnimation(new Animation(game, "holser", sheet, 1, 2));
		animations.addAnimation(new Animation(game, "drawn", sheet, 1, 3));
		animations.setCurrentAnimation("holser");
	}

	@Override
	public void OnEndRound() {
		hasCommand=true;
	}

	@Override
	public void OnStartRound() {
		if(!dead){
			if(!weaponDrawn){
				weaponDrawn=true;
				animations.setCurrentAnimation("drawn");
			}else{
				Double d = new Double();
				d.setLocation(level.player.location);
				d.x-=location.x;
				d.y-=location.y;
				setDirection(d);
				lookDirection=getDirection();
				level.entities.add(new Bullet(game, level, this, location, getDirection(), 5));
			}
		}
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

}
