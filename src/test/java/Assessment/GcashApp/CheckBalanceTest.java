package Assessment.GcashApp;

import Assessment.GcashApp.model.User;

import Assessment.GcashApp.service.CheckBalance;
import Assessment.GcashApp.service.UserAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CheckBalanceTest {

    @Autowired
    private CheckBalance checkBalance;

    @Autowired
    private UserAuthentication auth;

    @Test
    void testBalance() {

        auth.login("norman@email.com", "1234");

        User user = auth.getLoggedInUser();

        String result = checkBalance.checkBalance(user);

        assertTrue(result.contains("₱"));
    }
}