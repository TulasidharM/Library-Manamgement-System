package com.lms.service.impl;

import com.lms.dao.IssueRecordDao;

import java.util.List;

import com.lms.dao.BookDao;
import com.lms.dao.DataBookDao;
import com.lms.dao.IssueRecordDaoImpl;
import com.lms.dao.MemberDao;
import com.lms.dao.MemberDaoImpl;
import com.lms.exceptions.IdNotExistException;
import com.lms.model.Issue_Records;
import com.lms.service.IssueLogService;

public class IssueLogServiceImpl implements IssueLogService{

	private BookDao bookDao;
	private MemberDao memberDao;
	private IssueRecordDao issueRecordDao;
	
	public IssueLogServiceImpl() {
		this.bookDao = new DataBookDao();
		this.memberDao=new MemberDaoImpl();
		this.issueRecordDao=new IssueRecordDaoImpl();
	}
	
	@Override
	public void addIssueRecord(Issue_Records newRecord) {
		try {
			if(bookDao.getBookById(newRecord.getBookId())==null) {
				throw new IdNotExistException("Entered BookId Is Invalid");
			}
			if(memberDao.getMemberById(newRecord.getMemberId())==null) {
				throw new IdNotExistException("Entered MemberId Is Invalid");
			}
			issueRecordDao.addIssueRecord(newRecord);
			bookDao.updateBookAvailability(newRecord.getBookId(), false);
		}
		catch (IdNotExistException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//TODO: add validation , make sure the record exists
	public void returnIssuedBook(int issueId, boolean isReturned) {
		issueRecordDao.updateIssueRecord(issueId, isReturned);
	}
	
	@Override
	public List<Issue_Records> getAllIssuedRecords() {
		return issueRecordDao.getAllIssuedRecords();
	}

}
