package com.lms.controller;



import java.io.IOException;

import com.lms.exceptions.EmptyFieldsException;
import com.lms.main.Main;
import com.lms.model.Book;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddNewBookController {
	
	static BookService bookService;
	
	static {
		bookService = new BookServiceImpl();
	}
	
	@FXML
	TextField titleField;
	@FXML
	TextField authorField;
	@FXML
	ComboBox<String> categoryComboBox;
	@FXML
	Label errorLabel;
	
	@FXML
	public void initialize() {
		categoryComboBox.getItems().addAll(
	            "Fiction",
	            "Non-Fiction",
	            "Science",
	            "Technology",
	            "History",
	            "Biography"
	            
	        );
	}
	
	@FXML
	public void submitButtonClick() throws EmptyFieldsException{
		try {
			String title = titleField.getText();
			String author = authorField.getText();
			String category = categoryComboBox.getValue();
			
			if(title.trim().isEmpty() || author.trim().isEmpty() || category.isEmpty()) {
				throw new EmptyFieldsException("One or more of the fields are empty");
			}
			
			Book newBook = new Book(title,author,category);
			bookService.addNewBook(newBook);
			Main.changePage("LibraryHome");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException | EmptyFieldsException e) {
			errorLabel.setText("Please enter all values");
		}
	}
	
	@FXML
	public void cancleButtonClick() throws IOException {
		Main.changePage("LibraryHome"); 
	}
}
