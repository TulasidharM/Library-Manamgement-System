package com.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lms.model.Member;

public class MemberDaoImpl implements MemberDao {
    
    private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String user = "root";
    private static final String password = "Ashok@99122";

    @Override
    public void insertMember(Member newMember) {
        String query = "INSERT INTO members(Name, Email, Mobile, Gender, Address) VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newMember.getMember_Name());
            statement.setString(2, newMember.getEmail());
            statement.setInt(3, newMember.getMobile_No());
            statement.setString(4, String.valueOf(newMember.getGender()));
            statement.setString(5, newMember.getAddress());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("SQL ERROR: Failed to insert member");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Member> fetchAllMembers() {
        String query = "SELECT * FROM members;";
        List<Member> members = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet membersList = statement.executeQuery();
            
            while (membersList.next()) {
                int memberId = membersList.getInt("MemberId");
                String name = membersList.getString("Name");
                int phone = membersList.getInt("Mobile");
                String email = membersList.getString("Email");
                String address = membersList.getString("Address");
                char gender = membersList.getString("Gender").charAt(0);
                
                Member member = new Member(memberId, name, email, phone, gender,address);
                members.add(member);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return members;
    }
    
    @Override
    public void updateMember(Member member) {
        String query = "UPDATE members SET Name=?, Email=?, Mobile=?, Gender=?, Address=? WHERE MemberId=?";
        
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, member.getMember_Name());
            statement.setString(2, member.getEmail());
            statement.setInt(3, member.getMobile_No());
            statement.setString(4, String.valueOf(member.getGender()));
            statement.setString(5, member.getAddress());
            statement.setInt(6, member.getMember_Id());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update member: Member not found");
            }
            
        } catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
            e.printStackTrace();
        }
    }


	@Override
	public Member getMemberById(int memberId) {
		Member member=null;
		String query="SELECT MemberId,Name,Email,Mobile,Gender,Address FROM members WHERE MemberId=?";
		try (Connection connection = DriverManager.getConnection(url, user, password);){
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, memberId);
            ResultSet resultMember = statement.executeQuery();
            if(resultMember.next()) {
            	int member_Id = resultMember.getInt("MemberId");
                String name = resultMember.getString("Name");
                int phone = resultMember.getInt("Mobile");
                String email = resultMember.getString("Email");
                String address = resultMember.getString("Address");
                char gender = resultMember.getString("Gender").charAt(0);
                
                member = new Member(member_Id, name, email, phone, gender,address);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
}
