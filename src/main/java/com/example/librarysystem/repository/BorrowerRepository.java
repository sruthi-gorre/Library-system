package com.example.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.librarysystem.model.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

}
