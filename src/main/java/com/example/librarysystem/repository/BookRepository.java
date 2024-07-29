package com.example.librarysystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.librarysystem.model.Book;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long>{
	List<Book> findByIsbn(String isbn);

}
