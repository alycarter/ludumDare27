package com.alycarter.ludumDare27.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.ludumDare27.Game;

public class Animation {
	
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	private double currentFrame = 0;
	private Game game;
	public String name;
	
	public Animation(Game game, String name, TileSheet sheet, int frames) {
		this.name=name;
		this.game=game;
		for(int i=0;i<frames;i++){
			this.frames.add(sheet.getTile(i));
		}
	}
	
	public void update(){
		currentFrame+=game.deltaTime;
		if(currentFrame>=frames.size()){
			currentFrame-=frames.size();
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return frames.get((int)currentFrame);
	}

}
