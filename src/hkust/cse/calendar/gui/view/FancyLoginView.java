package hkust.cse.calendar.gui.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.border.DropShadowBorder;

import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;

public class FancyLoginView extends BaseLoginView implements ActionListener{
	private JTextField userName;
	private JPasswordField password;
	private JButton loginButton;
	private JButton closeButton;
	private JButton signupButton;
	private JLabel errorText;
	
	public FancyLoginView() {
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
		
		BufferedImage loginLogo = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("login.png");
			loginLogo = ImageIO.read(input);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JPanel picPanel = new JPanel();
		JLabel loginLogoLabel = new JLabel(new ImageIcon(loginLogo));
		picPanel.add(loginLogoLabel);
		
		JPanel messPanel = new JPanel();
		JLabel loginPrompt = new JLabel("Please input your user name and password to log in.");
		loginPrompt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		messPanel.add(loginPrompt);
		top.add(picPanel);
		top.add(messPanel);
		
		//create blue shadow borders
		final DropShadowBorder enterShadow = new DropShadowBorder();
		enterShadow.setShadowColor(Color.getHSBColor((float)0.6, (float)(0.6), (float)(0.9)));
		enterShadow.setShowLeftShadow(true);
		enterShadow.setShowRightShadow(true);
		enterShadow.setShowBottomShadow(true);
		enterShadow.setShowTopShadow(true);
		enterShadow.setCornerSize(3);
		enterShadow.setShadowSize(3);
        
        final DropShadowBorder clickShadow = new DropShadowBorder();
        clickShadow.setShadowColor(Color.getHSBColor((float)0.6, (float)(0.99), (float)(0.9)));
        clickShadow.setShowLeftShadow(true);
        clickShadow.setShowRightShadow(true);
        clickShadow.setShowBottomShadow(true);
        clickShadow.setShowTopShadow(true);
        clickShadow.setCornerSize(3);
        clickShadow.setShadowSize(3);
        
        //create white margin
        final EmptyBorder margin = new EmptyBorder(3, 3, 3, 3);
        
        
        //merge white margin and shadow border
		final CompoundBorder enterBorder = new CompoundBorder(enterShadow, margin);
		final CompoundBorder clickBorder = new CompoundBorder(clickShadow, margin);
		final CompoundBorder defaultBorder = new CompoundBorder(margin, margin);
		
		//set the Textfields to change border upon mouseEnter, mouseExit, focusGain, FocusLost
		MouseListener textFieldMouseListener = new MouseListener (){			
			
			@Override
			public void mouseEntered(MouseEvent e){
				JTextField source = (JTextField) e.getSource();
				if(source.isFocusOwner())
					source.setBorder(clickBorder);
				else
					source.setBorder(enterBorder);
				source.repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
					
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JTextField source = (JTextField) e.getSource();
				if(source.isFocusOwner())
					source.setBorder(clickBorder);
				else
					source.setBorder(defaultBorder);
				source.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		};
		
		FocusListener textFieldFocusListener = new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField) e.getSource();
				source.setBorder(clickBorder);
				source.repaint();
				if (source == password){
					userName.setBorder(defaultBorder);
					userName.repaint();
				}
				else if (source == userName)
				{
					password.setBorder(defaultBorder);
					password.repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				JTextField source = (JTextField) e.getSource();
				source.setBorder(defaultBorder);
			}
		};
		
		JPanel namePanel = new JPanel();
		userName = new JTextField(25);
		userName.setBorder(defaultBorder);
		userName.setUI(new HintTextFieldUI("   User Name", true, Color.LIGHT_GRAY));
		userName.addMouseListener(textFieldMouseListener);
		userName.addFocusListener(textFieldFocusListener);
		namePanel.add(userName);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		password = new JPasswordField(25);
		password.setBorder(defaultBorder);
		password.setUI(new HintTextFieldUI("   Password", true, Color.LIGHT_GRAY));
		password.addMouseListener(textFieldMouseListener);
		password.addFocusListener(textFieldFocusListener);
		pwPanel.add(password);
		top.add(pwPanel);
		
		JPanel errorPanel = new JPanel();
		errorText = new JLabel("");
		errorText.setForeground(Color.RED);
		errorText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		errorPanel.add(errorText);
		
		JPanel loginBtnPanel = new JPanel();
		loginButton = new JButton("Log in");
		loginButton.setBackground(Color.getHSBColor((float)0.6, (float)(0.7), (float)(0.9)));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBorderPainted(false);
		loginButton.addActionListener(this);
		loginButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		loginButton.setPreferredSize(password.getPreferredSize());
		
		//If mouse moves over the button, deepen the background color
		loginButton.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setBackground(Color.getHSBColor((float)0.6, (float)(0.99), (float)(0.9)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setBackground(Color.getHSBColor((float)0.6, (float)(0.8), (float)(0.9)));
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
		
		loginBtnPanel.add(loginButton);
		top.add(loginBtnPanel);
		top.add(errorPanel);
		
		contentPane.add("North", top);
		
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel signupPanel = new JPanel();
		//signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
		signupButton = new JButton("Create an account");
		signupButton.addActionListener(this);
		signupButton.setOpaque(true);
		signupButton.setContentAreaFilled(false);
		signupButton.setBorderPainted(false);
		signupButton.setForeground(Color.getHSBColor((float)0.6, (float)(0.6), (float)(0.9)));
		signupPanel.add(signupButton);
		midPanel.add(signupPanel);
		
		contentPane.add(midPanel);
		
		JPanel butPanel = new JPanel();
		
		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		closeButton.setBackground(Color.WHITE);
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
			loginButton.setEnabled(false);
			loginButton.setText("Logging in...");
		}
		else if(command == LoginControllerEvent.Command.PROMPT_ERR) {
			loginButton.setEnabled(true);
			loginButton.setText("Log in");
			String errTitle = e.getErrTitle();
			String errText = e.getErrText();			
			errorText.setText(errText);
			errorText.repaint();
			//JOptionPane.showMessageDialog(null, errText, errTitle, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent e) {
		LoginViewEvent ev = new LoginViewEvent(this);
		if(e.getSource() == loginButton)
		{
			// When the button is clicked, check the user name and password, and try to log the user in
			
			ev.setCommand(LoginViewEvent.Command.LOGIN);
			ev.setUsername(userName.getText());
			ev.setPassword(new String(password.getPassword()));
			
			triggerLoginViewEvent(ev);
		}
		else if(e.getSource() == signupButton)
		{
			errorText.setText("");
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
