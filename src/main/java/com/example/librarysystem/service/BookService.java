package com.example.librarysystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarysystem.Exception.BookAlreadyBorrowedException;
import com.example.librarysystem.Exception.ResourceNotFoundException;
import com.example.librarysystem.model.Book;
import com.example.librarysystem.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book registerBook(Book book) {
		List<Book> existingBooks = bookRepository.findByIsbn(book.getIsbn());
		if(!existingBooks.isEmpty()) {
			book.validateConsistency(existingBooks.get(0));
			
		}
		return bookRepository.save(book);
		
	}
	
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}
	
	public Book borrowBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
		if(book.isBorrowed()){
			throw new BookAlreadyBorrowedException("Book is already borrowed");
			
		}
		book.setBorrowed(true);
		return bookRepository.save(book);
	}
	
	public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setBorrowed(false);
        return bookRepository.save(book);
    }
	

}
