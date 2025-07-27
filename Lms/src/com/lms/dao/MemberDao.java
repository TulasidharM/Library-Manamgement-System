package com.lms.dao;

import java.util.List;
import com.lms.model.Member;

public interface MemberDao {
	void insertMember(Member newMember);
	List<Member> fetchAllMembers();
	void updateMember(Member member);
	public void addMemberLogs(Member member);
	Member getMemberById(int memberId);
}