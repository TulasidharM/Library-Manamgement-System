package com.lms.service.impl;

import java.util.List;

import com.lms.dao.BookDao;
import com.lms.dao.DataBookDao;
import com.lms.exceptions.DBConstrainsException;
import com.lms.model.Book;
import com.lms.service.BookService;

public class BookServiceImpl implements BookService{
	private BookDao bookDao;
	
	public BookServiceImpl() {
		this.bookDao = new DataBookDao();
	}
	
	
	@Override
	public void addNewBook(Book newBook) {
		try {
			validateBook(newBook);
			bookDao.addBook(newBook);
		}
		catch (DBConstrainsException e) {
			System.out.println(e.getMessage());
		}
	}


	private void validateBook(Book book) throws DBConstrainsException {
		String bookTitle=book.getBook_Title();
		String bookAuthor=book.getBook_Author();
		String bookCategory=book.getBook_Category();
		char bookStatus=book.getBook_Status();
		char bookAvailability=book.getBook_Availability();
		
		if(bookTitle.trim()==null || bookTitle.trim().isEmpty() || bookTitle.length()>255) {
			throw new DBConstrainsException("Entered Book Name Is Invalid");
		}
		if(bookAuthor.trim()==null || bookAuthor.trim().isEmpty() || bookAuthor.length()>255) {
			throw new DBConstrainsException("Entered Author Name Is Invalid");
		}
		if(bookCategory.trim()==null || bookCategory.trim().isEmpty() || bookCategory.length()>100) {
			throw new DBConstrainsException("Entered An Invalid Book Category");
		}
		if(bookStatus== '\u0000' || (bookStatus!='A' && bookStatus!='I')) {
			throw new DBConstrainsException("Entered An Invalid Book Status");
		}
		if(bookAvailability== '\u0000' || (bookAvailability!='A' && bookAvailability!='I')) {
			throw new DBConstrainsException("Entered An Invalid Book Availability");
		}
		
	}
	
	@Override
	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	
	@Override
	public void updateBookAvailability(Book book) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void updateBook(Book book) {
		try {
			validateBook(book);
			bookDao.updateBook(book);
			
		} catch (DBConstrainsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Book getBookById(int id) {
		return bookDao.getBookById(id);
	}
}
