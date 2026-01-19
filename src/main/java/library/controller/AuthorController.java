package library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import library.controller.model.AuthorData;
import library.dao.AuthorDao;
import library.entity.Author;

//REST controller for managing authors
@RestController
@RequestMapping("/authors")
public class AuthorController {

	// DAO for performing Author database operations
	private final AuthorDao authorDao;

	// Constructor-based dependency injection
	public AuthorController(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	// Handles POST /authors to create a new Author
	@PostMapping
	public AuthorData create(@RequestBody Map<String, Object> request) {
		// Build and save a new Author entity
		Author author = new Author();
		author.setAuthorName((String) request.get("authorName"));
		author = authorDao.save(author);

		// Convert the saved entity to a DTO
		return AuthorData.builder().id(author.getId()).authorName(author.getAuthorName()).build();
	}

	// Handles GET /authors to return all authors
	@GetMapping
	public List<AuthorData> getAll() {
		return authorDao.findAll().stream()
				// Map each Author entity to a DTO
				.map(a -> AuthorData.builder().id(a.getId()).authorName(a.getAuthorName()).build()).toList();
	}
}
