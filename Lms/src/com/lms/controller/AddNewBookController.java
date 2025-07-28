package com.lms.controller;



import java.io.IOException;

import com.lms.Utils.ControllerUtil;
import com.lms.exceptions.DBConstrainsException;
import com.lms.exceptions.EmptyFieldsException;
import com.lms.main.Main;
import com.lms.model.Book;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddNewBookController {
	
	BookService bookService;
	

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
		bookService = new BookServiceImpl();

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
			if(!title.matches("^[A-Za-z]{2}[A-Za-z0-9\\s]{0,253}$")) {
				throw new DBConstrainsException("Please Enter valid title for the book");
			}
			if(!author.matches("^[A-Za-z]{2}[A-Za-z0-9\\s]{0,253}$")) {
				throw new DBConstrainsException("Please Enter valid author name for the book");
			}
			if(!category.matches("^[A-Za-z]{2}[A-Za-z0-9\\s-]{0,98}$")) {
				throw new DBConstrainsException("Please Enter valid category name for the book");
			}
			
			Book newBook = new Book(title,author,category);
			bookService.addNewBook(newBook);
			
			titleField.setText("");
			authorField.setText("");
			categoryComboBox.getSelectionModel().clearSelection();
			errorLabel.setText("");
			
			ControllerUtil.createAlert(AlertType.CONFIRMATION, "Success", "Added book", "Successfully added " + title);
			
		}  catch (NullPointerException | EmptyFieldsException | DBConstrainsException e) {
			errorLabel.setText("Had an issue with creating book " + e.getMessage());
		}
	}
	
	@FXML
	public void cancleButtonClick() throws IOException {
		Main.changePage("LibraryHome"); 
	}
}
