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
import javax.swing.SwingUtilities;

import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;

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
				LoginViewEvent ev = new LoginViewEvent(this);
				ev.setCommand(LoginViewEvent.Command.EXIT);
				triggerLoginViewEvent(ev);
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

		button = new JButton("Log in");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	@Override
	public void fireEvent(LoginControllerEvent e) {
		LoginControllerEvent.Command command = e.getCommand();
		if(command == LoginControllerEvent.Command.START) {
			setVisible(true);
		}
		else if(command == LoginControllerEvent.Command.LOGINPENDING) {
			button.setEnabled(false);
			button.setText("Logging in...");
		}
		else if(command == LoginControllerEvent.Command.PROMPT_ERR) {
			button.setEnabled(true);
			button.setText("Log in");
			String errTitle = e.getErrTitle();
			String errText = e.getErrText();
			JOptionPane.showMessageDialog(null, errText, errTitle, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent e) {
		LoginViewEvent ev = new LoginViewEvent(this);
		if(e.getSource() == button)
		{
			// When the button is clicked, check the user name and password, and try to log the user in
			
			ev.setCommand(LoginViewEvent.Command.LOGIN);
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
			if (n == JOptionPane.YES_OPTION) {
				ev.setCommand(LoginViewEvent.Command.EXIT);
				triggerLoginViewEvent(ev);
			}
		}
	}
}