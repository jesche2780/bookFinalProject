package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Book;

public interface BookDao extends JpaRepository<Book, Long> {
}
