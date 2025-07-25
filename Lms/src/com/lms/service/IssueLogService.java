package com.lms.service;

import com.lms.model.Issue_Records;

public interface IssueLogService {
	public void addIssueRecord(Issue_Records newRecord);
public interface IssueLogService {
	void returnIssuedBook(int issueId , int bookId);
}
