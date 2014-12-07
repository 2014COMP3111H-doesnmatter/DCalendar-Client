package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.UserManagerControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseUserManagerView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.model.User;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PrimUserManagerView extends BaseUserManagerView implements ActionListener {
	
	private JList<User> userList;
	private DefaultListModel<User> listModel;
	
	private JButton changeBtn, cancelBtn, deleteBtn;
	
	public PrimUserManagerView() {
		setTitle("User Management");
		setLayout(new BorderLayout());
		listModel = new DefaultListModel<User>();
		
		userList = new JList<User>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setSelectedIndex(0);
		userList.setVisibleRowCount(8);
		
		JScrollPane listScrollPane = new JScrollPane(userList);
		
		
		changeBtn = new JButton("Modify");
		changeBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		
		deleteBtn = new JButton("Remove");
		deleteBtn.addActionListener(this);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                                           BoxLayout.LINE_AXIS));
		
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane.add(changeBtn);
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(deleteBtn);
        buttonPane.add(Box.createGlue());
        buttonPane.add(cancelBtn);
        
        this.add(listScrollPane, BorderLayout.CENTER);
        this.add(buttonPane, BorderLayout.PAGE_END);
        
		pack();
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == changeBtn) {
			UserManagerViewEvent ev = new UserManagerViewEvent(this, UserManagerViewEvent.Command.EDIT);
			ev.setUser((User)userList.getSelectedValue());
			triggerUserManagerViewEvent(ev);
		}
		else if(obj == cancelBtn) {
			UserManagerViewEvent ev = new UserManagerViewEvent(this, UserManagerViewEvent.Command.CLOSE);
			triggerUserManagerViewEvent(ev);
		}
		else if(obj == deleteBtn) {
			UserManagerViewEvent ev = new UserManagerViewEvent(this, UserManagerViewEvent.Command.DELETE);
			ev.setUser((User)userList.getSelectedValue());
			triggerUserManagerViewEvent(ev);
		}

	}

	@Override
	public void fireEvent(UserManagerControllerEvent e) {
		UserManagerControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			this.setVisible(true);
			break;
		case INFO_UPDATE:
			listModel.clear();
			for(User u:e.getaUser()) {
				listModel.addElement(u);
			}
			break;
		}

	}
	
}
