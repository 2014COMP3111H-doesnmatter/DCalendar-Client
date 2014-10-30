package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.ApptListControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent.Command;
import hkust.cse.calendar.utils.GenListener;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseApptListView extends JPanel implements GenListener<ApptListControllerEvent>
{
	public static class ApptListViewEvent extends EventObject {
		public static final class Command {
			public static final int PLACEHOLDER = 0;
		};
		private int command;
		public ApptListViewEvent(Object source) {
			super(source);
		}
		
		public ApptListViewEvent(Object source, int command) {
			super(source);
			this.command = command;
		}
		public int getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
	}

	@Override
	abstract public void fireEvent(ApptListControllerEvent e);
}
