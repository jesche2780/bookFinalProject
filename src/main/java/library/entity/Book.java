package library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

// Entity representing a book in the library system
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {

	// Primary key for the book
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	// Title of the book
	private String title;

	// Author of the book (many books can share one author)
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "author_id")
	@ToString.Exclude
	private Author author;

	// Borrower currently holding the book (nullable if not checked out)
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "borrower_id")
	@ToString.Exclude
	private Borrower borrower;

	// Genres assigned to the book (many-to-many relationship)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	private Set<Genre> genres;
}
