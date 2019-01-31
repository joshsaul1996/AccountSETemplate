package com.qa.persistence.repository;

import java.util.*;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.qa.persistence.domain.Account;
import com.qa.persistence.util.JSONUtil;

@Alternative
public class AccountMapRepository implements AccountRepository{
	
	@Inject
	public Map<Long, Account> accounts;
	
	@Inject
	public JSONUtil json;
	
	public String getAllAccounts() {
		 return json.getJSONForObject(accounts.values());
				
	}
 
	public String createAccount(String jsonString) {
		Account account = json.getObjectForJSON(jsonString, Account.class);
		accounts.put((long) accounts.size(), account);
		return null;
	}

	public String deleteAccount(Long id) {
		accounts.remove(id);
		return null;
	}

	public String updateAccount(Long id, String jsonString) {
		Account account = json.getObjectForJSON(jsonString, Account.class);
		accounts.replace(id, account);
		return null;
	}

	public int countForFirstName(String firstName) {
		int nameCounter = 0;
		
		for (Account account : accounts.values())
		{
			if(account.getFirstName().contains(firstName))
			{nameCounter++;
		}
		
	}
		return nameCounter;
}
}