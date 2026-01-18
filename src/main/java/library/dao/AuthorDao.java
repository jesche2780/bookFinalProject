package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {
	Optional<Author> findByAuthorName(String authorName);
}
