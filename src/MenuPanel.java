import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class MenuPanel extends JPanel implements ActionListener {

	JLabel addClientLabel,letterLabel,packLabel,transferLabel,activeWindowsLabel,
	activeWindowsNumberLabel,addWindowLabel,subWindowLabel;
	JButton letterButton,packButton,transferButton,addWindowButton,subWindowButton;
	BoardPanel board;


	
	
	public MenuPanel(BoardPanel board) {
		this.setBounds(0, 21*30+17, 300, 22*30+10);
		this.board=board;
		setLayout(null);
		
		addClientLabel = new JLabel("Dodaj Klienta:");
		addClientLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addClientLabel.setBounds(10, 11, 156, 30);
		add(addClientLabel);
		
		letterLabel = new JLabel("List");
		letterLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		letterLabel.setBounds(74, 52, 39, 30);
		add(letterLabel);
		
		packLabel = new JLabel("Paczka");
		packLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		packLabel.setBounds(57, 91, 54, 30);
		add(packLabel);
	
		
		transferLabel = new JLabel("Przelew");
		transferLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		transferLabel.setBounds(49, 125, 64, 33);
		add(transferLabel);
		
		letterButton = new JButton("+");
		letterButton.setBounds(123, 52, 54, 30);
		add(letterButton);
		letterButton.addActionListener(this);
		
		packButton = new JButton("+");
		packButton.setBounds(123, 86, 54, 35);
		add(packButton);
		packButton.addActionListener(this);
		
		transferButton = new JButton("+");
		transferButton.setBounds(123, 127, 54, 35);
		add(transferButton);
		transferButton.addActionListener(this);
		
		activeWindowsLabel = new JLabel("Aktywne okienka: ");
		activeWindowsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		activeWindowsLabel.setBounds(10, 188, 173, 49);
		add(activeWindowsLabel);
		
		activeWindowsNumberLabel = new JLabel(""+PostWindow.activeNumber);
		activeWindowsNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		activeWindowsNumberLabel.setBounds(193, 189, 54, 43);
		add(activeWindowsNumberLabel);
		
		addWindowLabel = new JLabel("Dodaj okienko:");
		addWindowLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addWindowLabel.setBounds(20, 248, 126, 30);
		add(addWindowLabel);
		
		subWindowLabel = new JLabel("Zabierz okienko:");
		subWindowLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		subWindowLabel.setBounds(10, 289, 136, 30);
		add(subWindowLabel);
		
		addWindowButton = new JButton("+");
		addWindowButton.setBounds(148, 248, 54, 30);
		add(addWindowButton);
		addWindowButton.addActionListener(this);
		
		subWindowButton = new JButton("-");
		subWindowButton.setBounds(148, 289, 54, 30);
		add(subWindowButton);
		subWindowButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		
		if(letterButton.equals(source)) {
			createLetterThread();
		}
		else if(addWindowButton.equals(source)) {
			handleWindowButtons(true);
		}
		else if(subWindowButton.equals(source)) {
			handleWindowButtons(false);
		}

	}


			private void acquireBoardSem() {
			try {
				board.boardSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
			
			private void createLetterThread(){
				
				acquireBoardSem();
				if(occupiedWindows())
				{				
				Runnable list = new PostThreadLetter(board.fieldsList.get(440),board.fieldsList, board);
				Thread thread = new Thread(list);
				board.boardSem.release();
				thread.start();
				}else board.boardSem.release();
				
				
			}
			
			private boolean occupiedWindows() {
				
				if(board.fieldsList.get(43).occupied==0&&
						board.fieldsList.get(45).occupied==0&&
						board.fieldsList.get(47).occupied==0&&
						board.fieldsList.get(49).occupied==0&&
						board.fieldsList.get(51).occupied==0&&
						board.fieldsList.get(53).occupied==0&&
						board.fieldsList.get(55).occupied==0&&
						board.fieldsList.get(57).occupied==0&&
						board.fieldsList.get(59).occupied==0&&
						board.fieldsList.get(61).occupied==0&&
						board.fieldsList.get(440).occupied==0&&
						board.fieldsList.get(439).occupied==0)
				return true;
				else
				return false;
			}

			private void handleWindowButtons(boolean add_window) {
				if(occupiedWindows()) {
					if(add_window==true&&PostWindow.activeNumber<11)PostWindow.activeNumber++;
					if(add_window==false&&PostWindow.activeNumber>1)PostWindow.activeNumber--;
					activeWindowsNumberLabel.setText(""+PostWindow.activeNumber);
			
					for(PostWindow i: board.windowsList )
					{
						if(i.postIndex<PostWindow.activeNumber)i.active=true;
						else i.active=false;
						i.readImage();
					}
				}
			}
			
}
