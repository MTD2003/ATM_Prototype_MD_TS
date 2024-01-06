/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Transfer Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/09
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class TransferScreen extends Screen { // GUI Transfer Screen.
	private JLabel _accountLabel;
	private JLabel _amountLabel;
	private JTextField _accountField;
	private JTextField _amountField;
	private JButton _transferButton;
	
	public TransferScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a TransferScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The TransferScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_accountLabel = new JLabel("Input the Account ID you'd like to transfer to: ");
		_amountLabel = new JLabel("Input the amount of money you'd like to transfer: ");
		_accountField = new JTextField();
		_amountField = new JTextField();
		_transferButton = new JButton("Send Money");
		
		_accountLabel.setBounds(110, 170, 260, 30);
		_amountLabel.setBounds(90, 210, 280, 30);
		_accountField.setBounds(370, 170, 170, 30);
		_amountField.setBounds(370, 210, 170, 30);
		_transferButton.setBounds(410, 270, 130, 40);
		
		_transferButton.addActionListener(this);
		
		add(_accountLabel);
		add(_amountLabel);
		add(_accountField);
		add(_amountField);
		add(_transferButton);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Signals to the ATM to perform a transaction.
		super.actionPerformed(sEvent);
		String targetAmount = _amountField.getText().replaceFirst("\\$", ""); // Chops off dollar symbols if given at the start of the input.
		
		if (!_backButton.equals(sEvent.getSource())) { // Doesn't perform action if back button was pressed.
			try {
				double realTransfer = DecimalFormat.getNumberInstance().parse(targetAmount).doubleValue();
				int targetAccount = Integer.parseInt(_accountField.getText());
				
				_atmSystem.transferMoney(targetAccount, realTransfer);
			} catch(Exception sExcept) {
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_accountField.setText("");
				_amountField.setText("");
			}
		}
	}
}
