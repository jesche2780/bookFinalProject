package library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

// Entity representing a borrower in the library system
@Entity
@Table(name = "borrowers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Borrower {

	// Primary key for the borrower
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	// Name of the borrower (must be unique)
	@Column(name = "borrower_name", nullable = false, unique = true)
	private String borrowerName;

	// Books currently checked out by this borrower (mapped by the 'borrower' field
	// in Book)
	@OneToMany(mappedBy = "borrower")
	@ToString.Exclude
	private List<Book> books;
}
