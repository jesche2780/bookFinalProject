package library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.controller.model.BorrowerData;
import library.dao.BorrowerDao;
import library.entity.Borrower;

// REST controller for managing borrowers
@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

	// DAO for performing Borrower database operations
	private final BorrowerDao borrowerDao;

	// Constructor-based dependency injection
	public BorrowerController(BorrowerDao borrowerDao) {
		this.borrowerDao = borrowerDao;
	}

	// Creates a new borrower
	@PostMapping
	public BorrowerData create(@RequestBody Map<String, Object> request) {
		// Build and save a new Borrower entity
		Borrower borrower = new Borrower();
		borrower.setBorrowerName((String) request.get("borrowerName"));
		borrower = borrowerDao.save(borrower);

		// Convert the saved entity to a DTO
		return BorrowerData.builder().id(borrower.getId()).borrowerName(borrower.getBorrowerName()).build();
	}

	// Returns all borrowers
	@GetMapping
	public List<BorrowerData> getAll() {
		return borrowerDao.findAll().stream()
				// Map each Borrower entity to a DTO
				.map(b -> BorrowerData.builder().id(b.getId()).borrowerName(b.getBorrowerName()).build()).toList();
	}
}
