package com.lms.service;

import java.util.List;
import com.lms.model.Member;

public interface MemberService {
	
	void addNewMember(Member member);
	List<Member> getAllMembers();
	void updateMember(Member member);
}
