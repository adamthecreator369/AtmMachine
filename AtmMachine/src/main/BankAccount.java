/* Created by Adam Jost on 08/07/2021 */
package main;

import java.util.Random;

public class BankAccount {

	private int number;
	private int pinNumber;
	private int cardNumber;
	private double actualBalance;
	private double availableBalance;
	private String ownersFullName;

	public BankAccount(int accountNumber, int pinNumber, double balance, String ownersName) {
		this.number = accountNumber;
		this.pinNumber = pinNumber;
		this.actualBalance = balance;
		this.availableBalance = balance;
		this.ownersFullName = ownersName;
		this.cardNumber = generateRandomCardNumber();
	}
	
	public int generateRandomCardNumber() {
		Random random = new Random();
		int cardNumber = random.nextInt();
		if (cardNumber < 0) {
			cardNumber *= -1;
		}
		return cardNumber;
	}

	public int getAccountNumber() {
		return this.number;
	}
	
	public int getCardNumber() {
		return this.cardNumber;
	}

	public int getPinNumber() {
		return this.pinNumber;
	}

	public double getActualBalance() {
		return this.actualBalance;
	}
	
	public double getAvailableBalance() {
		return this.availableBalance;
	}

	public String getOwnersName() {
		return this.ownersFullName;
	}

	public void setAccountBalance(double newBalance) {
		setAvailableBalance(newBalance);
		this.actualBalance = newBalance;
	}
	
	public void setAvailableBalance(double newBalance) {
		boolean isDepositTransaction = actualBalance < newBalance;
		if (isDepositTransaction) {
			double tenPercentOfDepositedFunds = (newBalance - actualBalance) / 10;
			availableBalance = actualBalance + tenPercentOfDepositedFunds;
		} else {
			double amountDecreased = actualBalance - newBalance;
			availableBalance = availableBalance - (amountDecreased);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %.2f %s", getAccountNumber(), getPinNumber(), getActualBalance(), getOwnersName());
	}
}
