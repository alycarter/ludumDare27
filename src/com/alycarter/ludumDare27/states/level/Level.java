package com.alycarter.ludumDare27.states.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.stateMachine.State;
import com.alycarter.ludumDare27.states.level.entity.Camera;
import com.alycarter.ludumDare27.states.level.entity.Enemy;
import com.alycarter.ludumDare27.states.level.entity.Entity;
import com.alycarter.ludumDare27.states.level.entity.Player;
import com.alycarter.ludumDare27.states.menu.Menu;

public class Level extends Menu implements State{
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Game game;
	
	public double unitResolution= 64;
	public double roundTime=0;
	public int round=0;
	public boolean roundEnd =false;
	
	public Point levelSize =new Point(10,10);
	
	public Camera camera;
	public Player player;
	
	public Level(Game game) {
		this.game=game;
		camera = new Camera(game, this, new Double(1,1), null);
		player = new Player(game, this, new Double(1,1));
		entities.add(player);
		entities.add(new Enemy(game, this, new Double(5, 5), new Double(0, 0)));
	}
	
	public double getLevelDeltaTime(){
		return game.deltaTime/3;
	}

	@Override
	public void update() {
		super.update();
		roundTime-=getLevelDeltaTime();
		camera.update();
		for(int i=0;i<entities.size();i++){
			entities.get(i).update();
		}
		if(roundTime<=0 &&!roundEnd){
			roundEnd=true;
			for(int i=0;i<entities.size();i++){
				entities.get(i).OnEndRound();
			}
		}
		boolean ready =true;
		if(roundEnd){
			for(int i=0;i<entities.size();i++){
				if(!entities.get(i).hasCommand){
					ready = false;
				}
			}
			if(ready){
				roundTime=1;
				round++;
				roundEnd=false;
				for(int i=0;i<entities.size();i++){
					entities.get(i).OnStartRound();
				}
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		double x=0-camera.location.x;
		double y=0-camera.location.y;
		x*=unitResolution;
		y*=unitResolution;
		x+=game.getWidth()/2;
		y+=game.getHeight()/2;
		g.drawRect((int)x, (int)y, (int)(levelSize.x * unitResolution), (int)(levelSize.y * unitResolution));
		for(int i=0;i<entities.size();i++){
			entities.get(i).render(g);
		}
		g.drawString(String.valueOf(round), game.getWidth()/2, 25);
		super.render(g);
	}
	
	public Double getMouseLocationInGame(){
		Double location  = new Double();
		location.setLocation(game.controls.mouseLocation.x, game.controls.mouseLocation.y);
		location.x-=game.getWidth()/2;
		location.y-=game.getHeight()/2;
		location.x/=unitResolution;
		location.y/=unitResolution;
		location.x+=camera.location.x;
		location.y+=camera.location.y;
		return location;
	}

}
