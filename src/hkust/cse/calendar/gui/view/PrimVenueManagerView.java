package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.VenueManagerControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;
import hkust.cse.calendar.model.Venue;

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

public class PrimVenueManagerView extends BaseVenueManagerView implements ActionListener {
	
	private JList<Venue> userList;
	private DefaultListModel<Venue> listModel;
	
	private JButton addBtn, changeBtn, cancelBtn, deleteBtn;
	
	public PrimVenueManagerView() {
		setTitle("Venue Management");
		setLayout(new BorderLayout());
		listModel = new DefaultListModel<Venue>();
		
		userList = new JList<Venue>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setSelectedIndex(0);
		userList.setVisibleRowCount(8);
		
		JScrollPane listScrollPane = new JScrollPane(userList);
		
		addBtn = new JButton("Create");
		addBtn.addActionListener(this);
		
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
        buttonPane.add(addBtn);
        buttonPane.add(Box.createHorizontalStrut(10));
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
		if(obj == addBtn) {
			VenueManagerViewEvent ev = new VenueManagerViewEvent(this, VenueManagerViewEvent.Command.CREATE);
			ev.setVenue((Venue)userList.getSelectedValue());
			triggerVenueManagerViewEvent(ev);
		}
		if(obj == changeBtn) {
			VenueManagerViewEvent ev = new VenueManagerViewEvent(this, VenueManagerViewEvent.Command.EDIT);
			ev.setVenue((Venue)userList.getSelectedValue());
			triggerVenueManagerViewEvent(ev);
		}
		else if(obj == cancelBtn) {
			VenueManagerViewEvent ev = new VenueManagerViewEvent(this, VenueManagerViewEvent.Command.CLOSE);
			triggerVenueManagerViewEvent(ev);
		}
		else if(obj == deleteBtn) {
			VenueManagerViewEvent ev = new VenueManagerViewEvent(this, VenueManagerViewEvent.Command.DELETE);
			ev.setVenue((Venue)userList.getSelectedValue());
			triggerVenueManagerViewEvent(ev);
		}

	}

	@Override
	public void fireEvent(VenueManagerControllerEvent e) {
		VenueManagerControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			this.setVisible(true);
			break;
		case INFO_UPDATE:
			listModel.clear();
			for(Venue u:e.getaVenue()) {
				listModel.addElement(u);
			}
			repaint();
			break;
		}

	}
	
}
