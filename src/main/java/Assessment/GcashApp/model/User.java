package Assessment.GcashApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    // ================= PRIMARY KEY =================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= COLUMNS =================
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String number;

    @Column(nullable = false, length = 255)
    private String pin;

    // ================= CONSTRUCTORS =================
    public User() {
    }

    public User(String name, String email, String number, String pin) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.pin = pin;
    }

    // ================= GETTERS =================
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    // ================= SETTERS =================
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    // ================= TO STRING =================
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}