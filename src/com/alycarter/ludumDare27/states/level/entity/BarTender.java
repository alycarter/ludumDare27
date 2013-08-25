package com.alycarter.ludumDare27.states.level.entity;

import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.Animation;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.states.level.Level;

public class BarTender extends Entity {
	private boolean weaponDrawn = false;
	
	public BarTender(Game game, Level level, Double location, Double direction) {
		super(game, level, Entity.enemy, location, true, 0.5, 1, direction, 0);
		TileSheet sheet = new TileSheet(FileLoader.loadImage("/player.png"), 16, 4);
		animations.addAnimation(new Animation(game, "holser", sheet, 1, 8));
		animations.addAnimation(new Animation(game, "drawn", sheet, 1, 9));
		animations.addAnimation(new Animation(game, "dead", sheet, 1, 10));
		animations.setCurrentAnimation("holser");
	}

	@Override
	public void OnEndRound() {
		hasCommand=true;
	}

	@Override
	public void OnStartRound() {
		if(!dead){
			Double d = new Double();
			d.setLocation(level.player.location);
			d.x-=location.x;
			d.y-=location.y;
			setDirection(d);
			lookDirection=getDirection();
			if(!weaponDrawn){
				weaponDrawn=true;
				animations.setCurrentAnimation("drawn");
			}else{
				level.entities.add(new Bullet(game, level, this, location, getDirection(), 5));
				level.entities.add(new Bullet(game, level, this, location, angleAsVector(VectorAsAngle(getDirection())-15), 5));
				level.entities.add(new Bullet(game, level, this, location, angleAsVector(VectorAsAngle(getDirection())+15), 5));
			}
		}
	}
	
	@Override
	public void onDie() {
		animations.setCurrentAnimation("dead");
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

}
