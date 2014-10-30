package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.TimeMachineControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent.Command;
import hkust.cse.calendar.utils.GenListener;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseTimeMachineView extends JPanel implements GenListener<TimeMachineControllerEvent>
{
	public static class TimeMachineViewEvent extends EventObject {
		public enum Command {
			PLACEHOLDER,
		};
		private Command command;
		public TimeMachineViewEvent(Object source) {
			super(source);
		}
		
		public TimeMachineViewEvent(Object source, Command command) {
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

	@Override
	abstract public void fireEvent(TimeMachineControllerEvent e);
}

