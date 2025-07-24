package com.lms.service.impl;

import java.util.List;

import com.lms.dao.MemberDao;
import com.lms.dao.MemberDaoImpl;
import com.lms.model.Member;
import com.lms.service.MemberService;

public class MemberServiceImpl implements MemberService{

	MemberDao memberDao;
	
	public MemberServiceImpl() {
		this.memberDao = new MemberDaoImpl();
	}
	
    @Override
    public void addNewMember(Member member) {
    	
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

       
        memberDao.insertMember(member);
    }


	@Override
	public List<Member> getAllMembers() {
		return memberDao.fetchAllMembers();
	}

}
