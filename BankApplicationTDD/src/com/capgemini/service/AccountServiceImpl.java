package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidDepositAmountException;
import com.capgemini.repository.AccountRepository;
import com.capgmeini.model.Account;

public class AccountServiceImpl implements AccountService {
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialBalanceException
	{
		
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		Account account = new Account();
		
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.capgemini.service.AccountService#withdraw(int, com.capgmeini.model.Account)
	 * 
	 */
	@Override
	public boolean withdraw(int amount,Account account) throws InsufficientBalanceException {
		
		boolean flag=false;
		int minBalance = 500;
		
		if(amount >= account.getAmount()){
			System.out.println("Withdraw amount is Greater");
			throw new InsufficientBalanceException(); 
		}
		else if(minBalance >= (account.getAmount() - amount)){
			System.out.println("minBalnance");
			throw new InsufficientBalanceException(); 
		}
		else
		{
			flag = true;
			account.setAmount(account.getAmount() - amount);
			System.out.println("____Withdraw____");
			System.out.println("Updated Balance::"+account.getAmount()+"\nAccount number::"+account.getAccountNumber());
		}
			
		return flag;
	}

	@Override
	public boolean deposit(int amount, Account account)
			throws InvalidDepositAmountException {
		
		boolean flag=false;
		
		if(amount <= 0){
			
			System.out.println("Invalid Deposit Amount");
			throw new InvalidDepositAmountException(); 
		}
		else
		{
			flag = true;
			account.setAmount(account.getAmount() + amount);
			System.out.println("____Deposit____");
			System.out.println("Updated Balance::"+account.getAmount()+"\nAccount number::"+account.getAccountNumber());
		}	
		
		return flag;
	}

	
}
