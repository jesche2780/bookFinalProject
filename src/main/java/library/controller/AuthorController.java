package library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import library.controller.model.AuthorData;
import library.dao.AuthorDao;
import library.entity.Author;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	private final AuthorDao authorDao;

	public AuthorController(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	@PostMapping
	public AuthorData create(@RequestBody Map<String, Object> request) {
		Author author = new Author();
		author.setAuthorName((String) request.get("authorName"));
		author = authorDao.save(author);

		return AuthorData.builder().id(author.getId()).authorName(author.getAuthorName()).build();
	}

	@GetMapping
	public List<AuthorData> getAll() {
		return authorDao.findAll().stream()
				.map(a -> AuthorData.builder().id(a.getId()).authorName(a.getAuthorName()).build()).toList();
	}
}
