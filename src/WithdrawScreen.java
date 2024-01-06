/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, The base Screen object.
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/09
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WithdrawScreen extends Screen { // GUI Withdraw Screen.
	private JLabel _withdrawLabel;
	private JButton _twentyButton;
	private JButton _fiftyButton;
	private JButton _hundredButton;
	private JButton _hunSixtyButton;
	
	public WithdrawScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a WithdrawScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The WithdrawScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_withdrawLabel = new JLabel("Please select an amount to withdraw:");
		_twentyButton = new JButton("$20.00");
		_fiftyButton = new JButton("$50.00");
		_hundredButton = new JButton("$100.00");
		_hunSixtyButton = new JButton("$165.00");
		
		_withdrawLabel.setBounds(230, 100, 220, 30);
		_twentyButton.setBounds(115, 160, 200, 80);
		_fiftyButton.setBounds(355, 160, 200, 80);
		_hundredButton.setBounds(115, 270, 200, 80);
		_hunSixtyButton.setBounds(355, 270, 200, 80);
		
		_twentyButton.addActionListener(this);
		_fiftyButton.addActionListener(this);
		_hundredButton.addActionListener(this);
		_hunSixtyButton.addActionListener(this);
		
		add(_withdrawLabel);
		add(_twentyButton);
		add(_fiftyButton);
		add(_hundredButton);
		add(_hunSixtyButton);
	}
	
	public void screenUpdate(boolean sActivate) { // Updates the screen to restrict options that would be out of price range.
		/*
		 * Description: Updates the screen, including visibility and the usability of buttons.
		 * Preconditions: User must be logged-in, _atmSystem must exist.
		 * Post-conditions: The visibility of the screen is set, buttons are enabled or disabled based on user's balance.
		 */
		super.screenUpdate(sActivate);
		
		double cashUpdate = _atmSystem.findCurrentBalance();
		
		if(cashUpdate >= 165.00) {
			_twentyButton.setEnabled(true);
			_fiftyButton.setEnabled(true);
			_hundredButton.setEnabled(true);
			_hunSixtyButton.setEnabled(true);
		} else if (cashUpdate >= 100.00) {
			_twentyButton.setEnabled(true);
			_fiftyButton.setEnabled(true);
			_hundredButton.setEnabled(true);
			_hunSixtyButton.setEnabled(false);
		} else if (cashUpdate >= 50.00) {
			_twentyButton.setEnabled(true);
			_fiftyButton.setEnabled(true);
			_hundredButton.setEnabled(false);
			_hunSixtyButton.setEnabled(false);
		} else if (cashUpdate >= 20.00) {
			_twentyButton.setEnabled(true);
			_fiftyButton.setEnabled(false);
			_hundredButton.setEnabled(false);
			_hunSixtyButton.setEnabled(false);
		} else {
			_twentyButton.setEnabled(false);
			_fiftyButton.setEnabled(false);
			_hundredButton.setEnabled(false);
			_hunSixtyButton.setEnabled(false);
		}
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Each button sends a different signal to the ATM to withdraw x amount of money.
		super.actionPerformed(sEvent); // Functionality for the back button.
		
		if(_twentyButton.equals(sEvent.getSource())) {
			_atmSystem.withdrawMoney(20.00);
		} else if(_fiftyButton.equals(sEvent.getSource())) {
			_atmSystem.withdrawMoney(50.00);
		} else if(_hundredButton.equals(sEvent.getSource())) {
			_atmSystem.withdrawMoney(100.00);
		} else if(_hunSixtyButton.equals(sEvent.getSource())) {
			_atmSystem.withdrawMoney(165.00);
		}
	}
}
