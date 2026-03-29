import java.io.*;
import java.util.*;

interface BankOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void display();
}

abstract class Account {
    protected String name;
    protected int accountNumber;
    protected double balance;

    public Account(String name, int accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public abstract void display();
}

class SavingsAccount extends Account implements BankOperations {

    public SavingsAccount(String name, int accountNumber, double balance) {
        super(name, accountNumber, balance);
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Amount Deposited Successfully!");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient Balance!");
        } else {
            balance -= amount;
            System.out.println("Withdrawal Successful!");
        }
    }

    public void display() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
}

class BankSystem {
    private static List<SavingsAccount> accounts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account");
            System.out.println("5. Save Data");
            System.out.println("6. Load Data");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> display();
                case 5 -> saveToFile();
                case 6 -> loadFromFile();
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid Choice!");
            }

        } while (choice != 7);
    }

    static void createAccount() {
        System.out.print("Enter Name: ");
        String name = sc.next();

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        System.out.print("Enter Initial Balance: ");
        double bal = sc.nextDouble();

        accounts.add(new SavingsAccount(name, accNo, bal));
        System.out.println("Account Created Successfully!");
    }

    static SavingsAccount findAccount(int accNo) {
        for (SavingsAccount acc : accounts) {
            if (acc.accountNumber == accNo) {
                return acc;
            }
        }
        return null;
    }

    static void deposit() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        SavingsAccount acc = findAccount(accNo);
        if (acc != null) {
            System.out.print("Enter Amount: ");
            double amt = sc.nextDouble();
            acc.deposit(amt);
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void withdraw() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        SavingsAccount acc = findAccount(accNo);
        if (acc != null) {
            System.out.print("Enter Amount: ");
            double amt = sc.nextDouble();
            acc.withdraw(amt);
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void display() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        SavingsAccount acc = findAccount(accNo);
        if (acc != null) {
            acc.display();
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank.dat"))) {
            oos.writeObject(accounts);
            System.out.println("Data Saved Successfully!");
        } catch (Exception e) {
            System.out.println("Error Saving Data!");
        }
    }

    static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank.dat"))) {
            accounts = (ArrayList<SavingsAccount>) ois.readObject();
            System.out.println("Data Loaded Successfully!");
        } catch (Exception e) {
            System.out.println("No Data Found!");
        }
    }
}
