
package com.shikhar.mario.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.shikhar.mario.util.ImageUtils;




/**
 * Class storing all the icons used by the application.
 * @author Mahesh Kurmi
 * @version 1.0.2
 * @since 1.0.0
 */
public class Icons {
	/** Reads in a BufferedImage using the standard ImageIO.read() */
	public static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e) { 
			System.out.println(e.getMessage());
		}
		return img;
	} 
	
	/** New icon */
	public static final ImageIcon NEW = new ImageIcon(loadImage("icons/new-icon.png"));
	
	/** Open icon */
	public static final ImageIcon OPEN = new ImageIcon("icons/open-icon.png");
	
	/** Save icon */
	public static final ImageIcon SAVE = new ImageIcon("icons/save-icon.png");

	/** Save As.. icon */
	public static final ImageIcon SAVEAS = new ImageIcon("icons/saveas-icon.png");
	
	/** Export as Image icon */
	public static final ImageIcon EXPORT = new ImageIcon("icons/export-icon.png");
	
	/** Print icon */
	public static final ImageIcon PRINT = new ImageIcon("icons/print-icon.png");
	
	/** Check icon */
    public static final ImageIcon CHECK =  new ImageIcon("icons/check-icon.png");

	/** Look and feel icon */
	public static final ImageIcon LOOKANDFEEL = new ImageIcon("icons/lookandfeel-icon.png");
	
	/** Prefereneces icon */
	public static final ImageIcon PREFERENCES = new ImageIcon("icons/preferences-icon.png");
	
	/**app icon */
	public static final ImageIcon ICON_G = new ImageIcon("icons/G.png");
	public static final ImageIcon ICON_K = new ImageIcon("icons/K_l.png");
	public static final ImageIcon ICON_L = new ImageIcon("icons/K_r.png");
	public static final ImageIcon ICON_H = new ImageIcon("icons/H_l.png");
	public static final ImageIcon ICON_J = new ImageIcon("icons/J.png");      //red shell
	public static final ImageIcon ICON_B_L = new ImageIcon("icons/B_l.png");
	public static final ImageIcon ICON_B_R = new ImageIcon("icons/B_r.png");      //red shell
	public static final ImageIcon ICON_F_R = new ImageIcon("icons/F_l.png");      //red shell
	public static final ImageIcon ICON_F_L = new ImageIcon("icons/F_r.png");      //red shell
	public static final ImageIcon ICON_G_L = new ImageIcon("icons/Fly_Goomba.png");      //red shell

	public static final ImageIcon ICON_I = new ImageIcon("icons/firedisc_I.png");
	public static final ImageIcon ICON_S = new ImageIcon("icons/S.png");      //red shell
	
	public static final ImageIcon ICON_Level = new ImageIcon("icons/levelIcon.png");
	public static final ImageIcon ICON_P= new ImageIcon("icons/brett_0.png");
	public static final ImageIcon ICON_Px= new ImageIcon("icons/brett_x.png");
	public static final ImageIcon ICON_Py= new ImageIcon("icons/brett_y.png");
	public static final ImageIcon ICON_BookMark= new ImageIcon("icons/BookMark.png");


	public static final ImageIcon ICON_B = new ImageIcon("icons/brick.png");
	public static final ImageIcon ICON_C = new ImageIcon("icons/C.png");
	public static final ImageIcon ICON_Q = new ImageIcon("icons/Q_S.png");  //Q Questiion bloack with coin
	public static final ImageIcon ICON_W = new ImageIcon("icons/Q_L.png");  //W Questiion bloack with life
	//public static final ImageIcon ICON_P = new ImageIcon("icons/P.png");
	public static final ImageIcon ICON_T = new ImageIcon("icons/tree.png");  //tree
	public static final ImageIcon ICON_R = new ImageIcon("icons/R_B.png");
	public static final ImageIcon ICON_V = new ImageIcon("icons/V_T.png");
	
	
	public static final ImageIcon ICON_2 = new ImageIcon("icons/2.png");  //
	public static final ImageIcon ICON_3 = new ImageIcon("icons/3.png");  // green floor middle
	public static final ImageIcon ICON_g = new ImageIcon("icons/left_g.png");  // 
	public static final ImageIcon ICON_4 = new ImageIcon("icons/4.png");  // 
	public static final ImageIcon ICON_h = new ImageIcon("icons/right_h.png");  // 
	
	public static final ImageIcon ICON_5 = new ImageIcon("icons/5.png");  // grass floor left edge 
	public static final ImageIcon ICON_6 = new ImageIcon("icons/6.png");  //grass floor right edge

	public static final ImageIcon ICON_7 = new ImageIcon("icons/7.png");  //slope grass centre
	public static final ImageIcon ICON_8 = new ImageIcon("icons/8.png");  //slope grass edge
	public static final ImageIcon ICON_9= new ImageIcon("icons/9.png");   //slope
		
	public static final ImageIcon ICON_i = new ImageIcon("icons/left_i.png");  //slope grass centre
	public static final ImageIcon ICON_j = new ImageIcon("icons/mid_j.png");  //slope grass edge
	public static final ImageIcon ICON_k= new ImageIcon("icons/right_k.png");   //slope

	
	public static final ImageIcon ICON_n = new ImageIcon("icons/n.png"); //roundtree left
	public static final ImageIcon ICON_m= new ImageIcon("icons/m.png");  //roundtree right
	
	public static final ImageIcon ICON_a = new ImageIcon("icons/a.png"); //roundtree left
	public static final ImageIcon ICON_b= new ImageIcon("icons/b.png");  //roundtree right
	public static final ImageIcon ICON_q = new ImageIcon("icons/q.png"); //rock left
	public static final ImageIcon ICON_r = new ImageIcon("icons/r.png"); //rock right
	public static final ImageIcon ICON_z = new ImageIcon("icons/z.png"); //stem ttree
	public static final ImageIcon ICON_t = new ImageIcon("icons/t.png"); //pipe top left
	public static final ImageIcon ICON_u = new ImageIcon("icons/u.png");
	public static final ImageIcon ICON_v = new ImageIcon("icons/v.png");
	public static final ImageIcon ICON_w = new ImageIcon("icons/w.png");
	public static final ImageIcon ICON_x = new ImageIcon("icons/x.png");
	public static final ImageIcon ICON_y = new ImageIcon("icons/y.png");  // pipe base right
	public static final ImageIcon ICON_bulb = new ImageIcon("icons/bulb.png");
	public static final ImageIcon ICON_bulbbase = new ImageIcon("icons/bulbbase.png");  
	public static final ImageIcon ICON_info = new ImageIcon("icons/info.png");  // Inf panel
	public static final ImageIcon ICON_spring= new ImageIcon("icons/Spring.png");  // 
	public static final ImageIcon ICON_bluerock = new ImageIcon("icons/blueRock.png");  
	public static final ImageIcon ICON_yellowrock= new ImageIcon("icons/yellowRock.png");  //
	public static final ImageIcon ICON_grayrock= new ImageIcon("icons/grayRock.png");  //
	
	public static final ImageIcon ICON_sandTop= new ImageIcon("icons/sandM.png");  // 
	public static final ImageIcon ICON_sand = new ImageIcon("icons/sand.png");  
	public static final ImageIcon ICON_waterTile = new ImageIcon("icons/water_tile.png");  
	public static final ImageIcon ICON_waterPlant = new ImageIcon("icons/water_plant.png");  
	public static final ImageIcon ICON_bush = new ImageIcon("icons/bush.png");  

	public static final ImageIcon ICON_virus= new ImageIcon("icons/virus.png");  // 
	public static final ImageIcon ICON_blueFish = new ImageIcon("icons/blueFish.png");  
	public static final ImageIcon ICON_redFish = new ImageIcon("icons/redFish.png");  
	public static final ImageIcon ICON_jumpingFish = new ImageIcon("icons/jumpingFish.png");  

	public static final ImageIcon ICON_fireTop = new ImageIcon("icons/FireTop.png");  
	public static final ImageIcon ICON_fireBase = new ImageIcon("icons/FireBase.png");  
	public static final ImageIcon ICON_fireBrickLeft = new ImageIcon("icons/FireBrickLeft.png");  
	public static final ImageIcon ICON_fireBrickRight = new ImageIcon("icons/FireBrickRight.png");  
	public static final ImageIcon ICON_Girder = new ImageIcon("icons/girder.png");  


}
