package com.lms.test;

import com.lms.dao.BookDao;
import com.lms.dao.DataBookDao;
import com.lms.model.Book;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.List;

public class TestBookDaoImpl {

    private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String user = "root";
    private static final String password = "root@pokemon";
    private BookDao bookDao;

    @Before
    public void setUp() {
        bookDao = new DataBookDao();
    }

    @Test
    public void testAddBook() {
        String title = "Test Book";
        String author = "Test Author";
        String category = "Test Category";
        Book newBook = new Book(title, author, category);
        Book addedBook = bookDao.addBook(newBook);
        assertNotNull("Book ID should be set after insert", addedBook.getBook_Id());
        assertTrue("Book ID should be greater than 0", addedBook.getBook_Id() > 0);
        String query="SELECT * FROM books WHERE BookId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addedBook.getBook_Id());
            ResultSet rs = stmt.executeQuery();
            assertTrue("Inserted book should exist in the DB", rs.next());
            assertEquals(title, rs.getString("Title"));
            assertEquals(author, rs.getString("Author"));
            assertEquals(category, rs.getString("Category"));
            assertEquals('A', rs.getString("BookStatus").charAt(0));
            assertEquals('A', rs.getString("Availability").charAt(0));

        } catch (SQLException e) {
            fail("SQLException during test: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetAllBooks() {
        Book testBook = new Book("JUnit Book", "JUnit Author", "Testing");

        Book insertedBook = bookDao.addBook(testBook);
        assertNotNull("Inserted book ID should not be null", insertedBook.getBook_Id());

        List<Book> books = bookDao.getAllBooks();

        assertNotNull("Book list should not be null", books);
        assertFalse("Book list should not be empty", books.isEmpty());

        boolean found = books.stream().anyMatch(book ->
            book.getBook_Id() == insertedBook.getBook_Id() &&
            "JUnit Book".equals(book.getBook_Title()) &&
            "JUnit Author".equals(book.getBook_Author()) &&
            "Testing".equals(book.getBook_Category()) &&
            book.getBook_Status() == 'A' &&
            book.getBook_Availability() == 'A'
        );

        assertTrue("Inserted test book should be in the list returned by getAllBooks", found);
    }
    
    @Test
    public void testUpdateBook() {
    	
        bookDao = new DataBookDao();
        Book originalBook = new Book("Original Title", "Original Author", "Original Category");

        Book insertedBook = bookDao.addBook(originalBook);
        assertNotNull("Inserted book ID should not be null", insertedBook.getBook_Id());

        insertedBook.setBook_Title("Updated Title");
        insertedBook.setBook_Author("Updated Author");
        insertedBook.setBook_Category("Updated Category");
        insertedBook.setBook_Status('I'); 

        bookDao.updateBook(insertedBook);
        String query = "SELECT * FROM books WHERE BookId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, insertedBook.getBook_Id());
            ResultSet rs = stmt.executeQuery();
            assertTrue("Book should exist after update", rs.next());
            assertEquals("Updated Title", rs.getString("Title"));
            assertEquals("Updated Author", rs.getString("Author"));
            assertEquals("Updated Category", rs.getString("Category"));
            assertEquals('I', rs.getString("BookStatus").charAt(0));

        } catch (SQLException e) {
            fail("SQLException during updateBook test: " + e.getMessage());
        }
    }
   
    @Test
    public void testAddBookLogs() {
    	int bookId=11;
    	Book testBook = new Book(bookId, "JUnit Book","Test Author","Action", 'A','A');
    	bookDao.addBookLogs(testBook);
    	String query = "SELECT * FROM books_log WHERE BookId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
        	PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            assertTrue("Inserted book log should exist", rs.next());
            assertEquals("JUnit Book", rs.getString("Title"));
            assertEquals("Test Author", rs.getString("Author"));
            assertEquals("Action", rs.getString("Category"));
            assertEquals('A', rs.getString("Status").charAt(0));
            assertEquals('A', rs.getString("Availability").charAt(0));
        } catch (SQLException e) {
            fail("Error verifying book log insert: " + e.getMessage());
        }
    }
    
    @Test
    public void testUpdateBookAvailability() {
    	Book testBook = new Book(150,"Test Name", "Test Author", "Test Category",'A','I');
        String addQuery = "INSERT INTO books (BookId, Title, Author, Category, BookStatus, Availability) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(addQuery)) {
            stmt.setInt(1, testBook.getBook_Id());
            stmt.setString(2, testBook.getBook_Title());
            stmt.setString(3, testBook.getBook_Author());
            stmt.setString(4, testBook.getBook_Category());
            stmt.setString(5, String.valueOf(testBook.getBook_Status()));
            stmt.setString(6, String.valueOf(testBook.getBook_Availability()));
            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            fail("insertion failed: " + e.getMessage());
        }
        bookDao.updateBookAvailability(testBook.getBook_Id(), false);
        String query = "SELECT Availability FROM books WHERE BookId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	
            stmt.setInt(1, testBook.getBook_Id());
            ResultSet rs = stmt.executeQuery();
          
            assertTrue("Book should exist in database", rs.next());
            assertEquals('I', rs.getString("Availability").charAt(0));
        } catch (SQLException e) {
            fail("Verification failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetBookById() {
        int bookId = 601;
        Book expectedBook = new Book(bookId, "GetTest Book", "GetTest Author", "Action", 'A', 'A');
        String insertQuery = "INSERT INTO books VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, expectedBook.getBook_Id());
            stmt.setString(2, expectedBook.getBook_Title());
            stmt.setString(3, expectedBook.getBook_Author());
            stmt.setString(4, expectedBook.getBook_Category());
            stmt.setString(5, String.valueOf(expectedBook.getBook_Status()));
            stmt.setString(6, String.valueOf(expectedBook.getBook_Availability()));
            stmt.executeUpdate();
        } catch (SQLException e) {
        	e.printStackTrace();
            fail("Failed to insert or update test book: " + e.getMessage());
        }
        Book book = bookDao.getBookById(bookId);
        assertNotNull("Book should not be null", book);
        assertEquals(expectedBook.getBook_Id(), book.getBook_Id());
        assertEquals(expectedBook.getBook_Title(),book.getBook_Title());
        assertEquals(expectedBook.getBook_Author(),book.getBook_Author());
        assertEquals(expectedBook.getBook_Category(),book.getBook_Category());
        assertEquals(expectedBook.getBook_Status(), book.getBook_Status());
        assertEquals(expectedBook.getBook_Availability(),book.getBook_Availability());
    }
    
}
