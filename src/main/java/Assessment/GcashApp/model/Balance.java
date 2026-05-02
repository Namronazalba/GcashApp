package Assessment.GcashApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "balance")
public class Balance {

    // ================= PRIMARY KEY =================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= AMOUNT =================
    @Column(nullable = false)
    private double amount;

    // ================= USER RELATION =================
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // ================= CONSTRUCTORS =================
    public Balance() {
    }

    public Balance(double amount, User user) {
        this.amount = amount;
        this.user = user;
    }

    // ================= GETTERS =================
    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    // ================= SETTERS =================
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ================= TO STRING =================
    @Override
    public String toString() {
        return "Ballance{" +
                "id=" + id +
                ", amount=" + amount +
                ", userId=" + user.getId() +
                '}';
    }
}