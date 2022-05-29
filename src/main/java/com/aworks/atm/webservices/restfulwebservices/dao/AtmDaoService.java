package com.aworks.atm.webservices.restfulwebservices.dao;

import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class AtmDaoService {

	TreeMap<Integer, Integer> denoMap = new TreeMap<Integer, Integer>(Collections.reverseOrder());
	Integer balance;
	
	public AtmDaoService() {
		denoMap.put(50, 10); //50$ notes of count=10
		denoMap.put(20, 30);
		denoMap.put(10, 30);
		denoMap.put(5, 20);
		balance=1500;
	}
	
	public TreeMap<Integer, Integer> getDenominations(Integer atmId) {
		return denoMap;
	}
	
	public void setNewDenominations(TreeMap<Integer, Integer> newDenoMap) {
		denoMap = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		denoMap.putAll(newDenoMap);
	}

	public Integer getBalance(Integer atmId) {
		return balance;
	}

	public void setBalance(Integer atmId,Integer debitAmt) {
		this.balance = balance-debitAmt;
	}
	
	
}
