/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Login Screen
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/07
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginScreen extends Screen { // GUI Login Screen.
	private JLabel _nameLabel;
	private JLabel _passcodeLabel;
	private JTextField _nameField;
	private JPasswordField _passcodeField;
	private JButton _loginButton;
	
	public LoginScreen(ATM sATMSystem) { 
		/*
		 * Description: Creates a LoginScreen object connected to the parameter ATM.
		 * Preconditions: sATMSystem must be a valid ATM object.
		 * Post-conditions: The LoginScreen object is created with no back button.
		 */
		super(sATMSystem, false);
		
		_nameLabel = new JLabel("Account ID: ");
		_passcodeLabel = new JLabel("Passcode: ");
		_nameField = new JTextField();
		_passcodeField = new JPasswordField();
		_loginButton = new JButton("Login");
				
		_nameLabel.setBounds(210, 140, 80, 30);
		_passcodeLabel.setBounds(210, 180, 80, 30);
		_nameField.setBounds(310, 140, 160, 30);
		_passcodeField.setBounds(310, 180, 160, 30);
		_loginButton.setBounds(210, 230, 260, 40);
		
		_loginButton.addActionListener(this);
		
		add(_nameLabel);
		add(_passcodeLabel);
		add(_nameField);
		add(_passcodeField);
		add(_loginButton);
	}
	
	public void actionPerformed(ActionEvent sEvent) { // The Login Button. Sends signal to ATM and then operates based on received signal.
		try { // User inputted data is prone to errors.
			int userID = Integer.parseInt(_nameField.getText());
			int passcode = Integer.parseInt(_passcodeField.getText()); // Using getText() for the first version. Later versions will use getPassword().
			int response = _atmSystem.tryLogin(userID, passcode);
			
			switch(response) {
				case 0: // Logs in.
					JOptionPane.showMessageDialog(_atmSystem, "Login Successful!");
					_nameField.setText(""); // Account ID is cleared on login for security reasons.
					_atmSystem.setScreen(ATM.WELCOME);
					break;
				case 1: // Informs user of invalid password.
					JOptionPane.showMessageDialog(_atmSystem, "Incorrect Passcode.");
					break;
				case 2: // Informs user of incorrect account ID.
					JOptionPane.showMessageDialog(_atmSystem, "Invalid Account ID.");
					break;
			}
		
		} catch(Exception sExcept) {
			JOptionPane.showMessageDialog(_atmSystem, "ERROR! Invalid input.", "Error!", JOptionPane.ERROR_MESSAGE);
		} finally {
			_passcodeField.setText(""); // For security reasons, the password is cleared upon execution of the password check.
		}
	}
}
