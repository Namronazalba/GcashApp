package Assessment.GcashApp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String name;

    private Long accountId;

    private LocalDateTime date;

    private Long transferToId;
    private Long transferFromId;

    // getters & setters

    public Long getId() { return id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getTransferToId() { return transferToId; }
    public void setTransferToId(Long transferToId) { this.transferToId = transferToId; }

    public Long getTransferFromId() { return transferFromId; }
    public void setTransferFromId(Long transferFromId) { this.transferFromId = transferFromId; }
}