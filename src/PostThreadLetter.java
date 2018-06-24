import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class PostThreadLetter implements Runnable {
	
	Semaphore boardSem;
	BoardField owningField;
	ArrayList<BoardField> fieldsList;
	BoardPanel board;
	private static final int right = 1;
	private static final int left = -1;
	private static final int up = -21;
	private static final int down = 21;
	public int headTo;
	
	PostThreadLetter(BoardField owningField, ArrayList<BoardField> fieldList, BoardPanel board, int headTo ){
		this.owningField = owningField; this.headTo=headTo;
		this.fieldsList = fieldList;
		this.board = board;	this.boardSem=board.boardSem;
		try {
			owningField.FieldSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		owningField.occupied=1;
		owningField.readImage();
		
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
			path();
			repaintBoard();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}
	
	void path() throws InterruptedException{
		int goLeft=19-(2*headTo);
		
		for(int i=0;i<goLeft;i++) move(left);
		for(int i=0;i<20;i++) move(up);
		
		
		owningField.occupied=0;
		owningField.readImage(); // zakoñczenie drogi
		repaintBoard();
		owningField.FieldSem.release();
	}
	

	private void repaintBoard() {
		try {
			boardSem.acquire();
			board.repaint();
			boardSem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
