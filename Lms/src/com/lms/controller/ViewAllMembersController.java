package com.lms.controller;

import com.lms.main.Main;
import com.lms.model.Member;
import com.lms.service.MemberService;
import com.lms.service.impl.MemberServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

public class ViewAllMembersController {
	
	MemberService service;
    
    @FXML
    private TableView<Member> membersTable;
    
    @FXML
    private TableColumn<Member, String> id;
    
    @FXML
    private TableColumn<Member, String> name;
    
    @FXML
    private TableColumn<Member, String> email;
    
    @FXML
    private TableColumn<Member, String> mobile;
    
    @FXML
    private TableColumn<Member, String> gender;
    
    @FXML
    private TableColumn<Member, String> address;
    
    @FXML
    private Button backButton;
    
    private ObservableList<Member> membersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    	
    	service = new MemberServiceImpl();
    	List<Member> members = service.getAllMembers();
    	System.out.println(members == null);
    	for(Member member : members) {
    		System.out.println(member.getMember_Id());
    	}
    	updateMembersList(members);
        id.setCellValueFactory(new PropertyValueFactory<>("member_Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("member_Name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile_No"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        membersTable.setItems(membersList);

    }
    
    public void updateMembersList(List<Member> members) {
       membersList.clear();
       membersList.addAll(members);
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
