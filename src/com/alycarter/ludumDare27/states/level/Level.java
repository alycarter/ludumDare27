package com.alycarter.ludumDare27.states.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.stateMachine.State;
import com.alycarter.ludumDare27.states.level.entity.Camera;
import com.alycarter.ludumDare27.states.level.entity.Enemy;
import com.alycarter.ludumDare27.states.level.entity.Entity;
import com.alycarter.ludumDare27.states.level.entity.Player;
import com.alycarter.ludumDare27.states.menu.Menu;

public class Level extends Menu implements State{
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Game game;
	
	public double unitResolution= 96;
	public double roundTime=0;
	public int round=0;
	public boolean roundEnd =false;
	
	private BufferedImage levelTexture;
	private BufferedImage groundTexture;
	
	public SideButton selectedButton;
	public SideButton activeLeftButton;
	public SideButton activeRightButton;
	public boolean buttonClicked  =false;
	
	public Point levelSize =new Point(5,5);
	
	public Camera camera;
	public Player player;
	
	public Level(Game game) {
		this.game=game;
		TileSheet tiles = new TileSheet(FileLoader.loadImage("/level.png"), 16, 7);
		levelTexture= new BufferedImage((int)(levelSize.x*unitResolution), (int)(levelSize.y*unitResolution), BufferedImage.TYPE_INT_ARGB);
		int x=0;
		int y=0;
		for(x=0;x<levelSize.x;x++){
			for(y=0;y<levelSize.y;y++){
				levelTexture.getGraphics().drawImage(tiles.getTile(0), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				if(y==0){
					levelTexture.getGraphics().drawImage(tiles.getTile(2), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				}
				if(y==levelSize.y-1){
					levelTexture.getGraphics().drawImage(tiles.getTile(4), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				}
				if(x==0){
					levelTexture.getGraphics().drawImage(tiles.getTile(3), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				}
				if(x==levelSize.x-1){
					levelTexture.getGraphics().drawImage(tiles.getTile(5), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				}
			}	
		}
		levelTexture.getGraphics().drawImage(tiles.getTile(6), (int)(levelSize.getX()/2*unitResolution), (int)((levelSize.getY()-1)*unitResolution), (int)unitResolution, (int)unitResolution, null);
		
		groundTexture=new BufferedImage(levelTexture.getWidth()+game.getWidth(), levelTexture.getHeight()+game.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(x=0;x<groundTexture.getWidth()/unitResolution;x++){
			for(y=0;y<groundTexture.getHeight()/unitResolution;y++){
				groundTexture.getGraphics().drawImage(tiles.getTile(1), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
			}	
		}
		camera = new Camera(game, this, new Double(1,1), null);
		player = new Player(game, this, new Double(1,1));
		entities.add(player);
		entities.add(new Enemy(game, this, new Double(4, 4), new Double(0, 0)));
		SideButton walk =new SideButton(game.controls, Player.command_walk, SideButton.sideLeft, this, 0, game.getHeight()-(int)unitResolution-25);
		buttons.add(walk);
		buttons.add(new SideButton(game.controls, Player.command_shoot, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)unitResolution-25));
		buttons.add(new SideButton(game.controls, Player.command_draw, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)(2*unitResolution)-25));
		buttons.add(new SideButton(game.controls, Player.command_dash, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)(3*unitResolution)-25));
		selectedButton=walk;
	}
	
	public double getLevelDeltaTime(){
		return game.deltaTime/3;
	}
	
	public void setSelectedButtonAsActive(){
		if(selectedButton.side==SideButton.sideLeft){
			activeLeftButton=selectedButton;
		}
		if(selectedButton.side==SideButton.sideRight){
			activeRightButton=selectedButton;
		}
	}

	@Override
	public void update() {
		buttonClicked=false;
		super.update();
		roundTime-=getLevelDeltaTime();
		camera.update();
		for(int i=0;i<entities.size();i++){
			entities.get(i).update();
		}
		if(roundTime<=0 &&!roundEnd){
			roundEnd=true;
			activeLeftButton=null;
			activeRightButton=null;
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
		g.drawImage(groundTexture,(int)x, (int)y, (int)(levelSize.x * unitResolution)+game.getWidth(), (int)(levelSize.y * unitResolution)+game.getHeight(),null);
		x+=game.getWidth()/2;
		y+=game.getHeight()/2;
		g.drawImage(levelTexture,(int)x, (int)y, (int)(levelSize.x * unitResolution), (int)(levelSize.y * unitResolution),null);
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
