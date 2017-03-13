import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class WelcomWindow extends MIDlet implements CommandListener {

	private Display display;

	private Canvas canvas;

	private Command exit;
	private Command add;
	private Command show;
	private Command showSorted;
	private Command back;

	private Form customAddForm;
	private Form definedAddForm;
	private Form setMeetingForm;
	private Form addFruitsForm;

	private List list;

	private TextBox textBox;

	public static RecordStore recordStore;

	public WelcomWindow() {
		display = Display.getDisplay(this);
		canvas = new DrawImage();
		createCommands();
		createTextBox();
		createList();
		createForms();
	}

	private void createCommands() {
		exit = new Command("Exit", Command.EXIT, 1);
		add = new Command("Add", Command.SCREEN, 1);
		show = new Command("Show", Command.SCREEN, 1);
		canvas.addCommand(exit);
		canvas.addCommand(add);
		canvas.addCommand(show);
	}

	private void createTextBox() {
		textBox = new TextBox("Todo list:", null, 256, 0);
		back = new Command("Back", Command.BACK, 1);
		showSorted = new Command("Show sorted", Command.SCREEN, 1);
		textBox.addCommand(back);
		textBox.addCommand(showSorted);
		textBox.setCommandListener(this);
	}

	private void createForms() {
		FormFactory formFactory = new FormFactory(display, textBox, list);
		customAddForm = formFactory.createCustomAddForm();
		definedAddForm = formFactory.createDefinedAddForm();
		setMeetingForm = formFactory.createSetMeetingForm();
		addFruitsForm = formFactory.createAddWithQuantity();
	}

	private void createList() {
		list = new List("Select what do You whant to add", List.EXCLUSIVE);
		list.append("Custom add", null);
		list.append("Defined add", null);
		list.append("Add meeting", null);
		list.append("Add fruits", null);
		list.addCommand(back);
		list.addCommand(add);
		list.addCommand(show);
		list.setCommandListener(new CommandListener() {

			public void commandAction(Command command, Displayable displayable) {
				if (command == add) {
					int i = list.getSelectedIndex();
					switch (i) {
					case 0:
						display.setCurrent(customAddForm);
						break;
					case 1:
						display.setCurrent(definedAddForm);
						break;
					case 2:
						display.setCurrent(setMeetingForm);
						break;
					case 3:
						display.setCurrent(addFruitsForm);
						break;
					}
				} else if (command == show) {
					textBox.setString(showToDoList(null));
					display.setCurrent(textBox);
				} else if (command == back) {
					display.setCurrent(canvas);
				}

			}
		});

	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		System.err.println("*** Wywo³aone destroyApp ***");
		try {
			recordStore.closeRecordStore();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(canvas);
		canvas.setCommandListener(this);
		openRecordStore();
	}

	private void openRecordStore() {
		try {
			recordStore = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public void commandAction(Command command, Displayable displayable) {
		if (command == exit) {
			try {
				destroyApp(false);
			} catch (MIDletStateChangeException e) {
				e.printStackTrace();
			}
			notifyDestroyed();
		} else if (command == add) {
			display.setCurrent(list);
		} else if (command == show) {
			textBox.setString(showToDoList(null));
			display.setCurrent(textBox);
		} else if (command == showSorted) {
			textBox.setString(showToDoList(new Comparator()));
			display.setCurrent(textBox);
		} else if (command == back) {
			display.setCurrent(list);
		}
	}

	public static String showToDoList(Comparator comparator) {
		RecordEnumeration iterator;
		String allText = "";
		try {
			iterator = recordStore.enumerateRecords(null, comparator, false);
			while (iterator.hasNextElement()) {
				byte[] record = iterator.nextRecord();
				String text = new String(record);
				allText += text + "\n";
			}
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return allText;
	}

}
