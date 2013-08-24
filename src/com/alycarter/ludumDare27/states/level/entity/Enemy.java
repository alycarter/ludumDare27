package com.alycarter.ludumDare27.states.level.entity;

import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.states.level.Level;

public class Enemy extends Entity {

	public Enemy(Game game, Level level, Double location, Double direction) {
		super(game, level, Entity.enemy, location, true, 1, 1, direction, 0);
		// TODO Auto-generated constructor stub
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
			level.entities.add(new Bullet(game, level, this, location, getDirection(), 5));
		}
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

}
