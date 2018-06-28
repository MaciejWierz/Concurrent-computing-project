import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

public class BoardField {

	 int occupied = 0;
	 int size=30;
	 int x,y,index;
	 Image img;
	 BoardPanel owner;
	 Semaphore FieldSem= new Semaphore(1);
	 public int passingTime = 500;
	
	BoardField(int x, int y, BoardPanel owner, int index){
		this.x=x;	this.owner=owner;
		this.y=y;	this.index=index;
		readImage();

	}
	
	void readImage(){
		try {
			if(occupied==0)img = ImageIO.read(new File("Images/0.bmp"));
			else if(occupied==2)img = ImageIO.read(new File("Images/paczka.bmp"));
			else if(occupied==1)img = ImageIO.read(new File("Images/list.bmp"));
		}catch(IOException e) {
			System.err.println(e);
		}
	}
	public void drawField(Graphics g)
	{
		g.drawImage(img, x*size, y*size, size, size, owner);
	}
}
