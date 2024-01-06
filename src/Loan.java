/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Loan Object
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.text.NumberFormat;

public class Loan { // Loan object created whenever the user takes out a loan.
 private String _creationDate;
 private double _amount;
 private float _interest;
 public Loan (String lCDate, double lAmount, float lInterest) {
  this._creationDate = lCDate;
  this._amount = lAmount;
  this._interest = lInterest;
 }
 
 public boolean takePayment(double lAmount) { // Returns true or false depending on if the full amount was paid.
  _amount -= lAmount;
  
  if(_amount <= 0) {
   return true;
  }
  
  return false;
 }
 
 public String getLoanCreationDate() {return _creationDate;}
 public float getInterestRate() {return _interest;}
 public double getAmount() {return _amount;}
 public void setLoanCreationDate(String lCDate) {this._creationDate = lCDate;}
 public void setLoanAmount(double lAmount) {this._amount = lAmount;}
 public void setLoanInterest(float lInterest) {this._interest = lInterest;}
 
 public String toString(){
  NumberFormat formatter = NumberFormat.getCurrencyInstance();
  
  String toString = _creationDate + ": " + formatter.format(_amount);
  toString +=  ". \nRate: " + _interest + "%";
  return toString;
 }
 
}
