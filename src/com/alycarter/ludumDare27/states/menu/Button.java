package com.alycarter.ludumDare27.states.menu;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.alycarter.ludumDare27.Controls;

public abstract class Button {
	public Rectangle rectangle;
	private Controls controls;
	
	public Button(Controls controls, int x, int y, int width, int height) {
		rectangle = new Rectangle(x, y, width, height);
		this.controls=controls;
	}
	
	public void update(){
		if(rectangle.contains(controls.mouseLocation) && controls.isMouseClicked()){
			onClick();
		}
	}
	
	public void render(Graphics g){
//		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		onRender(g);
	}
	
	
	
	public void onRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public abstract void onClick();

}
