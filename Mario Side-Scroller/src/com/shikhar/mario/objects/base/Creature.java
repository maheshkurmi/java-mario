package com.shikhar.mario.objects.base;


import java.awt.Point;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.CollidableObject;
import com.shikhar.mario.core.animation.Sprite;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.creatures.BoomRang;
import com.shikhar.mario.objects.creatures.Bowser;
import com.shikhar.mario.objects.creatures.FireBall;
import com.shikhar.mario.objects.creatures.FlyGoomba;
import com.shikhar.mario.objects.creatures.FlyRedKoopa;
import com.shikhar.mario.objects.creatures.RedShell;
import com.shikhar.mario.objects.tiles.FireTile;






public class Creature extends CollidableObject {
	
	protected static int xCollideOffset = 2; 
	protected static int offMapOffset = 15;
	protected static float GRAVITY = .0008f;
	protected  float gravityEffect = .22f; 
	
	// Wake up values are constants based on the number of tiles on the screen
	// that are used to determine when mario comes within range of a creature.
	// Used exclusively within GameRender.java.
	public static int WAKE_UP_VALUE_DOWN_RIGHT = 30;
	public static int WAKE_UP_VALUE_UP_LEFT = -3;
	
	/* 
	 * Creature Attributes:
	 * 
	 * Relevant:  A creature that is always relevant must be updated every frame. By default, no creature
	 * 			  is always relevant. 
	 * Alive:     A creature that is on the map is alive. All creatures start alive and can be killed using
	 * 			  the kill() method.
	 * Sleeping:  A creature that is sleeping has yet to be seen by the player. All creatures start out
	 * 			  sleeping, and can be woken up using wakeUp(). They cannot be put back to sleep.
	 * Flipped:   isFlipped is a flag used to determine when to change the animation of a creature to death.
	 * 			  For example, a goomba that is hopped on is 'flipped', then removed from the game.
	 * Item:      A creature that is an item represents an item the player can interact with.
	 * Platform:  A creature is a platform if it is a non-aligned moving object the player
	 * 			  and creatures can interact with. 
	 * Invisible: When a creature is invisible, it isn't drawn.
	 */
	private boolean isAlwaysRelevant; 
	private boolean isAlive; 
	private boolean isSleeping; 
	private boolean isFlipped;
	private boolean isItem;
	private boolean isPlatform;
	private boolean isInvisible;
	protected float gravityFactor=1;
	protected boolean inWater=true;
	
	public Creature() { 
		this(0, 0, null);
	}
	
	public Creature(int pixelX, int pixelY) {
		this(pixelX, pixelY, null);
	}
	
	/**
	 * @effects Creates a new Creature at the given pixelX, pixelY position that is capable
	 * of producing sounds from the soundManager. 
	 * 
	 * True: Collidable, Alive, Sleeping, Flipped.
	 * False: OnScreen, Item, Platform, Relevant.
	 */
	public Creature(int pixelX, int pixelY, MarioSoundManager22050Hz soundManager) {
		super(pixelX, pixelY, soundManager);	
		setIsCollidable(true);
		isAlive = true;
		isSleeping = true;
		isFlipped = false;
		setIsOnScreen(false);
		isItem = false;
		isPlatform = false;
		isAlwaysRelevant = false;
	}
	
	/**
	 * @return true if this creature is a Platform, false otherwise.
	 */
	public boolean isPlatform() {
		return isPlatform;
	}
	
	/**
	 * @modifies the platform status of this Creature.
	 */
	public void setIsPlatform(boolean isPlatform) {
		this.isPlatform = isPlatform;
	}
	
	/**
	 * @return true if this creature is an Item, false otherwise.
	 */
	public boolean isItem() {
		return isItem;
	}
	
	/**
	 * @modifies the item status of this Creature.
	 */
	public void setIsItem(boolean isItem) {
		this.isItem = isItem;
	}
	
	/**
	 * @return true if this creature is flipped, false otherwise.
	 */
	public boolean isFlipped() {
		return isFlipped;
	}
	
	/**
	 * @modifies the flipped status of this Creature.
	 */
	public void setIsFlipped(boolean isFlipped) {
		this.isFlipped = isFlipped;
	}
	
	/**
	 * @return true if this creature is sleeping, false otherwise.
	 */
	public boolean isSleeping() {
		return isSleeping;
	}
	
	/**
	 * @modifies the sleeping status of this creature to false.
	 * @param isLeft true if creative should begin moving left
	 */
	public void wakeUp(boolean isLeft) { 
		isSleeping = false;
	}
	
	/**
	 * @modifies the sleeping status of this creature to false.
	 */
	public void wakeUp() { 
		isSleeping = false;
	}
	
	
	/**
	 * @return true if this creature is alive, false otherwise.
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * @modifies the life state of this creature (alive or dead) to dead.
	 */
    public void kill() {
    	isAlive = false;
    }
	
	/**
	 * @return true if this creature is a Platform, false otherwise.
	 */
	public boolean isAlwaysRelevant() {
		return isAlwaysRelevant;
	}
	
	/**
	 * @modifies the platform status of this Creature.
	 */
	public void setIsAlwaysRelevant(boolean isAlwaysRelevant) {
		this.isAlwaysRelevant = isAlwaysRelevant;
	}
	
	/**
	 * @return true if this creature is invisible, false otherwise.
	 */
	public boolean isInvisible() {
		return isInvisible;
	}
	
	/**
	 * @modifies the invisible status of this Creature.
	 */
	public void setIsInvisible(boolean isInvisible) {
		this.isInvisible = isInvisible;
	}
    
	
	public void jumpedOn() { }
	public void flip() { }
	
	// for tile collisions
	public void xCollide(Point p) {
		if(dx > 0) {
			x = x - xCollideOffset;
		} else {
			x = x + xCollideOffset;
		}
		dx = -dx;
	}
	
	// for creature collisions
	public void creatureXCollide() {
		if(dx > 0) {
			x = x - xCollideOffset;
		} else {
			x = x + xCollideOffset;
		}
		dx = -dx;
	}
	
	/**
	 * Calculates the type of collision in the X direction between a Tile 
	 * and a Sprite given the Sprite is currentely colliding with the tile. 
	 * This method relies on the general heuristic that if two 
	 * rectangular objects are colliding, then one object is not completely
	 * contained in the other. Because the colliding objects stick out, we
	 * know the direction of the collision. 
	 * 
	 * pre-condition: sprite is colliding with tile.
	 * @return Collision.WEST if sprite is colliding with the tile from the west or
	 * Collision.EAST if sprite is colliding with the tile from the east.
	 */
	public static Collision tileCollisionX(GameTile tile, Sprite s) {
		if(s.getX() > tile.getPixelX()) {
			return Collision.WEST;
		} else {
			return Collision.EAST;
		}
	}
	
	/**
	 * Calculates the type of collision in the Y direction between a Tile 
	 * and a Sprite given the Sprite is currentely colliding with the tile. 
	 * This method relies on the general heuristic that if two 
	 * rectangular objects are colliding, that one object is not completely
	 * contained in the other. Because the colliding objects stick out, we
	 * know the direction of the collision. 
	 * 
	 * pre-condition: sprite is colliding with tile.
	 * @return Collision.NORTH if sprite is colliding with the tile from the north or
	 * Collision.SOUTH if sprite is colliding with the tile from the south.
	 */
	public static Collision tileCollisionY(GameTile tile, Sprite s) {
		if(s.getY() < tile.getPixelY()) {
			return Collision.NORTH;
		} else {
			return Collision.SOUTH;
		}
	}
	
	
	public void updateCreature(TileMap map, int time) {
		
		if(dy < gravityEffect) { // apply gravity...this must be done first
			if (isFlipped()){
				dy = dy + gravityFactor*GRAVITY * time;
			}else{
				dy = dy +  (inWater?0.3f:1)*gravityFactor*GRAVITY * time;
			}
		}
				
		float dx = this.dx;
		float oldX = this.x;
		float newX = oldX + dx * time;
		
		float dy = this.dy;
		float oldY = this.y;
		float newY = oldY + dy * time; 
		
		if(!isFlipped) {
			Point xTile = GameRenderer.getTileCollision(map, this, x, y, newX, y);
			Point yTile = GameRenderer.getTileCollision(map, this, x, y, x, newY);
			
			this.update(time);
			if (this instanceof Bowser){
				if (((Bowser)this).readytoFire){
					map.creaturesToAdd().add(new BoomRang((int) (dx<0?x:x+getWidth()),(int)(y+getHeight()*0.13f),dx<0?-2:2,soundManager));
					((Bowser)this).readytoFire=false;
				}
			}
			// Update collisions in the x-direction.
			if (oldX < -offMapOffset
					|| oldX > GameRenderer.tilesToPixels(map.getWidth())
							+ offMapOffset) { // offscreen
				kill();
			} else {
				if (xTile == null) {
					x = newX;
				} else {
					if (!xTile.equals(yTile)) { // Only manage x-collisions that are not y-collisions
						this.xCollide(xTile);
						if (dx > 0) {
							x = GameRenderer.tilesToPixels(xTile.x)	- this.getWidth() - 1;
						} else if (dx < 0) {
							x = GameRenderer.tilesToPixels(xTile.x + 1);
						}
					} else if ( xTile.y*16<y+8){//else condition added on 12-9-13
						if ((xTile.x*16>x && dx>0) ||(xTile.x*16<x && dx<0)){
							this.xCollide(xTile);
							if (dx > 0) {
								x = GameRenderer.tilesToPixels(xTile.x)	- this.getWidth() - 1;
							} else if (dx < 0) {
								x = GameRenderer.tilesToPixels(xTile.x + 1);
							}
							System.out.println("xTile.y" + xTile.y + "y="+ this.y);
							return;
						}
					}
				}
			}

			// Update collisions in the y-direction. 
			if(oldY > GameRenderer.tilesToPixels(map.getHeight()) + offMapOffset) { // offscreen
				kill();
			} else {
				if(yTile == null) {
					y = newY;
				} else {
					if(dy >= 0) {
						if (map.getTile(yTile.x, yTile.y) instanceof FireTile) flip();
						// mark this creature as colliding with a tile
						map.getTile(yTile.x, yTile.y).collidingCreatures().add(this); 
						GameTile tileRight = map.getTile(((int) yTile.x) + 1, ((int) yTile.y));
						if(tileRight != null) {
							tileRight.collidingCreatures().add(this);
						}
						y = GameRenderer.tilesToPixels(yTile.y) - this.getHeight();
						if (this instanceof FireBall || this instanceof BoomRang){
							this.dy=-0.7f*dy;
							if (dy<0.1)xCollide(new Point(0,0)) ;
						} else if (this instanceof FlyRedKoopa ){
							if (((FlyRedKoopa)this).canFly) this.dy=-0.12f;//dy;//0.18f;
						} else if(this instanceof FlyGoomba ){
							if (((FlyGoomba)this).canFly) this.dy=-0.12f;//dy;//0.18f;
						} else{
							this.dy=0;
						}
					} else if (dy < 0) {
						y = GameRenderer.tilesToPixels(yTile.y + 1);
						if (!(this instanceof FlyRedKoopa || this instanceof FlyGoomba))this.dy = -dy/4; // fall faster if a collision occured
					} 
				}
				//use artificial itelligence of creature if it is not in air
				if (yTile!=null)useAI(map);
				
			}
		} else { // flipped
			x = newX;
			y = newY;
			this.update(time);
		}
	}
	
	/**
	 * This is called when creature is walking on tiles(not in air)so that creature can do some intelligent work by overriding it
	 */
	protected void useAI(TileMap map) {
		// don't let it go beyond mapfall
		if (x<=0 || x>map.getWidth()*16) {
			this.xCollide(null);
		}
	}

	// Determines what happens when two different creatures collide.
	// Uncommenting the onSreen condition makes this more efficient, but more buggy
	public void creatureCollision(Creature creature) {
		if (!this.isItem && !creature.isItem && !this.isPlatform
				&& !creature.isPlatform && this != creature
				&& this.isCollidable() && creature.isCollidable()) {

			boolean collision = isCollision(this, creature);
			if (collision) {
				if (this instanceof FireBall) {
					soundManager.playKick();
					this.xCollide(new Point());
					creature.flip();
					
				} else if (creature instanceof FireBall) {
					soundManager.playKick();
					creature.xCollide(new Point());
					this.flip();
					
					// Handeling RedShell collision cases....
					// ______________________________________
					// creature 1 is a RedShell, creature 2 is not.
				} else if (this instanceof RedShell
						&& !(creature instanceof RedShell)) {
					if (((RedShell) this).isMoving()) {
						creature.flip();
						soundManager.playKick();
					}
					// creature 2 is a RedShell, creature 1 is not.
				} else if (!(this instanceof RedShell)
						&& creature instanceof RedShell) {
					if (((RedShell) creature).isMoving()) {
						this.flip();
						soundManager.playKick();
					}
					// both creature 1 and creature 2 are RedShells
				} else if (this instanceof RedShell
						&& creature instanceof RedShell) {
					// RedShell 1 is moving, RedShell 2 is not.
					this.flip();
					creature.flip();
					soundManager.playKick();
					// End of RedShell collision cases...
				} else {
					this.creatureXCollide();
					creature.creatureXCollide();
				}
			}
		}
	}

	public float getGravityFactor() {
		return gravityFactor;
	}

	public void setGravityFactor(float gravityFactor) {
		this.gravityFactor = gravityFactor;
	}
}
