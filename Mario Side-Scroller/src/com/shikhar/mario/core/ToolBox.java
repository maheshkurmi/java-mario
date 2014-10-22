package com.shikhar.mario.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.shikhar.mario.editor.Tool;


public class ToolBox {

	private ArrayList<Tool> tools;
	private int focus = -1;

	public ToolBox() {
		tools = new ArrayList<Tool>();
	}

	public void addTool(Tool tool){
		tools.add(tool);
	}
	
	public int hitTest(int x, int y) {
		for (int i = 0; i < tools.size(); i++) {
			if (tools.get(i).contains(x, y)) {
				focus = i;
				break;
			}
		}
		return focus;
	}

	public void draw(Graphics g, int x, int y) {
		Rectangle r;
		for (int i = 0; i < tools.size(); i++) {
			r = tools.get(i).getRect();
			g.setColor((focus == i) ? Color.yellow : Color.black);
			g.drawRect(x + r.x, y+r.y, x + r.x + r.width, y + r.y + r.height);
			//g.drawImage(tools.get(i).getImage(), x + r.x + 1, y + r.y + 1, x+ r.x + r.width - 1, y + r.y + r.width - 1, null);
			x+=(r.width+5);
		}
	}
}
