package com.alycarter.ludumDare27.states.menu;

import java.awt.Graphics;
import java.util.ArrayList;

import com.alycarter.ludumDare27.stateMachine.State;

public class Menu implements State {
	
	public ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		for(int i=0;i<buttons.size();i++){
			buttons.get(i).update();
		}
	}

	@Override
	public void render(Graphics g) {
		for(int i=0;i<buttons.size();i++){
			buttons.get(i).render(g);
		}
	}

}
