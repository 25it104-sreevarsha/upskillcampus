import java.io.*;
import java.util.*;

/*
=========================================
      ADVANCED BANKING SYSTEM
=========================================
Features:
✔ Account Creation
✔ Deposit & Withdrawal
✔ Balance Inquiry
✔ Transaction History
✔ PIN Verification
✔ File Handling
✔ Exception Handling
✔ OOP Concepts
=========================================
*/

interface BankingOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void checkBalance();
}

abstract class Account implements Serializable {
    protected String customerName;
    protected int accountNumber;
    protected int pin;
    protected double balance;

    public Account(String customerName, int accountNumber, int pin, double balance) {
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public abstract void displayAccountDetails();
}

class SavingsAccount extends Account implements BankingOperations {

    private List<String> transactionHistory = new ArrayList<>();

    public SavingsAccount(String customerName, int accountNumber, int pin, double balance) {
        super(customerName, accountNumber, pin, balance);
        transactionHistory.add("Account Created with Balance: ₹" + balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid Deposit Amount!");
            return;
        }

        balance += amount;
        transactionHistory.add("Deposited: ₹" + amount);

        System.out.println("₹" + amount + " Deposited Successfully!");
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid Withdrawal Amount!");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= amount;
        transactionHistory.add("Withdrawn: ₹" + amount);

        System.out.println("₹" + amount + " Withdrawn Successfully!");
    }

    @Override
    public void checkBalance() {
        System.out.println("Current Balance: ₹" + balance);
    }

    public void showTransactionHistory() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    @Override
    public void displayAccountDetails() {

        System.out.println("\n===== ACCOUNT DETAILS =====");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance       : ₹" + balance);
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }
}

public class BankingInformationSystem {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<SavingsAccount> accounts = new ArrayList<>();

    public static void main(String[] args) {

        loadAccounts();

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("     BANKING INFORMATION SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Display Account Details");
            System.out.println("6. Transaction History");
            System.out.println("7. Save Accounts");
            System.out.println("8. Exit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");

            choice = getIntegerInput();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    checkBalance();
                    break;

                case 5:
                    displayAccount();
                    break;

                case 6:
                    transactionHistory();
                    break;

                case 7:
                    saveAccounts();
                    break;

                case 8:
                    saveAccounts();
                    System.out.println("Thank you for using Banking System!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 8);
    }

    static void createAccount() {

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        System.out.print("Set 4-digit PIN: ");
        int pin = getIntegerInput();

        System.out.print("Enter Initial Balance: ");
        double balance = getDoubleInput();

        SavingsAccount account =
                new SavingsAccount(name, accNo, pin, balance);

        accounts.add(account);

        System.out.println("Account Created Successfully!");
    }

    static SavingsAccount findAccount(int accNo) {

        for (SavingsAccount account : accounts) {

            if (account.accountNumber == accNo) {
                return account;
            }
        }

        return null;
    }

    static void depositMoney() {

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        SavingsAccount account = findAccount(accNo);

        if (account != null) {

            System.out.print("Enter Amount to Deposit: ");
            double amount = getDoubleInput();

            account.deposit(amount);

        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void withdrawMoney() {

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        SavingsAccount account = findAccount(accNo);

        if (account != null) {

            System.out.print("Enter PIN: ");
            int pin = getIntegerInput();

            if (account.verifyPin(pin)) {

                System.out.print("Enter Withdrawal Amount: ");
                double amount = getDoubleInput();

                account.withdraw(amount);

            } else {
                System.out.println("Incorrect PIN!");
            }

        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void checkBalance() {

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        SavingsAccount account = findAccount(accNo);

        if (account != null) {

            System.out.print("Enter PIN: ");
            int pin = getIntegerInput();

            if (account.verifyPin(pin)) {
                account.checkBalance();
            } else {
                System.out.println("Incorrect PIN!");
            }

        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void displayAccount() {

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        SavingsAccount account = findAccount(accNo);

        if (account != null) {
            account.displayAccountDetails();
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void transactionHistory() {

        System.out.print("Enter Account Number: ");
        int accNo = getIntegerInput();

        SavingsAccount account = findAccount(accNo);

        if (account != null) {
            account.showTransactionHistory();
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void saveAccounts() {

        try {

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream("accounts.dat"));

            output.writeObject(accounts);

            output.close();

            System.out.println("Accounts Saved Successfully!");

        } catch (Exception e) {

            System.out.println("Error Saving Accounts!");
        }
    }

    static void loadAccounts() {

        try {

            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream("accounts.dat"));

            accounts = (ArrayList<SavingsAccount>) input.readObject();

            input.close();

            System.out.println("Accounts Loaded Successfully!");

        } catch (Exception e) {

            System.out.println("No Previous Account Data Found.");
        }
    }

    static int getIntegerInput() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {

                System.out.print("Invalid Input! Enter Again: ");
            }
        }
    }

    static double getDoubleInput() {

        while (true) {

            try {
                return Double.parseDouble(scanner.nextLine());

            } catch (Exception e) {

                System.out.print("Invalid Input! Enter Again: ");
            }
        }
    }
}
