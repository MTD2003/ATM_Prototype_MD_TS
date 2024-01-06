/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Bill File
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
public class Bill { // Bill object that is used to keep track of payments for Payees.
 private Account _payer;
 private double _amount;
 public Bill(Account bPayer, double bAmount) { 
  this._payer = bPayer;
  this._amount = bAmount;
 }
 public void partialPayment(double bAmount) {this._amount -= bAmount;}  //decreases the amount owed on this bill by a given amount
 public void setAmount(double bAmount) {this._amount = bAmount;} //sets the amount owed.
 public void setPayer(Account bPayer) {this._payer = bPayer;}
 public double getAmount() {return _amount;}
 public Account getPayer() {return _payer;}
 
 public String toString() {
  return _payer.toString() + " " + _amount;
 }
}
