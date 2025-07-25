package com.lms.service;

import java.util.List;
import com.lms.model.Issue_Records;
import com.lms.model.ReportMember;

public interface IssueLogService {
	public void addIssueRecord(Issue_Records newRecord);
	
	public List<Issue_Records> getAllIssuedRecords();
	void returnIssuedBook(int issueId , boolean isReturned);
	public List<ReportMember> booksOfMemberReport();
	
}
