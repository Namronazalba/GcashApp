package Assessment.GcashApp.service;

import Assessment.GcashApp.model.Balance;
import Assessment.GcashApp.model.Transaction;
import Assessment.GcashApp.model.User;
import Assessment.GcashApp.repository.BalanceRepository;
import Assessment.GcashApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.text.DecimalFormat;

@Service
public class Cashin {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ================= CASH IN =================
    public String cashIn(User user, double amount) {

        if (user == null) {
            return "No user logged in";
        }

        if (amount <= 0) {
            return "Invalid amount";
        }

        // 1. Find balance
        Optional<Balance> optionalBalance =
                balanceRepository.findByUser(user);

        if (optionalBalance.isEmpty()) {
            return "Balance account not found";
        }

        Balance balance = optionalBalance.get();

        // 2. Update balance
        balance.setAmount(balance.getAmount() + amount);
        balanceRepository.save(balance);

        // 3. Save transaction
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setName("CASH IN");
        tx.setAccountId(user.getId());
        tx.setDate(LocalDateTime.now());
        tx.setTransferFromId(null);
        tx.setTransferToId(user.getId());

        transactionRepository.save(tx);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "Cash In Successful: ₱" + df.format(amount);
    }
}