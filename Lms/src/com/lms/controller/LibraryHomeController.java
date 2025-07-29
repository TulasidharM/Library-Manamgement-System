package com.lms.controller;


import java.io.IOException;

import com.lms.Utils.ControllerUtil;
import com.lms.main.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LibraryHomeController {

    @FXML 
    private VBox dynamicParent;
   
    @FXML
    public void initialize() {
    	try {
    		changePage("Reports");

    	}catch(IOException e) {
    		ControllerUtil.createAlert(AlertType.ERROR, "Navigation Fail", "Error changing page", "Couldn't change the page please try again");
    	}
    }
    
    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) return;
        Button btn = (Button) event.getSource();
        String btnText = btn.getText();
        String fxml = null;

        switch (btnText) {
            case "Home/Reports":
                fxml = "Reports";
                break;
            case "Add Book":
                fxml = "AddNewBook";
                break;
            case "View Books":
                fxml = "ViewAllBooks";
                break;
            case "Add Member":
                fxml = "AddNewMember";
                break;
            case "View Members":
                fxml = "ViewAllMembers";
                break;
            case "Issue Book":
                fxml = "IssueBook";
                break;
            case "Return Book":
                fxml = "ReturnBook";
                break;
            case "Issued Records":
                fxml = "ViewAllissuedRecords";
                break;
            case "Member's issued books":
                fxml = "MemberBooks";
                break;
            case "Book's previous borrowers":
                fxml = "BookMembers";
                break;
            default:
                return;
        }

        try {
            changePage(fxml);
        } catch (IOException e) {
        	ControllerUtil.createAlert(AlertType.ERROR, "Navigation Fail", "Error changing page", "Couldn't change the page please try again");
        }
    }
    
    

    public void changePage(String fxml) throws IOException {
    	dynamicParent.getChildren().clear();
    	dynamicParent.getChildren().add(loadFXML(fxml));
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/lms/view/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
