package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import library.entity.Book;

// Repository interface for performing database operations on Book entities
public interface BookDao extends JpaRepository<Book, Long> {
}
