/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Loan Screen
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
import javax.swing.SwingConstants;

public class LoanScreen extends Screen { // GUI Loan Screen.
	private JLabel _loanLabel;
	private JLabel _outstandingLabel;
	private JTextField _loanField;
	private JButton _loanButton;
	
	public LoanScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a LoanScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The LoanScreen object is created with a back button.
		 */
		super(sATMSystem, true);
		
		_loanLabel = new JLabel("Enter the loan's amount ($100-$1000): ");
		_outstandingLabel = new JLabel("You have no outstanding loans.");
		_loanField = new JTextField();
		_loanButton = new JButton("Take Loan.");
		
		_loanLabel.setBounds(160, 250, 220, 30);
		_loanField.setBounds(380, 250, 160, 30);
		_loanButton.setBounds(160, 300, 380, 30);
		
		_outstandingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_outstandingLabel.setBounds(250, 120, 180, 30);
		
		_loanButton.addActionListener(this);
		
		add(_loanLabel);
		add(_outstandingLabel);
		add(_loanField);
		add(_loanButton);
	}
	
	public void screenUpdate(boolean sActivate) {
		/*
		 * Description: Updates the screen, including the visibility and usability of elements.
		 * Preconditions: The user must be logged-in, _atmSystem must exist.
		 * Post-conditions: Visibility is set based on parameter, functionality is set based on whether or not the user
		 * has any outstanding loans (disabled if they do).
		 */
		super.screenUpdate(sActivate);
		
		if(_atmSystem.countCurrentLoans() < 1) {
			_loanField.setEnabled(true);
			_loanButton.setEnabled(true);
			_outstandingLabel.setText("You have no outstanding loans.");
		} else {
			_loanField.setEnabled(false);
			_loanButton.setEnabled(false);
			_outstandingLabel.setText("You have outstanding loans.");
		}
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Signals to the ATM to attempt loan creation.
		super.actionPerformed(sEvent);
		
		if (!_backButton.equals(sEvent.getSource())) { // Doesn't perform action if back button was pressed.
			String choppedLoan = _loanField.getText().replaceFirst("\\$", ""); // Chops off dollar symbols if given at the start of the input.
			
			try {
				double realLoan = DecimalFormat.getNumberInstance().parse(choppedLoan).doubleValue();
				
				_atmSystem.takeLoan(realLoan);
			} catch(Exception sExcept) {
				System.out.println(sExcept.getMessage());
				JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
			} finally {
				_loanField.setText("");
			}
		}
	}
}
