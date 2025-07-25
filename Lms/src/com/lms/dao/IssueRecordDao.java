package com.lms.dao;

import java.util.List;

import com.lms.model.Issue_Records;

public interface IssueRecordDao {
	void addIssueRecord(Issue_Records newRecord);
	
	List<Issue_Records> getAllIssuedRecords();
}
