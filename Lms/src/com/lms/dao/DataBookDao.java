package com.lms.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lms.model.Book;

public class DataBookDao implements BookDao {
	
	private static final String url="jdbc:mysql://localhost:3306/library";
	private static final String user="root";
	private static final String password="root@pokemon";
	
	@Override
	public Book addBook(Book newBook) {
		String query="INSERT INTO books(Title,Author,Category,BookStatus,Availability) VALUES (?,?,?,?,?);";
		try (Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setString(1,newBook.getBook_Title());
			statement.setString(2, newBook.getBook_Author());
			statement.setString(3, newBook.getBook_Category());
			statement.setString(4,String.valueOf(newBook.getBook_Status()));
			statement.setString(5, String.valueOf(newBook.getBook_Availability()));
			
			int rows_Effected=statement.executeUpdate();
			
			if(rows_Effected==0) {
				throw new SQLException("SQL ERROR: NOTHING INSERTED");
			}
			
			try (ResultSet generatedKey = statement.getGeneratedKeys()){
				if(generatedKey.next()) {
					newBook.setBook_Id(generatedKey.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return newBook;
	}


	@Override
	public List<Book> getAllBooks() {
		String query="SELECT * FROM books;";
		List<Book> books=new ArrayList<Book>();
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			ResultSet booksList=statement.executeQuery();
			while(booksList.next()) {
				int bookId=booksList.getInt("BookId");
				String title=booksList.getString("Title");
				String author=booksList.getString("Author");
				String category=booksList.getString("Category");
				char status=booksList.getString("BookStatus").charAt(0);
				char availability=booksList.getString("Availability").charAt(0);
				
				Book book=new Book(bookId, title , author, category, status, availability);
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}


	@Override
	public void updateBook(Book book) {
	    String query = "UPDATE books SET Title=?, Author=?, Category=?, BookStatus=? WHERE BookId=?";
	    try (Connection connection = DriverManager.getConnection(url, user, password)) {
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, book.getBook_Title());
	        statement.setString(2, book.getBook_Author());
	        statement.setString(3, book.getBook_Category());
	        statement.setString(4, String.valueOf(book.getBook_Status()));
	        statement.setInt(5, book.getBook_Id());
	        
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected == 0) {
	            throw new SQLException("Book update failed, no rows affected.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error updating book: " + e.getMessage());
	    }
	}

	
	public Book getBookById(int bookId) {
		Book resultedbook=null;
		String query="SELECT BookId,Title,Author,Category,BookStatus,Availability FROM books WHERE BookId=?";
		try(Connection connection=DriverManager.getConnection(url,user,password);){
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setInt(1, bookId);
			ResultSet book=statement.executeQuery();
			while(book.next()) {
				int book_Id=book.getInt("BookId");
				String title=book.getString("Title");
				String author=book.getString("Author");
				String category=book.getString("Category");
				char status=book.getString("BookStatus").charAt(0);
				char availability=book.getString("Availability").charAt(0);
				resultedbook=new Book(book_Id, title , author, category, status, availability);
			}
		}
		catch(SQLException e) {
			System.out.println("BookId Not Existed");
		}
		return resultedbook;
	}
	
	
}
