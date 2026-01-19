package library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

// Entity representing a genre in the library system
@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {

	// Primary key for the genre
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	// Name of the genre (must be unique)
	@Column(name = "genre_name", nullable = false, unique = true)
	private String genreName;

	// Books associated with this genre (mapped by the 'genres' field in Book)
	@ManyToMany(mappedBy = "genres")
	@ToString.Exclude
	private Set<Book> books;
}
