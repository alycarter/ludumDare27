package com.alycarter.ludumDare27.stateMachine;

import java.util.Stack;

public class StateMachine {
	
	private Stack<State> states = new Stack<State>();
	
	public StateMachine() {
		
	}

	public State getCurrentState(){
		if(states.size()>0){
			return states.peek();
		}else{
			return null;
		}
	}
	
	public void pop(){
		states.pop();
	}
	
	public void push(State s){
		states.push(s);
	}
}
