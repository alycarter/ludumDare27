package com.alycarter.ludumDare27.states.level.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.AnimationSet;
import com.alycarter.ludumDare27.states.level.Level;

public abstract class Entity {
	
	public Game game;
	public Level level;
	
	public Double location;
	public double hitBoxSize;
	public double imageSize;
	private Double direction;
	public double velocity;
	
	public boolean dead=false;

	public boolean blockedByEntity;
	public static final boolean no_collision=false;
	public static final boolean yes_collision=true;
	
	public boolean hasCommand= false;

	public int entityType;
	public static final int player = 0;
	public static final int enemy = 1;
	public static final int bullet = 2;
	public static final int camera = -1;
	
	public AnimationSet animations =new AnimationSet();
	
	
	public Entity(Game game, Level level, int entityType,Double location, boolean blockedByEntity,double hitBoxSize, double imageSize, Double direction, double velocity) {
		this.game=game;
		this.blockedByEntity=blockedByEntity;
		this.level=level;
		this.entityType=entityType;
		this.location= new Double(location.x, location.y);
		this.hitBoxSize= hitBoxSize;
		this.imageSize = imageSize;
		this.direction= angleAsVector(VectorAsAngle(new Double(direction.x, direction.y)));
		this.velocity=velocity;
		
	}
	
	public abstract void OnEndRound();
	
	public abstract void OnStartRound();
	
	public void update(){
		onUpdate();
		Double oldLocation = new Double();
		oldLocation.setLocation(location);
		location.x+=velocity*direction.x*level.getLevelDeltaTime();
		location.y+=velocity*direction.y*level.getLevelDeltaTime();
		if(checkEntityCollisions()==yes_collision && blockedByEntity){
			location= oldLocation;
		}
		if(location.x-(hitBoxSize/2)<0 || location.y-(hitBoxSize/2) <0 ||
				location.x+(hitBoxSize/2)>level.levelSize.x || location.y-(hitBoxSize)/2>level.levelSize.y){
			location= oldLocation;
			onEdgeCollide();
		}
		animations.update();
	}
	
	public void onEdgeCollide() {
		// TODO Auto-generated method stub
		
	}

	private boolean checkEntityCollisions(){
		boolean collision = no_collision;
		for(int i = 0; i<level.entities.size();i++){
			Entity e = level.entities.get(i);
			if(e!=this && !e.dead && e.blockedByEntity){
				double distance = Math.sqrt((Math.pow(location.x-e.location.x,2) + Math.pow(location.y-e.location.y,2)));
				distance-=hitBoxSize/2;
				distance -= e.hitBoxSize/2;
				if(distance<0){
					collision=yes_collision;
					onEntityCollide(e);
				}
			}
		}
		return collision;
	}
	
	public void onEntityCollide(Entity e) {
		// TODO Auto-generated method stub
		
	}

	public abstract void onUpdate();

	public void render(Graphics g){
		if(dead){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.BLACK);
		}
		Point loc = getLocationOnScreen();
		loc.x-=(hitBoxSize/2)*level.unitResolution;
		loc.y-=(hitBoxSize/2)*level.unitResolution;
		g.drawImage(animations.getCurrentFrame(),loc.x, loc.y,(int)(imageSize*level.unitResolution), (int)(imageSize*level.unitResolution),null);
		g.drawOval(loc.x, loc.y,(int)(hitBoxSize*level.unitResolution), (int)(hitBoxSize*level.unitResolution));
		onRender(g);
	}
	
	public void onRender(Graphics g) {
		// TODO Auto-generated method stub
		
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
	
	public Point getLocationOnScreen(){
		double x= location.x -level.camera.location.x;
		double y= location.y-level.camera.location.y;
		x*=level.unitResolution;
		y*=level.unitResolution;
		x+=game.getWidth()/2;
		y+=game.getHeight()/2;
		return new Point((int)x, (int)y);
	}
	
	public static double VectorAsAngle(Double vector){
		return Math.toDegrees(Math.atan2(vector.x, vector.y));
	}

}
