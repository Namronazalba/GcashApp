package Assessment.GcashApp.service;

import Assessment.GcashApp.model.Transaction;
import Assessment.GcashApp.model.User;
import Assessment.GcashApp.repository.UserRepository;
import Assessment.GcashApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class Transactions {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    // ================= VIEW ALL TRANSACTIONS (SUMMARY ONLY) =================
    public String viewAll() {

        List<Transaction> list = transactionRepository.findAll();

        if (list.isEmpty()) {
            return "No transactions found.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n===== ALL TRANSACTIONS =====\n");

        for (Transaction tx : list) {
            sb.append(formatTransactionSummary(tx)).append("\n");
        }

        return sb.toString();
    }

    // ================= VIEW USER ALL (SUMMARY ONLY) =================
    public String viewUserAll(Long userId) {

        List<Transaction> list =
                transactionRepository.findByAccountId(userId);

        if (list.isEmpty()) {
            return "No transactions found for User ID: " + userId;
        }

        // SORT NEWEST FIRST
        list.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        Map<String, List<Transaction>> grouped =
                list.stream().collect(Collectors.groupingBy(
                        tx -> tx.getDate().toLocalDate().format(dateFormat),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        StringBuilder sb = new StringBuilder();
        sb.append("\n===== USER TRANSACTIONS =====\n");

        for (Map.Entry<String, List<Transaction>> entry : grouped.entrySet()) {

            sb.append("\n").append(entry.getKey()).append("\n");

            for (Transaction tx : entry.getValue()) {
                sb.append(formatTransactionSummary(tx)).append("\n");
            }
        }

        return sb.toString();
    }

    // ================= VIEW SINGLE TRANSACTION (FULL DETAILS) =================
    public String viewTransaction(Long transactionId) {

        Optional<Transaction> optionalTx =
                transactionRepository.findById(transactionId);

        if (optionalTx.isEmpty()) {
            return "Transaction not found.";
        }

        return "\n===== TRANSACTION DETAILS =====\n"
                + formatTransaction(optionalTx.get());
    }

    // ================= SUMMARY FORMAT =================
    private String formatTransactionSummary(Transaction tx) {

        return "ID: " + tx.getId()
                + " | Type: " + tx.getName()
                + " | Amount: ₱" + df.format(tx.getAmount());
    }

    // ================= FULL FORMAT =================
    private String formatTransaction(Transaction tx) {

        String result =
                "ID: " + tx.getId()
                        + " | Type: " + tx.getName()
                        + " | Amount: ₱" + df.format(tx.getAmount())
                        + " | Date and time: " + tx.getDate(); // or getTimestamp()

        if ("CASH TRANSFER".equals(tx.getName())) {

            String toEmail = "N/A";
            String fromEmail = "N/A";

            if (tx.getTransferToId() != null) {
                User toUser = userRepository.findById(tx.getTransferToId())
                        .orElse(null);

                if (toUser != null) {
                    toEmail = toUser.getEmail();
                }
            }

            if (tx.getTransferFromId() != null) {
                User fromUser = userRepository.findById(tx.getTransferFromId())
                        .orElse(null);

                if (fromUser != null) {
                    fromEmail = fromUser.getEmail();
                }
            }

            result += " | To: " + toEmail
                    + " | From: " + fromEmail;
        }

        return result;
    }
}