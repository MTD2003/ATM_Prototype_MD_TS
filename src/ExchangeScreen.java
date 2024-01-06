/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Exchange Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/09
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ExchangeScreen extends Screen {
	private JLabel _exchangeLabel;
	private JLabel _minMaxLabel;
	private JTextField _usdField;
	private JButton _exchangeButton;
	
	public ExchangeScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a ExchangeScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The ExchangeScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_exchangeLabel = new JLabel("Enter the USD you'd like to exchange:");
		_minMaxLabel = new JLabel("($3.65 min to $200.00 max)");
		_usdField = new JTextField();
		_exchangeButton = new JButton("Exchange to CAD (1.37x rate)");
		
		_exchangeLabel.setBounds(160, 200, 220, 30);
		_usdField.setBounds(380, 200, 160, 30);
		_exchangeButton.setBounds(160, 240, 380, 30);
		_minMaxLabel.setBounds(270, 270, 180, 30);
		
		_exchangeButton.addActionListener(this);
		
		add(_exchangeLabel);
		add(_minMaxLabel);
		add(_usdField);
		add(_exchangeButton);
		
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Signals to the ATM to perform the deposit function.
		super.actionPerformed(sEvent);
		
		if(_exchangeButton.equals(sEvent.getSource())) { // Only performs action if exchange button was pressed.
			String choppedDollar = _usdField.getText().replaceFirst("\\$", ""); // Chops off dollar symbols if given at the start of the input.
			
			try {
				double exchangeValue = DecimalFormat.getNumberInstance().parse(choppedDollar).doubleValue();
				
				_atmSystem.exchangeMoney(exchangeValue);
			} catch(Exception sExcept) {
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_usdField.setText("");
			}
		}
	}
}
