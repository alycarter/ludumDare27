package com.alycarter.ludumDare27.states.level.entity;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.states.level.Level;

public class Camera extends Entity {

	public Entity follow;
	
	public Camera(Game game, Level level, Double location, Entity follow) {
		super(game, level, Entity.camera, location, false,0, 0, new Double(0, 0), 0);
		this.follow=follow;
	}

	@Override
	public void onUpdate() {
		if(follow != null){
			location.setLocation(follow.location.x,follow.location.y);
		}
		int x=0;
		int y=0;
		if(game.controls.isKeyPressed(KeyEvent.VK_UP)){
			y-=1;
		}
		if(game.controls.isKeyPressed(KeyEvent.VK_DOWN)){
			y+=1;
		}
		if(game.controls.isKeyPressed(KeyEvent.VK_LEFT)){
			x-=1;
		}
		if(game.controls.isKeyPressed(KeyEvent.VK_RIGHT)){
			x+=1;
		}
		setDirection(new Double(x, y));
		if(x!=0 || y!= 0){
			velocity =3*(game.deltaTime/level.getLevelDeltaTime());
		}else{
			velocity = 0;
		}
	}

	@Override
	public void OnEndRound() {
		
	}

	@Override
	public void OnStartRound() {
			
	}

}
