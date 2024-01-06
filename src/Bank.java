/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Bank File
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.util.ArrayList;
//object class of a bank, stores accounts, payees, an amount of funds and its name
public class Bank{ // not a driver class, used for storing data
 private ArrayList<Account> _accounts;
 private String _name;
 private ArrayList<Payee> _payees;
 private double _funds;
 
 public Bank(double bFunds, ArrayList<Account> bAccount, ArrayList<Payee> bPayee, String bName) { // Basic constructor.
  this._funds = bFunds;
  this._accounts = bAccount;
  this._name = bName;
  this._payees = bPayee;
 }
 
 public void addAccount(Account a) {_accounts.add(a);} //adds an account to the storage
 public void removeAccount(Account bA) {_accounts.remove(bA);}
 public void addPayee(Payee bP) {_payees.add(bP);}
 public void removePayee(Account bP) {_payees.remove(bP);}
 public void modifyFunds(double bAmount) {
  _funds += bAmount;
 }
 public boolean withdrawal(Account bUser, double bAmount) { //boolean for simplicity of use in other methods
  //checks if the user has enough money for the requested operation, if yes, removes said money from the account
  boolean completed;
  final double check = bUser.getBalance();
  if(check >= bAmount) {
   bUser.modifyBalance(0-bAmount);
   completed = true;  
  }
  else {completed = false;}
  return completed;
 }
 public void deposit(Account bUser, double bAmount) { //increases the amount of stored funds in an account by the amount specified
  bUser.modifyBalance(bAmount);
 }
 
 public Account getAccount(int bAccNum) { // used to find a stored account by searching for an account with the given number
   //if the searched for account is not found, returns a null
  Account accountCopy = null; // Starts out as null. Default return if the correct account isn't found.
  for(int i = 0; i< _accounts.size(); i++) { //iterate across all stored 
   if(bAccNum == (_accounts.get(i)).getAccountNumber()) {
    accountCopy = _accounts.get(i);
    break;
   }
  }
  
  return accountCopy;
 }
 
 public Payee getPayee(int bPayeeNum) { // used to find a stored payee account by searching for a payee account with the given number
   //if the searched for account is not found, returns a null
  Payee payeeCopy = null;
  for(int i = 0; i< _accounts.size(); i++) { //iterate across all stored 
   if(bPayeeNum == (_payees.get(i)).getAccountNumber()) {
    payeeCopy = _payees.get(i);
    break;
   }
  }
  
  return payeeCopy;
 }
 
 public void setFunds(double bFunds) {this._funds = bFunds;}
 public void setAccountList(ArrayList<Account> bAccount) {this._accounts = bAccount;}
 public void setName(String bName) {this._name = bName;}
 public double getFunds() {return _funds;}
 public ArrayList<Account> getAccountList(){return _accounts;}
 public String getName() {return _name;}
 
 
}
