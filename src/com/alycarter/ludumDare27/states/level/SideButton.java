package com.alycarter.ludumDare27.states.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alycarter.ludumDare27.Controls;
import com.alycarter.ludumDare27.graphics.TileSheet;
import com.alycarter.ludumDare27.resourseLoading.FileLoader;
import com.alycarter.ludumDare27.states.menu.Button;

public class SideButton extends Button {

	public Level level;
	public int command;
	public int side;
	
	public static int sideLeft =0;
	public static int sideRight =1;
	
	private BufferedImage image;
	private BufferedImage selectionImage;
	
	
	
	public SideButton(Controls controls, int command, int side,Level level,int x, int y) {
		super(controls, x, y, (int)level.unitResolution, (int)level.unitResolution);
		TileSheet sheet = new TileSheet(FileLoader.loadImage("/buttons.png"), 16, 8);
		image =sheet.getTile(command);
		selectionImage=sheet.getTile(4);
		this.level=level;
		this.side=side;
		this.command=command;
	}

	@Override
	public void onClick() {
		level.selectedButton=this;
		level.buttonClicked=true;
	}
	
	@Override
	public void onRender(Graphics g) {
		if(level.selectedButton==this){
			g.drawImage(selectionImage, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
		g.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		if(level.activeLeftButton==this){
			g.setColor(Color.YELLOW);
			g.fillRect(rectangle.x+rectangle.width, rectangle.y, (int)level.unitResolution/2, rectangle.height);
		}
		if(level.activeRightButton==this){
			g.setColor(Color.YELLOW);
			g.fillRect(rectangle.x-(int)(level.unitResolution/2), rectangle.y, (int)level.unitResolution/2, rectangle.height);
		}
	}

}
