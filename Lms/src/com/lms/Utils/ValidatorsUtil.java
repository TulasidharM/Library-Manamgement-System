package com.lms.Utils;

import java.util.List;

import com.lms.dao.BookDao;
import com.lms.dao.DataBookDao;
import com.lms.dao.IssueRecordDao;
import com.lms.dao.IssueRecordDaoImpl;
import com.lms.dao.MemberDao;
import com.lms.dao.MemberDaoImpl;
import com.lms.exceptions.DBConstrainsException;
import com.lms.exceptions.IdNotExistException;
import com.lms.model.Book;
import com.lms.model.Issue_Records;
import com.lms.model.Member;


public class ValidatorsUtil {


	private static BookDao bookDao;
	private static MemberDao memberDao;
	private static IssueRecordDao issueRecordDao;

	static{
		bookDao = new DataBookDao();
		memberDao = new MemberDaoImpl();
		issueRecordDao = new IssueRecordDaoImpl();
	}
	
	public static void validateBook(Book book) throws DBConstrainsException {
		String bookTitle=book.getBook_Title();
		String bookAuthor=book.getBook_Author();
		String bookCategory=book.getBook_Category();
		char bookStatus=book.getBook_Status();
		char bookAvailability=book.getBook_Availability();
		
		if(bookTitle.trim()==null || bookTitle.trim().isEmpty() || bookTitle.length()>255) {
			throw new DBConstrainsException("Entered Book Name Is Invalid");
		}
		if(bookAuthor.trim()==null || bookAuthor.trim().isEmpty() || bookAuthor.length()>255) {
			throw new DBConstrainsException("Entered Author Name Is Invalid");
		}
		if(bookCategory.trim()==null || bookCategory.trim().isEmpty() || bookCategory.length()>100) {
			throw new DBConstrainsException("Entered An Invalid Book Category");
		}
		if(bookStatus== '\u0000' || (bookStatus!='A' && bookStatus!='I')) {
			throw new DBConstrainsException("Entered An Invalid Book Status");
		}
		if(bookAvailability== '\u0000' || (bookAvailability!='A' && bookAvailability!='I')) {
			throw new DBConstrainsException("Entered An Invalid Book Availability");
		}
	}
	
	
	public static void validateIssueRecord(Issue_Records newRecord) throws IdNotExistException {
		if(bookDao.getBookById(newRecord.getBookId())==null) {
			throw new IdNotExistException("Entered BookId Is Invalid");
		}
		if(memberDao.getMemberById(newRecord.getMemberId())==null) {
			throw new IdNotExistException("Entered MemberId Is Invalid");
		}
		
		if(newRecord.getBookId() < 0) {
			throw new IdNotExistException("Entered id(s) are not valid");

		}
	}
	
	public static void validateMember(Member member) throws IllegalArgumentException {
		if (member.getMember_Name() == null || member.getMember_Name().trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty");
        }

        if (member.getEmail() == null || !member.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (String.valueOf(member.getMobile_No()).length() != 10) {
            throw new IllegalArgumentException("Mobile number must be 10 digits");
        }
        
        if (member.getGender() != 'M' && member.getGender() != 'F' && member.getGender() != 'O') {
            throw new IllegalArgumentException("Invalid gender. Must be M, F, or O");
        }
        
        if (member.getAddress() == null || member.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
	}
}
