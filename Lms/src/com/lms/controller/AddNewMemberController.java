package com.lms.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import com.lms.main.Main;
import com.lms.model.Member;
import com.lms.service.MemberService;
import com.lms.service.impl.MemberServiceImpl;

import javafx.event.ActionEvent;

public class AddNewMemberController {
	MemberService service;
    @FXML
    public TextField nameField;
    
    @FXML
    public TextField emailField;
    
    @FXML
    public TextField mobileField;
    
    @FXML
    public ComboBox<String> genderChoiceBox;
    
    @FXML
    public TextArea addressArea;
    
    @FXML
    public Button submitButton;
    
    @FXML
    public Button cancelButton;
    
   @FXML
    public void initialize() {
	   genderChoiceBox.getItems().addAll(
    	        "M",
    	        "F"         
    	);
	   service=new MemberServiceImpl();
    }
    
    @FXML
    public void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText().trim();
        String mobile = mobileField.getText().trim();
        String gender = genderChoiceBox.getValue();
        String address = addressArea.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }
        
        if (!isValidEmail(email)) {
            showAlert("Error", "Please enter a valid email address!");
            return;
        }
        
        if (!isValidMobile(mobile)) {
            showAlert("Error", "Please enter a valid mobile number!");
            return;
        }
        
        saveMember(name, email, mobile, gender, address);

        showAlert("Success", "Member added successfully!");

        clearForm();
    }
    
    @FXML
    public void handleCancel(ActionEvent event) {
        clearForm();
    }
    
    public void saveMember(String name, String email, String mobile, String gender, String address) {
        
        Member member=new Member(name, email, Integer.parseInt(mobile),gender.charAt(0), address);
        service.addNewMember(member);
        
        try {
			Main.changePage("LibraryHome");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void clearForm() {
        try {
			Main.changePage("LibraryHome");
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{10}");
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}