package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Author;

// Repository interface for performing database operations on Author entities
public interface AuthorDao extends JpaRepository<Author, Long> {

	// Finds an author by name
	Optional<Author> findByAuthorName(String authorName);
}
