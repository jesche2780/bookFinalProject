package library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String title;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "author_id")
	@ToString.Exclude
	private Author author;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "borrower_id")
	@ToString.Exclude
	private Borrower borrower;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	private Set<Genre> genres;
}
