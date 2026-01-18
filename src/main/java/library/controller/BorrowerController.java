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

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

	private final BorrowerDao borrowerDao;

	public BorrowerController(BorrowerDao borrowerDao) {
		this.borrowerDao = borrowerDao;
	}

	@PostMapping
	public BorrowerData create(@RequestBody Map<String, Object> request) {
		Borrower borrower = new Borrower();
		borrower.setBorrowerName((String) request.get("borrowerName"));
		borrower = borrowerDao.save(borrower);

		return BorrowerData.builder().id(borrower.getId()).borrowerName(borrower.getBorrowerName()).build();
	}

	@GetMapping
	public List<BorrowerData> getAll() {
		return borrowerDao.findAll().stream()
				.map(b -> BorrowerData.builder().id(b.getId()).borrowerName(b.getBorrowerName()).build()).toList();
	}
}
