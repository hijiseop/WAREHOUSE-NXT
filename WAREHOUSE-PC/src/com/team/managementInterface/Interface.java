package com.team.managementInterface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class Interface extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -273352886151735237L;
	private JPanel contentPane;
	private JLabel lblRobot = new JLabel("Robot 1:");
	private JButton robotStartButton1 = new JButton("Start");
	private JButton robotCancelButton1 = new JButton("Cancel");
	private InterfaceBackground client = new InterfaceBackground();
	private JTextArea textArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(textArea);
	private JButton btnStop = new JButton("Stop");
	private String condition ="stop";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		new Interface();

	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		textArea.setEditable(false);
		setTitle("Warehouse Management Interface");
		//content Panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 457);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
	
		
		//GroupLayout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRobot)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(robotStartButton1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(robotCancelButton1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStop))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRobot)
						.addComponent(robotStartButton1)
						.addComponent(robotCancelButton1)
						.addComponent(btnStop))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(280, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		

		robotStartButton1.addActionListener(this);
		robotCancelButton1.addActionListener(this);
			setVisible(true);
		client.setGui(this);
		client.connect();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		String msg = "\n";
		textArea.append(msg);
		System.out.println(msg);
			
		if(e.getSource()==robotStartButton1){
			//condition = "start";
		}
		}

	public void appendMsg(String msg) {
		textArea.append(msg);
	}

}
