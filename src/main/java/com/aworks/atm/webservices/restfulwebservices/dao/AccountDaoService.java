package com.aworks.atm.webservices.restfulwebservices.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.aworks.atm.webservices.restfulwebservices.beans.Account;
import com.aworks.atm.webservices.restfulwebservices.beans.CheckBalanceResponse;

@Component
public class AccountDaoService {

	private static List<Account> accounts = new ArrayList<Account>();
	
	static {
		accounts.add(new Account(123456789,1234,800,200));
		accounts.add(new Account(987654321,4321,1230,150));
		accounts.add(new Account(187654322,5464,1230,150));
		accounts.add(new Account(287654324,2234,1230,150));
		accounts.add(new Account(587654327,1123,1230,150));
		accounts.add(new Account(787654323,1278,1230,150));
	}
	
	
	public CheckBalanceResponse findBalance(Integer acctNum,Integer pin) {
		for(Account account: accounts) {
			if((account.getNumber().intValue() == acctNum) && (account.getPin().intValue() == pin)) {
				return new CheckBalanceResponse(account.getBalance(), account.getOverDraft());
			} 
		}
		return null;
	}
	
	public Account findOne(Integer acctNum,Integer pin) {
		for(Account account: accounts) {
			if((account.getNumber().intValue() == acctNum) && (account.getPin().intValue() == pin)) {
				return account;
			} 
		}
		return null;
	}
	
	public void updateBalance(Integer acctNum, Integer newBalance) {

		for(Account account: accounts) {
			if((account.getNumber().intValue() == acctNum)) {
				account.setBalance(newBalance);
			} 
		}
	}
}
