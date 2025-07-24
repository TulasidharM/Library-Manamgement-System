package com.lms.controller;



import java.io.IOException;

import com.lms.main.Main;
import com.lms.model.Book;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewBookController {
	
	BookService bookService;
	
	@FXML
	TextField titleField;
	@FXML
	TextField authorField;
	@FXML
	TextField categoryField;
	
	@FXML
	public void intialize() {
		bookService = new BookServiceImpl();
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
