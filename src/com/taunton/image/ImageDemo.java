package com.taunton.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class ImageDemo {
public static void main(String[] args) {
	//图片缓冲区--画布
	BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_INT_BGR);
	//画笔
	Graphics2D gh = (Graphics2D) bi.getGraphics();
	gh.fillOval(0, 0, 300, 300);
	gh.drawString("aaaaa", 50, 10);
	
}
}
