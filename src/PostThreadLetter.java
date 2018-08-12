import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class PostThreadLetter implements Runnable {
	
	private static boolean firstPassed = false;
	Semaphore boardSem;
	BoardField owningField;
	ArrayList<BoardField> fieldsList;
	BoardPanel board;
	private static final int right = 1;
	private static final int left = -1;
	private static final int up = -21;
	private static final int down = 21;
	public int headTo;

	

	
	public PostThreadLetter(BoardField owningField, ArrayList<BoardField> fieldsList, BoardPanel board) {
		this.owningField = owningField; 
		this.fieldsList = fieldsList;
		this.board = board;	this.boardSem=board.boardSem;
		try {
			owningField.FieldSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		owningField.occupied=1;
		owningField.readImage();
		owningField.FieldSem.release();
	}

	void move(int direction) throws InterruptedException
	{
		
		Thread.sleep(owningField.passingTime);
		{
		
		if(owningField.getClass() == board.windowsList.get(0).getClass()) 
			((PostWindow)owningField).postIndex--;
		}
		try {
			fieldsList.get(owningField.index+direction).FieldSem.acquire(); //zajêcie nastêpnego semaphora
			owningField.FieldSem.release();
			owningField.occupied=0;
			owningField.readImage();
			
			owningField = fieldsList.get(owningField.index+direction); //przypisanie nowego jako zajêty
			owningField.occupied=1;
			owningField.readImage();
			
			repaintBoard();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public void run() {

		try {
			boardSem.acquire();
			setHeadTo();
			boardSem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			
			path();
			repaintBoard();

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	
	}
	
	protected void path() throws InterruptedException{
		int goLeft=19-(2*headTo);
		for(int i=0;i<goLeft;i++) {move(left); if(owningField.index==421) i=goLeft;}
		for(int i=0;i<20;i++) if(i!=17)move(up); else 
				{move(up);
				try{
					((PostWindow)owningField).queue--;
					
				}catch(Exception e) {
					break;
				}
				}
		
		firstPassed=true;
		owningField.occupied=0;
		owningField.readImage(); // zakoñczenie drogi
		repaintBoard();
		owningField.FieldSem.release();
	}
	

	protected void repaintBoard() {
		try {
			boardSem.acquire();
			board.repaint();
			boardSem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setHeadTo() {
		PostWindow smallestQueue = board.windowsList.get(0);
		resetPostID();
		
			for(PostWindow i: board.windowsList) {
			//System.out.print(" "+smallestQueue.queue+"|"+i.queue);		
			if(i.postIndex<PostWindow.activeNumber)	
			if(smallestQueue.queue>i.queue) smallestQueue = i;
			}
			
			//System.out.print("sQ.ID: "+smallestQueue.postIndex);
			headTo=smallestQueue.postIndex;
			if(headTo>=0)board.windowsList.get(headTo).queue++;
			else board.windowsList.get(0).queue++;
			//System.out.println(""+" size"+board.windowsList.size());
			
	}
	
	protected void resetPostID() {
		boolean doIt=false;
		int j=0;
		for(PostWindow i: board.windowsList)
			if(i.postIndex<0)doIt=true;
		
		for(PostWindow i: board.windowsList) {
			i.postIndex=j;
			j++;
		}
	}
	
}
