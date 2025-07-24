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
}
