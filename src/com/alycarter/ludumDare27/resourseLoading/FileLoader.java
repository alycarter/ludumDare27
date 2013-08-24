package com.alycarter.ludumDare27.resourseLoading;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class FileLoader {

	public static InputStream loadFile(String fileName){
		try{
			return FileLoader.class.getResourceAsStream(fileName);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage loadImage(String fileName){
		try {
			InputStream file = loadFile(fileName);
			BufferedImage image =  ImageIO.read(file);
			return image;
		} catch (IOException e) {
//			e.printStackTrace();
			return null;
		}
	}

}
