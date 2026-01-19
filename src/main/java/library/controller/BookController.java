package library.controller;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import library.controller.model.BookData;
import library.service.BookService;

//REST controller for managing books
@RestController
@RequestMapping("/library")
public class BookController {

	// Service layer handling book-related operations
	private final BookService bookService;

	// Constructor-based dependency injection
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	// Creates a new book
	@PostMapping
	public BookData create(@RequestBody Map<String, Object> request) {
		return bookService.create(request);
	}

	// Updates an existing book by ID
	@PutMapping("/{id}")
	public ResponseEntity<BookData> update(@PathVariable Long id, @RequestBody Map<String, Object> request) {
		try {
			BookData updated = bookService.update(id, request);
			return ResponseEntity.ok(updated);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Returns all books
	@GetMapping
	public Set<BookData> getAll() {
		return bookService.findAll();
	}

	// Returns a book by ID
	@GetMapping("/{id}")
	public BookData getById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	// Deletes a book by ID and returns a confirmation message
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
		String title = bookService.delete(id);
		String msg = String.format("Book '%s' deleted successfully.", title == null ? "unknown" : title);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", msg));
	}
}
