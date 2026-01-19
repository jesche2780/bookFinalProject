package library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

// Entity representing an author in the library system
@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {

	// Primary key for the author
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	// Name of the author (must be unique)
	@Column(name = "author_name", nullable = false, unique = true)
	private String authorName;

	// Books written by this author (mapped by the 'author' field in Book)
	@OneToMany(mappedBy = "author")
	@ToString.Exclude
	private List<Book> books;
}
