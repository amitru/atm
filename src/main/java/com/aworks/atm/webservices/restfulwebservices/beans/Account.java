package com.aworks.atm.webservices.restfulwebservices.beans;

public class Account {
	
	private Integer number;
	private Integer pin;
	private Integer balance;
	private Integer overDraft;
	
	public Account() {
		
	}
	
	public Account(Integer num,Integer pin,Integer bal,Integer od) {
		this.number=num;
		this.pin=pin;
		this.balance=bal;
		this.overDraft=od;
	}
	
	public Account(Integer bal,Integer od) {
		this.balance=bal;
		this.overDraft=od;
	}
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getOverDraft() {
		return overDraft;
	}
	public void setOverDraft(Integer overDraft) {
		this.overDraft = overDraft;
	}
}
