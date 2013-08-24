package com.alycarter.ludumDare27.states.level.entity;

import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.states.level.Level;

public class Bullet extends Entity {
	private double speed;
	private Entity source;
	public Bullet(Game game, Level level, Entity source, Double location, Double direction, double velocity) {
		super(game, level, Entity.bullet, location, false, 0.1, 0.1, direction, velocity);
		speed=velocity;
		this.source=source;
	}

	@Override
	public void OnEndRound() {
		velocity=0;
		hasCommand=true;
	}
	
	@Override
	public void onEntityCollide(Entity e) {
		if(e!=source && e.entityType!=Entity.bullet){
			e.dead=true;
			level.entities.remove(this);
		}
	}

	@Override
	public void OnStartRound() {
		velocity = speed;
	}

	@Override
	public void onEdgeCollide() {
		level.entities.remove(this);
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

}
