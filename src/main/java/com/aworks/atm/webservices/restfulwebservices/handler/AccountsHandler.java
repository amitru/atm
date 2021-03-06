package com.aworks.atm.webservices.restfulwebservices.handler;

import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.EXTRACTED_NOTES;
import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.INSUFF_BALANCE;
import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.INVALID_CREDS;
import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.NOT_ENOUGH_CASH_ATM;
import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.NO_SUCH_DENOM;
import static com.aworks.atm.webservices.restfulwebservices.constants.Constants.SPECIFY_AMT_GRTER_THAN_ZERO;

import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aworks.atm.webservices.restfulwebservices.beans.Account;
import com.aworks.atm.webservices.restfulwebservices.beans.CheckBalanceResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawalResponse;
import com.aworks.atm.webservices.restfulwebservices.beans.WithdrawlRequest;
import com.aworks.atm.webservices.restfulwebservices.dao.AccountDaoService;
import com.aworks.atm.webservices.restfulwebservices.dao.AtmDaoService;
import com.aworks.atm.webservices.restfulwebservices.exception.GenericException;
import com.aworks.atm.webservices.restfulwebservices.exception.InvalidCredentialsException;

@Service
public class AccountsHandler {

	private static final Logger logger = LogManager.getLogger(AccountsHandler.class);
	
	@Autowired
	AtmDaoService atmDaoService;
	
	@Autowired
	AccountDaoService accountDaoService;
	
	
	public CheckBalanceResponse fetchBalance(Account account) {
		CheckBalanceResponse acctDetails =  accountDaoService.findBalance(account.getNumber(),account.getPin());
		if(acctDetails == null) {
			logger.error("Invalid Credentials Entered");
			throw new InvalidCredentialsException(INVALID_CREDS);
		}
		return acctDetails;
	}
	
	public WithdrawalResponse withdrawBalance(WithdrawlRequest wReq) {
		WithdrawalResponse response=null;
		Account acctDetails =  accountDaoService.findOne(wReq.getAccount(),wReq.getPin());
		if(acctDetails == null) {
			logger.error("Invalid Credentials Entered");
			throw new InvalidCredentialsException(INVALID_CREDS);
		}
		
		Integer amountToWithdraw = wReq.getAmount();
		Integer withdrawableBal = acctDetails.getBalance();
		Integer atmBalance = atmDaoService.getBalance(wReq.getAtmId());
		TreeMap<Integer, Integer> denominationsMap =  atmDaoService.getDenominations(wReq.getAtmId());
		
		if(atmBalance<amountToWithdraw) {
			logger.error("Not Enough Cash in the ATM for atm id-"+ wReq.atmId);
			throw new GenericException(NOT_ENOUGH_CASH_ATM);} 
		else if(amountToWithdraw>withdrawableBal) {
			logger.error("The account balance is less, can't withdraw amountToWithdraw-"+ amountToWithdraw + " withdrawableBal-"+ withdrawableBal);
			throw new GenericException(INSUFF_BALANCE);
		} else if(amountToWithdraw<=0) {
			logger.error("Amount to withdraw is-" + amountToWithdraw + " and is <=0, can't withdraw");
			throw new GenericException(SPECIFY_AMT_GRTER_THAN_ZERO);
		}
		else {
			TreeMap<Integer, Integer> newDenoMap = new TreeMap<Integer, Integer>(denominationsMap);
			response = getDenominations(withdrawableBal,amountToWithdraw,denominationsMap,newDenoMap);
			atmDaoService.setNewDenominations(newDenoMap);
			atmDaoService.setBalance(wReq.getAtmId(),amountToWithdraw);
			accountDaoService.updateBalance(wReq.getAccount(),response.getNewBalance());
		}
		
		return response;
	}
	
	private static WithdrawalResponse getDenominations(Integer withdrawableBal, Integer withDrawlReq,TreeMap<Integer, Integer> denoMap,
			TreeMap<Integer, Integer> newDenoMap ) {
		
		int reim = -1;
		WithdrawalResponse response= null;
		StringBuilder message = new StringBuilder(EXTRACTED_NOTES);
		Integer toWithDraw = withDrawlReq;
		
		for(Map.Entry<Integer,Integer> entry : denoMap.entrySet()) {
			  Integer key = entry.getKey(); 
			  Integer value = entry.getValue();
			  Integer noteCounts = withDrawlReq / key;
			  Integer notesPossible = 0;
			  reim = withDrawlReq % key;
			  
			  if(noteCounts!=0) {
				  if(noteCounts<=value) { //perfect match
					  notesPossible=noteCounts;
				  } else if(noteCounts>value) { // 725 case = 50*14 + 25
					  notesPossible = value;
				  } 
				  withDrawlReq= withDrawlReq-(key*notesPossible) ;
				  
				  if(value-notesPossible!=0)
					  newDenoMap.put(key,value-notesPossible);
				  else {
					  newDenoMap.remove(key);
				  }
				  message.append(key + "*" + notesPossible + ",");
			  }
			  
			}
		
		if(reim>0) {
			logger.error("No denominations exists for the requested amount toWithDraw-" + toWithDraw);
			throw new GenericException(NO_SUCH_DENOM);
		} else {
			//all denomin correct, return new balance and denominations received
			response= new WithdrawalResponse(withdrawableBal-toWithDraw, message.toString());
			logger.debug("Amount debited New balance-" + response.getNewBalance() + " message-" + message.toString());
			
		}
		return response;
	}
	
}
