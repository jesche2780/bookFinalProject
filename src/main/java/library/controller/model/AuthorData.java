package library.controller.model;

import lombok.*;

// Data Transfer Object for returning Author information to the client
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorData {

	// Unique identifier for the author
	private Long id;

	// Name of the author
	private String authorName;
}
