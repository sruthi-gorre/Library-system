package com.example.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarysystem.model.Borrower;
import com.example.librarysystem.service.BorrowerService;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

	@Autowired
    private BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {
        Borrower savedBorrower = borrowerService.registerBorrower(borrower);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBorrower);
    }
}
