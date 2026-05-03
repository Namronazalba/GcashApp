package Assessment.GcashApp;

import Assessment.GcashApp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CashinTest {

    @Autowired
    private Cashin cashin;

    @Autowired
    private UserAuthentication auth;

    @Autowired
    private CheckBalance checkBalance;

    @Test
    void testCashIn() {

        auth.login("norman@email.com", "1234");

        cashin.cashIn(auth.getLoggedInUser(), 500);

        String result =
                checkBalance.checkBalance(auth.getLoggedInUser());

        assertTrue(result.contains("₱"));
    }
}