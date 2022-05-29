package com.aworks.atm.webservices.restfulwebservices.beans;

public class WithdrawalResponse {
	
	private Integer newBalance;
	private String message;
	
	public WithdrawalResponse(Integer newBalance, String message) {
		super();
		this.newBalance = newBalance;
		this.message = message;
	}
	public Integer getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(Integer newBalance) {
		this.newBalance = newBalance;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
	
	

}
