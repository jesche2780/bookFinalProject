package library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.controller.model.GenreData;
import library.dao.GenreDao;
import library.entity.Genre;

// REST controller for managing genres
@RestController
@RequestMapping("/genres")
public class GenreController {

	// DAO for performing Genre database operations
	private final GenreDao genreDao;

	// Constructor-based dependency injection
	public GenreController(GenreDao genreDao) {
		this.genreDao = genreDao;
	}

	// Creates a new genre
	@PostMapping
	public GenreData create(@RequestBody Map<String, Object> request) {
		// Build and save a new Genre entity
		Genre genre = new Genre();
		genre.setGenreName((String) request.get("genreName"));
		genre = genreDao.save(genre);

		// Convert the saved entity to a DTO
		return GenreData.builder().id(genre.getId()).genreName(genre.getGenreName()).build();
	}

	// Returns all genres
	@GetMapping
	public List<GenreData> getAll() {
		return genreDao.findAll().stream()
				// Map each Genre entity to a DTO
				.map(g -> GenreData.builder().id(g.getId()).genreName(g.getGenreName()).build()).toList();
	}
}
