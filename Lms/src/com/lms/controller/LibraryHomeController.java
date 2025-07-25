package com.lms.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.lms.main.Main;
import com.lms.service.BookService;
import com.lms.service.impl.BookServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibraryHomeController {
    
    @FXML
    private TableView<CategoryCount> categoryTable;
    
    @FXML
    private TableColumn<CategoryCount, String> categoryColumn;
    
    @FXML
    private TableColumn<CategoryCount, Long> totalBooksColumn;
    
    private BookService bookService;
    
    public void initialize() {
        bookService = new BookServiceImpl();
        
        // Initialize table columns
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        totalBooksColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        // Load category data
        loadCategoryData();
    }
    
    private void loadCategoryData() {
        Map<String, Long> categoryMap = bookService.getBooksByCategory();
        ObservableList<CategoryCount> categoryData = FXCollections.observableArrayList();
        
        for(Entry<String, Long> entry : categoryMap.entrySet()) {
            categoryData.add(new CategoryCount(entry.getKey(), entry.getValue()));
        }
        
        categoryTable.setItems(categoryData);
    }
    
    // Helper class to hold category data
    public static class CategoryCount {
        private String category;
        private Long count;
        
        public CategoryCount(String category, Long count) {
            this.category = category;
            this.count = count;
        }
        
        public String getCategory() { return category; }
        public Long getCount() { return count; }
        
        public void setCategory(String category) { this.category = category; }
        public void setCount(Long count) { this.count = count; }
    }
    
    // Existing methods...
    @FXML
    public void addBookButtonClick() throws IOException {
        Main.changePage("AddNewBook");
    }
    
    @FXML
    public void viewAllBooksClick() throws IOException {
        Main.changePage("ViewAllBooks");
    }
    
    @FXML
    public void addMemberButtonClick() throws IOException {
        Main.changePage("AddNewMember");
    }

    @FXML
    public void viewAllMembersClick() throws IOException {
        Main.changePage("ViewAllMembers");
    }
    
    @FXML
    public void issueBookButtonClick() throws IOException {
        Main.changePage("IssueBook");
    }
    
    @FXML 
    public void returnBookButtonClick() throws IOException {
        Main.changePage("ReturnBook");
    }
    
    @FXML
    public void viewIssuedRecordsClick() throws IOException {
        Main.changePage("ViewAllissuedRecords");
    }
}
