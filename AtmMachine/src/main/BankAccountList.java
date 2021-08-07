/* Created by Adam Jost on 08/07/2021 */
package main;

import java.util.ArrayList;

public class BankAccountList {
	private ArrayList<BankAccount> bankAccounts;
	
	public BankAccountList() {
		bankAccounts = new ArrayList<>();
	}
	
	/**
	 * Adds a new BankAccount to the list.
	 * @param newAccount: the BankAccount to be added. 
	 */
	public void addAccount(BankAccount newAccount) {
		bankAccounts.add(newAccount);
	}
	
	/**
	 * Gets a BankAccount with the matching account number from the BankAccount list. 
	 * @param accountNumber: the account number of the account to be retrieved.
	 * @return: the BankAccount with the matching account number if found; null otherwise. 
	 */
	public BankAccount getAccount(int accountNumber) {
		for (BankAccount currentAccount : bankAccounts) {
			if (currentAccount.getAccountNumber() == accountNumber) {
				return currentAccount;
			}
		}
		BankAccount isNonExistent = null;
		return isNonExistent;
	}
	
    @Override 
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for (BankAccount currentAccount : bankAccounts) {
    		sb.append(currentAccount.toString()).append("\n");
    	}
    	return sb.toString();
    }
}
