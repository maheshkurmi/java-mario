package com.shikhar.mario.objects.creatures;



import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageManipulator;




public class Score extends Creature {
	
	public Animation oneHundred;
	
	public Score(int x, int y) {
		super(x, y);
		setIsItem(true);
		
		dy = -.45f;

		BufferedImage one_hundred = ImageManipulator.loadImage("items/Score_100_New6.png");
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		oneHundred = new DeadAfterAnimation();
		
		oneHundred.addFrame(one_hundred, 380);
		oneHundred.addFrame(one_hundred, 380);	
		setAnimation(oneHundred);
	}
	
	public void updateCreature(TileMap map, int time) {
		this.update((int) time);
		y = y + dy * time;
		if(dy < 0) {
			dy = dy + .032f;
		} else {
			dy = 0;
		}
	}

}
