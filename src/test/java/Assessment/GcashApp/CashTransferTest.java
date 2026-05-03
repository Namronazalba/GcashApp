package Assessment.GcashApp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import Assessment.GcashApp.service.CashTransfer;
import Assessment.GcashApp.service.UserAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CashTransferTest {

    @Autowired
    private CashTransfer transfer;

    @Autowired
    private UserAuthentication auth;

    @Test
    void testTransfer() {

        auth.login("norman@email.com", "1234");

        String result =
                transfer.cashTransfer(
                        auth.getLoggedInUser(),
                        "nina@gmail.com",
                        100
                );

        assertTrue(result.contains("successful")
                || result.contains("Insufficient"));
    }
}