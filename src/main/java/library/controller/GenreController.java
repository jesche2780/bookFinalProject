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

@RestController
@RequestMapping("/genres")
public class GenreController {

	private final GenreDao genreDao;

	public GenreController(GenreDao genreDao) {
		this.genreDao = genreDao;
	}

	@PostMapping
	public GenreData create(@RequestBody Map<String, Object> request) {
		Genre genre = new Genre();
		genre.setGenreName((String) request.get("genreName"));
		genre = genreDao.save(genre);

		return GenreData.builder().id(genre.getId()).genreName(genre.getGenreName()).build();
	}

	@GetMapping
	public List<GenreData> getAll() {
		return genreDao.findAll().stream()
				.map(g -> GenreData.builder().id(g.getId()).genreName(g.getGenreName()).build()).toList();
	}
}
