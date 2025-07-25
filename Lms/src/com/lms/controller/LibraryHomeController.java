package com.lms.controller;

import java.io.IOException;
import com.lms.main.Main;
import javafx.fxml.FXML;

public class LibraryHomeController {
	
	
	@FXML
	public void addBookButtonClick() throws IOException {
		Main.changePage("AddNewBook");
	}
	
	@FXML
	public void viewAllBooksClick() throws IOException{
		Main.changePage("ViewAllBooks");
	}
	
	@FXML
	public void addMemberButtonClick() throws IOException {
		Main.changePage("AddNewMember");
	}

	@FXML
	public void viewAllMembersClick() throws IOException{
		Main.changePage("ViewAllMembers");
	}
	
	@FXML
	public void issueBookButtonClick() throws IOException{
		Main.changePage("IssueBook");
	}
	
	@FXML 
	public void returnBookButtonClick() throws IOException{
		Main.changePage("ReturnBook");
	}
}
