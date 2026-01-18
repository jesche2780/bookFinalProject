package library.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import library.controller.model.AuthorData;
import library.controller.model.BookData;
import library.controller.model.BorrowerData;
import library.controller.model.GenreData;
import library.dao.AuthorDao;
import library.dao.BookDao;
import library.dao.BorrowerDao;
import library.dao.GenreDao;
import library.entity.Author;
import library.entity.Book;
import library.entity.Borrower;
import library.entity.Genre;

@Service
public class BookService {

	@PersistenceContext
	private EntityManager entityManager;

	private final BookDao bookDao;
	private final AuthorDao authorDao;
	private final BorrowerDao borrowerDao;
	private final GenreDao genreDao;

	public BookService(BookDao bookDao, AuthorDao authorDao, BorrowerDao borrowerDao, GenreDao genreDao) {
		this.bookDao = bookDao;
		this.authorDao = authorDao;
		this.borrowerDao = borrowerDao;
		this.genreDao = genreDao;
	}

	// CREATE
	@Transactional
	public BookData create(Map<String, Object> request) {
		Book book = new Book();
		applyRequestToBook(book, request);
		Book saved = bookDao.save(book);
		return toData(saved);
	}

	// UPDATE
	@Transactional
	public BookData update(Long id, Map<String, Object> request) {
		Book book = bookDao.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found: " + id));

		applyRequestToBook(book, request);

		Book saved = bookDao.save(book);
		cleanupOrphans();
		return toData(saved);
	}

	// GET ALL
	@Transactional(readOnly = true)
	public Set<BookData> findAll() {
		return bookDao.findAll().stream().map(this::toData).collect(Collectors.toSet());
	}

	// GET BY ID
	@Transactional(readOnly = true)
	public BookData findById(Long id) {
		Book book = bookDao.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
		return toData(book);
	}

	// DELETE
	@Transactional
	public String delete(Long id) {
		Book book = bookDao.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found: " + id));

		String title = book.getTitle();

		book.setGenres(Collections.emptySet());
		book.setAuthor(null);
		book.setBorrower(null);

		bookDao.delete(book);
		cleanupOrphans();

		return title;
	}

	// APPLY REQUEST TO BOOK
	@SuppressWarnings("unchecked")
	private void applyRequestToBook(Book book, Map<String, Object> request) {

		// Prevent Hibernate from flushing Book before related entities are saved
		entityManager.detach(book);

		// TITLE
		if (request.containsKey("title")) {
			Object t = request.get("title");
			book.setTitle(t == null ? null : t.toString());
		}

		// AUTHOR
		if (request.containsKey("author")) {
			Object authorObj = request.get("author");

			if (authorObj == null) {
				book.setAuthor(null);
			} else {
				String name = authorObj.toString().trim();
				if (name.isEmpty()) {
					book.setAuthor(null);
				} else {
					Author author = authorDao.findAll().stream().filter(a -> a.getAuthorName().equalsIgnoreCase(name))
							.findFirst()
							.orElseGet(() -> authorDao.saveAndFlush(Author.builder().authorName(name).build()));

					book.setAuthor(author);
				}
			}
		}

		if (request.containsKey("borrower")) {
			Object borrowerObj = request.get("borrower");

			if (borrowerObj == null) {
				book.setBorrower(null);
			} else {
				String rawName = borrowerObj.toString();
				String name = (rawName == null) ? null : rawName.trim();

				if (name == null || name.isEmpty()) {
					book.setBorrower(null);
				} else {
					Borrower borrower = borrowerDao.findAll().stream()
							.filter(b -> b.getBorrowerName() != null && b.getBorrowerName().equalsIgnoreCase(name))
							.findFirst()
							.orElseGet(() -> borrowerDao.saveAndFlush(Borrower.builder().borrowerName(name).build()));

					book.setBorrower(borrower);
				}
			}
		}

		// GENRES
		if (request.containsKey("genre")) {
			Object genreObj = request.get("genre");
			Set<Genre> genres = new HashSet<>();

			if (genreObj instanceof List) {
				for (Object o : (List<?>) genreObj) {
					if (o == null)
						continue;
					String name = o.toString().trim();
					if (name.isEmpty())
						continue;

					Genre g = genreDao.findAll().stream().filter(x -> x.getGenreName().equalsIgnoreCase(name))
							.findFirst()
							.orElseGet(() -> genreDao.saveAndFlush(Genre.builder().genreName(name).build()));

					genres.add(g);
				}
			} else if (genreObj instanceof String) {
				String name = genreObj.toString().trim();
				if (!name.isEmpty()) {
					Genre g = genreDao.findAll().stream().filter(x -> x.getGenreName().equalsIgnoreCase(name))
							.findFirst()
							.orElseGet(() -> genreDao.saveAndFlush(Genre.builder().genreName(name).build()));
					genres.add(g);
				}
			}

			book.setGenres(genres);
		}
	}

	// CLEANUP ORPHANS
	private void cleanupOrphans() {

		authorDao.findAll().forEach(a -> {
			if (a.getBooks() == null || a.getBooks().isEmpty()) {
				authorDao.delete(a);
			}
		});

		borrowerDao.findAll().forEach(b -> {
			if (b.getBooks() == null || b.getBooks().isEmpty()) {
				borrowerDao.delete(b);
			}
		});

		genreDao.findAll().forEach(g -> {
			if (g.getBooks() == null || g.getBooks().isEmpty()) {
				genreDao.delete(g);
			}
		});
	}

	// ENTITY → DTO
	private BookData toData(Book book) {
		if (book == null)
			return null;

		Set<Genre> genresSnapshot;
		try {
			genresSnapshot = book.getGenres() == null ? Collections.emptySet() : new HashSet<>(book.getGenres());
		} catch (Exception e) {
			genresSnapshot = Collections.emptySet();
		}

		return BookData.builder().id(book.getId()).title(book.getTitle()).author(toData(book.getAuthor()))
				.borrower(toData(book.getBorrower()))
				.genres(genresSnapshot.stream().map(this::toData).collect(Collectors.toSet())).build();
	}

	private AuthorData toData(Author a) {
		if (a == null)
			return null;
		return AuthorData.builder().id(a.getId()).authorName(a.getAuthorName()).build();
	}

	private BorrowerData toData(Borrower b) {
		if (b == null)
			return null;
		return BorrowerData.builder().id(b.getId()).borrowerName(b.getBorrowerName()).build();
	}

	private GenreData toData(Genre g) {
		if (g == null)
			return null;
		return GenreData.builder().id(g.getId()).genreName(g.getGenreName()).build();
	}
}
