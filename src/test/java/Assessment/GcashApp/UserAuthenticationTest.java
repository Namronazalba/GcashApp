package Assessment.GcashApp;

import Assessment.GcashApp.service.UserAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserAuthenticationTest {

    @Autowired
    private UserAuthentication auth;

    @Test
    void validLogin() {
        String result = auth.login("norman@email.com", "1234");
        assertTrue(result.contains("Login successful"));
    }

    @Test
    void invalidLogin() {
        String result = auth.login("wrong@gmail.com", "9999");
        assertTrue(result.contains("User not found")
                || result.contains("Incorrect"));
    }
}