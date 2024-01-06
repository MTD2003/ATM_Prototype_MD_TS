/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Welcome Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/07
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WelcomeScreen extends Screen { // GUI Welcome Screen. Acts as a hub to all other screens.
	private JLabel _welcomeLabel;
	private JButton _withdrawButton;
	private JButton _depositButton;
	private JButton _balanceButton;
	private JButton _transferButton;
	private JButton _payeeButton;
	private JButton _loanButton;
	private JButton _payLoanButton;
	private JButton _exchangeButton;
	private JButton _logoutButton;
	
	public WelcomeScreen(ATM sATMSystem) {
		/*
		 * Description: Creates a WelcomeScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The WelcomeScreen object is created with no back button.
		 */
		super(sATMSystem, false);
		
		_welcomeLabel = new JLabel("Welcome!");;
		_withdrawButton = new JButton("Withdraw Cash");
		_depositButton = new JButton("Deposit Money");
		_balanceButton = new JButton("View Balance");
		_transferButton = new JButton("Transfer Money");
		_payeeButton = new JButton("Pay Bill");
		_loanButton = new JButton("Takeout Micro-loan");
		_payLoanButton = new JButton("Pay-off Loan");
		_exchangeButton = new JButton("Exchange USD");
		_logoutButton = new JButton("Log Out");
		
		_welcomeLabel.setBounds(315, 50, 80, 30);
		_withdrawButton.setBounds(110, 100, 200, 50);
		_depositButton.setBounds(370, 100, 200, 50);
		_balanceButton.setBounds(110, 200, 200, 50);
		_transferButton.setBounds(370, 200, 200, 50);
		_payeeButton.setBounds(110, 300, 200, 50);
		_loanButton.setBounds(370, 300, 200, 50);
		_payLoanButton.setBounds(110, 400, 200, 50);
		_exchangeButton.setBounds(370, 400, 200, 50);
		_logoutButton.setBounds(300, 480, 80, 30);
		
		_withdrawButton.addActionListener(this);
		_depositButton.addActionListener(this);
		_balanceButton.addActionListener(this);
		_transferButton.addActionListener(this);
		_payeeButton.addActionListener(this);
		_loanButton.addActionListener(this);
		_payLoanButton.addActionListener(this);
		_exchangeButton.addActionListener(this);
		_logoutButton.addActionListener(this);

		add(_welcomeLabel);
		add(_withdrawButton);
		add(_depositButton);
		add(_balanceButton);
		add(_transferButton);
		add(_payeeButton);
		add(_loanButton);
		add(_payLoanButton);
		add(_exchangeButton);
		add(_logoutButton);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // Each button will take the user to a different screen.
		if(_withdrawButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.WITHDRAW);
		} else if(_depositButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.DEPOSIT);
		} else if(_balanceButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.BALANCE);
		} else if(_transferButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.TRANSFER);
		} else if(_payeeButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.BILL);
		} else if(_loanButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.LOAN);
		} else if(_payLoanButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.PAY_LOAN);
		} else if(_exchangeButton.equals(sEvent.getSource())) {
			_atmSystem.setScreen(ATM.EXCHANGE);
		} else {
			_atmSystem.logout();
		}
	}
}
