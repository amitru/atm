package com.aworks.atm.webservices.restfulwebservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aworks.atm.webservices.restfulwebservices.beans.Account;
import com.aworks.atm.webservices.restfulwebservices.beans.CheckBalanceResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawalResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawlRequest;
import com.aworks.atm.webservices.restfulwebservices.dao.AccountDaoService;
import com.aworks.atm.webservices.restfulwebservices.exception.GenericException;
import com.aworks.atm.webservices.restfulwebservices.exception.InvalidCredentialsException;
import com.aworks.atm.webservices.restfulwebservices.handler.AccountsHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestfulWebservicesApplicationTests {

	@Autowired
	private AccountsHandler accountsHandler;
	
	@MockBean
	private AccountDaoService accountService;
	
	@Test(expected = InvalidCredentialsException.class)
	public void verifyPinTest() {
		Account account = new Account(123456789,7654,800,200);
		accountsHandler.fetchBalance(account);
	}
	
	@Test
	public void getBalanceTest() {
		CheckBalanceResponse response = new CheckBalanceResponse(800,200);
		when(accountService.findBalance(123456789, 1234)).thenReturn(response);
		assertEquals(response.getBalance(), accountService.findBalance(123456789, 1234).getBalance());
	}
	
	@Test
	public void withdrawBalanceTest() {
		WithdrawlRequest wReq = new WithdrawlRequest(1, 123456789, 1234, 100);
		WithdrawalResponse wResp= new WithdrawalResponse(700, "Extracted Notes-50*2");
		when(accountService.findOne(123456789, 1234)).thenReturn(new Account(800,200));
		//when(accountsHandler.withdrawBalance(wReq)).thenReturn(wResp);
		assertEquals(wResp.getNewBalance(), accountsHandler.withdrawBalance(wReq).getNewBalance());
	}
	
	//NOT_ENOUGH_CASH_ATM
	@Test(expected = GenericException.class)
	public void notEnoughCashATMTest() {
		WithdrawlRequest wReq = new WithdrawlRequest(1, 123456789, 1234, 1600);
		when(accountService.findOne(123456789, 1234)).thenReturn(new Account(800,200));
		accountsHandler.withdrawBalance(wReq);
	}
	
	
	//INSUFF_BALANCE
	@Test(expected = GenericException.class)
	public void testInsuffBalance() {
		WithdrawlRequest wReq = new WithdrawlRequest(1, 123456789, 1234, 900);
		when(accountService.findOne(123456789, 1234)).thenReturn(new Account(800,200));
		accountsHandler.withdrawBalance(wReq);
	}
	
	
	//SPECIFY_AMT_GRTER_THAN_ZERO
	@Test(expected = GenericException.class)
	public void testRequestedWithDrawal() {
		WithdrawlRequest wReq = new WithdrawlRequest(1, 123456789, 1234, 0);
		when(accountService.findOne(123456789, 1234)).thenReturn(new Account(800,200));
		accountsHandler.withdrawBalance(wReq);
	}
	
	//NO_SUCH_DENOM
	@Test(expected = GenericException.class)
	public void testNoSuchDenominations() {
		WithdrawlRequest wReq = new WithdrawlRequest(1, 123456789, 1234, 76);
		when(accountService.findOne(123456789, 1234)).thenReturn(new Account(800,200));
		accountsHandler.withdrawBalance(wReq);
	}
}
