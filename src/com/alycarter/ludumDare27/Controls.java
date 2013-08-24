package com.alycarter.ludumDare27;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Controls implements KeyListener, MouseListener{
	
	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private ArrayList<Integer> keysReleased = new ArrayList<Integer>();
	
	private ArrayList<Integer> keysPressedBuffer= new ArrayList<Integer>();
	private ArrayList<Integer> keysReleasedBuffer = new ArrayList<Integer>();
	
	private boolean mouseHeldBuffer=false;
	private boolean mouseClickedBuffer=false;
	private boolean mouseHeld = false;
	private boolean mouseClicked = false;
	
	public Point mouseLocation = new Point(0,0);
	
	public Game game;
	
	public Controls(Game game) {
		this.game=game;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			mouseHeldBuffer=true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			mouseHeldBuffer=false;
			mouseClickedBuffer=true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!keysPressedBuffer.contains(new Integer(e.getKeyCode()))){
			keysPressedBuffer.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressedBuffer.remove(new Integer(e.getKeyCode()));
		if(!keysReleasedBuffer.contains(new Integer(e.getKeyCode()))){
			keysReleasedBuffer.add(e.getKeyCode());
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(){
		try{
			mouseLocation=MouseInfo.getPointerInfo().getLocation();
			mouseLocation.x-=game.getLocationOnScreen().getX()+3;
			mouseLocation.y-=game.getLocationOnScreen().getY()+25;
			mouseHeld=mouseHeldBuffer;
			mouseClicked=mouseClickedBuffer;
			mouseClickedBuffer=false;
			keysPressed.clear();
			for(int i=0;i<keysPressedBuffer.size();i++){
				keysPressed.add(keysPressedBuffer.get(i));
			}
			keysReleased.clear();
			for(int i=0;i<keysReleasedBuffer.size();i++){
				keysReleased.add(keysReleasedBuffer.get(i));
				keysReleasedBuffer.remove(i);
			}
		}catch(Exception e){}
	}
	
	public boolean isKeyPressed(int key){
		return keysPressed.contains(new Integer(key));
	}
	
	public boolean isKeyReleased(int key){
		return keysReleased.contains(new Integer(key));
	}
	
	public boolean isMouseClicked(){
		return mouseClicked;
	}
	
	public boolean isMouseHeld(){
		return mouseHeld;
	}

}
