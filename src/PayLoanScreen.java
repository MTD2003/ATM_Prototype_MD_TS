/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Pay Loan Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/21
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class PayLoanScreen extends Screen {
	JTextField _paymentField;
	JButton _payButton;
	JLabel _enterLabel, _optionLabel;
	JComboBox<String> _optionBox;
	private final static String[] EMPTY_BOX = {"N/A"};
	
	public PayLoanScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a PayLoanScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The PayLoanScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_enterLabel = new JLabel("Enter the amount you would like to pay: ");
		_optionLabel = new JLabel("Select the loan.");
		_payButton = new JButton("Pay Loan");
		_paymentField = new JTextField();
		_optionBox = new JComboBox<String>(EMPTY_BOX);
		
		_enterLabel.setBounds(90, 200, 280, 30);
		_optionLabel.setBounds(370, 200, 200, 30);
		_paymentField.setBounds(90, 230, 220, 30);
		_payButton.setBounds(90, 270, 120, 40);
		_optionBox.setBounds(370, 230, 240, 30);
		
		_payButton.addActionListener(this);
		
		add(_enterLabel);
		add(_optionLabel);
		add(_paymentField);
		add(_payButton);
		add(_optionBox);
	}
	
	public void screenUpdate(boolean sActivate) {
		/*
		 * Description: Updates the screen visibility, as well as the usability of buttons and elements in the combobox.
		 * Preconditions: User must be logged-in, _atmSystem must exist.
		 * Post-conditions: The visibility is set. If the user has outstanding loans, the button and textbox are enabled,
		 * and the combobox is filled with strings correlating to the user's loans. Otherwise, the elements are disabled.
		 */
		super.screenUpdate(sActivate);
		
		remove(_optionBox); // Removes old optionBox.
		
		int loans = _atmSystem.countCurrentLoans();
		if(loans > 0) {
			String[] optionStrings = _atmSystem.grabCurrentLoanString();
			_optionBox = new JComboBox<String>(optionStrings);
			
			_paymentField.setEnabled(true);
			_payButton.setEnabled(true);
			_optionLabel.setText("Select the loan.");
		} else {
			_optionBox = new JComboBox<String>(EMPTY_BOX);
			
			_paymentField.setEnabled(false);
			_payButton.setEnabled(false);
			_optionLabel.setText("No available loans.");
		}
		
		_optionBox.setBounds(370, 230, 240, 30);
		add(_optionBox); // Replaces with the new optionbox with more accurate indexes.
		
		this.updateUI(); // Updates to remove the old screen (lags a little without). Not necessary on other screens.
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Communicates with the ATM to send a loan payment based on inputs.
		super.actionPerformed(sEvent);
		
		if(_payButton.equals(sEvent.getSource())) { // Only performs action if pay loan button was pressed.
			String choppedDollar = _paymentField.getText().replaceFirst("\\$", ""); // Chops off dollar symbols if given at the start of the input.
			
			try {
				int index = _optionBox.getSelectedIndex();
				double payment = DecimalFormat.getNumberInstance().parse(choppedDollar).doubleValue();
				
				_atmSystem.payLoan(index, payment);
				
			} catch(Exception sExcept) {
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_paymentField.setText("");
			}
		}
	}
}
