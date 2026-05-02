package Assessment.GcashApp.service;

import Assessment.GcashApp.model.User;
import Assessment.GcashApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthentication {

    @Autowired
    private UserRepository userRepository;

    private User loggedInUser;

    // ================= REGISTER =================
    public String register(String name, String email, String number, String pin) {

        if (name == null || name.isBlank()) {
            return "Name is required";
        }

        if (email == null || !email.contains("@")) {
            return "Invalid email";
        }

        if (number == null || !number.matches("\\d{11}")) {
            return "Number must be exactly 11 digits";
        }

        if (pin == null || pin.length() != 4) {
            return "PIN must be 4 digits";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already registered";
        }

        User user = new User(name, email, number, pin);
        userRepository.save(user);

        return "Registration successful";
    }

    // ================= LOGIN =================
    public String login(String email, String pin) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (!user.getPin().equals(pin)) {
            return "Incorrect email or PIN";
        }

        loggedInUser = user;

        return "Login successful. User ID: " + user.getId();
    }

    // ================= CHANGE PIN =================
    public String changePin(String oldPin, String newPin) {

        if (loggedInUser == null) {
            return "No user logged in";
        }

        if (!loggedInUser.getPin().equals(oldPin)) {
            return "Old PIN is incorrect";
        }

        if (newPin.length() != 4) {
            return "New PIN must be 4 digits";
        }

        loggedInUser.setPin(newPin);
        userRepository.save(loggedInUser);

        return "PIN changed successfully";
    }
    // ================= RESET PIN =================
    public String changePin(String email, String number, String newPin) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (!user.getNumber().equals(number)) {
            return "Number does not match account";
        }

        if (newPin == null || newPin.length() != 4) {
            return "PIN must be 4 digits";
        }

        user.setPin(newPin);
        userRepository.save(user);

        return "PIN reset successful";
    }
    // ================= LOGOUT =================
    public String logout() {

        if (loggedInUser == null) {
            return "No active session";
        }

        loggedInUser = null;

        return "Logout successful";
    }

    // ================= CURRENT USER =================
    public User getLoggedInUser() {
        return loggedInUser;
    }
}