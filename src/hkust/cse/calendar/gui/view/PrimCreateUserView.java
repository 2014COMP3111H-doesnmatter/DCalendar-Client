package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.CreateUserControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseCreateUserView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.model.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

public class PrimCreateUserView extends BaseCreateUserView implements ActionListener {
	JTextField usernameF, fullnameF, emailF;
	JPasswordField passwordF;
	
	JButton signBtn, cancelBtn;
	
	public PrimCreateUserView() {
		Container contentPane;
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CreateUserViewEvent ev = new CreateUserViewEvent(this);
				ev.setCommand(CreateUserViewEvent.Command.CLOSE);
				triggerCreateUserViewEvent(ev);
			}
		});
		
		setTitle("Sign Up");
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0, 1, 0, 10));
		top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		top.add(new JLabel("Please enter your user information"));

		Border defaultBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		usernameF = new JTextField(15);
		usernameF.setUI(new HintTextFieldUI("   User Name", false, Color.LIGHT_GRAY));
		usernameF.setBorder(defaultBorder);
		top.add(usernameF);
		
		passwordF = new JPasswordField(15);
		passwordF.setUI(new HintTextFieldUI("   Password", false, Color.LIGHT_GRAY, new BasicPasswordFieldUI()));
		passwordF.setBorder(defaultBorder);
		top.add(passwordF);

		emailF = new JTextField(15);
		emailF.setUI(new HintTextFieldUI("   Email", false, Color.LIGHT_GRAY));
		emailF.setBorder(defaultBorder);
		top.add(emailF);

		fullnameF = new JTextField(15);
		fullnameF.setUI(new HintTextFieldUI("   Full Name", false, Color.LIGHT_GRAY));
		fullnameF.setBorder(defaultBorder);
		top.add(fullnameF);
		
		contentPane.add(top, BorderLayout.CENTER);
		

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.X_AXIS));
		
		signBtn = new JButton("Sign up");
		signBtn.addActionListener(this);
		butPanel.add(signBtn);
		
		butPanel.add(Box.createGlue());
		

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		butPanel.add(cancelBtn);
		
		butPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		contentPane.add(butPanel, BorderLayout.SOUTH);
		
		
		pack();
		this.setVisible(false);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		CreateUserViewEvent ev = new CreateUserViewEvent(this);
		if(obj == signBtn) {
			ev.setCommand(CreateUserViewEvent.Command.CREATE);
			User u = new User();
			u.setUsername(usernameF.getText());
			u.setEmail(emailF.getText());
			u.setFullname(fullnameF.getText());
			ev.setUser(u);
			ev.setPassword(String.valueOf(passwordF.getPassword()));
			triggerCreateUserViewEvent(ev);
		}
		else if(obj == cancelBtn) {
			ev.setCommand(CreateUserViewEvent.Command.CLOSE);
			triggerCreateUserViewEvent(ev);
		}

	}

	@Override
	public void fireEvent(CreateUserControllerEvent e) {
		CreateUserControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			this.setVisible(true);
		}

	}
	
}
