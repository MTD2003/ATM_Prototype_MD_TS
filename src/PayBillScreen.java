/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Pay Bill Screen
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

public class PayBillScreen extends Screen { // GUI Pay Bill Screen.
	private JLabel _accountLabel;
	private JLabel _amountLabel;
	private JTextField _accountField;
	private JTextField _amountField;
	private JButton _payButton;
	
	public PayBillScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a PayBillScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The PayBillScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_accountLabel = new JLabel("Input the ID of the Payee you'd like to transfer to: ");
		_amountLabel = new JLabel("Input the amount of money you'd like to transfer: ");
		_accountField = new JTextField();
		_amountField = new JTextField();
		_payButton = new JButton("Pay");
		
		_accountLabel.setBounds(85, 190, 300, 30);
		_amountLabel.setBounds(90, 230, 300, 30);
		_accountField.setBounds(390, 190, 170, 30);
		_amountField.setBounds(390, 230, 170, 30);
		_payButton.setBounds(430, 290, 130, 40);
		
		_payButton.addActionListener(this);
		
		add(_accountLabel);
		add(_amountLabel);
		add(_accountField);
		add(_amountField);
		add(_payButton);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Communicates with the ATM to initiate the payment of a bill based on inputs.
		super.actionPerformed(sEvent);
		String choppedPayment = _amountField.getText().replaceFirst("\\$", "");
		
		if (!_backButton.equals(sEvent.getSource())) { // Doesn't perform action if back button was pressed.
			try {
				double truPayment = DecimalFormat.getNumberInstance().parse(choppedPayment).doubleValue();
				int truPayee = Integer.parseInt(_accountField.getText());
				
				_atmSystem.payBill(truPayee, truPayment);
			} catch(Exception sExcept) {
				System.out.println(sExcept.getMessage());
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_accountField.setText("");
				_amountField.setText("");
			}
		}
	}
}
