package com.lms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lms.exceptions.IdNotExistException;
import com.lms.model.Issue_Records;

public class IssueRecordDaoImpl implements IssueRecordDao {
	
	private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String user = "root";
    private static final String password = "root@pokemon";
    
	@Override
	public void addIssueRecord(Issue_Records newRecord) {
		String query="INSERT INTO issue_records(BookId,MemberId,Status,IssueDate,ReturnDate) VALUES (?,?,?,?,?);";
		 try (Connection connection = DriverManager.getConnection(url, user, password)) {
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setInt(1, newRecord.getBookId());
	            statement.setInt(2, newRecord.getMemberId());
	            statement.setString(3, String.valueOf(newRecord.getStatus()));
	            statement.setDate(4,newRecord.getIssueDate());
	            statement.setDate(5, newRecord.getReturnDate());
	            int rowsAffected = statement.executeUpdate();
	            
	            if (rowsAffected == 0) {
	                throw new SQLException("SQL ERROR: Failed to insert issueRecord");
	            }
		 } catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Issue_Records> getAllIssuedRecords() {
		String query="SELECT IssueId,BookId,MemberId,Status,IssueDate,ReturnDate FROM issue_records;";
		List<Issue_Records> records=new ArrayList<Issue_Records>();
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			ResultSet recordsList=statement.executeQuery();
			while(recordsList.next()) {
				int recordId=recordsList.getInt("IssueId");
				int bookId=recordsList.getInt("BookId");
				int memberId=recordsList.getInt("MemberId");
				char status=recordsList.getString("Status").charAt(0);
				Date issueDate=recordsList.getDate("IssueDate");
				Date returnDate=recordsList.getDate("ReturnDate");
				
				Issue_Records record=new Issue_Records(recordId, bookId , memberId, status, issueDate, returnDate);
				records.add(record);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return records;
	}
	
	@Override
	public void updateIssueRecord(int issueId , boolean isReturned) {
		
		String query="UPDATE issue_records SET Status=?,ReturnDate=? WHERE IssueId=?";
		
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setString(1, isReturned ? "R" : "I");
			statement.setInt(3, issueId);
			statement.setDate(2, isReturned? java.sql.Date.valueOf(LocalDate.now()) : null);
			statement.executeUpdate();
			
			Issue_Records record=getRecordById(issueId);
			addIssue_Records_Log(record);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void addIssue_Records_Log(Issue_Records record) {
		String query="INSERT INTO issue_records_log(IssueId,BookId,MemberId,Status,IssueDate,ReturnDate) VALUES (?,?,?,?,?,?);";
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setInt(1, record.getIssueId());
			statement.setInt(2, record.getBookId());
			statement.setInt(3, record.getMemberId());
			statement.setString(4, String.valueOf(record.getStatus()));
            statement.setDate(5,record.getIssueDate());
            statement.setDate(6, record.getReturnDate());
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Add issue_Record log failed, no rows affected.");
            } 
			
    	} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public Issue_Records getRecordById(int issueId) {
		String query="SELECT IssueId,BookId,MemberId,Status,IssueDate,ReturnDate FROM issue_records WHERE IssueId=?;";
		Issue_Records record = null;
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setInt(1, issueId);
			
			ResultSet resultRecord;
			try {
				resultRecord = statement.executeQuery();
				if(resultRecord.next()) {
					int recordId=resultRecord.getInt("IssueId");
					int bookId=resultRecord.getInt("BookId");
					int memberId=resultRecord.getInt("MemberId");
					char status=resultRecord.getString("Status").charAt(0);
					Date issueDate=resultRecord.getDate("IssueDate");
					Date returnDate=resultRecord.getDate("ReturnDate");
					
					record=new Issue_Records(recordId, bookId , memberId, status, issueDate, returnDate);
				}
				else {
					throw new IdNotExistException("Issue Id not Found");
				}
			}
			catch (IdNotExistException e) {
				System.out.println(e.getMessage());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return record;
	}
	
	

}
