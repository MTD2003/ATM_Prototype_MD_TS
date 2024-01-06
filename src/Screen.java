/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, The base Screen object.
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/07
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class Screen extends JPanel implements ActionListener { // Abstract GUI screen that acts a parent to all other screens.
	protected ATM _atmSystem; // Protected so that children can access it.
	protected JButton _backButton;
	// Buttons and the like are not accessed with setters/getters as they would bloat the amount of code for a relatively useless function.
	
	public Screen(ATM sATMSystem, boolean sUseBack) { // Base screen implements a back button due to its commonality in most screens.
		/*
		 * Description: Basic constructor for the Screen object, with an optional back button.
		 * Preconditions: sATMSystem must be a valid ATM object and sUseBack must be a valid boolean.
		 * Post-conditions: The Screen object is created, remaining invisible.
		 */
		_atmSystem = sATMSystem;
		setLayout(null);
		setVisible(false);
		
		if(sUseBack) {
			_backButton = new JButton("Back");
			_backButton.setBounds(20, 20, 80, 30); // Back button always in the top left.
			_backButton.addActionListener(this);
			add(_backButton);
		}
	}
	
	public void screenUpdate(boolean sActivate) {
		/*
		 * Description: Used to set the screen visibility and update other visual components. Mainly here for optional inheritance.
		 * Preconditions: Parameter must be a valid boolean.
		 * Post-conditions: The screens visibility is set based on the boolean.
		 */
		setVisible(sActivate);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Performs button actions. Implemented by ActionListener, triggered on button press.
		if(_backButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.WELCOME); // Back Button will only ever send you back to the welcome screen. Shortcut.
		}
	}
	
	public ATM getATM() { // Getter for _atmSystem.
		return _atmSystem;
	}
	
	public void setATM(ATM sATMSystem) { // Setter for _atmSystem.
		_atmSystem = sATMSystem;
	}
}
