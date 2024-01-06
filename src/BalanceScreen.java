/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, View Balance Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/08
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BalanceScreen extends Screen { // GUI Balance Screen.
	private JLabel _introLabel;
	private JLabel _balanceLabel;
	
	public BalanceScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a BalanceScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The BalanceScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_introLabel = new JLabel("Your current Balance is: ");
		_balanceLabel = new JLabel("$4.04"); // Default value that should never show up. A little error joke.
		_balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		_introLabel.setBounds(280, 200, 140, 30);
		_balanceLabel.setBounds(45, 230, 600, 30);
		
		_balanceLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
		
		add(_introLabel);
		add(_balanceLabel);
	}
	
	public void screenUpdate(boolean sActivate) {
		/*
		 * Description: Updates the screen, including visibility as well as the listed balance.
		 * Preconditions: User must be logged-in, _atmSystem must exist.
		 * Post-conditions: The visibility of the screen is set, the _balanceLabel text is set to the user's current balance.
		 */
		super.screenUpdate(sActivate);
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String cashUpdate = formatter.format(_atmSystem.findCurrentBalance());
		
		_balanceLabel.setText(cashUpdate);
	}
}
