package com.lms.service.impl;

import java.util.List;

import com.lms.dao.BookDao;
import com.lms.exceptions.DBConstrainsException;
import com.lms.model.Book;
import com.lms.service.BookService;

public class BookServiceImpl implements BookService{
	private BookDao bookDao;
	
	@Override
	public void addNewBook(Book newBook) {
		try {
			String bookTitle=newBook.getBook_Title();
			String bookAuthor=newBook.getBook_Author();
			String bookCategory=newBook.getBook_Category();
			char bookStatus=newBook.getBook_Status();
			char bookAvailability=newBook.getBook_Availability();
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
			
			bookDao.addBook(newBook);
			
		}
		catch (DBConstrainsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public List<Book> getAllBooks() {
		
		return null;
	}
	
	
	@Override
	public void updateBookAvailability(Book book) {
		// TODO Auto-generated method stub
	}
}
