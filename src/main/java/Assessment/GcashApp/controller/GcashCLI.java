package Assessment.GcashApp.controller;

import Assessment.GcashApp.service.CheckBalance;
import Assessment.GcashApp.service.UserAuthentication;
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
                System.out.println("3. Logout");
                System.out.print("Choose: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Numbers only.");
                    sc.nextLine();
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice < 1 || choice > 3) {
                    System.out.println("Choice must be between 1 to 2.");
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
                        System.out.println(auth.logout());
                        break;
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