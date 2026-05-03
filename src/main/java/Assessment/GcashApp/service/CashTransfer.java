package Assessment.GcashApp.service;

import Assessment.GcashApp.model.Balance;
import Assessment.GcashApp.model.Transaction;
import Assessment.GcashApp.model.User;
import Assessment.GcashApp.repository.BalanceRepository;
import Assessment.GcashApp.repository.TransactionRepository;
import Assessment.GcashApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class CashTransfer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ================= CASH TRANSFER =================
    public String cashTransfer(User sender, String receiverEmail, double amount) {

        DecimalFormat df = new DecimalFormat("#,##0.00");


        // 1. Login check
        if (sender == null) {
            return "No user logged in.";
        }

        // 2. Amount check
        if (amount <= 0) {
            return "Invalid amount.";
        }

        // 3. Receiver check
        Optional<User> optionalReceiver =
                userRepository.findByEmail(receiverEmail);

        if (optionalReceiver.isEmpty()) {
            return "Receiver account not found.";
        }

        User receiver = optionalReceiver.get();

        // 4. Own account restriction
        if (sender.getId().equals(receiver.getId())) {
            return "You cannot transfer to your own account.";
        }

        // 5. Get sender balance
        Optional<Balance> senderBalanceOpt =
                balanceRepository.findByUser(sender);

        if (senderBalanceOpt.isEmpty()) {
            return "Sender balance account not found.";
        }

        // 6. Get receiver balance
        Optional<Balance> receiverBalanceOpt =
                balanceRepository.findByUser(receiver);

        if (receiverBalanceOpt.isEmpty()) {
            return "Receiver balance account not found.";
        }

        Balance senderBalance = senderBalanceOpt.get();
        Balance receiverBalance = receiverBalanceOpt.get();

        // 7. Insufficient funds
        if (senderBalance.getAmount() < amount) {
            return "Insufficient balance.";
        }

        // 8. Daily style restriction example
        if (amount > 50000) {
            return "Transfer exceeds maximum single transaction limit.";
        }

        // 9. Perform transfer
        senderBalance.setAmount(senderBalance.getAmount() - amount);
        receiverBalance.setAmount(receiverBalance.getAmount() + amount);

        balanceRepository.save(senderBalance);
        balanceRepository.save(receiverBalance);

        // 10. Save transaction record
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setName("CASH TRANSFER");
        tx.setAccountId(sender.getId());
        tx.setDate(LocalDateTime.now());
        tx.setTransferFromId(sender.getId());
        tx.setTransferToId(receiver.getId());


        transactionRepository.save(tx);

        return "Transfer successful: ₱" + df.format(amount) +
                " sent to " + receiver.getName();
    }
}