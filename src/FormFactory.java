import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStoreException;

public class FormFactory implements CommandListener {

	private Display display;

	private TextBox textBox;

	private List list;

	private Command back;
	private Command add;
	private Command show;

	private Form definedAddForm;
	private ChoiceGroup choiceGroup;

	private Form customAddForm;
	private TextField textField;

	private Form setMeetingForm;
	private TextField setMeetingtextField;
	private DateField dateField;

	private Form addFruitsForm;
	private StringItem apples;
	private TextField applesQuantity;
	private StringItem oranges;
	private TextField orangesQuantity;
	private StringItem pineapples;
	private TextField pineapplesQuantity;

	public FormFactory(Display display, TextBox textBox, List list) {
		add = new Command("Add", Command.SCREEN, 1);
		show = new Command("Show", Command.SCREEN, 1);
		back = new Command("Back", Command.BACK, 1);
		this.display = display;
		this.textBox = textBox;
		this.list = list;
	}

	public Form createCustomAddForm() {
		customAddForm = new Form("Type your description");
		textField = new TextField(null, null, 200, TextField.ANY);
		customAddForm.append(textField);
		setBasicOpitons(customAddForm);
		return customAddForm;
	}

	public Form createDefinedAddForm() {
		definedAddForm = new Form("Select what do You whant to add");
		choiceGroup = new ChoiceGroup(null, ChoiceGroup.EXCLUSIVE);
		choiceGroup.append("Milk", null);
		choiceGroup.append("Water", null);
		choiceGroup.append("Carrot", null);
		choiceGroup.append("Bread", null);
		definedAddForm.append(choiceGroup);
		setBasicOpitons(definedAddForm);
		return definedAddForm;
	}

	public Form createSetMeetingForm() {
		setMeetingForm = new Form("Set your meeting");
		setMeetingtextField = new TextField(null, null, 200, TextField.ANY);
		dateField = new DateField("Pick date", DateField.DATE);
		setMeetingForm.append(setMeetingtextField);
		setMeetingForm.append(dateField);
		setBasicOpitons(setMeetingForm);
		return setMeetingForm;
	}

	public Form createAddWithQuantity() {
		addFruitsForm = new Form("Set with quantity");
		apples = new StringItem("Apples", null);
		applesQuantity = new TextField(null, null, 200, TextField.NUMERIC);
//		oranges;
//		orangesQuantity;
//		pineapples;
//		pineapplesQuantity;
		addFruitsForm.append(apples);
		addFruitsForm.append(applesQuantity);
		addFruitsForm.append(new Spacer(10, 10));
		setBasicOpitons(addFruitsForm);
		return addFruitsForm;
	}

	private void setBasicOpitons(Form form) {
		form.addCommand(back);
		form.addCommand(add);
		form.addCommand(show);
		form.setCommandListener(this);
	}

	private void add(byte[] record) {
		if (record.length > 0) {
			try {
				WelcomWindow.recordStore.addRecord(record, 0, record.length);
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}

	public void commandAction(Command command, Displayable displayable) {
		if (command == add) {
			if (displayable == definedAddForm) {
				definedAddForm.setTitle("Added");
				add(choiceGroup.getString(choiceGroup.getSelectedIndex()).getBytes());
			} else if (displayable == customAddForm) {
				customAddForm.setTitle("Added");
				add(textField.getString().getBytes());
			} else if (displayable == setMeetingForm) {
				setMeetingForm.setTitle("Added");
				String string = setMeetingtextField.getString() + " Date: " + dateField.getDate().toString();
				int dataLength = string.length();
				byte[] saveData = new byte[dataLength];
				for (int i = 0; i < saveData.length; i++) {
					saveData[i] = (byte) string.charAt(i);
				}
				add(saveData);
			}
		} else if (command == show) {
			textBox.setString(WelcomWindow.showToDoList(null));
			display.setCurrent(textBox);
		} else if (command == back) {
			display.setCurrent(list);
		}

	}

}
