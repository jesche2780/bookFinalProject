package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Borrower;

public interface BorrowerDao extends JpaRepository<Borrower, Long> {
	Optional<Borrower> findByBorrowerName(String borrowerName);
}
