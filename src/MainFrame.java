import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class MainFrame extends JFrame {

	private BoardPanel contentPane;
	private JPanel menuPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**--
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 21*30+17+300, 22*30+10);
		//this.setLayout(null);
		
		contentPane = new BoardPanel();
		menuPane = new MenuPanel(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		menuPane.setLocation(21*30+17, 0);
		add(menuPane);
		add(contentPane);
		
		

	}

}
