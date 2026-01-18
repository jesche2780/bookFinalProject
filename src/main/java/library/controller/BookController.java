package library.controller;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import library.controller.model.BookData;
import library.service.BookService;

@RestController
@RequestMapping("/library")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public BookData create(@RequestBody Map<String, Object> request) {
		return bookService.create(request);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookData> update(@PathVariable Long id, @RequestBody Map<String, Object> request) {
		try {
			BookData updated = bookService.update(id, request);
			return ResponseEntity.ok(updated);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping
	public Set<BookData> getAll() {
		return bookService.findAll();
	}

	@GetMapping("/{id}")
	public BookData getById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
		String title = bookService.delete(id);
		String msg = String.format("Book '%s' deleted successfully.", title == null ? "unknown" : title);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", msg));
	}
}
