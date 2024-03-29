package com.shikhar.mario.objects.tiles;


import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageManipulator;


public class Tree extends GameTile {
	
	private static BufferedImage[] c = {ImageManipulator.loadImage("items/Tree_1.png"),ImageManipulator.loadImage("items/Tree_2.png")};
	public static Animation swing = new Animation(400).addFrame(c[0]).addFrame(c[1]);

	public Tree(int pixelX, int pixelY) {
		super(pixelX, pixelY,null,null);
		setIsCollidable(false);
		setAnimation(swing);
	}
	
}
