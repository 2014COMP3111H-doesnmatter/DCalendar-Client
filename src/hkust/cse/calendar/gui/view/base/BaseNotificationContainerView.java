package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.DesktopNotificationControllerEvent;
import hkust.cse.calendar.utils.GenListener;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public abstract class BaseNotificationContainerView extends JFrame implements GenListener<DesktopNotificationControllerEvent> {

}