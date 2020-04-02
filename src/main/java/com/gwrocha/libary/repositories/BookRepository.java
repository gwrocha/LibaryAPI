package com.gwrocha.libary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwrocha.libary.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	
}
