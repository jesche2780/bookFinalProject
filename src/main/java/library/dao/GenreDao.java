package library.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {
	Optional<Genre> findByGenreName(String genreName);
}
