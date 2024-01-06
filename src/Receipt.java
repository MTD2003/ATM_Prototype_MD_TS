/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Receipt File
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime; 
public class Receipt { // Receipt object which creates a .txt file for record keeping whenever a transaction is completed.
 private LocalDate _date;
 private LocalTime _Time;
 private String _transType;
 Account _account;
 double _difference;
 
 public Receipt(String rTransaction, double rDifference, Account rAccount) {
  this._date = LocalDate.now();
  this._Time = LocalTime.now();
  this._transType = rTransaction;
  this._difference = rDifference;
  this._account = rAccount;  
 }
 
 public void printReceipt() { //this method creates a file named (current time)(current date).txt
  // impossible to have 2 receipts of the exact same name
  try{
   NumberFormat formatter = NumberFormat.getCurrencyInstance();
   String fileName = _Time.toString()+_date.toString()+".txt";
   fileName = fileName.replace(':', '~'); // : cannot be used in file names.
   
   FileWriter outputFile = new FileWriter(fileName);
   outputFile.write(_date.toString() + "\n" + _Time.toString() + "\n\n");
   outputFile.write(_account.getFullName() + ", " + _account.getAccountNumber() + "\n");
   outputFile.write(_transType + ": " + formatter.format(_difference) + "\nBalance: " + formatter.format(_account.getBalance()));
   outputFile.close();
   
   System.out.println(fileName); // Files are by default sent to the project folder (at least in eclipse).
  } catch(IOException rError){
   System.out.println(rError.getMessage());
  }
 }
 
 
}
