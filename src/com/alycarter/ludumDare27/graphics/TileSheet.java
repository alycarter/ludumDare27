package com.alycarter.ludumDare27.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TileSheet {
	private BufferedImage sheet;
	private int resolution;
	private int tilesWide;
	
	public TileSheet(BufferedImage image, int resolution, int tilesWide) {
		sheet= image;
		this.resolution=resolution;
		this.tilesWide=tilesWide;
	}
	
	public BufferedImage getTile(int tile){
		BufferedImage tileImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tileImage.getGraphics();
		int x = tile % tilesWide;
		int y = tile / tilesWide;
		g.drawImage(sheet,0-x*resolution,0-y*resolution,null);
		return tileImage;
	}

}
