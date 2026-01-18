package library.controller.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerData {
	private Long id;
	private String borrowerName;
}
