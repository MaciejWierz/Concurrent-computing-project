import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PostWindow extends BoardField {
	
	public static int activeNumber = 6;
	boolean type = false;//true: L+P, false:L+T
	boolean active = true;
	int queue=0,postIndex;

	PostWindow(int x, int y, BoardPanel owner, int index, int postIndex) {
		super(x, y, owner, index);
		this.postIndex=postIndex;
		if(postIndex%2==0)type=true;
		passingTime=2000;
		
		if(postIndex<PostWindow.activeNumber)active=true;
		else active=false;
		readImage();
	}
	
	void readImage(){
		try {
			if(queue==0&&active==false)
				if(type==true)img = ImageIO.read(new File("Images/L+P_disactive.bmp"));
				else img = ImageIO.read(new File("Images/L+T_disactive.bmp"));
			else if(occupied==0)
				if(type==true)img = ImageIO.read(new File("Images/L+P.bmp"));
				else img = ImageIO.read(new File("Images/L+T.bmp"));
			else 
				if(type==true)img = ImageIO.read(new File("Images/L+P_Occupied.bmp"));
				else img = ImageIO.read(new File("Images/L+T_Occupied.bmp"));
			owner.boardSem.acquire();
			owner.repaint();
			owner.boardSem.release();
		}catch(IOException | InterruptedException e) {
			System.err.println(e);
		}
		
	}
	
	void incQueue(){
		queue++;
	}
}
