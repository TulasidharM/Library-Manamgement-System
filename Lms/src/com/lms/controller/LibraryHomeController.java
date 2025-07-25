package com.lms.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lms.dao.IssueRecordDao;
import com.lms.dao.IssueRecordDaoImpl;
import com.lms.main.Main;
import com.lms.model.Issue_Records;
import com.lms.model.OverDueList;
import com.lms.service.BookService;
import com.lms.service.MemberService;
import com.lms.service.impl.BookServiceImpl;
import com.lms.service.impl.MemberServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibraryHomeController {
    
	@FXML
	private TableView<OverDueList> OverDue_BooksListTABLE;
	
	@FXML
	private TableColumn<OverDueList, Long> issueId;
	
	@FXML
	private TableColumn<OverDueList, Long> bookId;
	
	@FXML
	private TableColumn<OverDueList, String> Title;
	
	@FXML
	private TableColumn<OverDueList, Long> member;
	
	@FXML
	private TableColumn<OverDueList, String> dueDate;
	
    @FXML
    private TableView<CategoryCount> categoryTable;
    
    @FXML
    private TableColumn<CategoryCount, String> categoryColumn;
    
    @FXML
    private TableColumn<CategoryCount, Long> totalBooksColumn;
    
    private ObservableList<OverDueList> OverdueList = FXCollections.observableArrayList();
    
    private BookService bookService;
    private IssueRecordDao recordDao;
    
    public void initialize() {
        bookService = new BookServiceImpl();
        recordDao=new IssueRecordDaoImpl();
        List<OverDueList> overDueRecordsList=recordDao.getOverdueRecords();
        updateOverduerecordsList(overDueRecordsList);
        issueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        member.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("overDueDate"));
        OverDue_BooksListTABLE.setItems(OverdueList);
        
        // Initialize table columns
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        totalBooksColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        // Load category data
        loadCategoryData();
    }
    
    public void updateOverduerecordsList(List<OverDueList> records) {
    	OverdueList.clear();
    	OverdueList.addAll(records);
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
