package com.capgemini.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidDepositAmountException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import com.capgmeini.model.Account;

public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	/* create account
	 * 1. when the amount is less than 500, system should throw exception
	 * 2. when the valid info is passed account should be created successfully.
	 * 
	 * 
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}

	/*
	 *  when the withdraw amount is greater than or equal to account balance, system should throw exception
	 */

	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTheWithdrawAmountIsGreaterThanOrEqualToAccountBalance() throws InsufficientBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(2000);
		int amount=3000;
		
		accountService.withdraw(amount, account);
		
	}
	/*
	 * when the (withdraw amount - account balance) is less than minBalance, system should throw exception
	 * 
	 */
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTheWithdrawAmountMinusAccountBalanceIsLessThanMinBalance() throws InsufficientBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(2000);
		int amount=1600;
		
		accountService.withdraw(amount, account);
		
	}
	
	/*
	 * when withdraw is successful
	 */
	@Test
	public void whenTheWithdrawalIsSuccessfull() throws InsufficientBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(2000);
		int amount=1000;
		
		assertTrue(accountService.withdraw(amount, account));
		
	}
	
	/*
	 * When Deposit amount is Zero Or Negative
	 */
			
	@Test(expected=com.capgemini.exceptions.InvalidDepositAmountException.class)
	public void whenTheDepositeAmountIsZeroOrNegative() throws InvalidDepositAmountException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(2000);
		int amount=0;
		
		accountService.deposit(amount, account);
		
	}
	
	/*
	 * when deposit is successful
	 */
	@Test
	public void whenTheDepositIsSuccessfull() throws InvalidDepositAmountException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(2000);
		int amount=1000;
		
		assertTrue(accountService.deposit(amount, account));
		
	}
	
	
}
