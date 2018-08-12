import java.util.ArrayList;

public class PostThreadTransfer extends PostThreadLetter{
	PostThreadTransfer(BoardField owningField, ArrayList<BoardField> fieldList, BoardPanel board) {
		
		super(owningField, fieldList, board);
		owningField.occupied=3;
		owningField.readImage();

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
	private void setHeadTo() {
		PostWindow smallestQueue = board.windowsList.get(1);
		resetPostID();
		
			for(PostWindow i: board.windowsList) {
			//System.out.print(" "+smallestQueue.queue+"|"+i.queue);		
			if(i.postIndex<PostWindow.activeNumber)	
			if(smallestQueue.queue>i.queue&&i.postIndex%2==1) smallestQueue = i;
			}
			
			//System.out.print("sQ.ID: "+smallestQueue.postIndex);
			headTo=smallestQueue.postIndex;
			if(headTo>=0)board.windowsList.get(headTo).queue++;
			else board.windowsList.get(0).queue++;
			//System.out.println(""+" size"+board.windowsList.size());
			
	}
	void move(int direction) throws InterruptedException
	{
		
		Thread.sleep((long) (owningField.passingTime*0.5));
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
			owningField.occupied=3;
			owningField.readImage();
			
			repaintBoard();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
