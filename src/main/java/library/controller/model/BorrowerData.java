package library.controller.model;

import lombok.*;

// Data Transfer Object for returning Borrower information to the client
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerData {

	// Unique identifier for the borrower
	private Long id;

	// Name of the borrower
	private String borrowerName;
}
