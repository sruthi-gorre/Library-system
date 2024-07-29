package com.example.librarysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarysystem.model.Borrower;
import com.example.librarysystem.repository.BorrowerRepository;

@Service
public class BorrowerService {
	
	@Autowired
	private BorrowerRepository borrowerRepository;
	
	public Borrower registerBorrower(Borrower borrower) {
		return borrowerRepository.save(borrower);
	}

}
