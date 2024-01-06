/*
    Course: COMP 2663 FA01, 2023
    Assignment 4, Payee File
 Author: Michael Doherty & Tye States
 Student ID: 0293019d & 0300326s
    Date: 2023/11/07
    I certify that this code is my own. I have not broken any rules concerning Academic Dishonesty. 
*/
import java.util.ArrayList;
public class Payee extends Account { // Payee object which acts as a receiver for paid bills.
 private ArrayList<Bill> _receipts, _accountsReceivable;
 private ArrayList<Account> _payers;
 public Payee(double aBal, int aNum, String aFName, String aSurname, int pPIN) {
  super(aBal,aNum,aFName,aSurname,pPIN); //payee is an account type
  this._payers = new ArrayList<Account>(); //people that have paid
  this._receipts = new ArrayList<Bill>(); //list of receipts paid to payee
  this._accountsReceivable = new ArrayList<Bill>();//all pending bills 
 }
 
 public void addPayer(Account pAcc) {// adds  a payer to the existing arrayList of people who have payed to this payee
  if(_payers.indexOf(pAcc) == -1) { //a payer will only be saved once 
   _payers.add(pAcc);
  }
 }
 
 public void partialPayment(Bill pBill, double pAmount) {//used to make a partial payment on a bill stored by the payee. will store a payment in a different arrayList
  int c = _accountsReceivable.indexOf(pBill);
  pBill.partialPayment(pAmount);
  _receipts.add(new Bill(pBill.getPayer(),pAmount));//recording purposes
  _accountsReceivable.set(c, pBill); 
 }
 public void addPayment(Bill pPayment) { //each payment will be recorded, if this is not an outstanding bill it will just be recorded. If it is fully paying off a bill it 
   //will also remove said bill
  if(_accountsReceivable.indexOf(pPayment) == -1) {
   _receipts.add(pPayment);
  }
  else {
   _receipts.add(pPayment);
   _accountsReceivable.remove(pPayment);
  }
 }
 
 public void addAccRec(Bill pPayment) {//accRec is shorthand for accounts receivable, adds a value to the arraylist of outstanding bills
  _accountsReceivable.add(pPayment);
 }
 
 public Bill getBill(Account pPayer) {//finds a bill by looking for its payer, if it is not found, returns a null
  Bill billCopy = null;
  
  for(int i = 0; i< _accountsReceivable.size(); i++) {
   if(pPayer == (_accountsReceivable.get(i)).getPayer()) {
    billCopy = _accountsReceivable.get(i);
    break;
   }
  }
  
  return billCopy;
 }
 
 public String toString() { //returns the account info, payers, then receipts in a long string
  String toString = super.toString()+ "\n\nPayers:";
  for(int i = 0; i<_payers.size();i++) {
   toString += "\n"+(_payers.get(i)).toString() ;
  }
  toString += "\n\nreceipts:";
  for(int j = 0; j < _receipts.size(); j++) {
   toString += "\n"+(_receipts.get(j)).toString() + " payment#: " + j;
  }
  return toString;
 }
 
}
