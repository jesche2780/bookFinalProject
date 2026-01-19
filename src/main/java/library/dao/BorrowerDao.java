package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Borrower;

// Repository interface for performing database operations on Borrower entities
public interface BorrowerDao extends JpaRepository<Borrower, Long> {

	// Finds a borrower by name
	Optional<Borrower> findByBorrowerName(String borrowerName);
}
