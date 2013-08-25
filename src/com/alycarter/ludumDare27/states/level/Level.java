package com.alycarter.ludumDare27.states.level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.alycarter.ludumDare27.Game;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.stateMachine.LooseScreen;
import com.alycarter.ludumDare27.stateMachine.State;
import com.alycarter.ludumDare27.stateMachine.Victory;
import com.alycarter.ludumDare27.stateMachine.WinScreen;
import com.alycarter.ludumDare27.states.level.entity.BarTender;
import com.alycarter.ludumDare27.states.level.entity.Camera;
import com.alycarter.ludumDare27.states.level.entity.Enemy;
import com.alycarter.ludumDare27.states.level.entity.Entity;
import com.alycarter.ludumDare27.states.level.entity.Player;
import com.alycarter.ludumDare27.states.menu.Menu;

public class Level extends Menu implements State{
	
	public ArrayList<Entity> entities;
	public Game game;
	
	public double unitResolution= 96;
	public double roundTime=0;
	public int round=0;
	public boolean roundEnd =false;
	
	public int levelNumber =0;
	
	private BufferedImage levelTexture;
	private BufferedImage groundTexture;
	
	private TileSheet chamber;
	private TileSheet holster;
	private BufferedImage secondsRemaining;
	private TileSheet numbers;
	
	public SideButton selectedButton;
	public SideButton activeLeftButton;
	public SideButton activeRightButton;
	public boolean buttonClicked  =false;
	
	public Point levelSize =new Point(5,5);
	public ArrayList<ArrayList<Boolean>> tiles;
	public static final boolean obstructed=true;
	public static final boolean clear=false;
	
	public Camera camera;
	public Player player;
	
	public Level(Game game) {
		this.game=game;
		SideButton walk =new SideButton(game.controls, Player.command_walk, SideButton.sideLeft, this, 0, game.getHeight()-(int)unitResolution-25);
		buttons.add(new SideButton(game.controls, Player.command_reload, SideButton.sideLeft, this, 0, game.getHeight()-(int)(2*unitResolution)-25));
		buttons.add(walk);
		buttons.add(new SideButton(game.controls, Player.command_shoot, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)unitResolution-25));
		buttons.add(new SideButton(game.controls, Player.command_draw, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)(2*unitResolution)-25));
		buttons.add(new SideButton(game.controls, Player.command_dash, SideButton.sideRight, this, game.getWidth()-(int)unitResolution-3, game.getHeight()-(int)(3*unitResolution)-25));
		selectedButton=walk;
		chamber=new TileSheet(FileLoader.loadImage("/chamber.png"), 16, 4);
		holster=new TileSheet(FileLoader.loadImage("/holster.png"), 16, 2);
		numbers = new TileSheet(FileLoader.loadImage("/numbers.png"), 5, 10);
		secondsRemaining= FileLoader.loadImage("/seconds left.png");
		loadLevel();
	}
	
	public void loadLevel(){
		if(levelNumber>=5){
			levelNumber=4;
			game.stateMachine.push(new Victory(game));
		}
		entities = new ArrayList<Entity>();
		round=0;
		roundTime=0;
		roundEnd=false;
		TileSheet tiles = new TileSheet(FileLoader.loadImage("/level.png"), 16, 7);
		BufferedReader mapFile = new BufferedReader(new InputStreamReader(FileLoader.loadFile("/"+levelNumber+".map")));
		loadHitmap(mapFile);
		buildImageTexture(mapFile, tiles);
		try{
			while(!mapFile.readLine().equals("eof")){
				switch(Integer.valueOf(mapFile.readLine())){
				case 0:
					player = new Player(game, this, new Double(Integer.valueOf(mapFile.readLine())+0.5,Integer.valueOf(mapFile.readLine())+0.5));
					entities.add(player);
					break;
				case 1:
					entities.add(new Enemy(game, this, new Double(Integer.valueOf(mapFile.readLine())+0.5, Integer.valueOf(mapFile.readLine())+0.5), new Double(0, 0)));
					break;
				case 2:
					entities.add(new BarTender(game, this, new Double(Integer.valueOf(mapFile.readLine())+0.5, Integer.valueOf(mapFile.readLine())+0.5), new Double(0, 0)));
					break;
				}
			}
			mapFile.close();
		}catch(IOException e){}
		camera = new Camera(game, this, new Double(player.location.x,player.location.y), null);
	}
	
	private void loadHitmap(BufferedReader mapFile){
		tiles = new ArrayList<ArrayList<Boolean>>();
		try {
			String input =mapFile.readLine();
			int y=0;
			while(!input.equals("")){
				int x=0;
				int s=0;
				while(s<input.length()){
					String value="";
					while(input.charAt(s)!=','){
						value=value+input.charAt(s);
						s++;
					}
					if(x>=tiles.size()){
						tiles.add(x, new ArrayList<Boolean>());
					}
					if(Integer.valueOf(value).intValue()==0){
						tiles.get(x).add(y,obstructed);
					}else{
						tiles.get(x).add(y,clear);
					}
					s++;
					x++;
				}
				input = mapFile.readLine();
				y++;
			}
			levelSize=new Point(tiles.size(), tiles.get(0).size());
		} catch (IOException e) {e.printStackTrace();} 
	}
	
	private void buildImageTexture(BufferedReader mapFile, TileSheet tiles){
		try {
			levelTexture= new BufferedImage((int)(levelSize.x*unitResolution), (int)(levelSize.y*unitResolution), BufferedImage.TYPE_INT_ARGB);
			String input =mapFile.readLine();
			int y=0;
			while(!input.equals("")){
				int x=0;
				int s=0;
				while(s<input.length()){
					String value="";
					while(input.charAt(s)!=','){
						value=value+input.charAt(s);
						s++;
					}
					levelTexture.getGraphics().drawImage(tiles.getTile(0), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);				
					levelTexture.getGraphics().drawImage(tiles.getTile(Integer.valueOf(value)), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);				
					s++;
					x++;
				}
				input = mapFile.readLine();
				y++;
			}
			int x=0;
			groundTexture=new BufferedImage(levelTexture.getWidth()+game.getWidth(), levelTexture.getHeight()+game.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for(x=0;x<groundTexture.getWidth()/unitResolution;x++){
				for(y=0;y<groundTexture.getHeight()/unitResolution;y++){
					groundTexture.getGraphics().drawImage(tiles.getTile(1), (int)(x*unitResolution), (int)(y*unitResolution), (int)unitResolution, (int)unitResolution, null);
				}	
			}
			levelTexture.getGraphics().drawImage(tiles.getTile(6), (int)((levelSize.getX()-1)/2*unitResolution), (int)((levelSize.getY()-1)*unitResolution), (int)unitResolution, (int)unitResolution, null);
			for(x=0;x<levelSize.x;x++){
				for(y=0;y<levelSize.y;y++){
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
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public double getLevelDeltaTime(){
		return game.deltaTime/1;
	}
	
	public void setSelectedButtonAsActive(){
		if(selectedButton.side==SideButton.sideLeft){
			activeLeftButton=selectedButton;
		}
		if(selectedButton.side==SideButton.sideRight){
			activeRightButton=selectedButton;
		}
	}
	
	public boolean getTileMoveable(int x, int y){
		if(x>=0 && x<levelSize.x &&y>=0 && y<levelSize.y ){
			return tiles.get(x).get(y);
		}else{
			return obstructed;
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
			selectedButton=null;
			boolean enemiesRemaining = false;
			for(int i=0;i<entities.size();i++){
				entities.get(i).OnEndRound();
				if(!entities.get(i).dead && entities.get(i).entityType==Entity.enemy){
					enemiesRemaining=true;
				}
			}
			if(!enemiesRemaining){
				game.stateMachine.push(new WinScreen(game));
			}else{
				if (round==10){
					game.stateMachine.push(new LooseScreen(game));
				}
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
		g.drawImage(chamber.getTile(player.bullets), game.getWidth()/2-(int)unitResolution, game.getHeight()-(int)unitResolution-25, (int)unitResolution, (int)unitResolution, null);
		if(player.weaponDrawn){
			g.drawImage(holster.getTile(1), game.getWidth()/2, game.getHeight()-(int)unitResolution-25, (int)unitResolution, (int)unitResolution, null);
		}else{
			g.drawImage(holster.getTile(0), game.getWidth()/2, game.getHeight()-(int)unitResolution-25, (int)unitResolution, (int)unitResolution, null);
		}
		g.drawImage(secondsRemaining, 10, 10, secondsRemaining.getWidth()*4, secondsRemaining.getHeight()*4, null);
		int secondsLeft =10-round;
		if(secondsLeft==10){
			g.drawImage(numbers.getTile(1), 10+(secondsRemaining.getWidth()*4), 10, 20, 20, null);
			g.drawImage(numbers.getTile(0), 10+(secondsRemaining.getWidth()*4)+20, 10, 20, 20, null);
		}else{
			g.drawImage(numbers.getTile(secondsLeft), 10+(secondsRemaining.getWidth()*4), 10, 20, 20, null);
		}
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
