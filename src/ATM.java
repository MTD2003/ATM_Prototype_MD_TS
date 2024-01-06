/*
	Course: COMP 2663 FA01, 2023
	Assignment 4, Session Handler
	Author: Michael Doherty & Tye States
	Student ID: 0293019d & 0300326s
	Date: 2023/11/07
	I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class ATM extends JFrame { // Extends JFrame to act as a GUI element and system controller.
	private Bank[] _banks;
	private Screen[] _screens; // No getters or setters for this variable since the screens are fixed.
	private Bank currentSessionBank;
	private Account currentSessionAccount;
	private String _model;
	private int _screenIndex;
	private double _baseFine;
	private float _baseInterest;
	
	// Constants. Represent the screen indexes for the GUI. Public and static for ease of access with setScreen function.
	public final static int LOGIN = 0;
	public final static int WELCOME = 1;
	public final static int WITHDRAW = 2;
	public final static int DEPOSIT = 3;
	public final static int BALANCE = 4;
	public final static int TRANSFER = 5;
	public final static int BILL = 6;
	public final static int LOAN = 7;
	public final static int PAY_LOAN = 8;
	public final static int EXCHANGE = 9;
	
	public ATM(String winName, Bank aBanks[], String aModel, double aFine, float aInterest) { // Constructor. Fills in the ATMs variables.
		/*
		 * Description: Basic constructor for the ATM object.
		 * Preconditions: All parameters must be valid inputs.
		 * Post-conditions: The ATM object is created and the corresponding window appears set to the login screen.
		 */
		super(winName); // Sets the name of the window using the JFrame constructor.
		_banks = aBanks;
		_model = aModel;
		_baseFine = aFine;
		_baseInterest = aInterest;
		
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		_screens = new Screen[] { // The array of screens. Hard-coded GUI elements.
				   new LoginScreen(this), // 0
				   new WelcomeScreen(this), // 1
				   new WithdrawScreen(this), // 2
				   new DepositScreen(this), // 3
				   new BalanceScreen(this), // 4
				   new TransferScreen(this), // 5
				   new PayBillScreen(this), // 6
				   new LoanScreen(this), // 7
				   new PayLoanScreen(this), // 8
				   new ExchangeScreen(this) // 9
		};
		
		setScreen(LOGIN); // Sets default screen to login.
	}
	
	public static void main(String args[]) { // Driver method for testing.
		ArrayList<Account> testAccList = new ArrayList<>();
		ArrayList<Account> testAccList2 = new ArrayList<>();
		ArrayList<Payee> testPayeeList = new ArrayList<>();
		Bank testBankList[] = new Bank[2];
		
		Account testAccount = new Account(140, 1234, "Josh", "Tomar", 5678);
		Account voldeMort = new Account (0, 1500, "Volde", "Mort", 2000);
		testAccList.add(testAccount);
		testAccList2.add(voldeMort);
		
		Bill testBill = new Bill(testAccount, 100);
		Payee testPayee = new Payee(120, 4445, "JOHN", "PLUMBING CO.", 1222);
		testPayee.addAccRec(testBill);
		testPayee.addPayer(testAccount);
		
		testPayeeList.add(testPayee);
		
		testBankList[0] = new Bank(200, testAccList, testPayeeList, "Cool Bank");
		testBankList[1] = new Bank(120900, testAccList2, testPayeeList, "Evil Bank");
		
		ATM machine = new ATM("Digital Banking System 1", testBankList, "TEST", 2.00, (float)1.24);
	}
	
	public static double roundMoney(double aAmount) {
		/*
		 * Description: Takes the input double and rounds it to the nearest 100th (or cent).
		 * Preconditions: The input parameter must be a previously declared double.
		 * Post-conditions: Returns the rounded double, replacing the original double from where the code was called.
		 */
		aAmount *= 100;
		aAmount = Math.floor(aAmount);
		aAmount /= 100;
		
		return aAmount;
	}
	
	public int tryLogin(int aUserID, int aPasscode) { // Checks accounts and passwords and tries to login with them.
		/*
		 * Description: Cross-references the userID and passcode parameters against the accounts within the object's list of banks.
		 * Returns an integer depending on the outcome.
		 * Preconditions: ATM object has been properly created and the parameters are valid integers.
		 * Post-conditions: When returning 0, the user will be logged into the account and the currentSession variables will be set.
		 * When returning 1, the user will be notified that the password is incorrect.
		 * When returning 0, the user will be notified that the account does not exist.
		 */
		Account copyOfAccount = null;
		
		for(int i = 0; i < _banks.length; i++) {
			copyOfAccount = _banks[i].getAccount(aUserID);
			if(copyOfAccount != null) {
				currentSessionBank = _banks[i];
				break;
			}
		}
		
		if(copyOfAccount == null) { // If account wasn't found.
			currentSessionBank = null;
			return 2;
			
		} else if (aPasscode != copyOfAccount.getPasscode()) { // If password is incorrect.
			currentSessionBank = null;
			return 1;
			
		} else { // If account and password exist and are correct.
			currentSessionAccount = copyOfAccount;
			return 0;
			
		}
	}
	
	public void logout() { // Attempts to return the user to the login page.
		/*
		 * Description: Prompts the user to logout.
		 * Preconditions: The user should be logged-in when this method is called.
		 * Post-conditions: The currentSession variables are set to null and the screen is set back to the login.
		 */
		int confirm = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Logout?", JOptionPane.YES_NO_OPTION); // Stores answer.
		
		if(confirm == 0) { // If the user says "Yes", the machine will log them out.
			setScreen(LOGIN);
			currentSessionBank = null; // Clears the previously used bank/accounts for security reasons.
			currentSessionAccount = null;
		}
		
	}
	 
	public void withdrawMoney(double aAmount) {
		/*
		 * Description: Withdraws money from the current user's account, dispensing the corresponding money in bills.
		 * Preconditions: User should be logged-in, parameter should be positive double, account balance should be >= to (aAmount + 2).
		 * Post-conditions: The user's balance is decreased by (aAmount + 2). dispenseMoney(aAmount) method is called.
		 * A receipt object is created and the file is put in the project folder. The user is prompted to logout. Charge is added
		 * to the current bank's funds.
		 */
		if (currentSessionAccount != null) { // Only works if the currentSessionAccount is active.
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String fineWarning = formatter.format(_baseFine);
			aAmount = roundMoney(aAmount);
			
			int fineConfirm = JOptionPane.showConfirmDialog(this, "There is a flat fine of " + fineWarning + "\nContinue?", "Message", JOptionPane.YES_NO_OPTION);
			
			if (fineConfirm == 0) {
				if(currentSessionBank.withdrawal(currentSessionAccount, aAmount + _baseFine)) {
					dispenseMoney((int)aAmount); // Type-casts double to an int for dispensing money.
					JOptionPane.showMessageDialog(this, "Transaction Successful!");
					
					currentSessionBank.modifyFunds(_baseFine); // Charge is added to the banks funds.
					
					Receipt newReceipt = new Receipt("Withdrawal", (aAmount + _baseFine)*-1, currentSessionAccount);
					newReceipt.printReceipt();
					
					_screens[_screenIndex].screenUpdate(true); // Updates the screen to disable buttons.
					logout(); // Machine tries to logout at the end of every successful operation.
				} else {
					JOptionPane.showMessageDialog(this, "ERROR! Charge exceeds balance.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void exchangeMoney(double aUSAmount) {
		/*
		 * Description: Changes aUSAmount in accordance with the conversion rate of USD to CAD, dispensing the corresponding bills.
		 * Charges user in order to make sure the input amount is divisible by 5.
		 * Preconditions: User should be logged-in, parameter should be a positive double between 3.65 and 200.00.
		 * Post-conditions: dispenseMoney(aAmount) method is called. A receipt object is created and the file is put in the project
		 * folder. The user is prompted to logout. Charge is added to the current bank's funds.
		 */
		double leftoverCharge;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		aUSAmount = roundMoney(aUSAmount);
		
		if (currentSessionBank != null) {
			if (aUSAmount >= 3.65 && aUSAmount <= 200.00) { // This function doesn't need to check the bank or account as its just an exchange.
				aUSAmount *= 1.37;
				leftoverCharge = aUSAmount % 5; // Charge is the amount that cannot be converted into bills. A variable convenience fee.
				
				int chargeConfirm = JOptionPane.showConfirmDialog(this, "There is a charge of " + formatter.format(leftoverCharge) + " CAD\nContinue?", "Message", JOptionPane.YES_NO_OPTION);
				
				if (chargeConfirm == 0) {
					dispenseMoney((int) aUSAmount); // Dispenses the amount, don't need to subtract charge as it naturally ignores amounts < 5.
					
					JOptionPane.showMessageDialog(this, "Transaction Successful!");
					currentSessionBank.modifyFunds(leftoverCharge); // Deposits the leftover charge into the bank's funds.
					
					Receipt newReceipt = new Receipt("Exchange", aUSAmount, currentSessionAccount);
					newReceipt.printReceipt();
					
					logout();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Transaction could not be performed.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void dispenseMoney(int aAmount) {
		/*
		 * Description: Prints out the optimal and equivalent number of bills for the input amount.
		 * Preconditions: Called from inside another ATM method, user is logged in, parameter is a positive integer divisible by 5.
		 * Post-conditions: The user's balance is decreased by (aAmount + 2). dispenseMoney(aAmount) method is called.
		 * A receipt object is created and the file is put in the project folder. The user is prompted to logout.
		 */
		String cashString = "Dispensing:\n";
		int twenties, tens, fives;
		
		twenties = aAmount / 20;
		aAmount %= 20;
		
		if(twenties > 0) {
			cashString += twenties + " twenty-dollar bill(s).\n";
		}
		
		tens = aAmount / 10;
		aAmount %= 10;
		
		if(tens > 0) {
			cashString += tens + " ten-dollar bill(s).\n";
		}
		
		fives = aAmount / 5;
		aAmount %= 5;
		
		if(fives > 0) {
			cashString += fives + " five-dollar bill(s).\n";
		}
		
		System.out.println(cashString);
	}
	
	public void depositMoney(double aAmount) { // 
		/*
		 * Description: Deposits money into the current user's account, changing the balance.
		 * Preconditions: User should be logged-in, parameter should be positive double less than 250.00.
		 * Post-conditions: User balance increases by aAmount. A receipt object is created and the file is put in the project
		 * folder. The user is prompted to logout.
		 */
		aAmount = roundMoney(aAmount);
		if(currentSessionAccount != null) {
			if(aAmount >= 0.00 && aAmount <= 250.00) {
				currentSessionBank.deposit(currentSessionAccount, aAmount);
				
				JOptionPane.showMessageDialog(this, "Transaction Successful!");
				
				Receipt newReceipt = new Receipt("Deposit", aAmount, currentSessionAccount);
				newReceipt.printReceipt();
				
				logout();
			} else {
				JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
			}
		} else { // Somewhat superfluous error checking, theoretically impossible to reach this condition. Only through manual calls.
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void transferMoney(int aAccountID, double aAmount) {
		/*
		 * Description: Transfers money between accounts, altering the balance of each.
		 * Preconditions: User should be logged-in, parameter should be positive double, account balance should be >= to aAmount,
		 * aAccountID should correlate to an existing account.
		 * Post-conditions: The user's balance is decreased by aAmount, the recipient's balance is increased by aAmount.
		 * A receipt object is created and the file is put in the project folder. The user is prompted to logout.
		 */
		Account transferAccount = null;
		Bank tempBank = null;
		
		for(int i = 0; i < _banks.length; i++) { // Can transfer money to accounts in other banks. 
			transferAccount = _banks[i].getAccount(aAccountID);
			if(transferAccount != null) {
				tempBank = _banks[i];
				break;
			}
		}
		
		if(currentSessionAccount != null && transferAccount != null) {
			aAmount = roundMoney(aAmount);
			
			if(aAmount > 0.00) { // So negative amounts can't be inputted.
				boolean successful = currentSessionBank.withdrawal(currentSessionAccount, aAmount);
				
				if(successful) {
					tempBank.deposit(transferAccount, aAmount);
					
					JOptionPane.showMessageDialog(this, "Transaction Successful!");
					
					Receipt newReceipt = new Receipt("Transfer", (aAmount)*-1, currentSessionAccount);
					newReceipt.printReceipt();
					
					logout();
				} else {
					JOptionPane.showMessageDialog(this, "ERROR! Not enough funds in account.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void payBill(int aPayeeID, double aAmount) {
		/*
		 * Description: Sends money from the active account to a Payee, tracking the payment and its corresponding bill.
		 * Preconditions: User should be logged-in, parameter should be positive double, account balance should be >= to truAmount,
		 * aPayeeID should correlate to an existing Payee, Payee should have a bill corresponding to the user.
		 * Post-conditions: The user's balance is decreased by truAmount, the payee's balance is increased by truAmount.
		 * Makes a record of the payment and payer in the Payee object. Removes outstanding bill from Payee if it is fully paid.
		 * A receipt object is created and the file is put in the project folder. The user is prompted to logout.
		 */
		
		Payee payeeAccount = null;
		Bank tempBank = null;
		Bill billTarget = null;
		
		for(int i = 0; i < _banks.length; i++) { 
			payeeAccount = _banks[i].getPayee(aPayeeID);
			if(payeeAccount != null) {
				billTarget = payeeAccount.getBill(currentSessionAccount);
				if(billTarget != null) {
					tempBank = _banks[i];
					break;
				}
			}
		}
		
		if(currentSessionAccount != null && payeeAccount != null) {
			if(billTarget != null) {
				aAmount = roundMoney(aAmount);
				
				if(aAmount > 0.00) {
					double truAmount = Math.min(aAmount, billTarget.getAmount()); // So that the user doesn't overpay on accident.
					boolean successful = currentSessionBank.withdrawal(currentSessionAccount, truAmount);
					
					if(successful) {
						if(truAmount < billTarget.getAmount()) { // Partial Payment.
							payeeAccount.partialPayment(billTarget, truAmount);
							JOptionPane.showMessageDialog(this, "Payment sent!");
							
						} else { // Full Payment.
							payeeAccount.addPayment(billTarget);
							JOptionPane.showMessageDialog(this, "Full payment sent!");
							
						}
						
						tempBank.deposit(payeeAccount, truAmount);
						payeeAccount.addPayer(currentSessionAccount);
						
						Receipt newReceipt = new Receipt("Paid Bill", (truAmount)*-1, currentSessionAccount);
						newReceipt.printReceipt();
							
						logout();
						
					} else {
						JOptionPane.showMessageDialog(this, "ERROR! Not enough funds in account.", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "ERROR! Bill does not exist.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void takeLoan(double aAmount) {
		/*
		 * Description: Creates a loan object and deposits money into the user's account based on aAmount.
		 * Preconditions: User should be logged-in, parameter should be positive double between 100.00 and 1000.00, no outstanding
		 * loans should exist.
		 * Post-conditions: The user's balance is increased by aAmount, the bank's funds are decreased by aAmount.
		 * A loan object is created and added to their account. A receipt object is created and the file is put in the project folder.
		 * The user is prompted to logout.
		 */
		if(currentSessionAccount != null) {
			aAmount = roundMoney(aAmount);
			
			if(aAmount >= 100.00 && aAmount <= 1000.00) {
				String interestWarning = String.valueOf(_baseInterest);
				int interestConfirm = JOptionPane.showConfirmDialog(this, "There is an interst rate of " + interestWarning + "%\nContinue?", "Message", JOptionPane.YES_NO_OPTION);
				
				if(interestConfirm == 0) {
					currentSessionAccount.addLoan(LocalDate.now().toString(), aAmount, _baseInterest); // Establishes the loan.
					currentSessionBank.deposit(currentSessionAccount, aAmount); // Deposits the loan.
					
					JOptionPane.showMessageDialog(this, "Transaction Successful!");
					_screens[_screenIndex].screenUpdate(true);
					
					currentSessionBank.modifyFunds(aAmount * -1); // Money is taken from the bank for the loan; doesn't bother checking bank funds as the bank is all-mighty.
					
					Receipt newReceipt = new Receipt("Loan", aAmount, currentSessionAccount);
					newReceipt.printReceipt();
					
					logout();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void payLoan(int aIndex, double aAmount) {
		/*
		 * Description: Takes money from the active user account to pay the loan.
		 * Preconditions: User should be logged-in, parameter should be positive double, account balance should be >= to truAmount,
		 * aIndex should correspond to an active loan.
		 * Post-conditions: The user's balance is decreased by truAmount, the bank's balance is increased by truAmount. The loan is
		 * removed from the user's account if it is fully paid. A receipt object is created and the file is put in the project folder.
		 * The user is prompted to logout.
		 */
		Loan currentLoan = currentSessionAccount.getLoan(aIndex);
		
		if (currentSessionAccount != null) {
			if (currentLoan != null) {
				aAmount = roundMoney(aAmount);
				
				if (aAmount > 0.00) {
					double truAmount = Math.min(aAmount, currentLoan.getAmount()); // So that the user doesn't overpay on accident.
					boolean successful = currentSessionBank.withdrawal(currentSessionAccount, truAmount);
					
					if (successful) {
						if(currentLoan.takePayment(truAmount)) {
							JOptionPane.showMessageDialog(this, "Full payment sent!");
							currentSessionAccount.removeLoan(aIndex);
						} else {
							JOptionPane.showMessageDialog(this, "Payment sent!");
						}
						
						currentSessionBank.modifyFunds(truAmount); // Sends money back to the bank.
						
						Receipt newReceipt = new Receipt("Paid Loan", (truAmount)*-1, currentSessionAccount);
						newReceipt.printReceipt();
						
						_screens[_screenIndex].screenUpdate(true); 
						logout();
						
					}  else {
						JOptionPane.showMessageDialog(this, "ERROR! Not enough funds in account.", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please enter an input within bounds.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "ERROR! Loan does not exist.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "ERROR! Account out of range.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public double findCurrentBalance() { // Finds the balance of the current user account, if it exists.
		/*
		 * Description: Returns the currentSessionAccount's balance.
		 * Preconditions: User should be logged-in.
		 * Post-conditions: The user's balance is returned and the display is updated accordingly.
		 */
		double foundMoney = 0.00;
		
		if (currentSessionAccount != null) {
			foundMoney = currentSessionAccount.getBalance();
		}
		
		return foundMoney;
	}
	
	public int countCurrentLoans() {
		/*
		 * Description: Returns the currentSessionAccount's number of loans.
		 * Preconditions: User should be logged-in.
		 * Post-conditions: The number of active loans is returned.
		 */
		ArrayList<Loan> loans = (currentSessionAccount != null) ? currentSessionAccount.getLoans() : null; // Ternary that sets the loans variable to null if there is no active account.
		int foundLoans = 0;
		
		if(loans != null) {
			foundLoans = loans.size();
		}
		
		return foundLoans;
	}
	
	public String[] grabCurrentLoanString() { // Returns the an array of the toStrings for the current user loans. Called by PayLoanScreen.
		/*
		 * Description: Returns the an array of Strings corresponding to the currentSessionAccount's loans.
		 * Preconditions: User should be logged-in, the user should have one or more loans.
		 * Post-conditions: The array of strings is returned and populates a combobox.
		 */
		ArrayList<Loan> loans = currentSessionAccount.getLoans();
		String[] loanStrings = new String[loans.size()];
		
		for(int i = 0; i < loans.size(); i++) {
			loanStrings[i] = loans.get(i).toString();
			loanStrings[i] = loanStrings[i];
		}
		
		return loanStrings;
	}
	
	public void setScreen(int aIndex) { // Setter for the GUI screens.
		if (aIndex < _screens.length) {
			remove(_screens[_screenIndex]);
			_screens[_screenIndex].screenUpdate(false);
			
			add(_screens[aIndex]);
			_screens[aIndex].screenUpdate(true);
			
			_screenIndex = aIndex;
		} else { // If the screen doesn't exist, show an error message.
			JOptionPane.showMessageDialog(this, "ERROR! Something went wrong in the process.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setFine(double aFine) { // Setter for the base fine the ATM has.
		_baseFine = aFine;
	}
	
	public void setInterest(float aInterest) { // Setter for the interest the ATM implements.
		_baseInterest = aInterest;
	}
	
	public void setBanks(Bank[] aBanks) { // Setter for the Bank[] array.
		_banks = aBanks;
	}
	
	public void setCurrentAccount(Account aAccount) { // Setter for the current account.
		currentSessionAccount = aAccount;
	}
	
	public void setCurrentBank(Bank aBank) { // Setter for the current bank.
		currentSessionBank = aBank;
	}
	
	public void setModel(String aModel) {
		_model = aModel;
	}
	
	public int getScreen() { // Getter for the current screen.
		return _screenIndex;
	}
	
	public double getFine() { // Getter for the current fine.
		return _baseFine;
	}
	
	public float getInterest() { // Getter for the current interest rate.
		return _baseInterest;
	}
	
	public Bank[] getBanks() { // Getter for the current bank array.
		return _banks;
	}
	
	public Account getCurrentAccount() { // Getter for the current account.
		return currentSessionAccount;
	}
	
	public Bank getCurrentBank() { // Getter for the current bank.
		return currentSessionBank;
	}
	
	public String getModel() {
		return _model;
	}
}
