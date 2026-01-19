package library.controller.model;

import lombok.*;

// Data Transfer Object for returning Genre information to the client
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreData {

	// Unique identifier for the genre
	private Long id;

	// Name of the genre
	private String genreName;
}
