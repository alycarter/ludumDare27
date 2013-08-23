package com.alycarter.ludumDare27.states.level.entity;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.AnimationSet;
import com.alycarter.ludumDare27.states.level.Level;

public abstract class Entity {
	
	public Game game;
	public Level level;
	public Double location;
	public Double hitBoxSize;
	public Double imageSize;
	private Double direction;
	public double velocity;
	
	public AnimationSet animations =new AnimationSet();
	
	
	public Entity(Game game, Level level,Double location, Double hitBoxSize, Double imageSize, Double direction, double velocity) {
		this.game=game;
		this.level=level;
		this.location= new Double(location.x, location.y);
		this.hitBoxSize= new Double(hitBoxSize.x, hitBoxSize.y);
		this.imageSize = imageSize;
		this.direction= angleAsVector(VectorAsAngle(new Double(direction.x, direction.y)));
		this.velocity=velocity;
		
	}
	
	public void update(){
		onUpdate();
		location.x+=velocity*direction.x*game.deltaTime;
		location.y+=velocity*direction.y*game.deltaTime;
		animations.update();
	}
	
	public abstract void onUpdate();

	public void render(Graphics g){
		double x= location.x -level.camera.location.x;
		double y= location.y-level.camera.location.y;
		x-=(imageSize.x/2);
		y-=(imageSize.y/2);
		x*=level.unitResolution;
		y*=level.unitResolution;
		x+=game.getWidth()/2;
		y+=game.getHeight()/2;
		g.drawImage(animations.getCurrentFrame(),(int)x, (int)y,(int)(imageSize.x*level.unitResolution), (int)(imageSize.y*level.unitResolution),null);
	}
	
	public void setDirection(Double direction){
		this.direction= angleAsVector(VectorAsAngle(new Double(direction.x, direction.y)));
	}
	
	public Double getDirection(){
		return direction;
	}
	
	public static Double angleAsVector(double angle){ 
		Double vector = new Double();
		vector.x=Math.sin(Math.toRadians(angle));
		vector.y=Math.cos(Math.toRadians(angle));
		return vector;
	}
	
	public static double VectorAsAngle(Double vector){
		return Math.toDegrees(Math.atan2(vector.x, vector.y));
	}

}
