package com.lms.controller;

import com.lms.main.Main;
import com.lms.model.Book;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class ViewAllBooksController {
	
	BookService bookService;
    
    @FXML
    private TableView<Book> bookTable;
    
    @FXML
    private TableColumn<Book, String> idColumn;
    
    @FXML
    private TableColumn<Book, String> titleColumn;
    
    @FXML
    private TableColumn<Book, String> authorColumn;
    
    @FXML
    private TableColumn<Book, String> categoryColumn;
    
    @FXML
    private TableColumn<Book, String> statusColumn;
    
    @FXML
    private TableColumn<Book, String> availabilityColumn;
    
    @FXML
    private Button backButton;
    
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    	
    	bookService = new BookServiceImpl();
    	List<Book> books = bookService.getAllBooks();
    	//
    	System.out.println(books == null);
    	for(Book book : books) {
    		System.out.println(book.getBook_Title());
    	}
    	//
    	updateBookList(books);
    	
        // Update PropertyValueFactory to match exact property names from Book class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("book_Id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("book_Title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("book_Author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("book_Category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("book_Status"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("book_Availability"));

        bookTable.setItems(bookList);

    }
    
    public void updateBookList(List<Book> books) {
        bookList.clear();
        bookList.addAll(books);
    }
    
    
    @FXML
    private void handleBackButton() {
        try {
			Main.changePage("LibraryHome");
		} catch (IOException e) {
			System.out.println("Had a problem with going back to main: " + e.getMessage());
		}
    }
}
