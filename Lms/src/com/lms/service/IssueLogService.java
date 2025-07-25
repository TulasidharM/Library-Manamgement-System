package com.lms.service;

import java.util.List;
import com.lms.model.Issue_Records;

public interface IssueLogService {
	public void addIssueRecord(Issue_Records newRecord);
	
	public List<Issue_Records> getAllIssuedRecords();
	void returnIssuedBook(int issueId , int bookId);
	public List<Issue_Records> getAllIssueRecords();
}
