package com.alycarter.ludumDare27;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.alycarter.ludumDare27.stateMachine.StateMachine;
import com.alycarter.ludumDare27.stateMachine.Tutorial;
import com.alycarter.ludumDare27.states.level.Level;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	private boolean running = false;
	public StateMachine stateMachine = new StateMachine();
	public double deltaTime=0;
	public Controls controls = new Controls(this);
	
	private int frameLimit = 120;
	public int framesLastSeconds  =0;
	
	public Level level;
	
	public Game(String title, int width, int height) {
		 super(title);
		 setSize(width, height);
		 setResizable(false);
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setLayout(null);
		 canvas = new Canvas();
		 add(canvas);
		 canvas.addMouseListener(controls);
		 canvas.addKeyListener(controls);

	}
	
	public void play(){
		new Thread(){
			public void run() {
				inisialise();
				running = true;
				int ns =  1000000000;
				long last;
				long after;
				double nextFrame = 0;
				int frames=0;
				double second=0;
				while (running){
					last = System.nanoTime();
					update();
					if (nextFrame<=0){
						render();
						nextFrame=1d/frameLimit;
						frames++;
					}
					after = System.nanoTime();
					deltaTime = (after-last)/(double)ns;
					nextFrame-=deltaTime;
					second+=deltaTime;
					if(second>=1){
						framesLastSeconds=frames;
						frames=0;
						second=0;
					}
				}
			}		
		}.start();
	}
	
	private void inisialise(){
		setVisible(true); 
		canvas.setSize(getWidth(), getHeight());
		canvas.setLocation(0, 0);
		level = new Level(this);
		stateMachine.push(level);
		canvas.requestFocusInWindow();
		stateMachine.push(new Tutorial(this));
	}
	
	private void render() {
		if(canvas.getBufferStrategy()==null){
			canvas.createBufferStrategy(3);
		}
		Graphics g = canvas.getBufferStrategy().getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		if(stateMachine.getCurrentState()!=null){
			stateMachine.getCurrentState().render(g);
		}else{
			endGame();
		}
		canvas.getBufferStrategy().show();
	}

	private void update() {
		controls.update();
		if(stateMachine.getCurrentState()!=null){
			stateMachine.getCurrentState().update();
		}else{
			endGame();
		}
	};
	
	public void endGame(){
		running=false;
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		Game game = new Game("saloon shoot out", 640, 480);
		game.play();
	}

}
