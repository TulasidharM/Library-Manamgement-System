package com.lms.controller;



import java.io.IOException;

import com.lms.main.Main;
import com.lms.model.Book;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewBookController {
	
	static BookService bookService;
	
	static {
		bookService = new BookServiceImpl();
		System.out.println("bookService initialized?");
		System.out.println(bookService==null ? "Null" : bookService);
		bookService.addNewBook(new Book("random","hello","Scifi"));
	}
	
	@FXML
	TextField titleField;
	@FXML
	TextField authorField;
	@FXML
	TextField categoryField;
	
	@FXML
	public void initialize() {
	}
	
	@FXML
	public void submitButtonClick() throws IOException {
		String title = titleField.getText();
		String author = authorField.getText();
		String category = categoryField.getText();
		System.out.println(title + " " + author + " " + category);
		
		Book newBook = new Book(title,author,category);
		bookService.addNewBook(newBook);
		
		Main.changePage("LibraryHome");
	}
	
	@FXML
	public void cancleButtonClick() throws IOException {
		Main.changePage("LibraryHome"); 
	}
}
