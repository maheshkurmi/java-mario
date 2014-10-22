package com.shikhar.mario.core;

import javax.swing.JFrame;

// This is the main entry point..

public class GameFrame extends JFrame {
	
	public GameFrame() {	
		
		int w = 500;//566;//420;
		int h = 300;//320;//330;               
		setSize(w, h+50);
		setResizable(false);
		setTitle("Game Frame"); 
		GamePanel panel = new GamePanel(w, h);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}

}
