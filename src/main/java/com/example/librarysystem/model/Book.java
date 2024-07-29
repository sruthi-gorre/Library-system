package com.example.librarysystem.model;

import jakarta.persistence.Entity;

import com.example.librarysystem.Exception.ISBNConflictException;

import jakarta.persistence.*;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String isbn;
	private String title;
	private String author;
	private boolean isBorrowed;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public boolean isBorrowed() {
		return isBorrowed;
	}
	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	public void validateConsistency(Book existingBook) {
		if (!this.title.equals(existingBook.getTitle()) || !this.author.equals(existingBook.getAuthor())) {
            throw new ISBNConflictException("Books with the same ISBN must have the same title and author");
        }
		
	}
}
