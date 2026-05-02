package Assessment.GcashApp.repository;

import Assessment.GcashApp.model.Balance;
import Assessment.GcashApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUser(User user);
}