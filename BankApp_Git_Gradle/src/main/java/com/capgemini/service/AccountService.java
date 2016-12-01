package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidDepositAmountException;
import com.capgmeini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException;

	boolean withdraw(int amount,Account account) throws InsufficientBalanceException;
	
	boolean deposit(int amount,Account account) throws InvalidDepositAmountException;
}