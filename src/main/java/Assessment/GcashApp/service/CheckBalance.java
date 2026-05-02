package Assessment.GcashApp.service;

import Assessment.GcashApp.model.Balance;
import Assessment.GcashApp.model.User;
import Assessment.GcashApp.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class CheckBalance {

    @Autowired
    private BalanceRepository ballanceRepository;

    // ================= CHECK BALANCE =================
    public String checkBalance(User user) {

        if (user == null) {
            return "No user logged in";
        }

        Optional<Balance> balance = ballanceRepository.findByUser(user);

        if (balance.isEmpty()) {
            return "No balance found for this user";
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
//        return "Current Balance: " + balance.get().getAmount();
        return "Current Balance: ₱" + df.format(balance.get().getAmount());
    }
}