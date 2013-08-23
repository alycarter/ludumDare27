package com.alycarter.ludumDare27.states.level.entity;

import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.states.level.Level;

public class Camera extends Entity {

	public Entity follow;
	
	public Camera(Game game, Level level, Double location, Entity follow) {
		super(game, level, location,new Double(0, 0), new Double(0, 0), new Double(0, 0), 0);
		this.follow=follow;
	}

	@Override
	public void onUpdate() {
		if(follow != null){
			location.setLocation(follow.location.x,follow.location.y);
		}
	}

}
