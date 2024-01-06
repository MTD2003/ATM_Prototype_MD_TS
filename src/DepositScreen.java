/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Deposit Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/08
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.lang.Number;
import java.text.DecimalFormat;

public class DepositScreen extends Screen { // GUI Deposit Screen.
	private JLabel _depositLabel;
	private JTextField _amountField;
	private JButton _depositButton;
	
	public DepositScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a DepositScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The DepositScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_depositLabel = new JLabel("Input Deposit amount ($250 max):");
		_amountField = new JTextField();
		_depositButton = new JButton("Deposit Cash");
		
		_depositLabel.setBounds(160, 200, 200, 30);
		_amountField.setBounds(360, 200, 160, 30);
		_depositButton.setBounds(160, 240, 360, 30);
		
		_depositButton.addActionListener(this);
		
		add(_depositLabel);
		add(_amountField);
		add(_depositButton);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Signals to the ATM to perform the deposit function.
		super.actionPerformed(sEvent);
		
		if (!_backButton.equals(sEvent.getSource())) { // Doesn't perform action if back button was pressed.
			String choppedDollar = _amountField.getText().replaceFirst("\\$", ""); // Chops off dollar symbols if given at the start of the input.
			
			try {
				double realDeposit = DecimalFormat.getNumberInstance().parse(choppedDollar).doubleValue();
				
				_atmSystem.depositMoney(realDeposit);
			} catch(Exception sExcept) {
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_amountField.setText("");
			}
		}
	}
}
