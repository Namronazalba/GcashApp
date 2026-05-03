package Assessment.GcashApp;

import Assessment.GcashApp.service.Transactions;
import Assessment.GcashApp.service.UserAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
@ActiveProfiles("test")
public class TransactionsTest {

    @Autowired
    private Transactions transactions;

    @Autowired
    private UserAuthentication auth;

    @Test
    void testTransactionsDisplay() {

        auth.login("norman@email.com", "1234");

        String result =
                transactions.viewUserAll(
                        auth.getLoggedInUser().getId()
                );

        assertTrue(result.contains("ID:")
                || result.contains("No transactions"));
    }
}