package com.aworks.atm.webservices.restfulwebservices.beans;

public class WithdrawlRequest {
	
	public Integer atmId;
	public Integer account;
	public Integer pin;
	public Integer amount;
	
	
	public WithdrawlRequest(Integer atmId, Integer account, Integer pin, Integer amount) {
		super();
		this.atmId = atmId;
		this.account = account;
		this.pin = pin;
		this.amount = amount;
	}
	
	
	public Integer getAtmId() {
		return atmId;
	}

	public void setAtmId(Integer atmId) {
		this.atmId = atmId;
	}

	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	

}
