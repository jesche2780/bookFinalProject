package library.controller.model;

import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookData {
	private Long id;
	private String title;
	private AuthorData author;
	private BorrowerData borrower;
	private Set<GenreData> genres;
}
