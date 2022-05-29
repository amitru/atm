package com.aworks.atm.webservices.restfulwebservices.beans;

public class CheckBalanceResponse {

	private Integer balance;
	private Integer overDraft;
	
	public CheckBalanceResponse(Integer balance, Integer overDraft) {
		super();
		this.balance = balance;
		this.overDraft = overDraft;
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
