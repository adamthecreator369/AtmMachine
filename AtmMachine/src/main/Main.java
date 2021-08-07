/* Created by Adam Jost on 08/07/2021 */
package main;

public class Main {
	public static void main(String[] args) {
		Atm atm = new Atm();
		boolean running = true;
		
		while (running) {
			atm.printLoginScreenHeading();
			atm.getAndValidateAccountNumber();
			atm.getAndValidatePinNumber();
			if (atm.isLoggedIn()) {
				atm.printNowLoggedInMsg();
				atm.printNewScreenGap();
				atm.printScreenHeadingAndCommandKey();
			}
			while (atm.isLoggedIn()) {
				String commandEntered = atm.getUserCommand();
				switch (commandEntered) {
				case "deposit":
					atm.getAndValidateTransactionAmount();
					atm.depositTransactionAmountIntoAccount();
					atm.printProcessingReceiptMsg();
					atm.printReceipt();
					atm.printScreenHeadingAndCommandKey();
					break;
				case "withdrawal":
					atm.getAndValidateTransactionAmount();
					if (atm.withdrawalTransactionAmountFromAccount()) {
						atm.printProcessingReceiptMsg();
						atm.printReceipt();
						atm.printScreenHeadingAndCommandKey();
					}
					break;
				case "balance":
					atm.printAccountBalance();
					break;
				case "logout":
					atm.printThankYouMsg();
					atm.logoutOfCurrentAccount();
					atm.printNewScreenGap();
					break;
				default:
					atm.printInvalidCommandMsg(commandEntered);
				}
				atm.resetTransActionAmount();
			}
		}
	}
}


