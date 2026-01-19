package library.controller.model;

import java.util.Set;
import lombok.*;

// Data Transfer Object for returning Book information to the client
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookData {

	// Unique identifier for the book
	private Long id;

	// Title of the book
	private String title;

	// Associated author information
	private AuthorData author;

	// Borrower currently holding the book (null if not checked out)
	private BorrowerData borrower;

	// Genres assigned to the book
	private Set<GenreData> genres;
}
