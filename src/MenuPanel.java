import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuPanel extends JPanel implements ActionListener {

	JLabel addClientLabel,letterLabel,packLabel,transferLabel;
	JButton letterButton,packButton,transferButton;
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

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		
		if(letterButton.equals(source)) {
			PostWindow smallestQueue =board.windowsList.get(0);
			acquireBoardSem();
			
			for(int i=1;i<board.windowsList.size();i++ )
				if(board.windowsList.get(i).queue<smallestQueue.queue)
					smallestQueue=board.windowsList.get(i);
			smallestQueue.queue++;
			board.boardSem.release();
			
			Runnable list = new PostThreadLetter(board.fieldsList.get(440),board.fieldsList, board,smallestQueue.postIndex);
			Thread thread = new Thread(list);
			thread.start();
		}


	}
			private void acquireBoardSem() {
			try {
				board.boardSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}
