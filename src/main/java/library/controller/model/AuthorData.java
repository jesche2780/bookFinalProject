package library.controller.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorData {
	private Long id;
	private String authorName;
}
