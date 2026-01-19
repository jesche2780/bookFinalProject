package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Genre;

// Repository interface for performing database operations on Genre entities
public interface GenreDao extends JpaRepository<Genre, Long> {

	// Finds a genre by name
	Optional<Genre> findByGenreName(String genreName);
}
