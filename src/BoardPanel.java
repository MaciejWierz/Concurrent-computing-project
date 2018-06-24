import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;


public class BoardPanel extends JPanel {


	ArrayList<BoardField> fieldsList = new ArrayList<BoardField>();
	ArrayList<PostWindow> windowsList = new ArrayList<PostWindow>();
	Semaphore boardSem = new Semaphore(1,true);
	
	public BoardPanel() {
		int postIndex=0;
		for(int i=0;i<21;i++)
			for(int j=0;j<21;j++) 
				if(21*i+j!=43&&21*i+j!=45&&21*i+j!=47&&21*i+j!=49&&21*i+j!=51&&
					21*i+j!=53&&21*i+j!=55&&21*i+j!=57&&21*i+j!=59&&21*i+j!=61)
					fieldsList.add(new BoardField(j,i,this, 21*i+j));	
				else {
					fieldsList.add(new PostWindow(j,i,this, 21*i+j,postIndex));
					windowsList.add((PostWindow) fieldsList.get(21*i+j));
					postIndex++;
					}
			
		repaint();
		
		
		
		


	}

	public void paint(Graphics g) {

		for(BoardField i :fieldsList) i.drawField(g);

		
	}
	
}
