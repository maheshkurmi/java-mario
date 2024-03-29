package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

class Particle {
	public int distFromOrigin = 0;
	private double direction;
	private float speedX;
	private float speedY;
	public int color;
	public float x;
	public float y;
	private int initX;
	private int initY;
	private Image bitmap;
    private float speed=1.5f;
    private float dt=1;
	
	public Particle(int x, int y, Image bmp) {
		init(x, y);
		this.direction = Math.PI * new Random().nextFloat();//.nextInt(NO_OF_DIRECTION)/ NO_OF_DIRECTION;
		this.speedX =(float) (speed*Math.cos(direction));
		this.speedY = -(float) (speed*Math.sin(direction));
		this.color = new Random().nextInt(3);
		bitmap = bmp;
	}

	public void init(int x, int y) {
		distFromOrigin = 0;
		this.initX = (int) (this.x = x);
		this.initY = (int) (this.y = y);
	}

	public void updatePhysics(int distChange) {
		distFromOrigin += 2;
		x =  x + speedX *dt;
		y =  y + speedY * dt;
		speedY=speedY+0.03f;
	}

	public void doDraw(Graphics canvas,int offsetX,int offsetY) {
		canvas.drawImage(bitmap, offsetX+(int)x,offsetY+(int)y, null);
	}

}