package com.shikhar.mario.objects.creatures;





import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageManipulator;


public class Tree extends Creature {
	
	private static BufferedImage[] c = {ImageManipulator.loadImage("items/Tree_1.png"),ImageManipulator.loadImage("items/Tree_1.png")};
	public static Animation swing = new Animation(300).addFrame(c[0]).addFrame(c[1]);

	public Tree(int pixelX, int pixelY) {
		
		super(pixelX, pixelY);
		setIsItem(true);
		setIsCollidable(false);
		setAnimation(swing);
	}
	
}
