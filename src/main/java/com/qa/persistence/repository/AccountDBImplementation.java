package com.qa.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.qa.persistence.domain.Account;
import com.qa.persistence.util.JSONUtil;


@Transactional(TxType.SUPPORTS)
public class AccountDBImplementation implements AccountRepository {
	
	@PersistenceContext (unitName = "primary")
	private EntityManager eManager;
	JSONUtil json = new JSONUtil();
	
	@Override
	public String getAllAccounts() {
		TypedQuery<Account> query = eManager.createQuery("SELECT a FROM Accounts a", Account.class);
		return json.getJSONForObject(query.getResultList());
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public String createAccount(String jsonString) {
		Account account= json.getObjectForJSON(jsonString, Account.class);
		eManager.persist(account);
		TypedQuery<Account> query = eManager.createQuery("SELECT a FROM Accounts a", Account.class);
		return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully added\"}";
				
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public String deleteAccount(Long id) {
		TypedQuery<Account> query = eManager.createQuery("DELETE a FROM Accounts a WHERE a.id" + id, Account.class);
		return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully added\"}";
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public String updateAccount(Long id, String account) {
		
		return null;
	}
	
	

}
