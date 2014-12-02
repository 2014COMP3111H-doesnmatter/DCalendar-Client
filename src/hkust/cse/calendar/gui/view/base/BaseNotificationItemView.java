package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JPanel;

public abstract class BaseNotificationItemView extends JPanel {
	private List<GenListener<NotificationItemViewEvent>> aListener = new ArrayList<GenListener<NotificationItemViewEvent>>();

	public BaseNotificationItemView(String title, String content) {
	}
	
	public BaseNotificationItemView(String title, String content, String iconFile) {
	}
	
	public void addNotificationItemEventListener(GenListener<NotificationItemViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerNotificationItemViewEvent(NotificationItemViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class NotificationItemViewEvent extends EventObject {
		public enum Command {
			CLOSE,
			ACTIVATE
		};
		private Command command;
		public NotificationItemViewEvent(Object source) {
			super(source);
		}
		
		public NotificationItemViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
	}
}
