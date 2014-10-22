package com.shikhar.mario.objects.creatures;




import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;



public class Bowser extends Creature {
	
	private Animation left;
	private Animation right;
	private Animation flip;
	private Random r;
	
	private static BufferedImage left_1,left_2,right_1,right_2,flipped;
	private static boolean initialized=false;
	private float t;
	public boolean readytoFire=false;
	
	public Bowser(int x, int y, MarioSoundManager22050Hz soundManager) {
		
		super(x, y, soundManager);
		r = new Random();
		if (!initialized){
			BufferedImage[] images = new SpriteMap("baddies/Bowser_left.png", 5, 1)
			.getSprites();
			 left_1 = images[0];
			 left_2 = images[1];
			 images = new SpriteMap("baddies/Bowser_Right.png", 5, 1)
				.getSprites();
			 right_1 = images[0];
			 right_2 = images[1];
			 flipped = images[4];
			 initialized=true;
		}
		left = new Animation(200).addFrame(left_1).addFrame(left_2);
		right = new Animation(200).addFrame(right_1).addFrame(right_2);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		//dead = new DeadAfterAnimation();
		flip = new DeadAfterAnimation();
		//dead.addFrame(shell, 10);
		//dead.addFrame(shell, 10);
		flip.addFrame(flipped, 1200);
		flip.addFrame(flipped, 1200);
		setAnimation(left);
	}
	
	public void xCollide(Point p) {
		super.xCollide(p);
		if(currentAnimation() == left) {
			setAnimation(right);
		} else {
			setAnimation(left);
		}
	}
	
	public void creatureXCollide() {
		if(dx > 0) {
			x = x - 2;
			setAnimation(left);
		} else {
			setAnimation(right);
			x = x + 2;
		}
		dx = -dx;
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		if(isLeft) {
			dx = -.03f;
			setAnimation(left);
		} else {
			dx = .03f;
			setAnimation(right);
		}
		
	}
	
	@Override
	public void update(int time) {
		super.update(time);
		t++;
		if (t>100){
			readytoFire=true;
			t=0;
		}
	}
	
	public void jumpedOn() {
		//killl mario
	}
	
	@Override
	protected void useAI(TileMap map){
		int tileY = GameRenderer.pixelsToTiles(y+getHeight()-1);
		if (dx>0){
			int tileX = GameRenderer.pixelsToTiles(x-1);
			if (tileX+1>=map.getWidth()){
				x = x - 2;
				setAnimation(left);
				dx=-dx;
				return;
			}
			if (map.getTile(tileX+1, tileY+1)==null &&  (map.getTile(tileX+1, tileY+2)==null)){
				x = x - 2;
				setAnimation(left);
				dx=-dx;
			}
		}else if(dx<0){
			int tileX = GameRenderer.pixelsToTiles(x+getWidth()-1);
			if (tileX-1<0){
				x = x + 2;
				setAnimation(right);
				dx=-dx;
				return;
			}
			if (map.getTile(tileX-1, tileY+1)==null &&  (map.getTile(tileX-1, tileY+2)==null)){
				x = x + 2;
				setAnimation(right);
				dx=-dx;
			}
		}
	}
	
}
