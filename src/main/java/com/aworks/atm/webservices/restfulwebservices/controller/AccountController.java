package com.aworks.atm.webservices.restfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aworks.atm.webservices.restfulwebservices.beans.Account;
import com.aworks.atm.webservices.restfulwebservices.beans.CheckBalanceResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawalResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawlRequest;
import com.aworks.atm.webservices.restfulwebservices.handler.AccountsHandler;

@RestController
public class AccountController {
	
	@Autowired
	AccountsHandler accountsHandler;

	@PostMapping("/get-balance")
	public CheckBalanceResponse getBalance(@RequestBody Account account) {
		return accountsHandler.fetchBalance(account);
	}
	
	@PostMapping("/withdraw-balance")
	public WithdrawalResponse withdrawBalance(@RequestBody WithdrawlRequest wReq) {
		return accountsHandler.withdrawBalance(wReq);
	}
	
	
	
}
