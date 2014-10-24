package hkust.cse.calendar.gui.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;

public class PrimLoginView extends BaseLoginView implements ActionListener {
	private JTextField userName;
	private JPasswordField password;
	private JButton button;
	private JButton closeButton;
	private JButton signupButton;
	
	public PrimLoginView() {
		setTitle("Log in");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input your user name and password to log in."));
		top.add(messPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("User Name:"));
		userName = new JTextField(15);
		namePanel.add(userName);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password:  "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);
		
		JPanel signupPanel = new JPanel();
		signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
		signupButton = new JButton("Sign up now");
		signupButton.addActionListener(this);
		signupPanel.add(signupButton);
		top.add(signupPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button = new JButton("Log in (No user name and password required)");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	@Override
	public void fireEvent(LoginControllerEvent e) {
		String command = e.getCommand();
		if(command.equals("START")) {
			setVisible(true);
		}
		else if(command.equals("LoginPending")) {
			button.setEnabled(false);
			button.setText("Logging in...");
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button)
		{
			// When the button is clicked, check the user name and password, and try to log the user in
			
			LoginViewEvent ev = new LoginViewEvent(this);
			ev.setCommand("Login");
			ev.setUsername(userName.getText());
			ev.setPassword(new String(password.getPassword()));
			
			triggerLoginViewEvent(ev);
		}
		else if(e.getSource() == signupButton)
		{
			// Create a new account
		}
		else if(e.getSource() == closeButton)
		{
			int n = JOptionPane.showConfirmDialog(null, "Exit Program ?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				System.exit(0);			
		}
	}
}