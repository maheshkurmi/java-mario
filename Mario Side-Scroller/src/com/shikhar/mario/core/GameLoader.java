
package com.shikhar.mario.core;


import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.creatures.BlueFish;
import com.shikhar.mario.objects.creatures.Bowser;
import com.shikhar.mario.objects.creatures.Coin;
import com.shikhar.mario.objects.creatures.FireDisc;
import com.shikhar.mario.objects.creatures.FlyGoomba;
import com.shikhar.mario.objects.creatures.FlyRedKoopa;
import com.shikhar.mario.objects.creatures.Goomba;
import com.shikhar.mario.objects.creatures.JumpingFish;
import com.shikhar.mario.objects.creatures.Latiku;
import com.shikhar.mario.objects.creatures.LevelComplete;
import com.shikhar.mario.objects.creatures.Piranha;
import com.shikhar.mario.objects.creatures.Platform;
import com.shikhar.mario.objects.creatures.RedFish;
import com.shikhar.mario.objects.creatures.RedKoopa;
import com.shikhar.mario.objects.creatures.RedShell;
import com.shikhar.mario.objects.creatures.Spring;
import com.shikhar.mario.objects.creatures.Thorny;
import com.shikhar.mario.objects.creatures.Virus;
import com.shikhar.mario.objects.tiles.Brick;
import com.shikhar.mario.objects.tiles.Bush;
import com.shikhar.mario.objects.tiles.FireTile;
import com.shikhar.mario.objects.tiles.InfoPanel;
import com.shikhar.mario.objects.tiles.QuestionBlock;
import com.shikhar.mario.objects.tiles.RotatingBlock;
import com.shikhar.mario.objects.tiles.SlopedTile;
import com.shikhar.mario.objects.tiles.Tree;
import com.shikhar.mario.util.SpriteMap;




public class GameLoader {
	
	private ArrayList<BufferedImage> plain;
	private BufferedImage[] plainTiles;
	
	private BufferedImage sloped_image;
	private BufferedImage grass_edge;
	private BufferedImage grass_center;
	private BufferedImage mushroomTree;
	private BufferedImage woodenBridge;
	private BufferedImage waterRock;
	private int backGroundImageIndex=0;
	   private ArrayList<String> infoPanels;
		int infoCount=0;

	private boolean togglePlatform_velocity=false;
	private List<Rectangle> waterZones; // List of Water Zones.

	public GameLoader() {
		plain = new ArrayList<BufferedImage>();
		plainTiles = (new SpriteMap("tiles/Plain_Tiles.png", 6, 17)).getSprites();
		infoPanels =new ArrayList<String>(); 
		
		for (BufferedImage bImage : plainTiles) {
			plain.add(bImage);
		}
		
		mushroomTree=loadImage("items/mushRoom_tree.png");
		woodenBridge=loadImage("items/wooden_bridge.png");
		waterRock=loadImage("items/water_rock.png");
		
		sloped_image = loadImage("items/Sloped_Tile.png");
		grass_edge = loadImage("items/Grass_Edge.png");
		grass_center = loadImage("items/Grass_Center.png");
	}
	
	public BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e) { }
		return img;
	}
	
	// BufferedImage -> Image
	public static Image toImage(BufferedImage bufferedImage) {
	    return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
	}

	// loads a tile map, given a map to load..
    // use this to load the background and foreground. Note: the status of the tiles (ie collide etc)
    // is irrelevant. Why? I don't check collision on maps other than the main map. 
    public TileMap loadOtherMaps(String filename) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		infoCount=0;
		infoPanels.clear();
		// read in each line of the map into lines
		Scanner reader = new Scanner(new File(filename));
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}else{
				if (line.startsWith("#@")){
					infoPanels.add(line.substring(2).trim());
				}
			}
		}
		height = lines.size(); // number of elements in lines is the height
		
		TileMap newMap = new TileMap(width, height);
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=0; x < line.length(); x++) {
				char ch = line.charAt(x);
				int pixelX = GameRenderer.tilesToPixels(x);
				int pixelY = GameRenderer.tilesToPixels(y);
				if (ch == 'n') {
					newMap.setTile(x, y, plain.get(92));
				} else if (ch == 'm') {
					newMap.setTile(x, y, plain.get(93));
				} else if (ch == 'a') {
					newMap.setTile(x, y, plain.get(90));
				} else if (ch == 'b') {
					newMap.setTile(x, y, plain.get(91));
				} else if (ch == 'q') { // rock left
					newMap.setTile(x, y, plain.get(48));
				} else if (ch == 'r') { // rock right
					newMap.setTile(x, y, plain.get(49));
				} else if (ch == 'z') { // tree stem
					newMap.setTile(x, y, plain.get(75));
				} else if (ch=='T') {
					Tree t = new Tree(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch=='}') {
					Bush t = new Bush(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(56));
					newMap.setTile(x, y, t);
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(4));
					newMap.setTile(x, y, t);
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(10));
					newMap.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(86));
					newMap.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(81));
					newMap.setTile(x, y, t);
				} else if(ch == '9') {
					GameTile t = new GameTile(pixelX, pixelY, mushroomTree);
					newMap.setTile(x, y, t);
					//newMap.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, woodenBridge);
					newMap.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, waterRock);
					newMap.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(96)); //pipe top left
					newMap.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(97)); //pipe top middle
					newMap.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(98)); //pipe top right
					newMap.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(99)); //pipe base left
					newMap.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(100)); //pipe base middle
					newMap.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(101)); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(75)); //
					newMap.setTile(x, y, t);
				} else if(ch == 'i') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(42)); //
					newMap.setTile(x, y, t);
				} else if(ch == 'j') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(43)); //
					newMap.setTile(x, y, t);
				} else if(ch == 'k') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(44)); //tree stem
					newMap.setTile(x, y, t);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(9));
					newMap.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(11));
					newMap.setTile(x, y, t);
				}else if(ch == '*') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(78)); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == '!') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(84)); //pipe base right
					newMap.setTile(x, y, t);    
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(25));
					newMap.setTile(x, y, t);
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(28));
					newMap.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(26));
					newMap.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(27));
					newMap.setTile(x, y, t);
				} else if(ch == '@') {
					InfoPanel t = new InfoPanel(pixelX, pixelY, "Mario"); //pipe base right
					newMap.setTile(x, y, t);
					if(infoCount<infoPanels.size())t.setInfo(infoPanels.get(infoCount));
					infoCount++;
				}
			}
		}
		return newMap;	
	}
    
	// Fills given tile mapFrom file. Used in Editor for background and foreground
    public void fillMap(String filename, TileMap map) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		infoCount=0;
		infoPanels.clear();
		// read in each line of the map into lines
		Scanner reader = new Scanner(new File(filename));
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}else{
				if (line.startsWith("#@")){
					infoPanels.add(line.substring(2).trim());
				}
			}
		}
		height = lines.size(); // number of elements in lines is the height
		
		height=Math.min(height,map.getHeight());
		width=Math.min(width, map.getWidth());
		
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=0; x < line.length(); x++) {
				char ch = line.charAt(x);
				int pixelX = GameRenderer.tilesToPixels(x);
				int pixelY = GameRenderer.tilesToPixels(y);
				if (ch == 'n') {
					map.setTile(x, y, plain.get(92));
				} else if (ch == 'm') {
					map.setTile(x, y, plain.get(93));
				} else if (ch == 'a') {
					map.setTile(x, y, plain.get(90));
				} else if (ch == 'b') {
					map.setTile(x, y, plain.get(91));
				} else if (ch == 'q') { // rock left
					map.setTile(x, y, plain.get(48));
				} else if (ch == 'r') { // rock right
					map.setTile(x, y, plain.get(49));
				} else if (ch == 'z') { // tree stem
					map.setTile(x, y, plain.get(75));
				} else if (ch=='T') {
					Tree t = new Tree(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				} else if (ch=='}') {
					Bush t = new Bush(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(56));
					map.setTile(x, y, t);
				} else if (ch == '5') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(3));
					map.setTile(x, y, t);
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(4));
					map.setTile(x, y, t);
				} else if (ch == '6') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(5));
					map.setTile(x, y, t);
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(10));
					map.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(86));
					map.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(81));
					map.setTile(x, y, t);
				} else if(ch == 'T') {	
					Tree t = new Tree(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				} else if(ch == '}') {	
					Bush t = new Bush(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				}else if(ch == '9') {
					GameTile t = new GameTile(pixelX, pixelY, mushroomTree);
					map.setTile(x, y, t);
					//newMap.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, woodenBridge);
					map.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, waterRock);
					map.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(96)); //pipe top left
					map.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(97)); //pipe top middle
					map.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(98)); //pipe top right
					map.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(99)); //pipe base left
					map.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(100)); //pipe base middle
					map.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(101)); //pipe base right
					map.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(75)); //tree stem
					map.setTile(x, y, t);
				}else if(ch == 'n') {
					map.setTile(x, y, plain.get(92));
				} else if (ch == 'm') {
					map.setTile(x, y, plain.get(93));
				} else if (ch == 'a') {
					map.setTile(x, y, plain.get(90));
				} else if (ch == 'b') {
					map.setTile(x, y, plain.get(91));
				} else if (ch == 'q') { // rock left
					map.setTile(x, y, plain.get(48));
				} else if (ch == 'r') { // rock right
					map.setTile(x, y, plain.get(49));
				} else if(ch == 'i') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(42)); //
					map.setTile(x, y, t);
				} else if(ch == 'j') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(43)); //
					map.setTile(x, y, t);
				} else if(ch == 'k') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(44)); //tree stem
					map.setTile(x, y, t);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(9));
					map.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(11));
				    map.setTile(x, y, t);
				}else if(ch == '*') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(78)); //pipe base right
					map.setTile(x, y, t);
				} else if(ch == '!') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(84)); //pipe base right
					map.setTile(x, y, t);    
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					map.setTile(x, y, r);
					map.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(25));
					map.setTile(x, y, t);	
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(28));
					map.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(26));
					map.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(27));
					map.setTile(x, y, t);
				} else if(ch == '@') {
					InfoPanel t = new InfoPanel(pixelX, pixelY, "Mario"); //pipe base right
					map.setTile(x, y, t);
					if(infoCount<infoPanels.size())t.setInfo(infoPanels.get(infoCount));
					infoCount++;
				}

			}
		}
		
	}
    
    // Use this to load the main map
	public TileMap loadMap(String filename, MarioSoundManager22050Hz soundManager) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		
		int width = 0;
		int height = 0;
		
		// read in each line of the map into lines
		Scanner reader = new Scanner(new File(filename));
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			line="."+line;
			line=line.trim();
			line=line.substring(1,line.length());
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}else{
				line=line.substring(1);
				
				if (line.startsWith("background")) {
						setBackGroundImageIndex(Integer.parseInt(line
								.substring(line.length() - 1)));
				}else if (line.startsWith("waterzone")){
					line=line.substring(10);
					String[] pts=line.split(",");
					{   int[] x=new int[4];
						for (int i=0 ;i<=3;i++){
							x[i]=Integer.parseInt(pts[i]);
						}
						rects.add(new Rectangle(x[0],x[1],x[2],x[3]));
					}
				}else if (line.startsWith("@")){
					
				}
			}
		}
		height = lines.size(); // number of elements in lines is the height
		TileMap newMap = new TileMap(width, height);
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=0; x < line.length(); x++) {
				char ch = line.charAt(x);
				int pixelX = GameRenderer.tilesToPixels(x);
				int pixelY = GameRenderer.tilesToPixels(y);
				// enumerate the possible tiles...
				if (ch == 'G') {
					newMap.creatures().add(new Goomba(pixelX, pixelY, soundManager));
				} else if (ch == 'K') {
					newMap.creatures().add(new RedKoopa(pixelX, pixelY, soundManager,false));
				} else if (ch == 'L') {
					newMap.creatures().add(new RedKoopa(pixelX, pixelY, soundManager,true));
				} else if (ch == 'I') {
					newMap.creatures().add(new FireDisc(pixelX, pixelY, soundManager));
				} else if (ch == 'H') {
					newMap.creatures().add(new Thorny(pixelX, pixelY, soundManager));
				} else if (ch == 'F') {
					newMap.creatures().add(new FlyRedKoopa(pixelX, pixelY, soundManager));
				} else if (ch == 'O') {
					newMap.creatures().add(new FlyGoomba(pixelX, pixelY, soundManager));
				} else if (ch == 'N') {
					newMap.creatures().add(new Bowser(pixelX, pixelY, soundManager));
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(56));
					newMap.setTile(x, y, t);
				} else if (ch == 'J') {
					Piranha t = new Piranha(pixelX, pixelY,soundManager);
					newMap.creatures().add(t);
				} else if (ch == '?') {
					Virus t = new Virus(pixelX, pixelY,soundManager);
					newMap.creatures().add(t);
				} else if (ch == '(') {
					RedFish t = new RedFish(pixelX, pixelY,soundManager);
					newMap.creatures().add(t);
				} else if (ch == '&') {
					Latiku t = new Latiku(pixelX, pixelY,soundManager,newMap);
					newMap.creatures().add(t);
				} else if (ch == ')') {
					BlueFish t = new BlueFish(pixelX, pixelY,soundManager);
					newMap.creatures().add(t);
				} else if (ch == ',') {
					JumpingFish t = new JumpingFish(pixelX, pixelY,Math.random()>0.7f,soundManager);
					newMap.creatures().add(t);
				} else if (ch == 'l') {
					LevelComplete l= new LevelComplete(pixelX, pixelY);
					newMap.creatures().add(l);
				} else if (ch=='B') {
					Brick b = new Brick(pixelX, pixelY,newMap, plain.get(77),soundManager,10*(int)(Math.random()*1.1),false);
					newMap.setTile(x, y, b);
					newMap.animatedTiles().add(b);	
				} else if (ch == 'R') {
					RotatingBlock r = new RotatingBlock(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == '5') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(3));
					newMap.setTile(x, y, t);
			
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(4));
					newMap.setTile(x, y, t);
				} else if (ch == '6') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(5));
					newMap.setTile(x, y, t);
				} else if (ch == 'M') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(82));
					t.setIsCollidable(false);
					newMap.setTile(x, y, t);
					newMap.addBookMark(new  Point(x,y));
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(10));
					newMap.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(86));
					newMap.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(81));
					newMap.setTile(x, y, t);
				} else if (ch == 'Q') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, newMap, soundManager, true, false);
					newMap.setTile(x, y, q);
					newMap.animatedTiles().add(q);
				} else if (ch == 'W') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, newMap, soundManager, false, true);
					newMap.setTile(x, y, q);
					newMap.animatedTiles().add(q);
				} else if (ch == 'S') {
					newMap.creatures().add(new RedShell(pixelX, pixelY, newMap, soundManager, true,true));
				} else if(ch == 'C') {
					newMap.creatures().add(new Coin(pixelX, pixelY));
				} else if(ch == 'P') {
					Platform p = new Platform(pixelX, pixelY);
					newMap.creatures().add(p);
				} else if(ch == '<') {
					Platform p = new Platform(pixelX, pixelY,togglePlatform_velocity?0.05f:-0.05f,0,false);
					newMap.creatures().add(p);
					togglePlatform_velocity=!togglePlatform_velocity;
				} else if(ch == '>') {
					Platform p = new Platform(pixelX, pixelY,0,0.05f,false);
					newMap.creatures().add(p);
				} else if(ch == '^') {
					Spring spring = new Spring(pixelX, pixelY);
					newMap.creatures().add(spring);
				}  else if(ch == 'T') {	
					Tree t = new Tree(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				}  else if(ch == '}') {	
					Bush t = new Bush(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				}else if(ch == '9') {
					SlopedTile t = new SlopedTile(pixelX, pixelY, sloped_image, true);
					newMap.setTile(x, y, t);
					newMap.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, grass_edge);
					newMap.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, grass_center);
					newMap.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(30));//96)); //pipe top left
					newMap.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(31)); //pipe top middle
					newMap.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(32)); //pipe top right
					newMap.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(33)); //pipe base left
					newMap.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(34)); //pipe base middle
					newMap.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(35)); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(75)); //tree stem
					newMap.setTile(x, y, t);
				}else if(ch == 'n') {
					newMap.setTile(x, y, plain.get(92));
				} else if (ch == 'm') {
					newMap.setTile(x, y, plain.get(93));
				} else if (ch == 'a') {
					newMap.setTile(x, y, plain.get(90));
				} else if (ch == 'b') {
					newMap.setTile(x, y, plain.get(91));
				} else if (ch == 'q') { // rock left
					newMap.setTile(x, y, plain.get(48));
				} else if (ch == 'r') { // rock right
					newMap.setTile(x, y, plain.get(49));
				} else if(ch == 'i') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(42)); //
					newMap.setTile(x, y, t);
				} else if(ch == 'j') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(43)); //
					newMap.setTile(x, y, t);
				} else if(ch == 'k') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(44)); //tree stem
					newMap.setTile(x, y, t);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(9));
					newMap.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(11));
					newMap.setTile(x, y, t);
				} else if(ch == '%') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(36)); //BlueRock 
					newMap.setTile(x, y, t);
				} else if(ch == '&') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(37)); //Yellowrock
					newMap.setTile(x, y, t);
				} else if(ch == '$') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(38)); //greyrock
					newMap.setTile(x, y, t);
		    	} else if(ch == '-') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(40)); // 
					newMap.setTile(x, y, t);
				} else if(ch == '+') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(46)); //
					newMap.setTile(x, y, t);
				} else if(ch == '~') {
					GameTile t = new GameTile(pixelX, pixelY,plain.get(89)); //watertile
					newMap.setTile(x, y, t);
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(25));
					newMap.setTile(x, y, t);
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(28));
					newMap.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(26));
					newMap.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, plain.get(27));
					newMap.setTile(x, y, t);
				}
			}
		}
		
		for (Rectangle r:rects){
			newMap.addWaterZone(r);
		}
		waterZones=newMap.waterZones();
		return newMap;	
	}

	// Use this to return characterArray of the map
	public char[][] getMap(String filename) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;

		// read in each line of the map into lines
		Scanner reader = new Scanner(new File(filename));
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size(); // number of elements in lines is the height
		char[][] c = new char[width][height];
		// TileMap newMap = new TileMap(width, height);
		for (int y = 0; y < height; y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				char ch = line.charAt(x);
				c[x][y] = ch;
			}
		}
		return c;
	}

	// Use this to fill characterArray of the map
	public void fillMap(String filename, char[][] c, int w, int h)
			throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;

		// read in each line of the map into lines
		Scanner reader = new Scanner(new File(filename));
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size(); // number of elements in lines is the height

		width = Math.min(width, w);
		height = Math.min(height, h);

		// TileMap newMap = new TileMap(width, height);
		for (int y = 0; y < height; y++) {
			String line = lines.get(y);
			for (int x = 0; x < Math.min(width,line.length()); x++) {
				char ch = line.charAt(x);
				c[x][y] = ch;
			}
		}

	}
		
	public void saveMap(String filename, char[][] c,int height, int width) {
			File file = new File(filename);// this.getClass().getResource(filename).getFile());
			
			try {
				FileWriter fw = new FileWriter(file, false);
				BufferedWriter Bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(Bw);
				int m = 0;
				for (int j = 0; j < height; j++) {
					for (int i = 0; i < width; i++) {
						pw.print(c[i][j]);
						// System.out.println(m+":"+Level[i][j]);
						m++;
					}
					pw.println();
					pw.flush();
				}
				for (Rectangle r:waterZones){
					pw.println("#waterzone="+r.x+","+r.y+","+r.width+","+r.height);
				}
				pw.print("#background="+getBackGroundImageIndex());
				pw.println();
				for (String str:infoPanels){
					pw.print("#@"+str);
					pw.println();
				}
				pw.flush();
			} catch (Exception ex) {
				
				System.out.println("ERror Saving Game Map File");
				ex.printStackTrace();
			}

	}
	
	public int getBackGroundImageIndex() {
		return backGroundImageIndex;
	}

	public void setBackGroundImageIndex(int backGroundImageIndex) {
		if (backGroundImageIndex<0 ||backGroundImageIndex>10)return;
		this.backGroundImageIndex = backGroundImageIndex;
	}
}
