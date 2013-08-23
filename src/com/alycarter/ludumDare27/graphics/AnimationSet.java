package com.alycarter.ludumDare27.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationSet {
	
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private int currentAnimation=0;
	
	public AnimationSet() {
		
	}
	
	public void addAnimation(Animation animation){
		animations.add(animation);
	}
	
	public Animation getCurrentAnimation(){
		if(currentAnimation<animations.size()){
			return animations.get(currentAnimation);
		}else{
			return null;
		}
	}
	
	public BufferedImage getCurrentFrame(){
		if(currentAnimation<animations.size()){
			return animations.get(currentAnimation).getCurrentFrame();
		}else{
			return null;
		}
	}
	
	public void setCurrentAnimation(String name){
		boolean found = false;
		currentAnimation=0;
		while (!found && currentAnimation<animations.size()){
			if(animations.get(currentAnimation).name.equals(name)){
				found=true;
			}else{
				currentAnimation++;
			}
		}
		if(currentAnimation==animations.size()){
			currentAnimation=0;
		}
	}
	
	public void update(){
		if(currentAnimation<animations.size()){
			animations.get(currentAnimation).update();
		}
	}

}
