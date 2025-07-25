package com.lms.service;

import java.util.List;

import com.lms.model.Book;

public interface BookService {
	public void addNewBook(Book newBook);
	public List<Book> getAllBooks();
	public Book getBookById(int id);
	public void updateBook(Book book);
	void updateBookAvailability(int bookId, boolean isAvailable);
}
