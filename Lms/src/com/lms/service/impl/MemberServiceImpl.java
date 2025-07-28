package com.lms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
    	try {
    		validateMember(member);
    		memberDao.insertMember(member);
    	}catch(IllegalArgumentException e) {
    		System.out.println(e.getMessage());
    	} 
    }
    
    @Override
    public Member getMemberById(int memberId) {
	    if (memberId <= 0) {
	        throw new IllegalArgumentException("Member ID must be greater than 0");
	    }
	    
	    Member member = memberDao.getMemberById(memberId);
	    if (member == null) {
	        throw new IllegalArgumentException("No member found with ID: " + memberId);
	    }
	    
	    return member;
	}


    
	@Override
	public List<Member> getAllMembers() {
		return memberDao.fetchAllMembers();
	}
	@Override
	public String getMemberNameById(int id) {
		return memberDao.getMemberById(id).getMember_Name();
	}
	
	@Override
	public void updateMember(Member member) {
		try {
			validateMember(member);
			memberDao.updateMember(member);
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	private void validateMember(Member member) throws IllegalArgumentException {
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

	@Override
	public Member getMemberByEmail(String email) {
		
		List<Member> members = getAllMembers();
		
		members = members.stream()
				.filter(m->m.getEmail().equals(email))
				.collect(Collectors.toList());
		
		if(members == null || members.isEmpty()) {
			return null;
		}
		
		return members.get(0);
		
	}

}
