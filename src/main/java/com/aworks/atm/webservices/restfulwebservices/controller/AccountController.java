package com.aworks.atm.webservices.restfulwebservices.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static final Logger logger = LogManager.getLogger(AccountController.class);
	
	
	@Autowired
	AccountsHandler accountsHandler;

	@PostMapping("/get-balance")
	public CheckBalanceResponse getBalance(@RequestBody Account account) {
		logger.debug("getBalance() Entered-" + account.getNumber() );
		return accountsHandler.fetchBalance(account);
	}
	
	@PostMapping("/withdraw-balance")
	public WithdrawalResponse withdrawBalance(@RequestBody WithdrawlRequest wReq) {
		logger.debug("getBalance() Entered-" + wReq.getAccount() + " " + wReq.getAmount());
		return accountsHandler.withdrawBalance(wReq);
	}
	
	
	
}
