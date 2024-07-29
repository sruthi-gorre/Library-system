package com.example.librarysystem.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.librarysystem.Exception.BookAlreadyBorrowedException;
import com.example.librarysystem.Exception.ISBNConflictException;
import com.example.librarysystem.Exception.ResourceNotFoundException;
import com.example.librarysystem.model.Book;
import com.example.librarysystem.repository.BookRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testRegisterBook_NewBook() {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Sample Book");
        book.setAuthor("Author Name");

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.registerBook(book);

        assertEquals("Sample Book", savedBook.getTitle());
    }

    @Test
    public void testRegisterBook_ExistingBookWithSameISBN() {
        Book existingBook = new Book();
        existingBook.setIsbn("1234567890");
        existingBook.setTitle("Sample Book");
        existingBook.setAuthor("Author Name");

        Book newBook = new Book();
        newBook.setIsbn("1234567890");
        newBook.setTitle("Sample Book");
        newBook.setAuthor("Author Name");

        Mockito.when(bookRepository.findByIsbn("1234567890")).thenReturn(List.of(existingBook));
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);

        Book savedBook = bookService.registerBook(newBook);

        assertEquals("Sample Book", savedBook.getTitle());
    }

    @Test
    public void testRegisterBook_ExistingBookWithDifferentTitleOrAuthor() {
        Book existingBook = new Book();
        existingBook.setIsbn("1234567890");
        existingBook.setTitle("Sample Book");
        existingBook.setAuthor("Author Name");

        Book newBook = new Book();
        newBook.setIsbn("1234567890");
        newBook.setTitle("Different Title");
        newBook.setAuthor("Different Author");

        Mockito.when(bookRepository.findByIsbn("1234567890")).thenReturn(List.of(existingBook));

        ISBNConflictException exception = assertThrows(ISBNConflictException.class, () -> {
            bookService.registerBook(newBook);
        });

        assertEquals("Books with the same ISBN must have the same title and author", exception.getMessage());
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.borrowBook(1L);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testBorrowBook_BookAlreadyBorrowed() {
        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(true);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookAlreadyBorrowedException exception = assertThrows(BookAlreadyBorrowedException.class, () -> {
            bookService.borrowBook(1L);
        });

        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    public void testBorrowBook_Success() {
        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(false);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book borrowedBook = bookService.borrowBook(1L);

        assertTrue(borrowedBook.isBorrowed());
    }

    @Test
    public void testReturnBook_BookNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.returnBook(1L);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testReturnBook_Success() {
        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(true);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book returnedBook = bookService.returnBook(1L);

        assertFalse(returnedBook.isBorrowed());
    }
}
