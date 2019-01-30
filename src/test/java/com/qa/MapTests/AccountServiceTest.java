package com.qa.MapTests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.qa.persistence.domain.Account;
import com.qa.persistence.repository.AccountMapRepository;
import com.qa.persistence.util.JSONUtil;

public class AccountServiceTest {
	AccountMapRepository repository;
	JSONUtil json = new JSONUtil();

	@Before
	public void setup() {
		
		repository = new AccountMapRepository();
	
	}
	
	@Test
	public void addAccountTest() {
		int mapSize = repository.accounts.size(); 
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		assertEquals("Hash Map size did not increase by 1", mapSize + 1, repository.accounts.size());
		
	}
	 
	@Test
	public void add2AccountTest() {
		int mapSize = repository.accounts.size();
		Account account1 = new Account("Josh", "Saul" , 22);
		Account account2 = new Account("William", "Ashworth" , 21);
		repository.createAccount(json.getJSONForObject(account1));
		repository.createAccount(json.getJSONForObject(account2));
		assertEquals("Hash Map size did not increase by 2", mapSize + 2, repository.accounts.size());
		
	}
 
	@Test
	public void removeAccountTest() {
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		int mapSize = repository.accounts.size();
		repository.deleteAccount((long) mapSize -1);
		assertEquals("Map size did not decrease by 1", mapSize -1, repository.accounts.size());
	}
	
	@Test
	public void remove2AccountTest() {
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		Account account2 = new Account("William", "Ashworth" , 21);
		repository.createAccount(json.getJSONForObject(account2));
		int mapSize = repository.accounts.size();
		repository.deleteAccount((long) mapSize -1);
		repository.deleteAccount((long) mapSize -2);
		assertEquals("Map size did not decrease by 2", mapSize -2, repository.accounts.size());
	}
	
	@Test
	public void remove2AccountTestAnd1ThatDoesntExist() {
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		Account account2 = new Account("William", "Ashworth" , 21);
		repository.createAccount(json.getJSONForObject(account2));
		int mapSize = repository.accounts.size();
		repository.deleteAccount((long) mapSize -1);
		repository.deleteAccount((long) mapSize -2);
		repository.deleteAccount((long) mapSize -3);
		assertEquals("Map size did not decrease by 2 and doesnt error if none existent is removed", mapSize -2, repository.accounts.size());
	}
	
	@Test
	public void accountConversionToJSONTestWithEmptyMap() {
		String util = json.getJSONForObject(repository.accounts.get((long)45));
		assertEquals("Json string did not match", "null", util);
	
	}
	
	@Test
	public void accountConversionToJSONTestEmptyMapWithConversion() {
	
	}

	@Test
	public void accountConversionToJSONTest() {

	}

	@Test
	public void getCountForFirstNamesInAccountWhenZeroOccurances() {
		assertEquals("The method should have returned 0 matches", 0, repository.countForFirstName("james"));
	}
	
	@Test
	public void getCountForFirstNamesInAccountWhenOne() {
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		repository.accounts.put((long)1,account);
		assertEquals("The method should have returned 0 matches", 1, repository.countForFirstName("Josh"));
	}

	@Test
	public void getCountForFirstNamesInAccountWhenMult() {
		Account account = new Account("Josh", "Saul" , 22);
		repository.createAccount(json.getJSONForObject(account));
		repository.accounts.put((long)1,account);
		Account account2 = new Account("Josh", "Marbles" , 29);
		repository.createAccount(json.getJSONForObject(account2));
		repository.accounts.put((long)2,account2);
		assertEquals("The method should have returned 0 matches", 2, repository.countForFirstName("Josh"));
	}

}
