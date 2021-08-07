package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Atm {

	private final int GUI_WIDTH, RECEIPT_WIDTH;
	private final String WELCOME_MSG;
	private Scanner userInput;
	private BankAccountList bankAccounts;
	private BankAccount currentAccount;
	private int accountNumber;
	private int pinNumber;
	private String transactionType;
	private double transactionAmount;

	public Atm() {
		this.GUI_WIDTH = 60;
		this.RECEIPT_WIDTH = 40;
		this.WELCOME_MSG = "WELCOME TO SAINT CHARLES COMMUNITY BANK ATM";
		this.bankAccounts = getBankAccountDataFromFile();
		this.userInput = new Scanner(System.in);
		this.currentAccount = null;
		this.accountNumber = 0;
		this.pinNumber = 0;
		this.transactionAmount = 0;
	}

	/**
	 * Creates the BankAccountList containing many BankAccounts created using parsed
	 * data from an input data file.
	 * 
	 * @return a list of all the current BankAccounts.
	 */
	private BankAccountList getBankAccountDataFromFile() {
		BankAccountList bankAccounts = new BankAccountList();
		FileInputStream accountsFileStream = null;
		String inputFileName = "accounts.txt";
		try {
			accountsFileStream = new FileInputStream(inputFileName);
			Scanner accountsData = new Scanner(accountsFileStream);

			while (accountsData.hasNext()) {
				int accountNumber = accountsData.nextInt();
				int pinNumber = accountsData.nextInt();
				double accountBalance = accountsData.nextDouble();
				String ownersName = accountsData.nextLine().trim();
				bankAccounts.addAccount(new BankAccount(accountNumber, pinNumber, accountBalance, ownersName));
			}

			accountsData.close();
			accountsFileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bankAccounts;
	}

	/**
	 * Generate a symbolic transaction number representing the reference number of
	 * the current transaction.
	 * 
	 * @return the generated unsigned transaction number.
	 */
	private String generateRandomTransactionNumber() {
		Random random = new Random();
		int transactionNumber = random.nextInt();
		if (transactionNumber < 0) {
			transactionNumber *= -1;
		}
		return String.valueOf(transactionNumber);
	}

	/**
	 * Formats and returns the current date in the specified String format.
	 * 
	 * @return the current date in String format.
	 */
	private String getFormattedDate() {
		Date date = getCurrentDateAndTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);
	}

	/**
	 * Formats and returns the current time in the specified String format.
	 * 
	 * @return the current time in String format.
	 */
	private String getFormattedTime() {
		Date date = getCurrentDateAndTime();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");
		return timeFormat.format(date);
	}

	/**
	 * Creates and returns a Date object using the current date and time.
	 * 
	 * @return a Date object.
	 */
	private Date getCurrentDateAndTime() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return date;
	}

	/**
	 * Resets the transaction amount to its default value.
	 */
	public void resetTransActionAmount() {
		this.transactionAmount = resetNumber();
	}

	/**
	 * Returns the default value of number related objects used for processing
	 * transactions.
	 * 
	 * @return the default value of 0.
	 */
	private int resetNumber() {
		return 0;
	}

	/**
	 * Formats an argument String to U.S. currency format.
	 * 
	 * @param amount: the dollar amount to be formatted.
	 * @return the dollar amount in U.S. currency format (e.g. $1,002,000.20).
	 */
	private String formatCurrency(double amount) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		return currencyFormat.format(amount);
	}

	/**
	 * Prints the login screen heading to the console.
	 */
	public void printLoginScreenHeading() {
		printScreenHeading();
		printLoginScreenSubHeading();
	}

	/**
	 * Prints the heading containing the welcome message in between two separator
	 * lines.
	 */
	private void printScreenHeading() {
		printSeparator('=');
		printWelcomeMsg();
		printSeparator('=');
	}

	/**
	 * Prints the welcome message to the console.
	 */
	private void printWelcomeMsg() {
		printCenteredText(WELCOME_MSG);
	}

	/**
	 * Prints the login heading followed by a separator line to the console.
	 */
	private void printLoginScreenSubHeading() {
		printCenteredText("Login to Continue - Type \"QUIT\" to Quit");
		printSeparator('-');
	}

	/**
	 * Prints the heading and command key to the console.
	 */
	public void printScreenHeadingAndCommandKey() {
		printScreenHeading();
		printCommandKey();
	}

	/**
	 * Prints the command key to the console.
	 */
	private void printCommandKey() {
		printCenteredText("Available Commands List");
		printSeparator('-');
		printCommandsAndDescriptions();
		printSeparator('=');
		printCenteredText("Type any above command to continue");
		printSeparator('=');
	}

	/**
	 * Prints a list of commands and their descriptions to the console.
	 */
	private void printCommandsAndDescriptions() {
		String[] commands = new String[] { "DEPOSIT", "WITHDRAWAL", "BALANCE", "LOGOUT", "QUIT" };
		String[] commandDescriptions = new String[] { "Deposit money into your account",
				"Withdrawal money from your account", "View the current balance of your account",
				"Logout of the current account", "Quit and exit the system" };
		int i = 0;

		while (i < commands.length) {
			System.out.println(commands[i] + " - " + commandDescriptions[i]);
			i++;
		}
	}

	/**
	 * Prints a separator line consisting of the argument character that is the
	 * exact width of the GUI.
	 * 
	 * @param separatorChar: the character used to create the separator line.
	 */
	private void printSeparator(char separatorChar) {
		for (int i = 0; i < GUI_WIDTH; i++) {
			System.out.print(separatorChar);
		}
		System.out.println();
	}

	/**
	 * Prints text to the console exactly centered to the middle of the GUI.
	 * 
	 * @param textToCenter: the text to be printed.
	 */
	private void printCenteredText(String textToCenter) {
		int halfOfGui = GUI_WIDTH / 2;
		int halfOfText = textToCenter.length() / 2;
		int numSpacesNeededToCenter = halfOfGui - halfOfText;
		char blankSpace = ' ';

		for (int i = 0; i < numSpacesNeededToCenter; i++) {
			System.out.print(blankSpace);
		}
		System.out.println(textToCenter);
	}

	/**
	 * Prints a symbolic gap representing the idea that the system has moved to a
	 * new screen.
	 */
	public void printNewScreenGap() {
		System.out.println("\n\n");
	}

	/**
	 * Gets the user command entered by the end user.
	 * 
	 * @return the entered command in String format.
	 */
	public String getUserCommand() {
		String command = "";
		boolean hasEnteredACommand = false;
		while (!hasEnteredACommand) {
			printEnterCommandMsg();
			command = getUserInput();
			hasEnteredACommand = !command.equals("");
		}
		return command.toLowerCase();
	}

	/**
	 * Prints a message to the console prompting the user to enter a command.
	 */
	private void printEnterCommandMsg() {
		System.out.println("Please enter the command you wish to perform: ");
	}

	/**
	 * Prints a message to the console informing the user the command entered is
	 * invalid.
	 * 
	 * @param command: the invalid command entered by the user.
	 */
	public void printInvalidCommandMsg(String command) {
		System.out.println("* Error: " + command + " is not a valid command.");
	}

	/**
	 * Reads in the next line of input from the console and returns it in String
	 * format.
	 * 
	 * @return the input String.
	 */
	private String getUserInput() {
		String input = userInput.nextLine().trim();
		exitSystemIfUserHasQuit(input);
		return input;
	}

	/**
	 * Continuously prompts the user to enter their account number until a valid
	 * account number is entered, printing an error message to the console if the
	 * account number does not exist in the system.
	 */
	public void getAndValidateAccountNumber() {
		boolean accountNumberIsValid = false;

		while (!accountNumberIsValid) {
			printEnterAccountNumberMsg();
			getAccountNumberInput();
			accountNumberIsValid = accountNumber != 0;
			if (accountNumberIsValid) {
				currentAccount = bankAccounts.getAccount(accountNumber);
				boolean accountDoesNotExist = currentAccount == null;
				if (accountDoesNotExist) {
					printAccountDoesNotExistMsg(accountNumber);
					accountNumber = resetNumber();
				}
			}
			accountNumberIsValid = accountNumber != 0;
		}
	}

	/**
	 * Prints a message to the console prompting the user to enter their 7 digit
	 * account number.
	 */
	private void printEnterAccountNumberMsg() {
		System.out.println("Please enter your 7 digit account number: ");
	}

	/**
	 * Gets and parses the user-entered account number printing an error message to
	 * the console when a NumberFormatException is thrown.
	 */
	private void getAccountNumberInput() {
		try {
			accountNumber = Integer.parseInt(getUserInput());
		} catch (NumberFormatException e) {
			printInvalidAccountNumberMsg();
		}
	}

	/**
	 * Prints a message to the console informing the user that they have entered an
	 * invalid account number.
	 */
	private void printInvalidAccountNumberMsg() {
		System.out.println("* Error: You have entered an invalid account number.");
	}

	/**
	 * Prints a message to the console informing the user that entered account
	 * number does not match any account within the system.
	 * 
	 * @param accountNumber: the nonexistent account number entered by the user.
	 */
	private void printAccountDoesNotExistMsg(int accountNumber) {
		System.out.printf("* Error: Account # %s does not exist in our system.\n", accountNumber);
	}

	/**
	 * Gets and validates an user-entered pin number.  
	 */
	public void getAndValidatePinNumber() {
		boolean pinIsValid = false;
		int invalid = 0;
		int loginAttempts = 0;
		while (!pinIsValid) {
			if (++loginAttempts == 4) {
				printTooManyAttemptsMsg();
				logoutOfCurrentAccount();
				break;
			}
			printEnterPinNumberMsg();
			getPinNumberInput();
			pinIsValid = pinNumber != invalid;
			if (pinIsValid) {
				if (!isCorrectPinNumber()) {
					pinIsValid = false;
					printPinDoesNotMatchMsg();
				}
			}
		}
	}

	/**
	 * Prints a message to the console prompting the user to enter their 4 digit pin
	 * number.
	 */
	private void printEnterPinNumberMsg() {
		System.out.println("Please enter your 4 digit pin number: ");
	}

	/**
	 * Gets a pin number entered by the user. An error message is printed to the
	 * console if a NumberFormatException is thrown while attempting to parse the
	 * input String to an int.
	 */
	private void getPinNumberInput() {
		try {
			pinNumber = Integer.parseInt(getUserInput());
		} catch (NumberFormatException e) {
			printInvalidPinNumberMsg();
		}
	}

	/**
	 * Prints a message to the console informing the user that the pin number
	 * entered is invalid.
	 */
	private void printInvalidPinNumberMsg() {
		System.out.println("* Error: You have entered an invalid pin number.");
	}

	/**
	 * Checks that the entered pin number matches the pin number of the account that
	 * the user is attempting to log in to.
	 * 
	 * @return {true} if the pin numbers are indeed matching; {false} otherwise.
	 */
	private boolean isCorrectPinNumber() {
		return pinNumber == currentAccount.getPinNumber();
	}

	/**
	 * Prints a message to the console informing the user that the pin number match
	 * does not match the pin number of the account they are attempting to log in
	 * to.
	 */
	private void printPinDoesNotMatchMsg() {
		System.out.println("* Error: The pin number entered does not match our records.");
	}

	/**
	 * Prints a message to the console informing the user that they have exceeded
	 * the max amount of failed login attempts allowed.
	 */
	private void printTooManyAttemptsMsg() {
		System.out.println("* Error: Too many failed login attemps. Goodbye.");
		printNewScreenGap();
	}

	/**
	 * Prints a message to the console informing the user that they have been logged
	 * into the system.
	 */
	public void printNowLoggedInMsg() {
		printSeparator('=');
		printCenteredText("You are now logged in as " + currentAccount.getOwnersName());
		printSeparator('=');
	}

	/**
	 * Checks if the system is currently logged into an account.
	 * 
	 * @return {true} if an account is been logged into; {false} otherwise.
	 */
	public boolean isLoggedIn() {
		return currentAccount != null && pinNumber == currentAccount.getPinNumber();
	}

	/**
	 * Exits the system after updating all account data in the system (printing the output file) and printing a thank you message when
	 * the "QUIT" command is entered by the user.
	 * 
	 * @param command
	 */
	private void exitSystemIfUserHasQuit(String command) {
		if (command.equalsIgnoreCase("quit")) {
			printThankYouMsg();
			printUpdatedInfoToBankAccountDataFile();
			System.exit(-1);
		}
	}

	/**
	 * Prints the current account's available and actual balances to the console.
	 */
	public void printAccountBalance() {
		System.out.printf("Available balance: %41s\n" + "Total balance: %45s\n",
				String.format("%s", formatCurrency(currentAccount.getAvailableBalance())),
				String.format("%s", formatCurrency(currentAccount.getActualBalance())));
	}

	/**
	 * Gets and validates an user-entered transaction amount. If transaction amount
	 * is found to be invalid the transaction is not processed and a error message
	 * is printed to the console.
	 */
	public void getAndValidateTransactionAmount() {
		while (!isValidTransactionAmount()) {
			printEnterTransactionAmountMsg();
			try {
				transactionAmount = Integer.parseInt(getUserInput());
				rejectTransactionIfAmountIsInvalid();
			} catch (IllegalArgumentException e) {
				transactionAmount = resetNumber();
				printInvalidTransactionAmountMsg();
			}
		}
	}

	/**
	 * Prints a message to the console prompting the user to enter a transaction
	 * amount.
	 */
	private void printEnterTransactionAmountMsg() {
		System.out.println("Please enter the transaction amount (e.g. \"25\"): ");
	}

	/**
	 * Checks if the entered transaction amount is valid.
	 * 
	 * @return {true} if the transaction amount is greater than zero; {false}
	 *         otherwise.
	 */
	private boolean isValidTransactionAmount() {
		return transactionAmount != 0 && !isNegativeAmount();
	}

	/**
	 * Checks if the entered transaction amount is a negative amount.
	 * 
	 * @return {true} if less than zero; {false} otherwise.
	 */
	private boolean isNegativeAmount() {
		return transactionAmount < 0;
	}

	/**
	 * "Rejects" a transaction by throwing an IllegalArgumentException if the
	 * transaction amount is found not to be valid.
	 */
	private void rejectTransactionIfAmountIsInvalid() {
		if (!isValidTransactionAmount()) {
			transactionAmount = resetNumber();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Prints a message to the console informing the user that the entered
	 * transaction amount is not valid.
	 */
	private void printInvalidTransactionAmountMsg() {
		System.out.println("* Error: The transaction amount entered is not valid.");
	}

	/**
	 * Makes a deposit into the currently logged in account increasing its balance
	 * by the transaction amount.
	 */
	public void depositTransactionAmountIntoAccount() {
		String transactionType = "Deposit";
		double newBalance = currentAccount.getActualBalance() + transactionAmount;
		currentAccount.setAccountBalance(newBalance);
		setTransactionType(transactionType);
	}

	/**
	 * Makes a withdrawal from the currently logged in account decreasing its
	 * balance by the transaction amount.
	 * 
	 * @return {true} if enough funds exist to process the withdrawal; {false}
	 *         otherwise.
	 */
	public boolean withdrawalTransactionAmountFromAccount() {
		String transactionType = "Withdrawal";
		if (isNotEnoughFunds()) {
			printNotEnoughFundsMsg();
			return false;
		} else {
			double newBalance = currentAccount.getActualBalance() - transactionAmount;
			currentAccount.setAccountBalance(newBalance);
			setTransactionType(transactionType);
			return true;
		}
	}

	/**
	 * Set the transaction type.
	 * 
	 * @param transactionType: Either {"Withdrawal"} or {"Deposit"}.
	 */
	private void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Checks if the current account has enough funds to process the current
	 * transaction.
	 * 
	 * @return: {true} if enough funds exist; {false} otherwise.
	 */
	private boolean isNotEnoughFunds() {
		boolean notEnoughFunds = currentAccount.getAvailableBalance() - transactionAmount < 0;
		return notEnoughFunds;
	}

	/**
	 * Prints a message to the console informing the user that they do not have
	 * enough funds to process the current transaction.
	 */
	private void printNotEnoughFundsMsg() {
		System.out.println("* Error: Not have enough funds to perform this trasaction.");
	}

	/**
	 * Prints a message to the console informing the user that their transaction
	 * receipt is being printed.
	 */
	public void printProcessingReceiptMsg() {
		printSeparator('=');
		printCenteredText("Processing Transaction and Printing Receipt");
		printSeparator('=');
	}

	/**
	 * Prints a transaction receipt to the console.
	 */
	public void printReceipt() {
		printNewScreenGap();
		printReceiptHeading();
		printReceiptBody();
		printReceiptFooter();
		printNewScreenGap();
	}

	/**
	 * Prints the receipt heading to the console consisting of the title and two
	 * separator lines.
	 */
	private void printReceiptHeading() {
		printReceiptSeparator('*');
		printReceiptCenteredText("ATM TRANSACTION RECEIPT");
		printReceiptSeparator('*');
	}

	/**
	 * Prints the receipt body to the console consisting of all the details of the
	 * current transaction.
	 */
	private void printReceiptBody() {
		String date = String.format("Date: %34s", getFormattedDate());
		String time = String.format("Time: %34s", getFormattedTime());
		String location = String.format("Location: %30s", "12 Mid Rivers Dr");
		String atm = String.format("ATM: %35s", "4512");
		String cardNumber = String.format("Customer Card: %25s", currentAccount.getCardNumber());
		String transactionNum = String.format("Transaction #: %25.2s", generateRandomTransactionNumber());
		String typeOfTransaction = String.format("Transaction: %27s", "Checking " + transactionType);
		String amountOfTransaction = String.format("Amount: %32s", formatCurrency(transactionAmount));
		String accountNum = String.format("From Account #: %24s", currentAccount.getAccountNumber());
		String currentAvailableBalance = String.format("Available Balance: %21s",
				formatCurrency(currentAccount.getAvailableBalance()));
		String currentTotalBalance = String.format("Total Balance: %25s",
				formatCurrency(currentAccount.getActualBalance()));
		String thankYouMsg = "Thank you for using our ATM.\nFor questions, call 1-800-869-3557\nBusiness customers call 1-800-255-5935\n";
		System.out.printf("\n%s\n%s\n%s\n%s\n\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n%s\n", date, time, location, atm,
				cardNumber, transactionNum, typeOfTransaction, amountOfTransaction, accountNum, currentAvailableBalance,
				currentTotalBalance, thankYouMsg);
	}

	/**
	 * Prints the receipt footer to the console which consists of a separator line
	 * followed by the banks name and FDIC information.
	 */
	private void printReceiptFooter() {
		printReceiptSeparator('*');
		System.out.println();
		printReceiptCenteredText("Saint Charles Community Bank");
		printReceiptCenteredText("N.A. Member FDIC");
	}

	/**
	 * Prints a separating line consisting of an argument character to the width of
	 * the receipt.
	 * 
	 * @param separatorChar: the character printed repeatedly to create the
	 *        separator.
	 */
	private void printReceiptSeparator(char separatorChar) {
		for (int i = 0; i < RECEIPT_WIDTH; i++) {
			System.out.print(separatorChar);
		}
		System.out.println();
	}

	/**
	 * Prints an argument String to the console centered to the width of the
	 * receipt.
	 * 
	 * @param textToCenter: the text to print.
	 */
	private void printReceiptCenteredText(String textToCenter) {
		int halfOfReceipt = (RECEIPT_WIDTH) / 2;
		int halfOfText = textToCenter.length() / 2;
		int numSpacesNeededToCenter = halfOfReceipt - halfOfText;
		char blankSpace = ' ';
		for (int i = 0; i < numSpacesNeededToCenter; i++) {
			System.out.print(blankSpace);
		}
		System.out.println(textToCenter);
	}

	/**
	 * Logs out the currently logged in bank account.
	 */
	public void logoutOfCurrentAccount() {
		currentAccount = null;
		accountNumber = resetNumber();
		pinNumber = resetNumber();
	}

	/**
	 * Prints a thank you message to the console in between to separator lines.
	 */
	public void printThankYouMsg() {
		printSeparator('=');
		printCenteredText("Thank you for choosing Saint Charles Community Bank ATM");
		printSeparator('=');
	}

	/**
	 * Prints the data of all BankAccounts contained in the BankAccountsList to a
	 * output file.
	 */
	private void printUpdatedInfoToBankAccountDataFile() {
		FileOutputStream accountsFileStream = null;
		try {
			accountsFileStream = new FileOutputStream("accounts.txt");
			PrintWriter fileWriter = new PrintWriter(accountsFileStream);
			fileWriter.write(bankAccounts.toString());
			fileWriter.close();
			accountsFileStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
