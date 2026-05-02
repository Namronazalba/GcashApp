package Assessment.GcashApp.controller;

import Assessment.GcashApp.service.CashTransfer;
import Assessment.GcashApp.service.CheckBalance;
import Assessment.GcashApp.service.UserAuthentication;
import Assessment.GcashApp.service.Cashin;
import Assessment.GcashApp.service.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GcashCLI implements CommandLineRunner {

    @Autowired
    private UserAuthentication auth;
    @Autowired
    private CheckBalance checkBalance;
    @Autowired
    private Cashin cashin;
    @Autowired
    private CashTransfer cashTransfer;
    @Autowired
    private Transactions transactions;
    @Override
    public void run(String... args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            // ================= AUTHENTICATION MENU =================
            while (auth.getLoggedInUser() == null) {

                System.out.println("\n===== GCASH AUTHENTICATION =====");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Forgot PIN / Reset PIN");
                System.out.println("4. Exit");
                System.out.print("Choose: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Numbers only.");
                    sc.nextLine();
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice < 1 || choice > 4) {
                    System.out.println("Choice must be between 1 to 4.");
                    continue;
                }

                switch (choice) {

                    case 1:
                        registerUser(sc);
                        break;

                    case 2:
                        loginUser(sc);
                        break;

                    case 3:
                        forgotPin(sc);
                        break;

                    case 4:
                        System.out.println("Exiting App...");
                        System.exit(0);
                        break;
                }
            }

            // ================= USER ACCOUNT MENU =================
            while (auth.getLoggedInUser() != null) {

                System.out.println("\n===== USER ACCOUNT =====");
                System.out.println("Welcome: " + auth.getLoggedInUser().getName());
                System.out.println("1. Change PIN");
                System.out.println("2. Check Balance");
                System.out.println("3. Cash In");
                System.out.println("4. Cash Transfer");
                System.out.println("5. My Transactions");
                System.out.println("6. View Transaction by ID");
                System.out.println("7. Logout");
                System.out.print("Choose: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Numbers only.");
                    sc.nextLine();
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice < 1 || choice > 7) {
                    System.out.println("Choice must be between 1 to 4.");
                    continue;
                }

                switch (choice) {

                    case 1:
                        changePin(sc);
                        break;
                    case 2:
                        System.out.println(checkBalance.checkBalance(auth.getLoggedInUser()));
                        break;
                    case 3:
                        System.out.print("Enter amount to cash in: ");

                        if (!sc.hasNextDouble()) {
                            System.out.println("Invalid input. Numbers only.");
                            sc.nextLine();
                            continue;
                        }

                        double amount = sc.nextDouble();
                        sc.nextLine();

                        System.out.println(cashin.cashIn(auth.getLoggedInUser(), amount));
                        break;
                    case 4:
                        System.out.print("Enter Receiver Email: ");
                        String receiverEmail = sc.nextLine();

                        if (receiverEmail == null || receiverEmail.isBlank()) {
                            System.out.println("Email is required.");
                            break;
                        }

                        if (!receiverEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                            System.out.println("Invalid email format.");
                            break;
                        }

                        System.out.print("Enter Amount to Transfer: ");

                        if (!sc.hasNextDouble()) {
                            System.out.println("Invalid input. Numbers only.");
                            sc.nextLine();
                            continue;
                        }

                        double transferAmount = sc.nextDouble();
                        sc.nextLine();

                        System.out.println(
                                cashTransfer.cashTransfer(
                                        auth.getLoggedInUser(),
                                        receiverEmail,
                                        transferAmount
                                )
                        );
                        break;
                    case 5:
                        System.out.println(
                                transactions.viewUserAll(
                                        auth.getLoggedInUser().getId()
                                )
                        );
                        break;

                    case 6:
                        // ================= SHOW USER AVAILABLE TRANSACTION IDS =================
                        System.out.println("Your Available Transaction IDs:");
                        System.out.println(
                                transactions.viewUserAll(
                                        auth.getLoggedInUser().getId()
                                )
                        );

                        System.out.print("\nEnter Transaction ID: ");

                        if (!sc.hasNextLong()) {
                            System.out.println("Invalid input. Numbers only.");
                            sc.nextLine();
                            break;
                        }

                        long txId = sc.nextLong();
                        sc.nextLine();

                        System.out.println(
                                transactions.viewTransaction(txId)
                        );
                        break;

                    case 7:
                        System.out.println(auth.logout());
                        break;
                }
                if (auth.getLoggedInUser() != null) {

                    System.out.print("\nDo you want another transaction? (y/n): ");
                    String again = sc.nextLine();

                    if (again.equalsIgnoreCase("n")) {
                        System.out.println(auth.logout());
                    }
                }
            }
        }
    }

    // ================= REGISTER =================
    private void registerUser(Scanner sc) {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Number: ");
        String number = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        System.out.println(auth.register(name, email, number, pin));
    }

    // ================= LOGIN =================
    private void loginUser(Scanner sc) {

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        System.out.println(auth.login(email, pin));
    }

    // ================= FORGOT PIN =================
    private void forgotPin(Scanner sc) {

        System.out.print("Enter Registered Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Registered Number: ");
        String number = sc.nextLine();

        System.out.print("Enter New PIN: ");
        String newPin = sc.nextLine();

        System.out.println(auth.changePin(email, number, newPin));
    }

    // ================= CHANGE PIN =================
    private void changePin(Scanner sc) {

        System.out.print("Enter Old PIN: ");
        String oldPin = sc.nextLine();

        System.out.print("Enter New PIN: ");
        String newPin = sc.nextLine();

        System.out.println(auth.changePin(oldPin, newPin));
    }
}