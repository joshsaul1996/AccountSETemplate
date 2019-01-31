package com.qa.business.service;

import javax.inject.Inject;

import com.qa.persistence.domain.Account;
import com.qa.persistence.repository.AccountRepository;
import com.qa.persistence.util.JSONUtil;

public class AccountServiceImpl implements AccountService {

	@Inject
	public JSONUtil json;
	
	@Inject
	public AccountRepository repository;
	
	@Override
	public String getAllAccounts() {
		
		return  repository.getAllAccounts();
	}

	@Override
	public String createAccount(String jsonString) {
		Account account = json.getObjectForJSON(jsonString, Account.class);
		if(account.getAccountNumber() == 9) {
			return "{message\" : \"This account is blocked\"}";
		}else {
			return repository.createAccount(jsonString);
		}
	}

	@Override
	public String deleteAccount(Long id) {
		return repository.deleteAccount(id);
	}

	@Override
	public String updateAccount(Long id, String account) {
		return repository.updateAccount(id, account);
	}

}
