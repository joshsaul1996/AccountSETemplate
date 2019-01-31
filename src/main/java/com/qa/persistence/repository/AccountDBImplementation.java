package com.qa.persistence.repository;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.*;

import com.qa.persistence.domain.Account;
import com.qa.persistence.util.JSONUtil;

@Default
@Transactional(SUPPORTS)
public class AccountDBImplementation implements AccountRepository {
	
	@PersistenceContext (unitName = "primary")
	private EntityManager eManager;
	
	@Inject
	private JSONUtil json;
	
	@Override
	public String getAllAccounts() {
		TypedQuery<Account> query = eManager.createQuery("SELECT a FROM Account a", Account.class);
		return json.getJSONForObject(query.getResultList());
	}

	@Override
	@Transactional(REQUIRED)
	public String createAccount(String jsonString) {
		Account account= json.getObjectForJSON(jsonString, Account.class);
		eManager.persist(account);
		TypedQuery<Account> query = eManager.createQuery("SELECT a FROM Account a", Account.class);
		return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully added\"}";
				
	}

	@Override
	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		TypedQuery<Account> query = eManager.createQuery("DELETE a FROM Account a WHERE a.id" + id, Account.class);
		return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully deleted\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String updateAccount(Long id, String jsonString) {
		Account account= json.getObjectForJSON(jsonString, Account.class);
		String fName = account.getFirstName();
		String lName = account.getLastName();
		Integer aNumber = account.getAccountNumber();
		TypedQuery<Account> query = eManager.createQuery("UPDATE a FROM Account  a SET " + "firstName = :fName" + "lastName = :lName" + "accountNumber = :aNumber" + "WHERE a.id" + id, Account.class);
		query.setParameter("firstName", fName).executeUpdate();
		query.setParameter("lastName", lName).executeUpdate();
		query.setParameter("accountNumber", aNumber).executeUpdate();
		return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully updated\"}";
	}
	
//	@Transactional(TxType.REQUIRED)
//	TypedQuery<Account> query = eManager.createQuery("DELETE a FROM Accounts a WHERE a.id" + id, Account.class);
//	Account account= json.getObjectForJSON(jsonString, Account.class);
//	eManager.persist(account);
//	TypedQuery<Account> query = eManager.createQuery("SELECT a FROM Accounts a", Account.class);
//	return json.getJSONForObject(query.getResultList())+"{\"Message\": \"Account successfully updated\"}";
	
	
	
	

}
