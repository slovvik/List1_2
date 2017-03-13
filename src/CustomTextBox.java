import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;

public class CustomTextBox extends TextBox implements CommandListener {
	
	private Command exit;
	private Command back;

	public CustomTextBox(String title, String text, int maxSize, int constraints) {
		super(title, text, maxSize, constraints);
		exit = new Command("Exit", Command.EXIT, 1);
		back = new Command("Back", Command.BACK, 1);
		this.addCommand(exit);
		this.addCommand(back);	
		this.setCommandListener(this);		
	}

	public void commandAction(Command command, Displayable d) {
		 if (command == exit) {
			 
		 }
		
	}

}
