/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Account File
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.util.ArrayList;
//object class for an account to be stored and accessed in the bank and ATM's methods
public class Account {
 private double _balance;
 private int _accNum,_passcode;
 private String _firstName,_surname;
 private ArrayList<Loan> _loans;
 //private String _Name = _firstName + " " + _surname;
 public Account(double aBal, int aNum, String aFName, String aSurname, int aPIN) {
  this._balance = aBal; 
  this._accNum = aNum;
  this._firstName = aFName;
  this._surname = aSurname;
  this._loans = new ArrayList<Loan>();
  this._passcode = aPIN;
 }
 
 public void modifyBalance(double bDifference) {// made for less calculations, will increase or decrease the balance of the account by the difference
  this._balance = _balance + bDifference; // if bDifference is negative will perform subtraction 
 }
 public void addLoan(String lCDate, double lAmount, float lInterest) {//takes parameters for loan and adds one, increases the length of the Loans arrayList by 1
  Loan newLoan = new Loan(lCDate, lAmount, lInterest);
  _loans.add(newLoan);
 }
 
 public void removeLoan(int aIndex) {//removes a loan from the list of loans. used when loan is payed off, lessens number of loans stored by 1
  if (aIndex < _loans.size()) {
   _loans.remove(aIndex);
  }
 }
 
 public void setPasscode(int aPIN) {this._passcode = aPIN;}
 public void setBalance(double aBal) {this._balance = aBal;}
 public void setAccountNumber(int aNum) {this._accNum = aNum;}
 public void setFirstName(String aFName) {this._firstName = aFName;}
 public void setSurname(String aSurname) {this._surname = aSurname;}
 public void setLoans(ArrayList<Loan> aLoans) {_loans = aLoans;}
 
 public double getBalance() {return _balance;}
 public int getAccountNumber() {return _accNum;}
 public String getFirstName() {return _firstName;}
 public String getSurname() {return _surname;}
 public String getFullName() {return _firstName + " " + _surname;}
 public int getPasscode() {return _passcode;}
 public ArrayList<Loan> getLoans() {return _loans;}
 public Loan getLoan(int aIndex) {
  if (aIndex < _loans.size()) {
   return _loans.get(aIndex);
  }
  
  return null;
 }
 
 public String toString() {
  String toString = "Name: " + _firstName + " " + _surname;
  toString+= " account number: " + _accNum + " balance: " + _balance;
  return toString;
 }
}
