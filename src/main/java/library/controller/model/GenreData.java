package library.controller.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreData {
	private Long id;
	private String genreName;
}
