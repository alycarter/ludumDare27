package com.alycarter.ludumDare27.states.level;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.stateMachine.State;
import com.alycarter.ludumDare27.states.level.entity.Camera;
import com.alycarter.ludumDare27.states.level.entity.Entity;

public class Level implements State {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Game game;
	
	public double unitResolution= 128;
	public Camera camera;
	
	public Level(Game game) {
		this.game=game;
		camera = new Camera(game, this, new Double(1,1), null);
	}

	@Override
	public void update() {
		for(int i=0;i<entities.size();i++){
			entities.get(i).update();
		}
		camera.update();
	}

	@Override
	public void render(Graphics g) {
		for(int i=0;i<entities.size();i++){
			entities.get(i).render(g);
		}
	}

}
