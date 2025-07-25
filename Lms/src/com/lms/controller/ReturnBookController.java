package com.lms.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.lms.dao.DataBookDao;
import com.lms.main.Main;
import com.lms.model.Issue_Records;
import com.lms.service.BookService;
import com.lms.service.IssueLogService;
import com.lms.service.MemberService;
import com.lms.service.impl.BookServiceImpl;
import com.lms.service.impl.IssueLogServiceImpl;
import com.lms.service.impl.MemberServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;

public class ReturnBookController {
	
	MemberService memberService;
	IssueLogService IssueBookService;
	BookService bookService;
	
	
	@FXML
	ComboBox<String> memberComboBox;
	
	@FXML
	ComboBox<String> bookComboBox;
	@FXML
    public void initialize() {
		memberService = new MemberServiceImpl();
		IssueBookService = new IssueLogServiceImpl();
		bookService = new BookServiceImpl();
		List<String> members= memberService.getAllMembers().stream()
								.map(m->{
									return m.getMember_Id() +" - "+ m.getMember_Name();
								})
								.collect(Collectors.toList());
		memberComboBox.getItems().addAll(members);
	}
	
	@FXML
	void cancelButtonClick(){
		try {
			Main.changePage("LibraryHome");
		} catch (IOException e) {
			System.out.println("Couldn't change to home page " + e.getMessage());
		}
	}
	
	@FXML
	void getBooksByMember(){
		if(memberComboBox.getValue() == null) {
			return;
		}
		List<String> records= IssueBookService.getAllIssuedRecords().stream()
										.filter(r->{
											int memberId = Integer.parseInt(memberComboBox.getValue().split("-")[0].trim());
											return r.getMemberId() == memberId && r.getStatus() == 'I';
										})
										.map(r->{
											return r.getIssueId()+"-"+r.getBookId()+" - " +bookService.getBookById(r.getBookId()).getBook_Title();
										})
										.collect(Collectors.toList());
		
		bookComboBox.getItems().clear();
		bookComboBox.getItems().addAll(records);
		
	}
	
	@FXML
	void returnButtonClick(){
		
		if(bookComboBox.getValue()!= null && bookComboBox.getValue()!=null) {
			int bookId = Integer.parseInt(bookComboBox.getValue().split("-")[1].trim());
			int issueId = Integer.parseInt(bookComboBox.getValue().split("-")[0].trim());
			bookService.updateBookAvailability(bookId,true);
			
			IssueBookService.returnIssuedBook(issueId, true);
			createAlert(AlertType.CONFIRMATION,"Success","Return done","The selected book has been returned and has been updated!");

			try {
				Main.changePage("LibraryHome");
			} catch (IOException e) {
				System.out.println("Error changing to home page");
			}
		}
		else {
			createAlert(AlertType.WARNING,"Alert!","Data not selected","Please select a Member and a Book");
			
		}
		
	}
	
	private void createAlert(AlertType alertType,String title,String header,String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
}
