package com.gwrocha.libary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwrocha.libary.models.Book;
import com.gwrocha.libary.repositories.BookRepository;

@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping
	public List<Book> getAll(){
		return bookRepo.findAll();
	}
	
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		bookRepo.deleteById(id);
	}
	
}
