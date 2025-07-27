package com.lms.dao;

import java.util.List;

import com.lms.model.Issue_Records;
import com.lms.model.OverDueList;

public interface IssueRecordDao {
	void addIssueRecord(Issue_Records newRecord);
	public void updateIssueRecord(int issueId , boolean isReturned);
	List<Issue_Records> getAllIssuedRecords();
	public void addIssue_Records_Log(Issue_Records record);
	public Issue_Records getRecordById(int issueId);
	public List<OverDueList> getOverdueRecords();
}
