package com.kidszone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kidszone.entity.Books;
import com.kidszone.entity.User;
import com.kidszone.service.BooksService;

@RestController
@RequestMapping("/api/books")
public class BooksController {
	@Autowired
	private BooksService bookService;
	
	@GetMapping
	public ResponseEntity<List<Books>> getAllBooks(){
		List<Books> getBooks = bookService.getAllBooks();
		return new ResponseEntity<>(getBooks,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Books> getBooksById(@PathVariable long id){
		Optional<Books> user = bookService.getBooksById(id);
		return user.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
			.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/post")
	public ResponseEntity<Books> addBooks(@RequestBody Books book){
		Books addedBooks = bookService.addBooks(book);
		return new ResponseEntity<>(addedBooks,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooks(@PathVariable Long id){
		Optional<Books> existingBook = bookService.getBooksById(id);
		if(existingBook.isPresent()) {
			bookService.deleteBooks(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
//	@PutMapping("/{id}")
//	public ResponseEntity<Books> updateBooks(@PathVariable Long id, @RequestBody Books books){
//		Optional<Books> existingBooks = bookService.getBooksById(id);
//		if(existingBooks.isPresent()) {
//			books.setUserId(id);
//			User updatedBooks = bookService.updateBooks(books);
//			return new ResponseEntity<>(updatedBooks,HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//	}
}
